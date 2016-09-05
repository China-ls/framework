'use strict';

/**
 * Config for the router
 */
angular.module('app')
    .run(
        ['$rootScope', '$state', '$stateParams', '$anchorScroll',
            function ($rootScope, $state, $stateParams, $anchorScroll) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
                $rootScope.$on('$stateChangeStart', function (event) {
                    NProgress.start();
                    $anchorScroll();
                });
                $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState) {
                    NProgress.done();
                });
            }
        ]
    )
    .config(
        ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {
                $urlRouterProvider
                    .otherwise('/app/device');
                $stateProvider
                    .state('app', {
                        abstract: true,
                        url: '/app',
                        templateUrl: 'tpl/app.html'
                    })
                    .state('app.device', {
                        url: '/device',
                        cache: false,
                        views: {
                            'subContentTitle': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'DeviceHeaderCtrl'
                            },
                            'aside': {templateUrl: 'tpl/blocks/aside_device.html', controller: 'AsideDeviceCtrl'},
                            'contentView': {templateUrl: 'tpl/app_dashboard.html', controller: 'DashbordCtrl'}
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['angularBootstrapNavTree']).then(
                                        function () {
                                            return loadBaiduMaps("7482d6d695c8d7d8dccca6d5de410704");
                                        }
                                    );
                                }]
                        }
                    })
                    .state('app.device.detail', {
                        url: '/detail/:id',
                        parent: 'app.device',
                        views: {
                            'contentView@app': {
                                templateUrl: 'tpl/app_device_detail.html'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng']);
                                }]
                        }
                    })
                    .state('app.route', {
                        url: '/route',
                        cache: false,
                        views: {
                            'subContentTitle': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'RouteHeaderCtrl'
                            },
                            'aside': {templateUrl: 'tpl/blocks/aside_routing.html', controller: 'AsideRouteCtrl'},
                            'contentView': {templateUrl: 'tpl/app_routing.html', controller: 'RouteCtrl'}
                        },
                        resolve: {
                            deps: ['uiLoad',
                                function () {
                                    return loadBaiduMaps("7482d6d695c8d7d8dccca6d5de410704");
                                }]
                        }
                    })
                    .state('app.mngroute', {
                        url: '/mngroute',
                        cache: false,
                        views: {
                            'subContentTitle': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'ManageRouterCtrl'
                            },
                            'contentView': {templateUrl: 'tpl/app_mng_router.html', controller: 'ManageRouterCtrl'}
                        }
                    })
                    .state('app.mngrole', {
                        url: '/mngrole',
                        cache: false,
                        views: {
                            'subContentTitle': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'ManageRouterCtrl'
                            },
                            'contentView': {templateUrl: 'tpl/app_mng_role.html', controller: 'ManageRouterCtrl'}
                        }
                    })
                    .state('app.mngdevice', {
                        url: '/mngdevice',
                        cache: false,
                        views: {
                            'subContentTitle': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'ManageDeviceCtrl'
                            },
                            'contentView': {templateUrl: 'tpl/app_mng_device.html', controller: 'ManageDeviceCtrl'}
                        }
                    })
            }
        ]
    );
