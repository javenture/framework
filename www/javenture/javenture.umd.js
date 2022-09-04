(function (root, factory)
{
    if (typeof define === 'function' && define.amd) define(['vue'], factory);
    else root.Javenture = factory(root.Vue);
} (this, function (Vue) {
	'use strict';

	//
	Vue = Vue && Vue.hasOwnProperty('default') ? Vue['default'] : Vue;

	//
	var MODULE = 'Javenture';

	function get(object, property)
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
	}

	function set(object, property, value)
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
	}

	function equal(object1, object2)
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
					if (!equal(object1[i], object2[i])) return false;
				}

				return true;
			}
		}

		return false;
	}


	/*
		vue
	 */
	function location(value)
	{
		if (typeof value === 'object')
		{
			var destination = value.destination;
			var redirect = value.redirect;

			if (typeof destination !== 'undefined')
			{
				var result = location0(destination);

				if (typeof redirect !== 'undefined')
				{
					if (typeof result.query === 'undefined') result.query = [];

					result.query.push
					(
						{ name: "REDIRECT", value: getUrl(location0(redirect)) }
					);
				}

				//console.info('url: ' + getUrl(result));            // XXX: del
				window.location = getUrl(result);
			}
		}
		else if (typeof value === 'string')
		{
			window.location = value;
		}
		else
		{
			console.error('[' + MODULE + '] unsupported type: ' + (typeof value));
		}
	}

	function location0(object)
	{
		var result = {};

		//
		var url = object.url;

		{
			result.scheme = url.scheme;
			result.host = url.host;
			result.port = url.port;
			result.absolute = url.absolute;
			result.path = url.path;
			result.file = url.file;
			result.extension = url.extension;
			result.query = url.query;
			result.fragment = url.fragment;
		}

		//
		var parameters = object.parameters;

		if (typeof parameters !== 'undefined')
		{
			if (typeof result.query === 'undefined') result.query = [];

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
					(typeof init === 'undefined' || !equal(init, value))
				)
				{
					result.query.push({ name: name, value: value });
				}
			}
		}

		return result;
	}

	function exchange(application, config)
	{
		var backend = config.backend;
		var request = config.request;
		var response = config.response;
		var redirect = config.redirect;
		var confirmation = config.confirmation;                          // XXX: object (title, message, okLabel, cancelLabel)
		var handler = config.handler;

		//
		if
		(
			typeof confirmation === 'undefined' ||
			typeof confirmation === 'string' && confirm(confirmation)
		)
		{
			var form = new FormData();

			if (typeof request !== 'undefined')
			{
				for (var i = 0, length = request.length; i < length; i++)
				{
					if (typeof request[i].value === 'object' && request[i].name === 'REDIRECT')                                              // XXX: !!!!!!!!!!!!!
					{
						form.append(request[i].name, getUrl(location0(request[i].value)));
					}
					else
					{
						form.append(request[i].name, request[i].value);
					}
				}
			}

			fetch
			(
				getUrl(location0(backend)),
				{
					method: 'POST',
					credentials: 'same-origin',
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
					var redirect2 = json.redirect;

					//
					if (typeof response !== 'undefined')
					{
						if (typeof data !== 'undefined')
						{
							for (var i = 0, length = response.length; i < length; i++)
							{
								var name = response[i].name;
								var init = response[i].init;

								if
								(
									typeof name !== 'undefined' &&
									typeof init !== 'undefined'
								)
								{
									if (typeof get(application, name) !== 'undefined')
									{
										var value = get(data, name);

										if (typeof value !== 'undefined') set(application, name, value);
										else set(application, name, init);
									}
									else
									{
										console.error('[' + MODULE + '] undefined property: ' + name);
									}
								}
							}
						}
						else
						{
							for (var i = 0, length = response.length; i < length; i++)
							{
								var name = response[i].name;
								var init = response[i].init;

								if
								(
									typeof name !== 'undefined' &&
									typeof init !== 'undefined'
								)
								{
									if (typeof get(application, name) !== 'undefined') set(application, name, init);
									else console.error('[' + MODULE + '] undefined property: ' + name);
								}
							}
						}
					}

					if (status === 'OK')
					{
						if (typeof redirect !== 'undefined')
						{
							if (typeof redirect === 'object') window.location = getUrl(location0(redirect));
							else if (typeof redirect === 'string') window.location = redirect;
							else console.error('[' + MODULE + '] unsupported type: ' + (typeof redirect));
						}
						else if (typeof redirect2 !== 'undefined')
						{
							if (typeof redirect2 === 'object') window.location = getUrl(location0(redirect2));
							else if (typeof redirect2 === 'string') window.location = redirect2;
							else console.error('[' + MODULE + '] unsupported type: ' + (typeof redirect2));
						}

						if (typeof notification !== 'undefined')
						{
							Quasar.plugins.Notify.create({ message: notification, type: 'info' });                   // XXX: ?
						}
					}
					else if (status === 'ERROR')
					{
						if (typeof notification !== 'undefined')
						{
							Quasar.plugins.Notify.create({ message: notification, type: 'negative' });                   // XXX: ?
						}
					}
					else
					{
						console.error('[' + MODULE + '] unsupported status: ' + status);
					}

					// handler
					if (typeof handler !== 'undefined') handler();


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
		}
	}

	var URL =
	{
		REGEX: /^(?:([^:\/#?]+:)?(?:(?:\/\/)?(?:(?:[^:@\/#?]+)(?::(?:[^:@\/#?]+))?@)?([^:\/#?\]\[]+|\[[^\/\]@#?]+])?(?::([\d]+))?)?)?(\/?(?:[^/?#]+\/+)*)?([^./?#][^/?]+?)?(?:\.([^.?#]*))?(?:\?([^#]*))?(?:#(.*))?$/,

		GROUP_SCHEME: 1,
		GROUP_HOST: 2,
		GROUP_PORT: 3,
		GROUP_PATH: 4,
		GROUP_FILE: 5,
		GROUP_EXTENSION: 6,
		GROUP_QUERY: 7,
		GROUP_FRAGMENT: 8
	};

	function parseUrl(string)
	{
		var matches = URL.REGEX.exec(string);

		var scheme = matches[URL.GROUP_SCHEME];
		var host = matches[URL.GROUP_HOST];
		var port = matches[URL.GROUP_PORT];
		var absolute;                                                 // XXX: ?
		var path;
		var file = matches[URL.GROUP_FILE];
		var extension = matches[URL.GROUP_EXTENSION];
		var query;
		var fragment = matches[URL.GROUP_FRAGMENT];

		{
			var p = matches[URL.GROUP_PATH];

			if (typeof p !== 'undefined')
			{
				absolute = p.charAt(0) === '/';
				var first = absolute ? 1 : 0;
				var last = p.length - 1;
				path = first < last ? p.substring(first, last).split('/') : [];
			}
		}

		{
			var q = matches[URL.GROUP_QUERY];

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
	}

	function getUrl(object)
	{
		var scheme = object.scheme;
		var host = object.host;
		var port = object.port;
		var absolute = object.absolute;                               // XXX: ?
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

		if (typeof query !== 'undefined' && query.length !== 0)
		{
			result += '?';

			for (var i = 0, length = query.length, last = length - 1; i < length; i++)
			{
				var name = query[i].name;
				var value = query[i].value;
				result += name + (value.length !== 0 ? '=' + encodeURIComponent(value) : '') + (i < last ? ';' : '');                           // XXX: delimiter - '&' | ';' ?
			}
		}

		if (typeof fragment !== 'undefined') result += '#' + fragment;

		return result;
	}

	function getParameterFromUrl(object, name)
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


	/*
		quasar
	 */
	function getScroll()
	{
		return Quasar.utils.scroll.getScrollPosition(window);
	}

	function setScroll(value)
	{
		Quasar.utils.scroll.setScrollPosition(window, value);
	}


	//
	var util = Object.freeze
	({
		get: get,
		set: set,
		equal: equal
	});

	var url = Object.freeze
	({
		parse: parseUrl,
		get: getUrl,
		parameter: getParameterFromUrl

	});

	var vue = Object.freeze
	({
		location: location,
	    exchange: exchange
	});

	var quasar = Object.freeze
	({
		getScroll: getScroll,
		setScroll: setScroll
	});

	//
	var umd =
	{
		util: util,
		url: url,
		vue: vue,
		quasar: quasar
	};

    return umd;
}));
