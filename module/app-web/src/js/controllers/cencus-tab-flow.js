'use strict';

// DeviceDetail controller
app.controller('CencusTabFlowTitleCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.app.settings.asideHide = true;
        $scope.app.subHeader.goBackHide = true;
        $scope.app.subHeader.contentTitle = '水电统计';
    }]);

app.controller('CencusTabFlowCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        var start = new Date();
        var end = new Date();
        var month = end.getMonth();
        if (month > 2) {
            start.setMonth(month - 1);
        } else {
            start.setMonth(12);
            start.setYear(end.getFullYear() - 1);
        }
        $scope.filter = {
            station_type: '',
            startDate: start,
            endDate: end
        };
        $scope.filterQuery = function () {
            $scope.filter.start = $scope.filter.startDate.getTime();
            $scope.filter.end = $scope.filter.endDate.getTime();
            $scope.loadDataPromise = $http.post(APPCONST.CTX + APPCONST.CENCUS_FLOW, $scope.filter)
                .then(function (response) {
                    // console.warn(response);
                    $scope.filterResults = response.data.data;
                });
        };
    }])
;