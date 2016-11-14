'use strict';

/* Controllers */

// ManageRouterCtrl controller
app.controller('ManageDeviceModifyTitleCtrl', ['$scope', '$http', '$localStorage',
    function ($scope, $http, $localStorage) {
        $scope.app.subHeader.goBackHide = false;
        $scope.app.settings.asideHide = true;
        $scope.app.subHeader.contentTitle = $localStorage.isUpdate ? '更新站点' : '新增站点';
        $scope.app.subHeader.goBackSref = 'app.mngdevice';
    }]
);

// ManageRouterCtrl controller
app.controller('ManageDeviceModifyCtrl', ['$scope', '$http', '$localStorage', '$state', 'toaster', 'APPCONST',
    function ($scope, $http, $localStorage, $state, toaster, APPCONST) {
        $scope.components = [];
        $scope.device = $localStorage.selectDevice;
        if (!$scope.device) {
            $scope.device = {longitude: 120.95281, latitude: 30.883874, setupDate: new Date()};
            // $scope.device = angular.extend($scope.device, {
            //     name: '上海测试设备',
            //     station_type: 'B类站点',
            //     day_deal_water_ability: '15吨',
            //     sim: 'sim',
            //     internal_id: 123456,
            //     admin: 'hx',
            //     contact: 'contact',
            //     address: 'address',
            //     desc: 'desc'
            // });
        } else if ($scope.device.components) {
            console.warn($scope.device);
            angular.forEach($scope.device.components, function (item) {
                var arr = item.comp_id.split(":");
                item.channel = parseInt(arr[0]);
                item.sub_channel = parseInt(arr[1]);
                $scope.components.push(item);
            });
        }
        $scope.mapOptions = {
            center: {longitude: 120.95281, latitude: 30.883874},
            zoom: 17,
            city: 'JiaShan',
            enableMapClick: false,
            clientHeight: $scope.baidumapeight,
            overlays: $scope.overlays
        };
        $scope.compTypeMap = [
            {name: 'analog_sensor', dn: '数字输入量'},
            {name: 'camera_controller', dn: '摄像机'},
            {name: 'cardreader_sensor', dn: '考勤机'},
            {name: 'electricmeter_sensor', dn: '电表'},
            {name: 'flowmeter_sensor', dn: '流量计'},
            {name: 'onboard_controller', dn: '继电器开关'},
            {name: 'status_sensor', dn: '状态输入量'},
            {name: 'plc_controller', dn: 'PLC'}
        ];
        $scope.componentTypeInstanceMap = {
            analog_sensor: [4101, 4102, 4103],
            camera_controller: [3],
            cardreader_sensor: [4100],
            electricmeter_sensor: [4099],
            flowmeter_sensor: [4098],
            onboard_controller: [4101, 4102, 4103],
            plc_controller: [],
            status_sensor: [4104, 4105, 4106, 4107]
        };

        $http.get(APPCONST.CTX + APPCONST.DEPARTMENT_LIST_BY_TYPE + '0').then(function (response) {
            $scope.departments = response.data.data.data;
            // console.warn($scope.departments);
        }, function (response) {
            $scope.Toast('error', '警告', '服务器响应异常，请联系管理员。');
        });

        $scope.myImage = $scope.device.icon;
        $scope.myCroppedImage = '';

        var handleFileSelect = function (evt) {
            var file = evt.currentTarget.files[0];
            var reader = new FileReader();
            reader.onload = function (evt) {
                $scope.$apply(function ($scope) {
                    $scope.myImage = evt.target.result;
                });
            };
            reader.readAsDataURL(file);
        };
        angular.element(document.querySelector('#fileInput')).on('change', handleFileSelect);

        $scope.compTypeChange = function (comp) {
            if (comp.type === 'analog_sensor') {
                comp.category = 2;
                delete comp.instance_type;
            } else if (comp.type === 'camera_controller') {
                comp.category = 0;
                comp.instance_type = 3;
            } else if (comp.type === 'cardreader_sensor') {
                comp.category = 0;
                comp.instance_type = 4100;
            } else if (comp.type === 'electricmeter_sensor') {
                comp.category = 0;
                comp.instance_type = 4099;
            } else if (comp.type === 'flowmeter_sensor') {
                comp.category = 0;
                comp.instance_type = 4098;
            } else if (comp.type === 'onboard_controller') {
                comp.category = 1;
                comp.instance_type = 4101;
            } else if (comp.type === 'status_sensor') {
                comp.category = 2;
                comp.instance_type = 4104;
            } else if (comp.type === 'plc_controller') {
                comp.category = 1;
                comp.instance_type = 4101;
            } else {
                delete comp.category;
                delete comp.instance_type;
            }
        };

        $scope.compCategoryChange = function (comp) {
            if (comp.category != 3) {
                for (var i = 0; i < $scope.components.length; i++) {
                    if ($scope.components[i].ref_channel === comp.channel) {
                        delete $scope.components[i].ref_channel;
                    }
                }
            }
        };

        $scope.onMapClick = function ($event, $params) {
            if (!$params || !$params[0] || !$params[0].point) {
                return;
            }
            $scope.device.longitude = $params[0].point.lng;
            $scope.device.latitude = $params[0].point.lat;
            if (!$scope.marker) {
                return;
            }
            $scope.marker.setPosition($params[0].point);
        };
        $scope.onMapLoaded = function ($event, $params) {
            $scope.marker = new BMap.Marker(
                new BMap.Point($scope.device.longitude, $scope.device.latitude),
                {icon: new BMap.Icon("img/marker_blue.png", new BMap.Size(32, 32))}
            );
            $scope.marker.enableDragging();
            $scope.marker.addEventListener('dragend', function (obj) {
                $scope.device.longitude = obj.point.lng;
                $scope.device.latitude = obj.point.lat;
            });
            $scope.myMap.addOverlay($scope.marker);
        };
        $scope.cancel = function () {
            $state.go('app.mngdevice');
        };

        $scope.onAddComponent = function () {
            $scope.components.push({setupDate: new Date()});
        };
        $scope.onRemoveComponent = function (index) {
            $scope.components.splice(index, 1);
        };

        $scope.checkFormAndToast = function (data, field, isSelect) {
            isSelect = isSelect || false;
            if (!data) {
                toaster.pop('warning', '提示', (isSelect ? '请选择' : '请输入') + field);
                return false;
            }
            return true;
        };

        $scope.onSubmit = function () {
            // console.warn($scope.device);
            if (!$scope.checkFormAndToast($scope.device.name, '姓名')) {
                return;
            }
            if (!$scope.checkFormAndToast($scope.device.station_type, '站点类型', true)) {
                return;
            }
            if (!$scope.checkFormAndToast($scope.device.day_deal_water_ability, '日处水能力', true)) {
                return;
            }

            var requestData = angular.extend({}, $scope.device);
            if ($localStorage.selectDevice == null && requestData.setupDate) {
                requestData.setupDate = $scope.formatDate(requestData.setupDate, 'yyyy-MM-dd');
            }
            if ($scope.components) {
                var components = angular.extend([], $scope.components);
                angular.forEach(components, function (item) {
                    item.comp_id = item.channel + ':' + item.sub_channel;
                });
                requestData.components = JSON.stringify(components);
            }
            requestData.icon = $scope.myCroppedImage;
            console.warn(requestData);

            var url = null;

            if ($localStorage.isUpdate) {
                url = APPCONST.CTX + APPCONST.SENSOR_UPDATE;
            } else {
                url = APPCONST.CTX + APPCONST.SENSOR_ADD;
            }

            $http.post(url, requestData).then(function (response) {
                // console.warn(response);
                toaster.pop('success', '提示', $localStorage.isUpdate ? '更新成功' : '添加成功');
                $state.go('app.mngdevice');
            }, function (response) {
                $scope.Toast('error', '警告', '服务器响应异常，请联系管理员。');
                // console.error(response);
            });

        };
    }]
);