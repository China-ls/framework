'use strict';

/* Controllers */

// ManageRouterCtrl controller
app.controller('ManageRouterEmployeeTitleCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.stateParams = $localStorage[APPCONST.APP_LOCAL_STORAGE_MANAGE_EMPLOYEE];
        $scope.app.subHeader.contentTitle = (!$scope.stateParams || $scope.stateParams.create) ? '添加人员' : '修改人员';
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
        $scope.stateParams = $localStorage[APPCONST.APP_LOCAL_STORAGE_MANAGE_EMPLOYEE];

        $scope.open = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.opened = true;
        };
        $scope.dateOptions = {
            formatYear: 'yy',
            startingDay: 1,
            class: 'datepicker'
        };
        $scope.format = 'yyyy-MM-dd';

        $scope.submitform = function () {
            console.warn($scope.stateParams.node);

            if (!$scope.user || !$scope.user.name || !$scope.user.birthdayTime
                || !$scope.user.sex || !$scope.user.identity || !$scope.user.address
                || !$scope.user.phone || !$scope.user.type) {
                return;
            }
            if ($scope.user.birthdayTime) {
                $scope.user.birthday = $scope.user.birthdayTime.getTime();
            }
            var param = {
                pid: $scope.stateParams.node.id,
                ppath: $scope.stateParams.node.path,
                pl: $scope.stateParams.node.level
            };
            angular.extend(param, $scope.stateParams.node, $scope.user);
            $http.post(APPCONST.CTX + APPCONST.EMP_DEPARTMENT_ADD_EMP, param)
                .then(function (response) {
                    if( response.data.data.code === '0') {
                        $scope.Toast('success', '提示', '人员添加成功。');
                        $state.go('app.mr');
                    }
                });
        };
    }]
);