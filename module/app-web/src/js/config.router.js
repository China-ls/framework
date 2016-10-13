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
                            'subContentTitle@app': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'DeviceTabCtrl'
                            },
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
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['frapontillo.bootstrap-switch', 'cgBusy']);
                                }]
                        }
                    })
                    .state('app.device.tab.flow', {
                        url: '/flow',
                        parent: 'app.device.tab',
                        views: {
                            'tab_contentView': {
                                templateUrl: 'tpl/app_device_tab_flow.html',
                                controller: 'DeviceTabFlowCtrl'
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
                                    return $ocLazyLoad.load(['cgBusy', 'rzModule']);
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
                    .state('app.mr.emp', {
                        url: '/emp',
                        parent: 'app.mr',
                        params: {create: false, node: null},
                        views: {
                            'subContentTitle@app': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'ManageRouterEmployeeTitleCtrl'
                            },
                            'contentView@app': {
                                templateUrl: 'tpl/app_mng_router_emp.html',
                                controller: 'ManageRouterEmployeeCtrl'
                            }
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
                        views: {
                            'subContentTitle': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'ManageDeviceTitleCtrl'
                            },
                            'contentView': {templateUrl: 'tpl/app_mng_device.html'}
                        }
                    })
                    .state('app.mngdevice.modify', {
                        url: '/modify',
                        views: {
                            'subContentTitle@app': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'ManageDeviceModifyTitleCtrl'
                            },
                            'contentView@app': {templateUrl: 'tpl/app_mng_device_modify.html'}
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['cgBusy']).then(
                                        function () {
                                            return loadBaiduMaps("7482d6d695c8d7d8dccca6d5de410704");
                                        }
                                    );
                                }]
                        }
                    })
                    .state('app.mngdpt', {
                        url: '/mngdpt',
                        views: {
                            'subContentTitle': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'ManageDepartmentTitleCtrl'
                            },
                            'contentView': {templateUrl: 'tpl/app_mng_department.html'}
                        }
                    })
                    .state('app.mngdpt.edit', {
                        url: '/edit',
                        views: {
                            'subContentTitle@app': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'DepartmentEditTitleCtrl'
                            },
                            'contentView@app': {templateUrl: 'tpl/app_mng_department_add.html'}
                        }
                    })
                    .state('app.cencus', {
                        url: '/cencus',
                        views: {
                            'contentView@app': {
                                templateUrl: 'tpl/app_cencus_tab.html'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng']);
                                }]
                        }
                    })
                    .state('app.cencus.devicetype', {
                        url: '/devicetype',
                        parent: 'app.cencus',
                        views: {
                            'subContentTitle@app': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'CencusDeviceTypeTitleCtrl'
                            },
                            'tab_contentView': {
                                templateUrl: 'tpl/app_cencus_device_type.html'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['cgBusy', 'highcharts-ng']);
                                }]
                        }
                    })
                    .state('app.cencus.flow', {
                        url: '/flow',
                        parent: 'app.cencus',
                        views: {
                            'subContentTitle@app': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'CencusTabFlowTitleCtrl'
                            },
                            'tab_contentView': {
                                templateUrl: 'tpl/app_cencus_flow.html'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['cgBusy', 'highcharts-ng']);
                                }]
                        }
                    })
                    .state('app.cencus.routing', {
                        url: '/routing',
                        parent: 'app.cencus',
                        views: {
                            'subContentTitle@app': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'CencusTabRoutingTitleCtrl'
                            },
                            'tab_contentView': {
                                templateUrl: 'tpl/app_cencus_routing.html'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['cgBusy', 'highcharts-ng']);
                                }]
                        }
                    })
                    .state('app.cencus.qos', {
                        url: '/qos',
                        parent: 'app.cencus',
                        views: {
                            'subContentTitle@app': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'CencusTabQosTitleCtrl'
                            },
                            'tab_contentView': {
                                templateUrl: 'tpl/app_cencus_qos.html'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['cgBusy', 'highcharts-ng']);
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
                            'contentView': {
                                templateUrl: 'tpl/app_system_information.html',
                                controller: 'SystemInformationCtrl'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng']);
                                }]
                        }
                    })
                    .state('app.employee', {
                        url: '/employee',
                        views: {
                            'subContentTitle': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'EmployeeTitleCtrl'
                            },
                            'contentView': {
                                templateUrl: 'tpl/app_employee.html'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng']);
                                }]
                        }
                    })
                    .state('app.employee.add', {
                        url: '/add',
                        views: {
                            'subContentTitle@app': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'EmployeeAddTitleCtrl'
                            },
                            'contentView@app': {
                                templateUrl: 'tpl/app_employee_add.html'
                            }
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['highcharts-ng']);
                                }]
                        }
                    })
                    .state('app.employee.duty', {
                        url: '/duty',
                        views: {
                            'subContentTitle@app': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'EmployeeDutyTitleCtrl'
                            },
                            'contentView@app': {templateUrl: 'tpl/app_employee_duty.html'}
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
                    .state('app.role', {
                        url: '/role',
                        views: {
                            'subContentTitle@app': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'RoleTitleCtrl'
                            },
                            'contentView@app': {templateUrl: 'tpl/app_role.html'}
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['angularBootstrapNavTree', 'cgBusy']);
                                }]
                        }
                    })
                    .state('app.role.modify', {
                        url: '/modify',
                        views: {
                            'subContentTitle@app': {
                                templateUrl: 'tpl/blocks/common_sub_header.html',
                                controller: 'RoleAddTitleCtrl'
                            },
                            'contentView@app': {templateUrl: 'tpl/app_role_add.html'}
                        },
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function ($ocLazyLoad) {
                                    return $ocLazyLoad.load(['angularBootstrapNavTree', 'cgBusy']);
                                }]
                        }
                    })
            }
        ]
    );
