<!DOCTYPE html>
<html lang="en">
	<head>
		<title>three.js webgl - panorama</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
		<style>
			html, body {
				background-color: #000;
				margin: 0px;
				padding: 0px;
				overflow: hidden;
			}
			#info {
				position: absolute;
				top: 0px; width: 100%;
				color: #ffffff;
				padding: 5px;
				font-family:Monospace;
				font-size:13px;
				font-weight: bold;
				text-align:center;
			}
			a {
				color: #ffffff;
			}
		</style>

	</head>
	<body>
		<div id="container"></div>
		<div id="info">
			<a href="http://threejs.org" target="_blank" rel="noopener">three.js webgl</a> - cube panorama demo.
		</div>

		<script src="/js/three.min.js"></script>
		<script src="/js/OrbitControls.js"></script>

		<script>
		var camera, controls;
		var renderer;
		var scene;
		init();
		animate();
		function init() {
			var container = document.getElementById( 'container' );
			renderer = new THREE.WebGLRenderer();
			renderer.setPixelRatio( window.devicePixelRatio );
			renderer.setSize( window.innerWidth, window.innerHeight );
			container.appendChild( renderer.domElement );
			scene = new THREE.Scene();
			camera = new THREE.PerspectiveCamera( 75, window.innerWidth / window.innerHeight, 1, 1100 );
			camera.position.z = 0.01;
			controls = new THREE.OrbitControls( camera, renderer.domElement );
			controls.enableZoom = false;
			controls.enablePan = false;
			controls.enableDamping = true;
			controls.rotateSpeed = - 0.25;

			var cube = [ "/tour/2/1LEFT.jpg", "/tour/2/1RIGHT.jpg", "/tour/2/1BOTTOM.jpg", "/tour/2/1TOP.jpg", "/tour/2/1FRONT.jpg", "/tour/2/1BACK.jpg" ];
			var loader = new THREE.CubeTextureLoader();
  			var textureCube = loader.load(cube);
			var material = new THREE.MeshBasicMaterial( { color: 0xffffff, envMap: textureCube } );

			//var textures = getTexturesFromAtlasFile( "textures/cube/sun_temple_stripe.jpg", 6 );

/*
			var materials = [];
			for ( var i = 0; i < 6; i ++ ) {
				materials.push( new THREE.MeshBasicMaterial( { map: textures[ i ] } ) );
			}
*/

			var skyBox = new THREE.Mesh( new THREE.BoxBufferGeometry( 10, 10, 10 ), material );
			skyBox.geometry.scale( 1, 1, -1 );
			scene.add( skyBox );
			window.addEventListener( 'resize', onWindowResize, false );
		}


/*
		function getTexturesFromAtlasFile( atlasImgUrl, tilesNum ) {
			var textures = [];
			for ( var i = 0; i < tilesNum; i ++ ) {
				textures[ i ] = new THREE.Texture();
			}
			var imageObj = new Image();
			imageObj.onload = function () {
				var canvas, context;
				var tileWidth = imageObj.height;
				for ( var i = 0; i < textures.length; i ++ ) {
					canvas = document.createElement( 'canvas' );
					context = canvas.getContext( '2d' );
					canvas.height = tileWidth;
					canvas.width = tileWidth;
					context.drawImage( imageObj, tileWidth * i, 0, tileWidth, tileWidth, 0, 0, tileWidth, tileWidth );
					textures[ i ].image = canvas;
					textures[ i ].needsUpdate = true;
				}
			};
			imageObj.src = atlasImgUrl;
			return textures;
		}
*/


		function onWindowResize() {
			camera.aspect = window.innerWidth / window.innerHeight;
			camera.updateProjectionMatrix();
			renderer.setSize( window.innerWidth, window.innerHeight );
		}
		function animate() {
			requestAnimationFrame( animate );
			controls.update(); // required when damping is enabled
			renderer.render( scene, camera );
		}
		</script>
	</body>
</html>