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
        $scope.device = {longitude: 120.95281, latitude: 30.883874, setupDate: new Date()};
        $scope.device = angular.extend($scope.device, {
            name: '上海测试设备',
            type: 'B类站点',
            dayDealDirtyWaterAbility: '15吨',
            sim: 'sim',
            internal_id: 123456,
            admin: 'hx',
            contact: 'contact',
            address: 'address',
            desc: 'desc'
        });
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

        $scope.components = [];

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
                toaster.pop('warning', '提示', isSelect ? '请选择' : '请输入' + field);
                return false;
            }
            return true;
        };

        $scope.onSubmit = function () {
            if (!$scope.checkFormAndToast($scope.device.name, '姓名')) {
                return;
            }
            if (!$scope.checkFormAndToast($scope.device.type, '站点类型', true)) {
                return;
            }
            if (!$scope.checkFormAndToast($scope.device.dayDealDirtyWaterAbility, '日处水能力', true)) {
                return;
            }
            // console.warn($scope.device);
            var requestData = angular.copy($scope.device, {});
            if ($scope.components) {
                requestData.components = JSON.stringify($scope.components);
            }
            console.warn(requestData);
            $http.post(APPCONST.CTX + APPCONST.SENSOR_ADD, requestData).then(function (response) {
                console.warn(response);
            }, function (response) {
                console.error(response);
            });
        };
    }]
);