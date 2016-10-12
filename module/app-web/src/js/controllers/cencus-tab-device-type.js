'use strict';

// DeviceDetail controller
app.controller('CencusDeviceTypeTitleCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.app.settings.asideHide = true;
        $scope.app.subHeader.goBackHide = true;
        $scope.app.subHeader.contentTitle = '分类统计';
    }]);

app.controller('CencusDeviceTypeCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {

    }])
;