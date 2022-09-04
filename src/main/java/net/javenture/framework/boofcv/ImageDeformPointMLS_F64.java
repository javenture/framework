package net.javenture.framework.boofcv;


/*
 * Copyright (c) 2011-2020, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://boofcv.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import boofcv.alg.distort.mls.TypeDeformMLS;
import boofcv.struct.distort.Point2Transform2_F64;
import georegression.struct.point.Point2D_F64;
import org.ddogleg.struct.DogArray;
import org.ddogleg.struct.DogArray_F64;
import org.ejml.data.DMatrix2x2;

import java.util.Arrays;

/**
 * <p>Implementation of 'Moving Least Squares' (MLS) control point based image deformation models described in [1].</p>
 *
 * Usage Procedure:
 * <ol>
 *     <li>Invoke {@link #configure}</li>
 *     <li>Invoke {@link #addControl} for each control point</li>
 *     <li>Invoke {@link #setDistorted} to change the distorted location of a control point</li>
 *     <li>Invoke {@link #fixate()} when all control points have been added and after you are done changing distorted locations</li>
 * </ol>
 *
 * <p>Each control point has an undistorted and distorted location.  The fixate functions are used to precompute
 * different portions of the deformation to maximize speed by avoiding duplicate computations. Instead of computing
 * a distortion for each pixel a regular grid is used instead.  Pixel points are interpolated between grid points
 * using bilinear interpolation.
 * </p>
 *
 * <p>This should be a faithful implementation of MLS.  Potential deviations listed below:</p>
 * <ol>
 * <li>Pixels should be adjusted when converting to grid coordinates to maintain the same
 * aspect ratio as the input image.  This way the results is "independent" of the internal grids shape/size.
 * [1] does not mention this issue.</li>
 * <li>When compared against images published in [1] the rigid transform appears slightly different.  However,
 * when compared against other implementations those appear to produce nearly identical results to this
 * implementation.</li>
 * </ol>
 *
 * <p>[1] Schaefer, Scott, Travis McPhail, and Joe Warren. "Image deformation using moving least squares."
 * ACM transactions on graphics (TOG). Vol. 25. No. 3. ACM, 2006.</p>
 *
 * @author Peter Abeles
 */
public class ImageDeformPointMLS_F64 implements Point2Transform2_F64
{
	// control points that specify the distortion
	DogArray<Control> controls = new DogArray<>(Control::new);

	// size of interpolation grid
	int gridRows,gridCols;
	// points inside interpolation grid
	DogArray<Point2D_F64> deformationGrid = new DogArray<>(Point2D_F64::new);

	// DESIGN NOTE:  Because the aspect ratio is maintained it's likely that some points in the grid are unreachable
	//               a small speed boost could be brought about by adjusting the grid size so that the minimum number
	//               of cells are used

	// parameter used to adjust how distance between control points is weighted
	double alpha = 3.0/2.0;

	// scale between image and grid, adjusted to ensure aspect ratio doesn't change
	double scaleX,scaleY;

	// Pixel distortion model
	Model model;

	//--------------------------- Internal Workspace --------------------------------------------
	DogArray_F64 weights = new DogArray_F64(); // weight of each control point
	DogArray<DMatrix2x2> matrices = new DogArray<>(DMatrix2x2::new);
	DogArray_F64 A = new DogArray_F64(); // As as the variable 'A' in the paper
	Point2D_F64 aveP = new Point2D_F64();  // average control point for given weights
	Point2D_F64 aveQ = new Point2D_F64();  // average distorted point for given weights
	double totalWeight;
	double mu;                              // mu for simularity

	public ImageDeformPointMLS_F64( TypeDeformMLS type ) {
		switch( type ) {
			case AFFINE: model = new AffineModel(); break;
			case SIMILARITY: model = new SimilarityModel(); break;
			case RIGID: model = new RigidModel(); break;
			default:
				throw new RuntimeException("Unknown model type "+type);
		}
	}

	protected ImageDeformPointMLS_F64(){}

	/**
	 * Discards all existing control points
	 */
	public void reset() {
		controls.reset();
	}

	/**
	 * Specifies the input image size and the size of the grid it will use to approximate the idea solution. All
	 * control points are discarded
	 *
	 * @param width Image width
	 * @param height Image height
	 * @param gridRows grid rows
	 * @param gridCols grid columns
	 */
	public void configure( int width , int height , int gridRows , int gridCols ) {
		scaleX = width/(double)(gridCols-1);
		scaleY = height/(double)(gridRows-1);

		this.gridRows = gridRows;
		this.gridCols = gridCols;

		deformationGrid.resize(gridCols*gridRows);
		reset();



/*
		// need to maintain the same ratio of pixels in the grid as in the regular image for similarity and rigid
		// to work correctly
		int s = Math.max(width,height);
		scaleX = s/(double)(gridCols-1);
		scaleY = s/(double)(gridRows-1);
		if( gridRows > gridCols ) {
			scaleY /= (gridCols-1)/ (double) (gridRows-1);
		} else {
			scaleX /= (gridRows-1)/ (double) (gridCols-1);
		}

		this.gridRows = gridRows;
		this.gridCols = gridCols;

		deformationGrid.resize(gridCols*gridRows);
		reset();
*/
	}

