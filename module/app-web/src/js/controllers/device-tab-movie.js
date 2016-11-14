'use strict';

// DeviceTabMovieCtrl controller
app.controller('DeviceTabMovieCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST', '$timeout',
    function ($scope, $http, $localStorage, $state, APPCONST, $timeout) {
        $scope.app.subHeader.goBackHide = false;
        $scope.app.subHeader.goBackSref = 'app.device';

        $scope.sensor_id = $localStorage.selectDeviceId;
        if (!$scope.sensor_id) {
            $state.go('app.device');
            return;
        }
        $scope.app.subHeader.contentTitle = $localStorage.selectDeviceName;

        $scope.startDate = new Date();
        $scope.start = $scope.formatDate($scope.startDate, 'yyyy-MM-dd');
        $scope.endDate = new Date();
        $scope.endDate.setDate($scope.endDate.getDate() + 1);
        $scope.end = $scope.formatDate($scope.endDate, 'yyyy-MM-dd');

        $scope.imageListSlideInterval = 10000;
        $scope.imageUrl = APPCONST.CTX + APPCONST.SENSOR_DATA_IMAGE;
        $scope.imageactive = 0;

        $scope.slider = {
            index: 0,
            timeArray: [],
            options: {
                floor: 0,
                ceil: 1,
                translate: function (value) {
                    if (value >= $scope.slider.timeArray.length) {
                        return '';
                    }
                    return $scope.slider.timeArray[value];
                },
                onChange: function (sliderId, modelValue, highValue, pointerType) {
                    $scope.imageactive = modelValue;
                    angular.forEach($scope.imageList, function (item, index) {
                        item.active = $scope.imageactive == index;
                    });
                }
            }
        };

        $scope.$watch('imageList', function (nv) {
            angular.forEach(nv, function (item, index) {
                if (item.active) {
                    $scope.imageactive = index;
                    $scope.slider.index = index;
                }
            });
        }, true);

        $scope.loadImageData = function () {
            $scope.start = $scope.formatDate($scope.startDate, 'yyyy-MM-dd');
            $scope.end = $scope.formatDate($scope.endDate, 'yyyy-MM-dd');
            $scope.loadDataPromise = $http.post(
                APPCONST.CTX + APPCONST.SENSOR_DATA_IMAGE_LIST.replace("{id}", $scope.sensor_id),
                {start: $scope.start, end: $scope.end}
            )
                .then(function (response) {
                    // console.warn(response);
                    var resp = response.data.data;
                    $scope.device = resp.device;
                    $scope.imageList = resp.data;
                    if ($scope.imageList) {
                        for (var i = 0; i < $scope.imageList.length; i++) {
                            var time = $scope.formatDate(new Date($scope.imageList[i].time), "yyyy年MM月dd日HH:mm:ss");
                            $scope.imageList[i].time = time;
                            $scope.imageList[i].dataType = 0;
                            $scope.slider.timeArray.push(time);
                        }
                        $scope.slider.index = 0;
                        $scope.slider.options.ceil = $scope.imageList.length - 1;

                        // $scope.imageListItemSelect(0);
                    }
                });
        };

        $scope.imageListItemSelect = function (index) {
            if ($scope.imageListSelectItem) {
                $scope.imageListSelectItem.active = false;
            }
            $scope.imageListSelectIndex = index;
            $scope.imageListSelectItem = $scope.imageList[index];
            $scope.imageListSelectItem.active = true;
        };

        $scope.takepicTimer = null;
        $scope.isTakingPick = false;

        $scope.takepick = function () {
            if ($scope.isTakingPick) {
                return;
            }
            $scope.isTakingPick = true;
            $timeout.cancel($scope.takepicTimer);
            var id = null;
            angular.forEach($scope.device.components, function (comp) {
                if (comp.type === 'camera_controller') {
                    id = comp.comp_id;
                }
            });
            $scope.wsSend('{"channel_id" : "' + id + '","operation" : "take_photo","param" : 1,"sensor_id" : "' + $scope.device.sensor_id + '"}');
            $scope.takepicTimer = $timeout(function () {
                $scope.isTakingPick = false;
                $scope.Toast('warning', '提示', '拍照失败，拍照指令数据未返回!');
            }, 80000);
        };

        $scope.loadImageData();

        $scope.$on("WS_MESSAGE", function (event, data) {
            // console.warn(data);
            try {
                var isMinePic = false, isNowPic = false;
                angular.forEach(data, function (item) {
                    if (item.sensor_id != $scope.device.sensor_id
                        || item.comp_type != 'camera_controller') {
                        return;
                    }
                    if (!item.image) {
                        return;
                    }
                    isMinePic = true;
                    // if ($scope.start > item.time || $scope.end < item.time) {
                    //     return;
                    // }
                    // isNowPic = true;
                    item.time = $scope.formatDate(new Date(item.time), "yyyy年MM月dd日HH:mm:ss");
                    item.dataType = 1;
                    $scope.imageList.splice(0, 0, item);
                });
                //不是本设备的图片
                if (!isMinePic) {
                    return;
                }
                if ($scope.isTakingPick) {
                    $timeout.cancel($scope.takepicTimer);
                    $scope.Toast('success', '提示', '拍照成功!');
                    $scope.isTakingPick = false;
                }
                //不需要添加到界面上，因为不是当前查询间隔的位置
                // if (!isNowPic) {
                //     return;
                // }
                for (var i = 0; i < $scope.imageList.length; i++) {
                    $scope.slider.timeArray.push($scope.imageList[i].time);
                }
                $scope.slider.timeArray.splice(0);
                $scope.slider.index = 0;
                $scope.slider.options.ceil = $scope.imageList.length - 1;
                angular.forEach($scope.imageList, function (item, index) {
                    if (item.active) {
                        $scope.imageactive = index;
                        $scope.slider.index = index;
                    }
                });
            } catch (e) {
                console.error(e);
            }
        });

    }])
;