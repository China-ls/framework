// config

var app =
        angular.module('app')
            .constant('APPCONST', {
                APPKEY: 'abc123',
                CTX: '',
                DEPARTMENT: '/dpt',
                SENSOR_BY_ID: '/sensor/',
                SENSORS: '/sensor/all',
                SENSOR_DATA_TODAY: '/sensor/{id}/data/today?comp_type=flowmeter_sensor',
                SENSOR_DATA_TOTAL_MONTH: '/sensor/{id}/data/month',
                SENSOR_DATA_IMAGE_LIST: '/sensor/{id}/data/image',
                SENSOR_DATA_IMAGE: '/sensor/image/',
                DEPARTMENT_ROUTE: '/dpt/route',
                EMP_DEPARTMENT_LIST: '/dpt/emp',
                EMP_DEPARTMENT_ADD: '/dpt/emp/add',
                EMP_DEPARTMENT_UPDATE: '/dpt/emp/update',
                EMP_DEPARTMENT_DELETE: '/dpt/emp/{id}',
                SYSTEM_INFORMATION: '/sys/info',

                APP_LOCAL_STORAGE_MANAGE_EMPLOYEE : 'app_loc_storage_mg_emp',
            })
            .config(['$httpProvider', 'APPCONST', function ($httpProvider, APPCONST) {
                // $httpProvider.defaults.headers.common['APPKEY'] = NETCONST.APPKEY;

                $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
                $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

                // Override $http service's default transformRequest
                $httpProvider.defaults.transformRequest = [function (data) {
                    /**
                     * The workhorse; converts an object to x-www-form-urlencoded serialization.
                     * @param {Object} obj
                     * @return {String}
                     */
                    var param = function (obj) {
                        var query = '';
                        var name, value, fullSubName, subName, subValue, innerObj, i;

                        for (name in obj) {
                            value = obj[name];

                            if (value instanceof Array) {
                                for (i = 0; i < value.length; ++i) {
                                    subValue = value[i];
                                    fullSubName = name + '[' + i + ']';
                                    innerObj = {};
                                    innerObj[fullSubName] = subValue;
                                    query += param(innerObj) + '&';
                                }
                            } else if (value instanceof Object) {
                                for (subName in value) {
                                    subValue = value[subName];
                                    fullSubName = name + '[' + subName + ']';
                                    innerObj = {};
                                    innerObj[fullSubName] = subValue;
                                    query += param(innerObj) + '&';
                                }
                            } else if (value !== undefined && value !== null) {
                                query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
                            }
                        }
                        return query.length ? query.substr(0, query.length - 1) : query;
                    };

                    return angular.isObject(data) && String(data) !== '[object File]'
                        ? param(data)
                        : data;
                }];
            }])
            .config(
                ['$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
                    function ($controllerProvider, $compileProvider, $filterProvider, $provide) {

                        // lazy controller, directive and service
                        app.controller = $controllerProvider.register;
                        app.directive = $compileProvider.directive;
                        app.filter = $filterProvider.register;
                        app.factory = $provide.factory;
                        app.service = $provide.service;
                        app.constant = $provide.constant;
                        app.value = $provide.value;
                    }
                ])
            .config(['$translateProvider', function ($translateProvider) {
                // Register a loader for the static files
                // So, the module will search missing translation tables under the specified urls.
                // Those urls are [prefix][langKey][suffix].
                $translateProvider.useStaticFilesLoader({
                    prefix: 'l10n/',
                    suffix: '.js'
                });
                // Tell the module what language to use by default
                $translateProvider.preferredLanguage('en');
                // Tell the module to store the language in the local storage
                $translateProvider.useLocalStorage();
            }])
    ;