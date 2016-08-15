'use strict';

/* Controllers */

angular.module('app')
    .controller(
        'AppCtrl', ['$scope', '$translate', '$localStorage', '$window', '$http', 'GLOBAL', '$state', 'toaster',
            function ($scope, $translate, $localStorage, $window, $http, GLOBAL, $state, toaster) {
                console.warn("aaa");
                GLOBAL.CTX = angular.element("#_ctx").val();
                $scope._ctx = GLOBAL.CTX;

                $scope.analysDepartment = function (data) {
                    var children = [];
                    data.type = 'dpt';
                    if (data.sub_departments) {
                        data.sub_departments.forEach(function (item) {
                            children.push($scope.analysDepartment(item));
                        });
                    }
                    if (data.devices) {
                        data.devices.forEach(function (item) {
                            var child = $scope.analysDepartment(item);
                            child.type = 'device';
                            children.push(child);
                        });
                    }
                    data.children = children;
                    data.label = data.name;
                    return data;
                };

                $scope.init = function () {
                    $http.get(GLOBAL.API.u).then(function (response) {
                            $scope.user = response.data;
                        },
                        function (errResponse) {
                        });
                    $http.get(GLOBAL.API.department).then(function (response) {
                            // console.warn(response.data.data);
                            $scope.aside.treeData.splice(0);
                            var data = $scope.analysDepartment(response.data.data);
                            $scope.aside.treeData.push(data);
                            try {
                                $scope.aside.tree.expand_all();
                            } catch (e) {
                            }
                        },
                        function (errResponse) {
                        });
                };

                $scope.treeItemClick = function (branch) {
                    if (branch.type === 'dpt') {
                        $state.go("app.department", {branch: branch});
                    } else if (branch.type === 'device') {
                        $state.go("app.device", {branch: branch});
                    }
                };

                $scope.logout = function () {
                    console.warn("click");
                    toaster.pop('success', '提示', '退出测试信息');
                };

                // add 'ie' classes to html
                var isIE = !!navigator.userAgent.match(/MSIE/i);
                isIE && angular.element($window.document.body).addClass('ie');
                isSmartDevice($window) && angular.element($window.document.body).addClass('smart');

                // config
                $scope.app = {
                    name: 'Angulr',
                    version: '2.0.1',
                    // for chart colors
                    color: {
                        primary: '#7266ba',
                        info: '#23b7e5',
                        success: '#27c24c',
                        warning: '#fad733',
                        danger: '#f05050',
                        light: '#e8eff0',
                        dark: '#3a3f51',
                        black: '#1c2b36'
                    },
                    settings: {
                        themeID: 1,
                        navbarHeaderColor: 'bg-black',
                        navbarCollapseColor: 'bg-white-only',
                        asideColor: 'bg-black',
                        headerFixed: true,
                        asideFixed: false,
                        asideFolded: false,
                        asideDock: false,
                        container: false
                    }
                }

                // save settings to local storage
                if (angular.isDefined($localStorage.settings)) {
                    $scope.app.settings = $localStorage.settings;
                } else {
                    $localStorage.settings = $scope.app.settings;
                }
                $scope.$watch('app.settings', function () {
                    if ($scope.app.settings.asideDock && $scope.app.settings.asideFixed) {
                        // aside dock and fixed must set the header fixed.
                        $scope.app.settings.headerFixed = true;
                    }
                    // save to local storage
                    $localStorage.settings = $scope.app.settings;
                }, true);

                // angular translate
                $scope.lang = {isopen: false};
                $scope.langs = {en: 'English', de_DE: 'German', it_IT: 'Italian'};
                $scope.selectLang = $scope.langs[$translate.proposedLanguage()] || "English";
                $scope.setLang = function (langKey, $event) {
                    // set the current lang
                    $scope.selectLang = $scope.langs[langKey];
                    // You can change the language during runtime
                    $translate.use(langKey);
                    $scope.lang.isopen = !$scope.lang.isopen;
                };

                function isSmartDevice($window) {
                    // Adapted from http://www.detectmobilebrowsers.com
                    var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
                    // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
                    return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
                }

            }]);
