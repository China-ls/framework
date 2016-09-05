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
                    .otherwise('/app/device')
                ;
                $stateProvider
                    .state('app', {
                        abstract: true,
                        url: '/app',
                        templateUrl: 'tpl/app.html'
                    })
                    .state('app.device', {
                        url: '/device',
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
                    .state('app.device.tab', {
                        url: '/tab',
                        params: {
                            id: null,
                            device: null
                        },
                        parent: 'app.device',
                        views: {
                            'contentView@app': {
                                templateUrl: 'tpl/app_device_tab.html'/*,
                                 controller: 'DeviceTabCtrl'*/
                            }
                        }
                    })
                    .state('app.device.tab.detail', {
                        url: '/detail',
                        parent: 'app.device.tab',
                        views: {
                            'tab_contentView': {
                                templateUrl: 'tpl/app_device_tab_detail.html'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng']);
                                }]
                        }
                    })
                    .state('app.device.tab.map', {
                        url: '/map',
                        parent: 'app.device.tab',
                        views: {
                            'tab_contentView': {
                                templateUrl: 'tpl/app_device_tab_map.html',
                                controller: 'DeviceTabMapCtrl'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng', 'cgBusy'])
                                        .then(function () {
                                            return loadBaiduMaps("7482d6d695c8d7d8dccca6d5de410704");
                                        });
                                }]
                        }
                    })
                    .state('app.device.tab.info', {
                        url: '/info',
                        parent: 'app.device.tab',
                        views: {
                            'tab_contentView': {
                                templateUrl: 'tpl/app_device_tab_information.html',
                                controller: 'DeviceTabInfoCtrl'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng']);
                                }]
                        }
                    })
                    .state('app.device.tab.control', {
                        url: '/control',
                        parent: 'app.device.tab',
                        views: {
                            'tab_contentView': {
                                templateUrl: 'tpl/app_device_tab_control.html',
                                controller: 'DeviceTabControlCtrl'
                            }
                        }
                    })
                    .state('app.device.tab.water', {
                        url: '/water',
                        parent: 'app.device.tab',
                        views: {
                            'tab_contentView': {
                                templateUrl: 'tpl/app_device_tab_water.html',
                                controller: 'DeviceTabWaterCtrl'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng', 'cgBusy']);
                                }]
                        }
                    })
                    .state('app.device.tab.positive', {
                        url: '/positive',
                        parent: 'app.device.tab',
                        views: {
                            'tab_contentView': {
                                templateUrl: 'tpl/app_device_tab_positive.html',
                                controller: 'DeviceTabPositiveCtrl'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng', 'cgBusy']);
                                }]
                        }
                    })
                    .state('app.device.tab.el', {
                        url: '/el',
                        parent: 'app.device.tab',
                        views: {
                            'tab_contentView': {
                                templateUrl: 'tpl/app_device_tab_el.html',
                                controller: 'DeviceTabElCtrl'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng']);
                                }]
                        }
                    })
                    .state('app.device.tab.wq', {
                        url: '/wq',
                        parent: 'app.device.tab',
                        views: {
                            'tab_contentView': {
                                templateUrl: 'tpl/app_device_tab_water_q.html',
                                controller: 'DeviceTabWqCtrl'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng']);
                                }]
                        }
                    })
                    .state('app.device.tab.route', {
                        url: '/route',
                        parent: 'app.device.tab',
                        views: {
                            'tab_contentView': {
                                templateUrl: 'tpl/app_device_tab_route.html',
                                controller: 'DeviceTabRouteCtrl'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['cgBusy']);
                                }]
                        }
                    })
                    .state('app.device.tab.movie', {
                        url: '/movie',
                        parent: 'app.device.tab',
                        views: {
                            'tab_contentView': {
                                templateUrl: 'tpl/app_device_tab_movie.html',
                                controller: 'DeviceTabMovieCtrl'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['cgBusy']);
                                }]
                        }
                    })
                    .state('app.route', {
                        url: '/route',
                        views: {
                            'subContentTitle': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'RouteHeaderCtrl'
                            },
                            'aside': {templateUrl: 'tpl/blocks/aside_routing.html', controller: 'AsideRouteCtrl'},
                            'contentView': {templateUrl: 'tpl/app_routing.html', controller: 'RouteCtrl'}
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['angularBootstrapNavTree', 'cgBusy']).then(
                                        function () {
                                            return loadBaiduMaps("7482d6d695c8d7d8dccca6d5de410704");
                                        }
                                    );
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
                    .state('app.cencus', {
                        url: '/cencus',
                        cache: false,
                        views: {
                            'subContentTitle': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'CencusCtrl'
                            },
                            'contentView': {templateUrl: 'tpl/app_cencus.html', controller: 'CencusCtrl'}
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng']);
                                }]
                        }
                    })
                    .state('app.system', {
                        url: '/system',
                        cache: false,
                        views: {
                            'subContentTitle': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'SystemInformationCtrl'
                            },
                            'contentView': {templateUrl: 'tpl/app_system_information.html', controller: 'SystemInformationCtrl'}
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng']);
                                }]
                        }
                    })
            }
        ]
    );
