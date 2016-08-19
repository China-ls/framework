'use strict';

(function () {
    //Setup map events from a google map object to trigger on a given element too,
    //then we just use ui-event to catch events from an element
    function bindMapEvents(scope, eventsStr, baiduObject, element) {
        angular.forEach(eventsStr.split(' '), function (eventName) {
            baiduObject.addEventListener(eventName, function (event) {
                element.triggerHandler('map-' + eventName, event);
                //We create an $apply if it isn't happening. we need better support for this
                //We don't want to use timeout because tons of these events fire at once,
                //and we only need one $apply
                if (!scope.$$phase) {
                    scope.$apply();
                }
            });
        });
    }

    app.value('uiBaiduMapConfig', {});
    app.directive('uiBaiduMap',
        ['uiBaiduMapConfig', '$parse', function (uiBaiduMapConfig, $parse) {

            var mapEvents = 'click dblclick rightclick rightdblclick maptypechange mousemove ' +
                'mouseover mouseout movestart moving moveend zoomstart ' +
                'zoomend addoverlay addcontrol removecontrol removeoverlay clearoverlays ' +
                'dragstart dragging dragend addtilelayer removetilelayer load resize hotspotclick ' +
                'hotspotover hotspotout tilesloaded touchstart touchmove touchend longpress';
            var options = uiBaiduMapConfig || {};

            return {
                restrict: 'A',
                //doesn't work as E for unknown reason
                link: function (scope, elm, attrs) {
                    var opts = angular.extend({}, options, scope.$eval(attrs.uiOptions));
                    var map = new window.BMap.Map(elm[0]);
                    var point = new BMap.Point(opts.center.longitude, opts.center.latitude); // 创建点坐标
                    map.centerAndZoom(point, opts.zoom);
                    map.enableScrollWheelZoom();
                    var model = $parse(attrs.uiBaiduMap);

                    //Set scope variable for the map
                    model.assign(scope, map);
                    bindMapEvents(scope, mapEvents, map, elm);

                    elm.triggerHandler('map-loaded');
                }
            };
        }]);

    app.value('uiBaiduMapInfoWindowConfig', {});
    app.directive('uiBaiduMapInfoWindow',
        ['uiBaiduMapInfoWindowConfig', '$parse', '$compile', function (uiBaiduMapInfoWindowConfig, $parse, $compile) {

            var infoWindowEvents = 'closeclick content_change domready ' +
                'position_changed zindex_changed';
            var options = uiBaiduMapInfoWindowConfig || {};

            return {
                link: function (scope, elm, attrs) {
                    var opts = angular.extend({}, options, scope.$eval(attrs.uiOptions));
                    opts.content = elm[0];
                    var model = $parse(attrs.uiBaiduMapInfoWindow);
                    var infoWindow = model(scope);

                    if (!infoWindow) {
                        infoWindow = new window.google.maps.InfoWindow(opts);
                        model.assign(scope, infoWindow);
                    }

                    bindMapEvents(scope, infoWindowEvents, infoWindow, elm);

                    /* The info window's contents dont' need to be on the dom anymore,
                     google maps has them stored.  So we just replace the infowindow element
                     with an empty div. (we don't just straight remove it from the dom because
                     straight removing things from the dom can mess up angular) */
                    elm.replaceWith('<div></div>');

                    //Decorate infoWindow.open to $compile contents before opening
                    var _open = infoWindow.open;
                    infoWindow.open = function open(a1, a2, a3, a4, a5, a6) {
                        $compile(elm.contents())(scope);
                        _open.call(infoWindow, a1, a2, a3, a4, a5, a6);
                    };
                }
            };
        }]);

    /*
     * Map overlay directives all work the same. Take map marker for example
     * <ui-map-marker="myMarker"> will $watch 'myMarker' and each time it changes,
     * it will hook up myMarker's events to the directive dom element.  Then
     * ui-event will be able to catch all of myMarker's events. Super simple.
     */
    function mapOverlayDirective(directiveName, events) {
        app.directive(directiveName, [function () {
            return {
                restrict: 'A',
                link: function (scope, elm, attrs) {
                    scope.$watch(attrs[directiveName], function (newObject) {
                        if (newObject) {
                            bindMapEvents(scope, events, newObject, elm);
                        }
                    });
                }
            };
        }]);
    }

    mapOverlayDirective('uiBaiduMapMarker',
        'animation_changed click clickable_changed cursor_changed ' +
        'dblclick drag dragend draggable_changed dragstart flat_changed icon_changed ' +
        'mousedown mouseout mouseover mouseup position_changed rightclick ' +
        'shadow_changed shape_changed title_changed visible_changed zindex_changed');

    mapOverlayDirective('uiBaiduMapPolyline',
        'click dblclick mousedown mousemove mouseout mouseover mouseup rightclick');

    mapOverlayDirective('uiBaiduMapPolygon',
        'click dblclick mousedown mousemove mouseout mouseover mouseup rightclick');

    mapOverlayDirective('uiBaiduMapRectangle',
        'bounds_changed click dblclick mousedown mousemove mouseout mouseover ' +
        'mouseup rightclick');

    mapOverlayDirective('uiBaiduMapCircle',
        'center_changed click dblclick mousedown mousemove ' +
        'mouseout mouseover mouseup radius_changed rightclick');

    mapOverlayDirective('uiBaiduMapGroundOverlay', 'click dblclick');

})();
