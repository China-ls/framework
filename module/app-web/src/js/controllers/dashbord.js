'use strict';

/* Controllers */

// DashbordCtrl controller
app.controller('DeviceHeaderCtrl', ['$scope', '$http', '$localStorage',
    function ($scope, $http, $localStorage) {
        $scope.app.subHeader.subAsideTitle = '设备列表';
        $scope.app.subHeader.contentTitle = '设备数据';
        $scope.app.subHeader.goBackHide = true;
    }]
);

app.controller('AsideDeviceCtrl', ['$scope', '$http', '$localStorage', '$state', 'NETCONST',
    function ($scope, $http, $localStorage, $state, NETCONST) {
        $scope.treeData = [];
        $scope.tree = {};

        $scope.analysDepartment = function (data, level) {
            var children = [];
            data.type = 'dpt';
            var color = 'c-red';
            if (level === 1) {
                color = 'c-yellow';
            } else if (level === 2) {
                color = 'c-green';
            } else if (level >= 3) {
                color = 'c-blue';
            }
            level++;
            data.icon = 'fa fa-flag ' + color;
            if (data.sub_departments) {
                data.sub_departments.forEach(function (item) {
                    children.push($scope.analysDepartment(item, level));
                });
            }
            if (data.devices) {
                data.devices.forEach(function (item) {
                    var child = $scope.analysDepartment(item, level);
                    child.type = 'device';
                    child.icon = 'fa  fa-map-marker c-green';
                    children.push(child);
                });
            }
            data.children = children;
            data.label = data.name;
            return data;
        };

        $http.get(NETCONST.CTX + NETCONST.DEPARTMENT).then(function (response) {
            var data = $scope.analysDepartment(response.data.data, 0);
            $scope.treeData.push(data);
            try {
                $scope.tree.expand_all();
                $scope.tree.select_first_branch();
            } catch (e) {
            }
        }, function (response) {

        });

        $scope.treeItemClick = function (branch) {
            if (branch.type === 'dpt') {
                $state.go("app.device", {id: branch.uuid}, {inherit: false});
                $scope.$emit("EMIT_DEVICE_TREE_CLICK", branch);
                $scope.app.cache.selectParentBranch = null;
            } else if (branch.type === 'device') {
                $scope.app.cache.selectParentBranch = $scope.tree.get_parent_branch(branch);
                // console.warn($scope.tree.get_parent_branch(branch));
                $state.go("app.device.detail", {id: branch.uuid}, {inherit: false});
            }
        };
    }]
);


app.controller('DashbordCtrl', ['$scope', '$http', '$localStorage',
    function ($scope, $http, $localStorage) {
        $scope.overlays = [];
        $scope.offlineOpts = {retryInterval: 5000};
        $scope.mapOptions = {
            center: {
                longitude: 121.60398,
                latitude: 31.214385
            },
            zoom: 17,
            city: 'ShangHai',
            enableMapClick: false,
            clientHeight: $scope.baidumapeight,
            overlays: $scope.overlays
        };

        $scope.onMapClick = function ($event, $params) {
            console.warn('clickmap');
        };
        $scope.onMapLoaded = function ($event, $params) {
            console.warn($scope.myMap);
            console.warn('onMapLoaded');
        };

        $scope.itemid = "abc123";

        if ($scope.app.cache.selectParentBranch) {
            $scope.app.subHeader.goBackHide = true;
            $scope.app.subHeader.contentTitle = $scope.app.cache.selectParentBranch.label;
        }

        $scope.$on("BROADCAST_DEVICE_TREE_CLICK", function (event, data) {
            // console.warn(data);
            $scope.app.subHeader.contentTitle = data.label;
        });

    }])
;