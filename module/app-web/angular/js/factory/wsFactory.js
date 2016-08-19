'use strict';


angular.module('app')
    .factory('WS', ['$websocket', function ($websocket) {
        // Open a WebSocket connection
        var dataStream = $websocket('ws://127.0.0.1:9090/app/ws');
        var collection = [];

        dataStream.onMessage(function (message) {
            collection.push(JSON.parse(message.data));
        });

        var methods = {
            collection: collection,
            get: function () {
                dataStream.send(JSON.stringify({action: 'get'}));
            }
        };

        return methods;
    }])
;