'use strict';

// DeviceTabMovieCtrl controller
app.controller('DeviceTabMovieCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.stateParams = $localStorage[APPCONST.APP_LOCAL_STORAGE_SELECT_DEVICE];
        if (!$scope.stateParams) {
            $state.go('app.device');
            return;
        }
        $scope.imageListSlideInterval = 10000;
        $scope.imageUrl = APPCONST.CTX + APPCONST.SENSOR_DATA_IMAGE;

        $scope.loadDataPromise = $http.get(
            APPCONST.CTX + APPCONST.SENSOR_DATA_IMAGE_LIST.replace("{id}", $scope.stateParams.id))
            .then(function (response) {
                $scope.imageList = response.data.data;
                if ($scope.imageList) {
                    for (var i = 0; i < $scope.imageList.length; i++) {
                        $scope.imageList[i].time = $scope.formatDate(new Date($scope.imageList[i].time), "yyyy年MM月dd日HH:mm:ss")
                    }
                    $scope.imageListItemSelect(0);
                }
            });

        $scope.imageListItemSelect = function (index) {
            if ($scope.imageListSelectItem) {
                $scope.imageListSelectItem.active = false;
            }
            $scope.imageListSelectIndex = index;
            $scope.imageListSelectItem = $scope.imageList[index];
            $scope.imageListSelectItem.active = true;
        };
    }])
;