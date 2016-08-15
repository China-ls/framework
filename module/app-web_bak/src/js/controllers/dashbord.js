'use strict';

/* Controllers */

// DashbordCtrl controller
app.controller('DashbordCtrl', ['$scope', '$http', '$localStorage',
    function ($scope, $http, $localStorage) {
        console.warn(window);
        $scope.overlays = [];
        $scope.offlineOpts = {retryInterval: 5000};
        $scope.mapOptions = {
            center: {
                longitude: 121.60398,
                latitude: 31.214385
            },
            zoom: 17,
            city: 'ShangHai',
            enableMapClick: false,
            clientHeight: $scope.baidumapeight,
            overlays: $scope.overlays
        };
    }])
;