	/**
	 * Adds a new control point at the specified location.  Initially the distorted and undistorted location will be
	 * set to the same
	 *
	 * @param x coordinate x-axis in image pixels
	 * @param y coordinate y-axis in image pixels
	 * @return Index of control point
	 */
	public int addControl( double x , double y ) {
		Control c = controls.grow();
		c.q.setTo(x,y);
		setUndistorted(controls.size-1,x,y);
		return controls.size-1;
	}

	/**
	 * Sets the location of a control point.
	 * @param x coordinate x-axis in image pixels
	 * @param y coordinate y-axis in image pixels
	 */
	public void setUndistorted(int which, double x, double y) {
		if( scaleX <= 0 || scaleY <= 0 )
			throw new IllegalArgumentException("Must call configure first");

		controls.get(which).p.setTo(x/scaleX,y/scaleY);
	}

	/**
	 * Function that let's you set control and undistorted points at the same time
	 * @param srcX distorted coordinate
	 * @param srcY distorted coordinate
	 * @param dstX undistorted coordinate
	 * @param dstY undistorted coordinate
	 * @return Index of control point
	 */
	public int add( double srcX , double srcY , double dstX , double dstY )
	{
		int which = addControl(srcX,srcY);
		setUndistorted(which,dstX,dstY);
		return which;
	}

	/**
	 * Sets the distorted location of a specific control point
	 * @param which Which control point
	 * @param x distorted coordinate x-axis in image pixels
	 * @param y distorted coordinate y-axis in image pixels
	 */
	public void setDistorted( int which , double x , double y ) {
		controls.get(which).q.setTo(x,y);
	}

	/**
	 * Precompute the portion of the equation which only concerns the undistorted location of each point on the
	 * grid even the current undistorted location of each control point.

	 * Recompute the deformation of each point in the internal grid now that the location of control points is
	 * not changing any more.
	 */
	public void fixate() {
		if( controls.size < 2 )
			throw new RuntimeException("Not enough control points specified.  Found "+controls.size);
		model.allocate(weights,A,matrices);
		for (int row = 0; row < gridRows; row++) {
			for (int col = 0; col < gridCols; col++) {

				double v_x = col;
				double v_y = row;

				computeWeights(v_x, v_y,weights.data);
				computeAverageP( weights.data);
				computeAverageQ( weights.data);

				model.computeIntermediate( v_x, v_y);
				model.computeDeformed(col, row, getGrid(row,col));
			}
		}
	}

	/**
	 * Computes the average P given the weights at this cached point
	 */
	void computeAverageP(double[] weights) {
		double x = 0;
		double y = 0;

		for (int i = 0; i < controls.size; i++) {
			Control c = controls.get(i);
			double w = weights[i];
			x += c.p.x * w;
			y += c.p.y * w;
		}
		aveP.setTo(x / totalWeight, y / totalWeight);
	}

	/**
	 * Computes the average Q given the weights at this cached point
	 */
	void computeAverageQ(double[] weights) {
		double x = 0;
		double y = 0;

		for (int i = 0; i < controls.size; i++) {
			Control c = controls.get(i);
			double w = weights[i];
			x += c.q.x * w;
			y += c.q.y * w;
		}
		aveQ.setTo(x / totalWeight, y / totalWeight);
	}

	/**
	 * Computes the weight/influence of each control point when distorting point v.
	 * @param v_x undistorted grid coordinate of cached point.
	 * @param v_y undistorted grid coordinate of cached point.
	 */
	void computeWeights(double v_x, double v_y, double[] weights) {
		// first compute the weights
		double totalWeight = 0.0;
		for (int i = 0; i < controls.size; i++) {
			Control c = controls.get(i);

			double d2 = c.p.distance2(v_x, v_y);
			// check for the special case
			if( d2 == 0 ) {
				Arrays.fill(weights, 0);
				weights[i] = 1;
				totalWeight = 1.0;
				break;
			} else {
				totalWeight += weights[i] = 1.0/Math.pow(d2,alpha);
			}
		}
		this.totalWeight = totalWeight;
	}

	@Override
	public void compute(double x, double y, Point2D_F64 out) {
		interpolateDeformedPoint(x/scaleX, y/scaleY, out);
	}

