// lazyload config

angular.module('app')
/**
 * jQuery plugin config use ui-jq directive , config the js and css files that required
 * key: function name of the jQuery plugin
 * value: array of the css js file located
 */
    .constant('JQ_CONFIG', {
            easyPieChart: ['lib/jquery.easy-pie-chart/dist/jquery.easypiechart.js'],
            sparkline: ['lib/jquery.sparkline/dist/jquery.sparkline.js'],
            plot: ['lib/flot/jquery.flot.js',
                'lib/flot/jquery.flot.pie.js',
                'lib/flot/jquery.flot.resize.js'/*,
                 'lib/flot.tooltip/js/jquery.flot.tooltip.js',
                 'lib/flot.orderbars/js/jquery.flot.orderBars.js',
                 'lib/flot-spline/js/jquery.flot.spline.js'*/],
            moment: ['lib/moment/moment.js'],
            screenfull: ['lib/screenfull/dist/screenfull.min.js'],
            slimScroll: ['lib/slimscroll/jquery.slimscroll.min.js'],
            sortable: ['lib/html5sortable/jquery.sortable.js'],
            nestable: ['lib/nestable/jquery.nestable.js'],
            filestyle: ['lib/bootstrap-filestyle/src/bootstrap-filestyle.js'],
            slider: ['lib/bootstrap-slider/bootstrap-slider.js',
                'lib/bootstrap-slider/bootstrap-slider.css'],
            chosen: ['lib/chosen/chosen.jquery.min.js',
                'lib/bootstrap-chosen/bootstrap-chosen.css'],
            TouchSpin: ['lib/bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min.js',
                'lib/bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min.css'],
            wysiwyg: ['lib/bootstrap-wysiwyg/bootstrap-wysiwyg.js',
                'lib/bootstrap-wysiwyg/external/jquery.hotkeys.js'],
            dataTable: ['lib/datatables/media/js/jquery.dataTables.min.js',
                'lib/plugins/integration/bootstrap/3/dataTables.bootstrap.js',
                'lib/plugins/integration/bootstrap/3/dataTables.bootstrap.css'],
            vectorMap: ['lib/bower-jvectormap/jquery-jvectormap-1.2.2.min.js',
                'lib/bower-jvectormap/jquery-jvectormap-world-mill-en.js',
                // 'lib/bower-jvectormap/jquery-jvectormap-us-aea-en.js',
                'lib/bower-jvectormap/jquery-jvectormap-1.2.2.css'],
            footable: ['lib/footable/dist/footable.all.min.js',
                'lib/footable/css/footable.core.css'],
            fullcalendar: ['lib/moment/moment.js',
                'lib/fullcalendar/dist/fullcalendar.min.js',
                'lib/fullcalendar/dist/fullcalendar.css'/*,
                 'lib/fullcalendar/dist/fullcalendar.theme.css'*/],
            daterangepicker: ['lib/moment/moment.js',
                'lib/bootstrap-daterangepicker/daterangepicker.js',
                'lib/bootstrap-daterangepicker/daterangepicker-bs3.css'],
            tagsinput: ['lib/bootstrap-tagsinput/dist/bootstrap-tagsinput.js',
                'lib/bootstrap-tagsinput/dist/bootstrap-tagsinput.css']

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
                        'lib/angular-bootstrap-nav-tree/dist/abn_tree_directive.js',
                        'lib/angular-bootstrap-nav-tree/dist/abn_tree.css'
                    ]
                },
                {
                    name: 'highcharts-ng',
                    files: [
                        'lib/Highcharts-4.2.5/js/highcharts.js',
                        'lib/highcharts-ng/dist/highcharts-ng.js'
                    ]
                }
            ]
        });
    }])
;
