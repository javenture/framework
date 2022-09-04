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

			.info {
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

		<div class="info">
			<span onclick="getTexturesBackward()"><-</span> &nbsp;&nbsp;&nbsp; <span onclick="getTexturesAll()">***</span> &nbsp;&nbsp;&nbsp; <span onclick="getTexturesForward()">-></span>
		</div>

		<script src="/js/three.min.js"></script>
		<script src="/js/OrbitControls.js"></script>

		<script>
			var camera, controls;
			var renderer;
			var scene;

			var cube = 1024;
			var tour = -1;
			var last = 90;
			var contexts = [];
			var textures = [];
			var images = [];

			init();
			animate();

			function init()
			{
				var container = document.getElementById('container');
				renderer = new THREE.WebGLRenderer();
				renderer.setPixelRatio(window.devicePixelRatio);
				renderer.setSize(window.innerWidth, window.innerHeight);
				container.appendChild(renderer.domElement);
				scene = new THREE.Scene();
				camera = new THREE.PerspectiveCamera(90, window.innerWidth / window.innerHeight, 0.1, 100);
				camera.position.z = 0.01;
				controls = new THREE.OrbitControls(camera, renderer.domElement);
				controls.enableZoom = false;
				controls.enablePan = false;
				controls.enableDamping = true;
				controls.rotateSpeed = -0.25;

				for (var i = 0; i < 6; i++)
				{
					//
					var canvas = document.createElement('canvas');
					canvas.height = cube;
					canvas.width = cube;
					contexts[i] = canvas.getContext('2d', { alpha: false });

					//
					textures[i] = new THREE.CanvasTexture(canvas);

					//
					var image = new Image();

					(
						function (img, number)
						{
							img.onload = function ()
							{
								contexts[number].drawImage(img, 0, 0, cube, cube, 0, 0, cube, cube);
								textures[number].needsUpdate = true;
							};
						}
					)
					(image, i);

					images[i] = image;
				}

				var materials = [];

				for (var i = 0; i < 6; i++)
				{
					materials.push(new THREE.MeshBasicMaterial( { map: textures[i] } ));
				}

				var skyBox = new THREE.Mesh(new THREE.BoxBufferGeometry(1, 1, 1), materials);
				skyBox.geometry.scale(1, 1, -1);
				scene.add(skyBox);
				window.addEventListener('resize', onWindowResize, false);

				getTextures2();
			}


			function getTexturesAll()
			{
				tour = -1;

				for (var i = 0; i < 91; i++)
				{
					setTimeout(function ()
					{
						getTexturesForward()
					}, i * 5000);
				}
			}

			function getTexturesForward()
			{
				var names = [ 'RIGHT', 'LEFT', 'TOP', 'BOTTOM', 'FRONT', 'BACK' ];

				//
				var prefix = ++tour;

				if (prefix > last)
				{
					tour = -1;
					prefix = 0;
				}

				console.info("getTextures2: " + prefix);

				//
				for (var i = 0; i < 6; i++)
				{
					images[i].src = '/tour/2/' + prefix + names[i] + '.jpg';
				}
			}

			function getTexturesBackward()
			{
				var names = [ 'RIGHT', 'LEFT', 'TOP', 'BOTTOM', 'FRONT', 'BACK' ];

				//
				var prefix = --tour;

				if (prefix < 0)
				{
					tour = last + 1;
					prefix = last;
				}

				console.info("getTextures2: " + prefix);

				//
				for (var i = 0; i < 6; i++)
				{
					images[i].src = '/tour/2/' + prefix + names[i] + '.jpg';
				}
			}













			function getTextures2()
			{
				var names = [ 'RIGHT', 'LEFT', 'TOP', 'BOTTOM', 'FRONT', 'BACK' ];

				//
				var prefix = ++tour;

				if (prefix > last) prefix = 0;

				console.info("getTextures2: " + prefix);

				//
				for (var i = 0; i < 6; i++)
				{
					(
						function (number)
						{
							var imageObj = new Image();

							imageObj.onload = function ()
							{
								contexts[number].drawImage(imageObj, 0, 0, cube, cube, 0, 0, cube, cube);
								textures[number].needsUpdate = true;
								imageObj = null;
							};

							imageObj.src = '/tour/2/' + prefix + names[number] + '.jpg';


	/*
							return function ()
							{
								imageObj0.onload = function ()
								{
									contexts[number].drawImage(imageObj0, 0, 0, cube, cube, 0, 0, cube, cube);
									textures[number].needsUpdate = true;
								};

								imageObj0.src = '/tour/2/output/' + prefix + name[0] + '.jpg';
							}
	*/
						}
					)
					(i);

				}
			}










			function getTextures()
			{
				var name = [ 'RIGHT', 'LEFT', 'TOP', 'BOTTOM', 'FRONT', 'BACK' ];
				var textures = [];

				//
				var prefix = tour++;

				if (prefix > last) prefix = 0;

				// RIGHT
				{
					var texture0 = new THREE.Texture();
					var imageObj0 = new Image();

					imageObj0.onload = function ()
					{
						var canvas = document.createElement('canvas');
						var context = canvas.getContext('2d');
						canvas.height = cube;
						canvas.width = cube;
						context.drawImage(imageObj0, 0, 0, cube, cube, 0, 0, cube, cube);
						texture0.image = canvas;
						texture0.needsUpdate = true;
					};

					imageObj0.src = '/tour/2/' + prefix + name[0] + '.jpg';
					textures[0] = texture0;
				}

				// LEFT
				{
					var texture1 = new THREE.Texture();
					var imageObj1 = new Image();

					imageObj1.onload = function ()
					{
						var canvas = document.createElement('canvas');
						var context = canvas.getContext('2d');
						canvas.height = cube;
						canvas.width = cube;
						context.drawImage(imageObj1, 0, 0, cube, cube, 0, 0, cube, cube);
						texture1.image = canvas;
						texture1.needsUpdate = true;
					};

					imageObj1.src = '/tour/2/' + prefix + name[1] + '.jpg';
					textures[1] = texture1;
				}

				// TOP
				{
					var texture2 = new THREE.Texture();
					var imageObj2 = new Image();

					imageObj2.onload = function ()
					{
						var canvas = document.createElement('canvas');
						var context = canvas.getContext('2d');
						canvas.height = cube;
						canvas.width = cube;
						context.drawImage(imageObj2, 0, 0, cube, cube, 0, 0, cube, cube);
						texture2.image = canvas;
						texture2.needsUpdate = true;
					};

					imageObj2.src = '/tour/2/' + prefix + name[2] + '.jpg';
					textures[2] = texture2;
				}

				// BOTTOM
				{
					var texture3 = new THREE.Texture();
					var imageObj3 = new Image();

					imageObj3.onload = function ()
					{
						var canvas = document.createElement('canvas');
						var context = canvas.getContext('2d');
						canvas.height = cube;
						canvas.width = cube;
						context.drawImage(imageObj3, 0, 0, cube, cube, 0, 0, cube, cube);
						texture3.image = canvas;
						texture3.needsUpdate = true;
					};

					imageObj3.src = '/tour/2/' + prefix + name[3] + '.jpg';
					textures[3] = texture3;
				}

				// FRONT
				{
					var texture4 = new THREE.Texture();
					var imageObj4 = new Image();

					imageObj4.onload = function ()
					{
						var canvas = document.createElement('canvas');
						var context = canvas.getContext('2d');
						canvas.height = cube;
						canvas.width = cube;
						context.drawImage(imageObj4, 0, 0, cube, cube, 0, 0, cube, cube);
						texture4.image = canvas;
						texture4.needsUpdate = true;
					};

					imageObj4.src = '/tour/2/' + prefix + name[4] + '.jpg';
					textures[4] = texture4;
				}

				// BACK
				{
					var texture5 = new THREE.Texture();
					var imageObj5 = new Image();

					imageObj5.onload = function ()
					{
						var canvas = document.createElement('canvas');
						var context = canvas.getContext('2d');
						canvas.height = cube;
						canvas.width = cube;
						context.drawImage(imageObj5, 0, 0, cube, cube, 0, 0, cube, cube);
						texture5.image = canvas;
						texture5.needsUpdate = true;
					};

					imageObj5.src = '/tour/2/' + prefix + name[5] + '.jpg';
					textures[5] = texture5;
				}

				return textures;
			}

			function onWindowResize()
			{
				camera.aspect = window.innerWidth / window.innerHeight;
				camera.updateProjectionMatrix();
				renderer.setSize(window.innerWidth, window.innerHeight);
			}

			function animate()
			{
				requestAnimationFrame(animate);
				controls.update(); // required when damping is enabled
				renderer.render(scene, camera);
			}
		</script>
	</body>
</html>