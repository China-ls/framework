'use strict';

/* Controllers */

// DashbordCtrl controller
app.controller('DashbordCtrl', ['$scope', '$http', 'GLOBAL', '$localStorage',
    function ($scope, $http, GLOBAL, $localStorage) {
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
        // $scope.$on(GLOBAL.BROADCASE.ON.ASIDE_CLICK_DPT, function (event, data) {
        //     console.warn("on");
        // });
        // $scope.$on(GLOBAL.BROADCASE.ON.ASIDE_CLICK_DEVICE, function (event, data) {
        //     console.warn("on");
        // });
    }])
;