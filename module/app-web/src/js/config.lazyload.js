// lazyload config

angular.module('app')
/**
 * jQuery plugin config use ui-jq directive , config the js and css files that required
 * key: function name of the jQuery plugin
 * value: array of the css js file located
 */
    .constant('JQ_CONFIG', {
            easyPieChart: ['lib/jquery.easy-pie-chart/jquery.easypiechart.js'],
            sparkline: ['lib/jquery.sparkline/jquery.sparkline.js'],
            plot: ['lib/flot/jquery.flot.js',
                'lib/flot/jquery.flot.pie.js',
                'lib/flot/jquery.flot.resize.js'/*,
                 'lib/flot.tooltip/js/jquery.flot.tooltip.js',
                 'lib/flot.orderbars/js/jquery.flot.orderBars.js',
                 'lib/flot-spline/js/jquery.flot.spline.js'*/],
            moment: ['lib/moment/moment.js'],
            screenfull: ['lib/screenfull/screenfull.min.js'],
            slimScroll: ['lib/slimscroll/jquery.slimscroll.min.js'],
            sortable: ['lib/html5sortable/jquery.sortable.js'],
            nestable: ['lib/nestable/jquery.nestable.js'],
            filestyle: ['lib/bootstrap-filestyle/src/bootstrap-filestyle.js'],
            slider: ['lib/bootstrap-slider/bootstrap-slider.js',
                'lib/bootstrap-slider/bootstrap-slider.css'],
            chosen: ['lib/chosen/chosen.jquery.min.js',
                'lib/bootstrap-chosen/bootstrap-chosen.css'],
            TouchSpin: ['lib/bootstrap-touchspin/jquery.bootstrap-touchspin.min.js',
                'lib/bootstrap-touchspin/jquery.bootstrap-touchspin.min.css'],
            wysiwyg: ['lib/bootstrap-wysiwyg/bootstrap-wysiwyg.js',
                'lib/bootstrap-wysiwyg/external/jquery.hotkeys.js'],
            dataTable: ['lib/datatables/media/js/jquery.dataTables.min.js',
                'lib/plugins/integration/bootstrap/3/dataTables.bootstrap.js',
                'lib/plugins/integration/bootstrap/3/dataTables.bootstrap.css'],
            vectorMap: ['lib/bower-jvectormap/jquery-jvectormap-1.2.2.min.js',
                'lib/bower-jvectormap/jquery-jvectormap-world-mill-en.js',
                // 'lib/bower-jvectormap/jquery-jvectormap-us-aea-en.js',
                'lib/bower-jvectormap/jquery-jvectormap-1.2.2.css'],
            footable: ['lib/footable/footable.all.min.js',
                'lib/footable/css/footable.core.css'],
            fullcalendar: ['lib/moment/moment.js',
                'lib/fullcalendar/fullcalendar.min.js',
                'lib/fullcalendar/fullcalendar.css'/*,
                 'lib/fullcalendar/fullcalendar.theme.css'*/],
            daterangepicker: ['lib/moment/moment.js',
                'lib/bootstrap-daterangepicker/daterangepicker.js',
                'lib/bootstrap-daterangepicker/daterangepicker-bs3.css'],
            tagsinput: ['lib/bootstrap-tagsinput/bootstrap-tagsinput.js',
                'lib/bootstrap-tagsinput/bootstrap-tagsinput.css']

        }
    )
    // oclazyload config
    .config(['$ocLazyLoadProvider', function ($ocLazyLoadProvider) {
        // We configure ocLazyLoad to use the lib script.js as the async loader
        $ocLazyLoadProvider.config({
            debug: false,
            events: false,
            modules: [
                {
                    name: 'angularFileUpload',
                    files: [
                        'lib/angular-file-upload/angular-file-upload.min.js'
                    ]
                },
                {
                    name: 'angularBootstrapNavTree',
                    files: [
                        'lib/abnt/abntree.css',
                        'lib/abnt/abntree.js'
                    ]
                },
                {
                    name: 'highcharts-ng',
                    files: [
                        'lib/Highcharts-4.2.5/highcharts.js',
                        'lib/highcharts-ng/highcharts-ng.js'
                    ]
                },
                {
                    name: 'cgBusy',
                    files: [
                        'lib/angular-busy/angular-busy.min.js',
                        'lib/angular-busy/angular-busy.min.css'
                    ]
                },
                {
                    name: 'frapontillo.bootstrap-switch',
                    files: [
                        'lib/bootstrap-switch/js/bootstrap-switch.min.js',
                        'lib/bootstrap-switch/css/bootstrap-switch.min.css',
                        'lib/angular-bootstrap-switch/angular-bootstrap-switch.min.js'
                    ]
                },
                {
                    name: 'ngImgCrop',
                    files: [
                        'lib/ngImgCrop/compile/minified/ng-img-crop.js',
                        'lib/ngImgCrop/compile/minified/ng-img-crop.css'
                    ]
                },
                {
                    name: 'rzModule',
                    files: [
                        'lib/angularjs-slider/rzslider.min.css',
                        'lib/angularjs-slider/rzslider.min.js'
                    ]
                },
                {
                    name: 'angular-carousel',
                    files: [
                        'lib/angular-carousel/dist/angular-carousel.min.css',
                        'lib/angular-carousel/dist/angular-carousel.min.js'
                    ]
                }
            ]
        });
    }])
;
