'use strict';

/* Controllers */

// Temp controller
app.controller('TempCtrl', ['$scope', 'GLOBAL', function ($scope, GLOBAL) {
    console.warn(GLOBAL.CTX);
}])
;