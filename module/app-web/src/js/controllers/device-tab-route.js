'use strict';

// DeviceDetail controller
app.controller('DeviceTabRouteCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.app.subHeader.goBackHide = false;
        $scope.app.subHeader.goBackSref = 'app.device';

        if (!$scope.$stateParams) {
            $state.go('app.device');
            return;
        }

        $scope.dataType = 1;
        $scope.selectDataType = function (type) {
            $scope.dataType = type;
        };

        $scope.walk = [
            {time: '2016年09月10日10:34:12', name: '张大磊', stay: '5分5秒', content: '例行巡检', status: '有效'},
            {time: '2016年09月02日14:23:25', name: '张大磊', stay: '12分45秒', content: '例行巡检', status: '有效'},
            {time: '2016年09月02日08:43:56', name: '张大磊', stay: '7分23秒', content: '例行巡检', status: '有效'},
        ];
    }])
;