	@Override
	public ImageDeformPointMLS_F64 copyConcurrent() {
		ImageDeformPointMLS_F64 out = new ImageDeformPointMLS_F64();
		out.controls = controls;
		out.gridRows = gridRows;
		out.gridCols = gridCols;
		out.deformationGrid = deformationGrid;
		out.model = model;
		out.scaleX = scaleX;
		out.scaleY = scaleY;
		out.alpha = alpha;
		return out;
	}

	/**
	 * Samples the 4 grid points around v and performs bilinear interpolation
	 *
	 * @param v_x Grid coordinate x-axis, undistorted
	 * @param v_y Grid coordinate y-axis, undistorted
	 * @param deformed Distorted grid coordinate in image pixels
	 */
	void interpolateDeformedPoint(double v_x , double v_y , Point2D_F64 deformed ) {

		// sample the closest point and x+1,y+1
		int x0 = (int)v_x;
		int y0 = (int)v_y;
		int x1 = x0+1;
		int y1 = y0+1;

		// make sure the 4 sample points are in bounds
		if( x1 >= gridCols )
			x1 = gridCols-1;
		if( y1 >= gridRows )
			y1 = gridRows-1;

		// weight along each axis
		double ax = v_x - x0;
		double ay = v_y - y0;

		// bilinear weight for each sample point
		double w00 = (1.0 - ax) * (1.0 - ay);
		double w01 = ax * (1.0 - ay);
		double w11 = ax * ay;
		double w10 = (1.0 - ax) * ay;

		// apply weights to each sample point
		Point2D_F64 d00 = getGrid(y0,x0);
		Point2D_F64 d01 = getGrid(y0,x1);
		Point2D_F64 d10 = getGrid(y1,x0);
		Point2D_F64 d11 = getGrid(y1,x1);

		deformed.setTo(0,0);
		deformed.x += w00 * d00.x;
		deformed.x += w01 * d01.x;
		deformed.x += w11 * d11.x;
		deformed.x += w10 * d10.x;

		deformed.y += w00 * d00.y;
		deformed.y += w01 * d01.y;
		deformed.y += w11 * d11.y;
		deformed.y += w10 * d10.y;
	}

	/**
	 * Returns distorted control point in the deformation grid
	 */
	Point2D_F64 getGrid(int row , int col ) {
		return deformationGrid.data[row*gridCols + col];
	}

	/**
	 * See paper section 2.1
	 */
	public class AffineModel implements Model {

		@Override
		public void allocate(DogArray_F64 weights, DogArray_F64 A, DogArray<DMatrix2x2> matrices) {
			weights.resize(controls.size);
			A.resize(controls.size);
			matrices.resize(controls.size);
		}

		@Override
		public void computeIntermediate(double v_x, double v_y) {
			// compute the weighted covariance 2x2 matrix
			// Two below equation 5
			// sum hat(p[i])'*w[i]*hat(p[i])
			double inner00 = 0, inner01 = 0, inner11 = 0;

			for (int i = 0; i < controls.size; i++) {
				Control c = controls.get(i);
				double w = weights.data[i];

				double hat_p_x = c.p.x-aveP.x;
				double hat_p_y = c.p.y-aveP.y;

				inner00 += hat_p_x*hat_p_x*w;
				inner01 += hat_p_y*hat_p_x*w;
				inner11 += hat_p_y*hat_p_y*w;
			}

			// invert it using minor equation
			double det = (inner00*inner11 - inner01*inner01);

			if( det == 0.0 ) {
				// see if a control point and grid point are exactly the same
				if( inner00 == 0.0 && inner11 == 0.0 ) {
					det = 1.0;
				} else {
					throw new RuntimeException("Insufficient number of or geometric diversity in control points");
				}
			}

			double inv00 =  inner11 / det;
			double inv01 = -inner01 / det;
			double inv11 =  inner00 / det;

			// Finally compute A[i] for each control point
			// (v-p*)
			double v_m_ap_x = v_x - aveP.x;
			double v_m_ap_y = v_y - aveP.y;
			double tmp0 = v_m_ap_x * inv00 + v_m_ap_y * inv01;
			// (v-p*)*inv(stuff)
			double tmp1 = v_m_ap_x * inv01 + v_m_ap_y * inv11;

			for (int i = 0; i < controls.size; i++) {
				Control c = controls.get(i);

				double hat_p_x = c.p.x-aveP.x;
				double hat_p_y = c.p.y-aveP.y;

				// mistake in paper that w[i] was omitted?
				A.data[i] = (tmp0 * hat_p_x + tmp1 * hat_p_y)*weights.data[i];
			}
		}

