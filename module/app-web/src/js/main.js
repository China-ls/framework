'use strict';

/* Controllers */

angular.module('app')
    .controller('AppCtrl', ['$scope', '$translate', '$localStorage', '$window', 'APPCONST', '$websocket', 'toaster', '$interval',
        function ($scope, $translate, $localStorage, $window, APPCONST, $websocket, toaster, $interval) {
            APPCONST.CTX = angular.element("#__ctx").val();

            // add 'ie' classes to html
            var isIE = !!navigator.userAgent.match(/MSIE/i);
            isIE && angular.element($window.document.body).addClass('ie');
            isSmartDevice($window) && angular.element($window.document.body).addClass('smart');

            // config
            $scope.app = {
                name: 'Angulr',
                version: '1.0.0',
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

            try {
                var wsUrl = APPCONST.CTX.replace("http://", "ws://") + "/ws";
                $scope.ws = $websocket(wsUrl, {
                    reconnectIfNotNormalClose: true
                });
            } catch (e) {
                $scope.ws = $websocket("ws://120.55.165.5:9998/app/ws", {
                    reconnectIfNotNormalClose: true
                });
            }
            $scope.wsSendCount = new Date().getTime();
            $scope.ws.onMessage(function (message) {
                $scope.wsSendCount = new Date().getTime();
                try {
                    var data = JSON.parse(message.data);
                    $scope.$broadcast("WS_MESSAGE", data);
                } catch (e) {
                }
            });
            $scope.wsSend = function (data) {
                $scope.ws.send(data);
                $scope.wsSendCount = new Date().getTime();
            };

            $scope.Toast = function (type, title, text) {
                toaster.pop(type, title, text);
            };

            $interval(function () {
                if (new Date().getTime() - $scope.wsSendCount >= 15000) {
                    $scope.wsSend('heartbeat');
                }
            }, 10000);

            $scope.formatDate = function (date, formatStr) {
                var str = formatStr;
                var Week = ['日', '一', '二', '三', '四', '五', '六'];

                str = str.replace(/yyyy|YYYY/, date.getFullYear());
                str = str.replace(/yy|YY/, (date.getYear() % 100) > 9 ? (date.getYear() % 100).toString() : '0' + (date.getYear() % 100));

                str = str.replace(/MM/, date.getMonth() >= 9 ? (date.getMonth() + 1).toString() : '0' + (date.getMonth() + 1));
                str = str.replace(/M/g, (date.getMonth() + 1));

                str = str.replace(/w|W/g, Week[date.getDay()]);

                str = str.replace(/dd|DD/, date.getDate() > 9 ? date.getDate().toString() : '0' + date.getDate());
                str = str.replace(/d|D/g, date.getDate());

                str = str.replace(/hh|HH/, date.getHours() > 9 ? date.getHours().toString() : '0' + date.getHours());
                str = str.replace(/h|H/g, date.getHours());
                str = str.replace(/mm/, date.getMinutes() > 9 ? date.getMinutes().toString() : '0' + date.getMinutes());
                str = str.replace(/m/g, date.getMinutes());

                str = str.replace(/ss|SS/, date.getSeconds() > 9 ? date.getSeconds().toString() : '0' + date.getSeconds());
                str = str.replace(/s|S/g, date.getSeconds());
                return str;
            };

            $scope.$on('bc.event.win.resize', function (event, data) {
                console.warn("on win resize");
                console.warn(data);
            });
        }]
    );
