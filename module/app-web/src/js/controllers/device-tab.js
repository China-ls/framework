'use strict';

// DeviceDetail controller
app.controller('DeviceTabCtrl', ['$scope', function ($scope) {
    $scope.app.subHeader.goBackHide = false;
    $scope.app.subHeader.goBackSref = 'app.device';
}]);