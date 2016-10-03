'use strict';

/* Controllers */

// ManageRouterCtrl controller
app.controller('EmployeeDutyTitleCtrl', ['$scope', '$http', '$localStorage', '$modal', 'APPCONST', '$state',
    function ($scope, $http, $localStorage, $modal, APPCONST, $state) {
        $scope.app.subHeader.contentTitle = '职能修改';
        $scope.app.subHeader.goBackHide = false;
        $scope.app.subHeader.goBackSref = 'app.employee';
        $scope.app.settings.asideHide = true;
    }]);

app.controller('EmployeeDutyCtrl', ['$scope', '$http', '$localStorage', '$modal', 'APPCONST', '$state',
    function ($scope, $http, $localStorage, $modal, APPCONST, $state) {
        $scope.duty = {type: 1};

        $scope.mapOptions = {
            center: {
                longitude: 120.95281,
                latitude: 30.883874
            },
            zoom: 17,
            city: 'JiaShan',
            enableMapClick: false,
            clientHeight: 300,
            overlays: $scope.overlays
        };

    }]
);