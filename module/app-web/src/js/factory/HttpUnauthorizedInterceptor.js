'use strict';

angular.module('app')
    .factory('HttpInterceptor', ['$q', 'APPCONST', '$location', function ($q, APPCONST, $location) {
        return {
            request: function (config) {
                return config;
            },
            requestError: function (err) {
                return $q.reject(err);
            },
            response: function (res) {
                return res;
            },
            responseError: function (err) {
                if (-1 === err.status || 401 === err.status) {
                    var ctx = APPCONST.CTX;
                    if (ctx.indexOf('http') == -1 && ctx.indexOf('https') == -1) {
                        ctx = $location.protocol + '://' + $location.host + ':' + $location.port + ctx + '/access/signup';
                    }
                    ctx = 'http://localhost:63342/app-web/src/index.html#/access/signup';
                    window.location.href = ctx;
                    window.navigate(ctx);
                    self.location = ctx;
                    top.location = ctx;
                }
                return $q.reject(err);
            }
        };
    }])
    .config(function ($httpProvider) {
        $httpProvider.interceptors.push('HttpInterceptor');
    });