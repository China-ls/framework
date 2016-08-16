'use strict';

/* Controllers */

angular.module('app')
    .controller('AppCtrl', ['$scope', '$translate', '$localStorage', '$window', 'NETCONST', '$websocket', 'toaster',
        function ($scope, $translate, $localStorage, $window, NETCONST, $websocket, toaster) {
            NETCONST.CTX = angular.element("#__ctx").val();

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
                    navbarHeaderColor: 'bg-lt-black',
                    navbarCollapseColor: 'bg-lt-black',
                    asideColor: 'bg-white-only',
                    headerFixed: true,
                    asideFixed: true,
                    asideFolded: false,
                    asideDock: false,
                    container: false
                },
                subHeader: {
                    subAsideTitle: '',
                    contentTitle: '',
                    goBackHide: false,
                    goBackSref: 'app.device',
                    goBackHref: '#/app/device'
                }
            };

            $scope.app.cache = {};

            // save settings to local storage
            // if (angular.isDefined($localStorage.settings)) {
            //     $scope.app.settings = $localStorage.settings;
            // } else {
            //     $localStorage.settings = $scope.app.settings;
            // }
            // $scope.$watch('app.settings', function () {
            //     if ($scope.app.settings.asideDock && $scope.app.settings.asideFixed) {
            //         // aside dock and fixed must set the header fixed.
            //         $scope.app.settings.headerFixed = true;
            //     }
            //     // save to local storage
            //     $localStorage.settings = $scope.app.settings;
            // }, true);

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

            $scope.$on("EMIT_DEVICE_TREE_CLICK", function (event, data) {
                $scope.$broadcast("BROADCAST_DEVICE_TREE_CLICK", data);
            });

            var wsUrl = NETCONST.CTX.replace("http://", "ws://") + "/ws";
            try {
                $scope.ws = $websocket(wsUrl, {
                    reconnectIfNotNormalClose: true
                });
            } catch (e){
                $scope.ws = $websocket("ws://120.55.165.5:9998/app/ws", {
                    reconnectIfNotNormalClose: true
                });
            }
            $scope.ws.onMessage(function (message) {
                try {
                    $scope.$broadcast("WS_MESSAGE", JSON.parse(message.data));
                    console.warn(message);
                } catch (e) {
                }
            });
            $scope.wsSend = function (data) {
                $scope.ws.send(data);
            };

            $scope.Toast = function (type, title, text) {
                toaster.pop(type, title, text);
            };
        }]
    );
