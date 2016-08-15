/*!
 * JavaScript - loadGoogleMaps( version, apiKey, language )
 *
 * - Load Google Maps API using jQuery Deferred. 
 *   Useful if you want to only load the Google Maps API on-demand.
 * - Requires jQuery 1.5
 * 
 * Copyright (c) 2011 Glenn Baker
 * Dual licensed under the MIT and GPL licenses.
 */
/*globals window, google, jQuery*/
var loadBaiduMaps = (function ($) {
    var now = $.now(),
        promise;

    return function (apiKey, version) {
        if (promise) {
            return promise;
        }

        //Create a Deferred Object
        var deferred = $.Deferred(),
            //Declare a resolve function, pass google.maps for the done functions
            resolve = function () {
                deferred.resolve(window.BMap ? window.BMap : false);
            },

            //global callback name
            callbackName = "loadBaiduMaps_" + ( now++ ),

            // Default Parameters
            params = $.extend(
                apiKey ? {"ak": apiKey} : {}
                , version ? {"v": version} : {}
            );
        ;

        //If google.maps exists, then Google Maps API was probably loaded with the <script> tag
        if (window.BMap) {
            resolve();
        } else {
            //Ajax URL params
            params = $.extend(params, {
                'v': version || '2.0',
                'callback': callbackName
            });

            //Declare the global callback
            window[callbackName] = function () {
                resolve();
                //Delete callback
                setTimeout(function () {
                    try {
                        delete window[callbackName];
                    } catch (e) {
                    }
                }, 20);
            };
            //Can't use the jXHR promise because 'script' doesn't support 'callback=?'
            $.ajax({
                dataType: 'script',
                data: params,
                url: 'http://api.map.baidu.com/api'
            });
        }
        promise = deferred.promise();
        return promise;
    };
}(jQuery));
