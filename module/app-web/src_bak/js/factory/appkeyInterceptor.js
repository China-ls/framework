'use strict';


angular.module('app')
    .factory('appkeyInterceptor',['NETCONST',  function (NETCONST) {
        return {
            request: function (config) {
                config.headers = config.headers || {};
                config.headers.APPKEY = NETCONST.APPKEY;
                return config;
            }
        };
    }])
    .config(function ($httpProvider) {
        $httpProvider.interceptors.push('appkeyInterceptor');
    });