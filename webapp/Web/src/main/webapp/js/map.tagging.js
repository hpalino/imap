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
var dataAttributeMaps;
var imageUrl = 'img/marker.png';

$(document).ready(function () {
    // Window preparation
    $("body").toggleClass("mini-navbar");
    $('.navbar-minimalize').on('click', function () {
        window.setTimeout(function () {
            $("#map").width($(".ibox").width() * 0.99);
        }, 500);
    });
    SmoothlyMenu();
    window.setTimeout(function () {
        $("#map").width($(".ibox").width() * 0.99);
    }, 1000);

    hWrapper=$("body").height() - $(".navbar-static-top").height() - $(".footer").height() - 80;
    $("#map").css("min-height",hWrapper);
    window.onresize = function (event) {
    hWrapper=$("body").height() - $(".navbar-static-top").height() - $(".footer").height() - 80;
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
//    getData();
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
    /* Modal: recordData */
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
    
    /* Modal: filterData */
    dataProvinces = getProvinces();
    $("#filter-province").append(new Option("(ALL)", ""));
    $.each(dataProvinces, function(index, item){
        $("#filter-province").append($('<option>',{value: item.provinceCode, text: item.province}));
    });
    $("#filter-province").select2();
    $("#filter-city").select2();
    $("#filter-district").select2();
    $("#filter-subDistrict").select2();
    $("select[id^='filter-']").on("change",function(){
        if($(this).attr('id')==='filter-province'){
            $("#filter-city").html('').select2({data: {id:null, text: null}});
            $("#filter-district").html('').select2({data: {id:null, text: null}});
            $("#filter-subDistrict").html('').select2({data: {id:null, text: null}});
            $("#filter-city").append(new Option("(ALL)", ""));
            $.each(getCities($("#filter-province").val()),function(index, item){
                $("#filter-city").append(new Option(item.city, item.cityCode));
            });
            $("#filter-city").val($("#filter-city option:eq(0)"));
        } else if($(this).attr('id')==='filter-city'){
            $("#filter-district").html('').select2({data: {id:null, text: null}});
            $("#filter-subDistrict").html('').select2({data: {id:null, text: null}});
            $("#filter-district").append(new Option("(ALL)", ""));
            $.each(getDistricts($("#filter-province").val(), $("#filter-city").val()),function(index, item){
                $("#filter-district").append(new Option(item.district, item.districtCode));
            });
            $("#filter-district").val($("#filter-district option:eq(0)"));
        } else if($(this).attr('id')==='filter-district'){
            $("#filter-subDistrict").html('').select2({data: {id:null, text: null}});
            $("#filter-subDistrict").append(new Option("(ALL)", ""));
            $.each(getSubDistricts($("#filter-province").val(), $("#filter-city").val(), $("#filter-district").val()),function(index, item){
                $("#filter-subDistrict").append(new Option(item.subDistrict, item.subDistrictCode));
            });
            $("#filter-subDistrict").val($("#filter-subDistrict option:eq(0)"));
        }
    });
    $("#filter-province").val($("#filter-province option:eq(1)").val());
    
}
    
function updateSettings() {
    if ($('#filter-province').val()==='') {
        swal({
            title: "Invalid Filter",
            text: "Please fill the parameters, at least the \"Province\" field.",
            confirmButtonColor: "#DD6B55"
        }, function(){$('#modFilter').modal('show');});
    } else {
        var sProv="", sCity="", sDistrict="", sSubDistrict="";
        dataAttributeMaps = getAttributeMaps($('#filter-province').val(), $('#filter-city').val(), $('#filter-district').val(), $('#filter-subDistrict').val());
        sProv=$('#filter-province option[value=' + $('#filter-province').val() + ']').text();
        sCity=$('#filter-city option[value=' + $('#filter-city').val() + ']').text();
        sDistrict=$('#filter-district option[value=' + $('#filter-district').val() + ']').text();
        sSubDistrict=$('#filter-subDistrict option[value=' + $('#filter-subDistrict').val() + ']').text();
        $('#stat-area').show().text((sProv!==""?sProv:"")+(sCity!==""?" > "+sCity:"")+(sDistrict!==""?" > "+sDistrict:"")+(sSubDistrict!==""?" > "+sSubDistrict:""));
        initMap();
        google.maps.event.addDomListener(window, 'load');
    }
}

var pickMarker;
var centerLatLng;
var zoom;

function addMarker(location, map) {
    pickMarker = new google.maps.Marker({
        position: location,
        map: map
    });
}

function initMap(){
    var markerImage = new google.maps.MarkerImage(imageUrl, new google.maps.Size(24, 24));
    var infowindow = new google.maps.InfoWindow({content:''});

    if(localStorage.centerLat===undefined) {
        localStorage.centerLat=-6.293224;
        localStorage.centerLng=107.089070;
    }
    if(localStorage.zoom===undefined) localStorage.zoom=12;
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: parseFloat(localStorage.zoom),
        center: {lat: parseFloat(localStorage.centerLat), lng: parseFloat(localStorage.centerLng)},
        draggableCursor:'pointer'
    });

    google.maps.event.addListener(map, 'click', function (event) {
        if(pickMarker!==null&&pickMarker!==undefined) pickMarker.setMap(null);
        addMarker(event.latLng, map);
        localStorage.centerLat=pickMarker.getPosition().lat();
        localStorage.centerLng=pickMarker.getPosition().lng();
        localStorage.zoom = map.getZoom();
        showModalRecord(pickMarker.getPosition().lat(), pickMarker.getPosition().lng());
    });
    
    var markers = [];
    var arr = [];
    $('#stat-countNop').hide();
    $('#stat-countNpwp').hide();
    if(dataAttributeMaps!==undefined)
        $('#stat-countNop').text("Count NOP: " + dataAttributeMaps.countAttribute).show();
        $('#stat-countNpwp').text("Count NPWP: " + dataAttributeMaps.countTaxPerson).show();
        for (var i = 0; i < dataAttributeMaps.countAttribute; i++) {

        var dataContent = dataAttributeMaps.content[i];
        var latLng = new google.maps.LatLng(dataContent.latitude, dataContent.longitude);

	var pos = latLng.lat() + "#" + latLng.lng();
	if( arr[pos] === undefined ) {
		arr[pos] = " ";   
    	} else {
		var a = 360.0 / markers.length;
                var newLat = latLng.lat() + -.00003 * Math.cos((+a*markers.length) / 180 * Math.PI);  //x
                var newLng = latLng.lng() + -.00003 * Math.sin((+a*markers.length) / 180 * Math.PI);  //Y
                latLng = new google.maps.LatLng(newLat,newLng);
	}

        var marker = new google.maps.Marker({
            map: map,
            position: latLng,
            draggable: false,
            icon: markerImage,
            title: 'AttributeId: ' + dataContent.attributeId
        });
        google.maps.event.addListener(marker, 'click', function(){
            infowindow.setContent(this.title.replace(/\n/g,'</b><br/>').replace(/:/g,':<b>'));
            infowindow.open(map, this);
        });
        markers.push(marker);
    }
    var markerCluster = new MarkerClusterer(map, markers, {imagePath: 'img/m', maxZoom: 20});
}

function showModalRecord(lat, lng){
    $('#modal-gps').val((lat).toFixed(6).toString() + "," + (lng).toFixed(6).toString());
    $('#modal-datetime').val(dateFormat(new Date(), "yyyy-mm-dd HH:MM:ss"));
    $('#modRecord').modal('show');
}

function getAttributeMaps(provinceCode, cityCode, districtCode, subDistrictCode) {
    var param="";
    var tmp;
    if(provinceCode!==undefined&&provinceCode!==null) {
        param+="&provinceCode=" + provinceCode;
        if(cityCode!==undefined&&cityCode!==null) {
            param+="&cityCode=" + cityCode;
            if(districtCode!==undefined&&districtCode!==null) {
                param+="&districtCode=" + districtCode;
                if(subDistrictCode!==undefined&&subDistrictCode!==null) {
                    param+="&subDistrictCode=" + subDistrictCode;
                }
            }
        }
    }
    $.ajax({
        url: "?getListAttributeMaps=" + param,
        dataType: "json",
        async: false,
        success: function (data) {
            tmp = data;
        },
        timeout: 5000
    });
    return tmp;
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