		@Override
		public void computeDeformed(double v_x, double v_y, Point2D_F64 deformed) {
			deformed.setTo(0,0);

			final int totalControls =  controls.size;
			for (int i = 0; i < totalControls; i++) {
				Control c = controls.data[i];
				final double a = A.data[i];
				deformed.x += a*(c.q.x-aveQ.x);
				deformed.y += a*(c.q.y-aveQ.y);
			}
			deformed.x += aveQ.x;
			deformed.y += aveQ.y;
		}
	}

	/**
	 * See paper section 2.2
	 */
	public class SimilarityModel implements Model
	{
		@Override
		public void allocate(DogArray_F64 weights, DogArray_F64 A, DogArray<DMatrix2x2> matrices) {
			weights.resize(controls.size);
			matrices.resize(controls.size);
		}

		@Override
		public void computeIntermediate(double v_x, double v_y) {
			final double[] weights = ImageDeformPointMLS_F64.this.weights.data;
			mu = 0;

			// mu = sum{ w[i]*dot( hat(p). hat(p) ) }
			// A[i] = w[i]*( hat(p); hat(p^|) )( v-p*; -(v-p*)^|)'
			// where ^| means perpendicular to vector
			final int totalControls =  controls.size;
			for (int i = 0; i < totalControls; i++) {
				Control c = controls.get(i);
				double w = weights[i];

				double hat_p_x = c.p.x-aveP.x;
				double hat_p_y = c.p.y-aveP.y;

				mu += w*(hat_p_x*hat_p_x + hat_p_y*hat_p_y);

				double v_ps_x = v_x - aveP.x;
				double v_ps_y = v_y - aveP.y;

				DMatrix2x2 m = matrices.get(i);

				m.a11 = w*(hat_p_x*v_ps_x + hat_p_y*v_ps_y);
				m.a12 = w*(hat_p_x*v_ps_y - hat_p_y*v_ps_x);
				m.a21 = -m.a12;
				m.a22 = m.a11;
			}
			// point being sampled and the key point are exactly the same
			if( mu == 0.0 )
				mu = 1.0;
		}

		@Override
		public void computeDeformed(double v_x, double v_y, Point2D_F64 deformed) {
			deformed.setTo(0,0);

			final int totalControls =  controls.size;
			for (int i = 0; i < totalControls; i++) {
				Control c = controls.get(i);

				DMatrix2x2 m = matrices.data[i];
				double hat_q_x = c.q.x-aveQ.x;
				double hat_q_y = c.q.y-aveQ.y;

				deformed.x += hat_q_x*m.a11 + hat_q_y*m.a21;
				deformed.y += hat_q_x*m.a12 + hat_q_y*m.a22;
			}
			deformed.x = deformed.x/mu + aveQ.x;
			deformed.y = deformed.y/mu + aveQ.y;
		}
	}

	/**
	 * Paper section 2.3
	 */
	public class RigidModel extends SimilarityModel {

		@Override
		public void computeDeformed(double v_x, double v_y, Point2D_F64 deformed) {

			// f_r[v] equation just above equation 8
			double fr_x = 0, fr_y = 0;
			for (int i = 0; i < controls.size; i++) {
				Control c = controls.get(i);

				double hat_q_x = c.q.x - aveQ.x;
				double hat_q_y = c.q.y - aveQ.y;

				DMatrix2x2 m = matrices.get(i);
				fr_x += (hat_q_x*m.a11 + hat_q_y*m.a21);
				fr_y += (hat_q_x*m.a12 + hat_q_y*m.a22);
			}

			// equation 8
			double v_avep_x = v_x - aveP.x;
			double v_avep_y = v_y - aveP.y;

			double norm_fr = Math.sqrt(fr_x*fr_x + fr_y*fr_y);
			double norm_vp = Math.sqrt(v_avep_x*v_avep_x + v_avep_y*v_avep_y);

			// point being sampled and the key point are exactly the same
			if( norm_fr == 0.0 && norm_vp == 0.0 ) {
				deformed.x = aveQ.x;
				deformed.y = aveQ.y;
			} else {
				double scale = norm_vp / norm_fr;

				deformed.x = scaleX * fr_x * scale + aveQ.x;
				deformed.y = scaleY * fr_y * scale + aveQ.y;
			}
		}
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * Distortion model interface
	 */
	private interface Model {
		/** Pre-allocates the size of each of these arrays */
		void allocate( DogArray_F64 weights, DogArray_F64 A , DogArray<DMatrix2x2> matrices );

		/** Computes intermediate results needed for the distortion*/
		void computeIntermediate(double v_x, double v_y);

		/** Computes the deformation at each control point */
		void computeDeformed(double v_x, double v_y, Point2D_F64 deformed);
	}

	public static class Control {
		/**
		 * Control point location in grid coordinates
		 */
		Point2D_F64 p = new Point2D_F64();
		/**
		 * Deformed control point location in image pixels
		 */
		Point2D_F64 q = new Point2D_F64();
	}
}
