'use strict';

/* Controllers */

// ManageRouterCtrl controller
app.controller('ManageRouterEmployeeTitleCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.app.subHeader.contentTitle = $scope.$stateParams.create ? '添加人员' : '修改人员';
        $scope.app.settings.asideHide = true;
        $scope.app.subHeader.goBackHide = false;
    }]
);
app.controller('ManageRouterEmployeeCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        if (!$localStorage[APPCONST.APP_LOCAL_STORAGE_MANAGE_EMPLOYEE]) {
            $state.go('app.mr');
            return;
        }
        $scope.stateParams = $localStorage.__manageEmployeeStateParams;

        console.warn($scope.stateParams);

        $scope.today = function () {
            $scope.dt = new Date();
        };
        $scope.today();

        $scope.clear = function () {
            $scope.dt = null;
        };

        // Disable weekend selection
        $scope.disabled = function (date, mode) {
            return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
        };

        $scope.toggleMin = function () {
            $scope.minDate = $scope.minDate ? null : new Date();
        };
        $scope.toggleMin();

        $scope.open = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.opened = true;
        };
        $scope.initDate = new Date('2016-15-20');
        $scope.dateOptions = {
            formatYear: 'yy',
            startingDay: 1,
            class: 'datepicker'
        };
        $scope.format = 'yyyy-MM-dd';

    }]
);