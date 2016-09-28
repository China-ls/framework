'use strict';

// DeviceDetail controller
app.controller('DeviceTabCtrl', ['$scope', '$stateParams', '$state', '$http', 'APPCONST', '$log',
    function ($scope, $stateParams, $state, $http, APPCONST, $log) {
        // console.warn( $scope.$state );
        $scope.app.subHeader.goBackHide = false;
        $scope.app.subHeader.goBackSref = 'app.device';
    }]
)
;