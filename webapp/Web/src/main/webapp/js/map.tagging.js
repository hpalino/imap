/*
 * Refer to:
 * https://googlemaps.github.io/js-marker-clusterer/examples/simple_example.html
 * https://googlemaps.github.io/js-marker-clusterer/examples/advanced_example.html
 * 
 * Source:
 * https://github.com/googlemaps/js-marker-clusterer
 */

/* global google */
var map;
var markers=[];
var dataMapPicker;

$(document).ready(function () {
    $('.input-daterange').datepicker({
        keyboardNavigation: true,
        forceParse: false,
        autoclose: true,
        format: "yyyy-mm-dd",
        todayHighlight: true
    });

    // Window preparation
    $("body").toggleClass("mini-navbar");
    $('.navbar-minimalize').on('click', function () {
        window.setTimeout(function () {
            $("#map").width($(".ibox").width() * 0.97);
        }, 500);
    });
    SmoothlyMenu();
    window.setTimeout(function () {
        $("#map").width($(".ibox").width() * 0.97);
    }, 1000);

    hWrapper=$("body").height() - $(".navbar-static-top").height() - $(".footer").height() - 100;
    $("#map").css("min-height",hWrapper);
    window.onresize = function (event) {
    hWrapper=$("body").height() - $(".navbar-static-top").height() - $(".footer").height() - 100;
        $("#map").css("min-height",hWrapper);
    };

    window.onresize = function (event) {
        var maxHeight = window.screen.height,
            curHeight = window.innerHeight;

        if (maxHeight === curHeight) {
            $("#map").height(555 * 1.05);
        } else {
            $("#map").height(555 * 0.93);
        }
    };
    
    initSetting();
    initMap();
    getData();
    r='';
//    $.each(dataMapPicker, function(index, item){
//        r +='<tr>' +
//            '    <td>' + item.name + '</td>' +
//            '    <td>' + item.npwp + '</td>' +
//            '    <td>' + (item.description===null||item.description===undefined? '':item.description) + '</td>' +
//            '    <td>' + item.latitude + "," + item.longitude + '</td>' +
//            '    <td>' + (item.attachment.filename!==null?'<a href="#" onClick="window.open(\'?downloadFile=&filename=' + item.attachment.filename + '\')">' + item.attachment.filenameOrigin + '</a>':'-') + '</td>' +
//            '    <td>' + (item.attachment.filename!==null?item.attachment.contentType:'-') + '</td>' +
//            '    <td>' + item.userInput + '</td>' +
//            '    <td>' + dateFormat(item.dateTime, "yyyy-mm-dd HH:MM:ss") + '</td>' +
//            '</tr>';
//
//    });
//    $('.table-content').html(r===''?'<tr><td colspan="6">(There are no data were uploaded)</td></tr>':r);
});

function initSetting(){
    dataProvinces = getProvinces();
    $.each(dataProvinces, function(index, item){
        $("#modal-province").append($('<option>',{value: item.provinceCode, text: item.province}));
    });
    $.each(getCities($("#modal-province").val()), function(index, item){
        $("#modal-city").append($('<option>',{value: item.cityCode, text: item.city}));
    });
    $("#modal-province").select2();
    $("#modal-city").select2();
    $("#modal-district").select2();
    $("#modal-subDistrict").select2();
    $("select[id^='modal-']").on("change",function(){
        console.log("change: " + $(this).attr('id'));
        if($(this).attr('id')==='modal-province'){
            $("#modal-city").html('').select2({data: {id:null, text: null}});
            $("#modal-district").html('').select2({data: {id:null, text: null}});
            $("#modal-subDistrict").html('').select2({data: {id:null, text: null}});
            $.each(getCities($("#modal-province").val()),function(index, item){
                $("#modal-city").append(new Option(item.city, item.cityCode));
            });
            $("#modal-city").val($("#modal-city option:eq(0)"));
        } else if($(this).attr('id')==='modal-city'){
            $("#modal-district").html('').select2({data: {id:null, text: null}});
            $("#modal-subDistrict").html('').select2({data: {id:null, text: null}});
            $.each(getDistricts($("#modal-province").val(), $("#modal-city").val()),function(index, item){
                $("#modal-district").append(new Option(item.district, item.districtCode));
            });
            $("#modal-district").val($("#modal-district option:eq(0)"));
        } else if($(this).attr('id')==='modal-district'){
            $("#modal-subDistrict").html('').select2({data: {id:null, text: null}});
            $.each(getSubDistricts($("#modal-province").val(), $("#modal-city").val(), $("#modal-district").val()),function(index, item){
                $("#modal-subDistrict").append(new Option(item.subDistrict, item.subDistrictCode));
            });
            $("#modal-subDistrict").val($("#modal-subDistrict option:eq(0)"));
        }
    });
}
    
var marker;

function addMarker(location, map) {
    marker = new google.maps.Marker({
        position: location,
        map: map
    });
}

function initMap(){
    var myLatlng = {lat: -6.293224, lng: 107.089070};
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12,
        center: myLatlng
    });

    google.maps.event.addListener(map, 'click', function (event) {
        if(marker!==null&&marker!==undefined) marker.setMap(null);
        addMarker(event.latLng, map);
        console.log(marker.getPosition().lat() + ":" + marker.getPosition().lng());
        showModalRecord(marker.getPosition().lat(), marker.getPosition().lng());
    });
}

function showModalRecord(lat, lng){
    $('#modal-gps').val((lat).toFixed(6).toString() + "," + (lng).toFixed(6).toString());
    $('#modal-datetime').val(dateFormat(new Date(), "yyyy-mm-dd HH:MM:ss"));
    $('#modRecord').modal('show');
}

function getData() {
    $.ajax({
        url: "?getMapPickers=",
        dataType: "json",
        async: false,
        success: function (data) {
            dataMapPicker = data;
            console.log(data);
        },
        timeout: 5000
    });
}

function getProvinces() {
    var tmp;
    $.ajax({
        url: "?listProvinces=",
        dataType: "json",
        async: false,
        success: function (data) {tmp = data;},
        timeout: 5000
    });
    return tmp;
}

function getCities(provinceCode) {
    var tmp;
    $.ajax({
        url: "?listCities=&provinceCode=" + provinceCode,
        dataType: "json",
        async: false,
        success: function (data) {tmp = data;},
        timeout: 5000
    });
    return tmp;
}

function getDistricts(provinceCode, cityCode) {
    var tmp;
    $.ajax({
        url: "?listDistricts=&provinceCode=" + provinceCode + "&cityCode=" + cityCode,
        dataType: "json",
        async: false,
        success: function (data) {tmp = data;},
        timeout: 5000
    });
    return tmp;
}

function getSubDistricts(provinceCode, cityCode, districtCode) {
    var tmp;
    $.ajax({
        url: "?listSubDistricts=&provinceCode=" + provinceCode + "&cityCode=" + cityCode + "&districtCode=" + districtCode,
        dataType: "json",
        async: false,
        success: function (data) {tmp = data;},
        timeout: 5000
    });
    return tmp;
}

function imageExists(image_url){
    var http = new XMLHttpRequest();
    http.open('HEAD', image_url, false);
    http.send();
    return http.status !== 404;
}