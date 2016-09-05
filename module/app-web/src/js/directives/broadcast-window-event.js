(function (window, angular) {
    'use strict';

    angular.module('app')
        .directive('bcWinResize', ['$rootScope', '$window', '$timeout', function ($rootScope, $window, $timeout) {
            return {
                restrict: 'AE',
                link: function (scope, element, attrs) {
                    var win = angular.element($window),
                        oldWidth = $window.innerWidth,
                        oldHeight = $window.innerHeight,
                        updateStuffTimer = null,
                        broadcastEventName = attrs.bcWinResize || attrs.broadcastName || 'bc.event.win.resize';

                    win.bind("resize", function (e) {
                        var newWidth = $window.innerWidth,
                            newHeight = $window.innerHeight;
                        if (null !== updateStuffTimer) {
                            $timeout.cancel(updateStuffTimer);
                        }
                        updateStuffTimer = $timeout(function () {
                            $rootScope.$broadcast(broadcastEventName, {
                                w: newWidth,
                                h: newHeight,
                                ow: oldWidth,
                                oh: oldHeight
                            });
                        }, 300);
                    });
                    scope.$on('$destroy', function () {
                        win.unbind('resize.doResize'); // remove the handler added earlier
                    });
                }
            };
        }]);
})(window, window.angular);