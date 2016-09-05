'use strict';

// ComfirmModalCtrl controller
app.controller('ComfirmModalCtrl',
    ['$scope', '$modalInstance', '$http', 'title', 'content',
        function ($scope, $modalInstance, $http, title, content) {
            $scope.title = title;
            $scope.content = content;
            $scope.ok = function () {
                $modalInstance.close();
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);