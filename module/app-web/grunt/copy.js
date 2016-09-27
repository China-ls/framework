module.exports = {
    angular: {
        files: [
            {expand: true, src: "**", cwd: 'bower_components/bootstrap/fonts',         dest: "angular/fonts"},
            {expand: true, src: "**", cwd: 'bower_components/font-awesome/fonts',      dest: "angular/fonts"},
            {expand: true, src: "**", cwd: 'bower_components/simple-line-icons/fonts', dest: "angular/fonts"},
            {expand: true, src: "**", cwd: 'src/fonts',   dest: "angular/fonts"},
            {expand: true, src: "**", cwd: 'src/api',     dest: "angular/api"},
            {expand: true, src: "**", cwd: 'src/l10n',    dest: "angular/l10n"},
            {expand: true, src: "**", cwd: 'src/img',     dest: "angular/img"},
            {expand: true, src: "**", cwd: 'src/js',      dest: "angular/js"},
            {expand: true, src: "**", cwd: 'src/tpl',     dest: "angular/tpl"},
            {expand: true, src: "**", cwd: 'bower_components/angular-bootstrap-nav-tree/dist',     dest: "angular/lib/angular-bootstrap-nav-tree"},
            {src: 'bower_components/Highcharts-4.2.5/js/highcharts.js', dest : 'angular/lib/Highcharts-4.2.5/highcharts.js'},
            {src: 'bower_components/highcharts-ng/dist/highcharts-ng.js', dest : 'angular/lib/highcharts-ng/highcharts-ng.js'},
            {src: 'bower_components/screenfull/dist/screenfull.min.js', dest : 'angular/lib/screenfull/screenfull.min.js'},
            {src: 'bower_components/angular-busy/dist/angular-busy.min.js', dest : 'angular/lib/angular-busy/angular-busy.min.js'},
            {src: 'bower_components/angular-busy/dist/angular-busy.min.css', dest : 'angular/lib/angular-busy/angular-busy.min.css'},
            {src: 'bower_components/jquery/dist/jquery.min.js', dest : 'angular/lib/jquery/jquery.min.js'},
            {src: 'bower_components/angular/angular.min.js', dest : 'angular/lib/angular/angular.min.js'},
            {src: 'bower_components/angular-bootstrap/ui-bootstrap.min.js', dest : 'angular/lib/angular-bootstrap/ui-bootstrap.min.js'},
            {src: 'bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js', dest : 'angular/lib/angular-bootstrap/ui-bootstrap-tpls.min.js'},
            {src: 'bower_components/angularjs-toaster/toaster.min.js', dest : 'angular/lib/angularjs-toaster/toaster.min.js'},
            {src: 'bower_components/bootstrap-switch/dist/js/bootstrap-switch.min.js', dest : 'angular/lib/bootstrap-switch/js/bootstrap-switch.min.js'},
            {src: 'bower_components/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.min.css', dest : 'angular/lib/bootstrap-switch/css/bootstrap-switch.min.css'},
            {src: 'bower_components/angular-bootstrap-switch/dist/angular-bootstrap-switch.min.js', dest : 'angular/lib/angular-bootstrap-switch/angular-bootstrap-switch.min.js'},
            {src: 'src/index.min.html', dest : 'angular/index.html'},
            {src: 'src/login.min.html', dest : 'angular/login.html'}
        ]
    },
    html: {
        files: [
            {expand: true, src: "**", cwd: 'bower_components/bootstrap/fonts',         dest: "html/fonts"},
            {expand: true, src: "**", cwd: 'bower_components/font-awesome/fonts',      dest: "html/fonts"},
            {expand: true, src: "**", cwd: 'bower_components/Simple-Line-Icons/fonts', dest: "html/fonts"},
            {expand: true, src: '**', cwd:'src/fonts/', dest: 'html/fonts/'},
            {expand: true, src: "**", cwd: 'src/api',     dest: "html/api"},
            {expand: true, src: '**', cwd:'src/img/', dest: 'html/img/'},
            {expand: true, src: '*.css', cwd:'src/css/', dest: 'html/css/'},
            {expand: true, src: '**', cwd:'swig/js/', dest: 'html/js/'}
        ]
    },
    landing: {
        files: [
            {expand: true, src: "**", cwd: 'bower_components/bootstrap/fonts',         dest: "landing/fonts"},
            {expand: true, src: "**", cwd: 'bower_components/font-awesome/fonts',      dest: "landing/fonts"},
            {expand: true, src: "**", cwd: 'bower_components/Simple-Line-Icons/fonts', dest: "landing/fonts"},
            {expand: true, src:'**', cwd:'src/fonts/', dest: 'landing/fonts/'},
            {expand: true, src:'*.css', cwd:'src/css/', dest: 'landing/css/'},
            {src:'html/css/app.min.css', dest: 'landing/css/app.min.css'}
        ]
    },
    debug_lib: {
        files: [
            {expand: true, src: "**", cwd: 'bower_components/bootstrap/fonts',         dest: "landing/fonts"},
            {expand: true, src: "**", cwd: 'bower_components/font-awesome/fonts',      dest: "landing/fonts"},
            {expand: true, src: "**", cwd: 'bower_components/Simple-Line-Icons/fonts', dest: "landing/fonts"},
            {expand: true, src:'**', cwd:'src/fonts/', dest: 'landing/fonts/'},
            {expand: true, src:'*.css', cwd:'src/css/', dest: 'landing/css/'},
            {src:'html/css/app.min.css', dest: 'landing/css/app.min.css'}
        ]
    }

};
