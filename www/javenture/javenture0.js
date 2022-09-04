function JaventureUtil()
{
	var JAVENTURE = 'Javenture';
	var application = this;

	return {
		get: function (object, property)
		{
			var result;
			var array = property.split('.');
			var length = array.length;

			if (length !== 0)
			{
				result = object[array[0]];

				for (var i = 1; i < length; i++)
				{
					if (typeof result === 'undefined') break;

					result = result[array[i]];
				}
			}

			return result;
		},

		set: function (object, property, value)
		{
			var array = property.split('.');
			var length = array.length;

			if (length !== 0)
			{
				for (var i = 0; i < length - 1; i++)
				{
					if (typeof object === 'undefined') break;

					object = object[array[i]];
				}

				if (typeof object !== 'undefined') object[array[length - 1]] = value;
			}
		},

		toggle: function (object)                                                                  // XXX: ?
		{
			application[object] = !application[object];
		},

		equal: function (object1, object2)
		{
			if (object1 === object2)
			{
				return true;
			}
			else if (Array.isArray(object1) && Array.isArray(object2))
			{
				var length = object1.length;

				if (object2.length !== length)
				{
					return false;
				}
				else
				{
					for (var i = 0; i < length; i++)
					{
						if (!this.equal(object1[i], object2[i])) return false;
					}

					return true;
				}
			}

			return false;
		},

		location: function (object)                                   // XXX: vue.location()
		{
			var destination = object.destination;
			var redirect = object.redirect;

			if (typeof destination !== 'undefined')
			{
				var result = this.location0(destination);

				if (typeof redirect !== 'undefined')
				{
					var query = typeof result.query !== 'undefined' ? result.query : [];

					query.push
					(
						{ name: "REDIRECT", value: this.getUrl(this.location0(redirect)) }
					);
				}

				console.info('url: ' + this.getUrl(result));            // XXX: del
				window.location = this.getUrl(result);
			}
		},

		location0: function (object)                                // XXX: vue.getUrl()
		{
			var url = object.url;
			var parameters = object.parameters;

			var query = typeof url.query !== 'undefined' ? url.query : [];

			if (typeof parameters !== 'undefined')
			{
				for (var i = 0, length = parameters.length; i < length; i++)
				{
					var parameter = parameters[i];
					var name = parameter.name;
					var value = parameter.value;
					var init = parameter.init;

					if
					(
						typeof name !== 'undefined' &&
						typeof value !== 'undefined' &&
						(typeof init === 'undefined' || !this.equal(init, value))
					)
					{
						query.push({ name: name, value: value });
					}
				}
			}

			url.query = query;

			return url;
		},

		exchange: function (config)
		{
			var bundle = this;

			var url = config.url;
			var redirect = config.redirect;
			var request = config.request;
			var response = config.response;

			//
			var form = new FormData();

			if (request)
			{
				var length = request.length;

				for (var i = 0; i < length; i++) form.append(request[i].name, request[i].value);
			}

			fetch
			(
				url,
				{
					method: 'POST',
					body: form
				}
			)
			.then
			(
				function (response)
				{
					return response.json();
				}
			)
			.then
			(
				function (json)
				{
					var status = json.status;
					var data = json.data;
					var notification = json.notification;

					//
					if (response && data)
					{
						var length = response.length;

						for (var i = 0; i < length; i++)
						{
							var name = response[i].name;
							var init = response[i].init;
							var value = bundle.get(data, name);

							if (typeof value !== 'undefined' && !bundle.equal(value, init))
							{
								if (typeof bundle.get(application, name) !== 'undefined') bundle.set(application, name, value);
								else console.error('[' + JAVENTURE + ']' + ' undefined property: ' + name);
							}
						}
					}

					//if (status === 'OK' && redirect) window.location = redirect;

					if (notification) Quasar.plugins.Notify.create(notification);                   // XXX: ?
				}
			)
			.catch
			(
				function(error)
				{
					console.error(error);

					Quasar.plugins.Notify.create({ message: 'Error', type: 'negative' });
				}
			);
		},


		//
		parseUrl: function(string)                                                   // XXX: url.parse()
		{
			var regx = /^(?:([^:\/#?]+:)?(?:(?:\/\/)?(?:(?:[^:@\/#?]+)(?::(?:[^:@\/#?]+))?@)?([^:\/#?\]\[]+|\[[^\/\]@#?]+])?(?::([\d]+))?)?)?(\/?(?:[^/?#]+\/+)*)?([^./?#][^/?]+?)?(?:\.([^.?#]*))?(?:\?([^#]*))?(?:#(.*))?$/;
			var SCHEME = 1;
			var HOST = 2;
			var PORT = 3;
			var PATH = 4;
			var FILE = 5;
			var EXTENSION = 6;
			var QUERY = 7;
			var FRAGMENT = 8;

			var matches = regx.exec(string);

			var scheme = matches[SCHEME];
			var host = matches[HOST];
			var port = matches[PORT];
			var absolute;                                                 // XXX: ?
			var path;
			var file = matches[FILE];
			var extension = matches[EXTENSION];
			var query;
			var fragment = matches[FRAGMENT];

			{
				var p = matches[PATH];

				if (typeof p !== 'undefined')
				{
					absolute = p.charAt(0) === '/';
					var first = absolute ? 1 : 0;
					var last = p.length - 1;
					path = first < last ? p.substring(first, last).split('/') : [];
				}
			}

			{
				var q = matches[QUERY];

				if (typeof q !== 'undefined')
				{
					query = [];
					var parameters;

					if (q.indexOf(';') !== -1) parameters = q.split(';');
					else if (q.indexOf('&') !== -1) parameters = q.split('&');
					else parameters = [ q ];

					for (var i = 0, length = parameters.length; i < length; i++)
					{
						var parameter = parameters[i];
						var delimiter = parameter.indexOf('=');
						var name = delimiter !== -1 ? parameter.substring(0, delimiter) : parameter;
						var value = delimiter !== -1 ? parameter.substring(delimiter + 1) : '';

						if (name.length !== 0) query.push({ name: name, value: value });
					}
				}
			}

			//
			var result = {};

			{
				if (typeof scheme !== 'undefined') result.scheme = scheme;

				if (typeof host !== 'undefined') result.host = host;

				if (typeof port !== 'undefined') result.port = port;

				if (typeof absolute !== 'undefined') result.absolute = absolute;

				if (typeof path !== 'undefined') result.path = path;

				if (typeof file !== 'undefined') result.file = file;

				if (typeof extension !== 'undefined') result.extension = extension;

				if (typeof query !== 'undefined') result.query = query;

				if (typeof fragment !== 'undefined') result.fragment = fragment;
			}

			return result;
		},

		getUrl: function (object)                                                     // XXX: url.get()
		{
			var scheme = object.scheme;
			var host = object.host;
			var port = object.port;
			var absolute = object.absolute;
			var path = object.path;
			var file = object.file;
			var extension = object.extension;
			var query = object.query;
			var fragment = object.fragment;

			var result = '';

			if (typeof scheme !== 'undefined') result += scheme;

			if (typeof host !== 'undefined')
			{
				result += '//' + host;

				if (typeof port !== 'undefined') result += ':' + port;
			}

			if (typeof absolute !== "undefined" && absolute) result += '/';

			if (typeof path !== 'undefined')
			{
				for (var i = 0, length = path.length; i < length; i++) result += path[i] + "/";
			}

			if (typeof file !== 'undefined') result += file;

			if (typeof extension !== 'undefined') result += '.' + extension;

			if (typeof query !== 'undefined')
			{
				result += '?';

				for (var i = 0, length = query.length, last = length - 1; i < length; i++)
				{
					var name = query[i].name;
					var value = query[i].value;
					result += name + (value.length !== 0 ? '=' + encodeURIComponent(value) : '') + (i < last ? ';' : '');
				}
			}

			if (typeof fragment !== 'undefined') result += '#' + fragment;

			return result;
		},

		getParameterFromUrl: function (object, name)                                                // XXX: url.parameter(object, name)
		{
			var query = object.query;

			if (typeof query !== 'undefined')
			{
				for (var i = 0, length = query.length; i < length; i++)
				{
					if (query[i].name === name) return query[i].value;
				}
			}
		}


	}
}
