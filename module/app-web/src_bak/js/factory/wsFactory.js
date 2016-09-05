'use strict';

angular.module('app')
    .factory('socket', ['socketFactory', function (socketFactory) {
        return socketFactory({
            ioSocket: io.connect('ws://120.55.165.5:9998/app/ws')
        });
    }])
;