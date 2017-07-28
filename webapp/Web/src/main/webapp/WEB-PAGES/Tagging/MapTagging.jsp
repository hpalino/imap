<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-PAGES/taglibs.jsp" %>
<s:layout-render name="/WEB-PAGES/index.jsp" title="">
    <s:layout-component name="wrapper">
        <link href="${pageContext.request.contextPath}/css/plugins/datapicker/datepicker3.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet"/>

        <!-- Source -->
        <script src="${pageContext.request.contextPath}/js/date.format.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugins/datapicker/datepicker.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugins/sweetalert/sweetalert.min.js"></script>

        <!-- select2 -->
        <script src="${pageContext.request.contextPath}/js/plugins/select2/select2.full.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugins/select2/placeholders.jquery.min.js"></script>
        <link href="${pageContext.request.contextPath}/css/plugins/select2/select2.min.css" rel="stylesheet">

        <!-- dataTables -->
        <script src="${pageContext.request.contextPath}/js/plugins/dataTables/datatables.min.js"></script>
        <link href="${pageContext.request.contextPath}/css/plugins/dataTables/datatables.min.css" rel="stylesheet">

        <!-- jasny / file input -->
        <script src="${pageContext.request.contextPath}/js/plugins/jasny/jasny-bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/css/plugins/jasny/jasny-bootstrap.min.css" rel="stylesheet">

        <script src="https://maps.googleapis.com/maps/api/js?libraries=places&key=AIzaSyAmNd1_VgvOjDFn4A0XpLyd3pCObPCVvk0"></script>
        <script src="${pageContext.request.contextPath}/js/plugins/markerclusterer/markerclusterer.js"></script>
        <script src="${pageContext.request.contextPath}/js/map.tagging.js"></script>

        <style>
            .alert-response {position: absolute;top: 10px;z-index: 1;right: 0;}
            .ibox-title {padding: 7px 15px 7px; min-height: 36px;}
            .float-e-margins .btn {margin-bottom: 0px;}
            .wrapper-content {padding-top:10px; padding-bottom:20px;}
            .row.border-bottom {height: 51px}
            .row.vertical-align {height: 34px}
            .navbar.navbar-static-top {height: 50px}
            .control-label {font-weight: 400;}
            .form-group {margin-bottom: 3px}
            .fileinput {margin-bottom: 0px}
            .form-control {font-size: 13px; height: 31px; padding: 4px 8px}
            .list-group {margin-bottom: 5px}
            .list-group-item {padding: 4px 8px;height: 31px; background-color: #ffffff}
            .col-xs-3.control-label {padding-right: 0px; text-align: left}
        </style>
        <div class="row">
            <div class="col-lg-12" style="padding-left:5px; padding-right: 5px">
                <div class="ibox float-e-margins" style="margin-bottom: 5px">
                    <div class="ibox-title">
                        <h5>Google Map Picker<small></small></h5>
                        <div class="ibox-tools">
                            <a id="stat-area" class="btn btn-primary btn-xs" style="display: none;"></a>
                            <a id="stat-countNpwp" class="btn btn-primary btn-xs" style="display: none;"></a>
                            <a id="stat-countNop" class="btn btn-primary btn-xs" style="display: none;"></a>
                            <a id="stat-avgPpm" class="btn btn-primary btn-xs" style="display: none;"></a>
                            <a class="dropdown-toggle" data-toggle="modal" data-target="#modFilter">
                                <i class="fa fa-wrench"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content" style="padding: 7px">
                        <div id="map-container">
                            <div id="map"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div>
            <div class="modal inmodal" id="modRecord" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-sm" style="width: 750px">
                    <s:form beanclass="id.co.icg.imap.tax.web.tagging.MapTaggingActionBean">
                    <div class="modal-content animated fadeIn">
                        <div class="modal-header" style="padding: 10px 10px">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                            <i class="fa fa-edit" style="zoom: 3;"></i>
                            <h4 class="modal-title" style="font-size: 16px">Form Attribute Input</h4>
                        </div>
                        <div class="modal-body" style="padding: 10px 10px 5px 5px">
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="row" style="margin-left:15px">
                                        <div class="form-horizontal form-reseller">
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Search Key:</label>
                                                <div class="col-xs-9">
                                                    <s:select name="searchKey" style="border-radius: 4px; width: 84%" id="modal-searchKey" >
                                                    </s:select>
                                                    <button type="button" onclick="addTaxPerson()" class="btn btn-white" style="padding: 8px 6px 4px 6px"><i class="fa fa-plus"></i></button>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Name:</label>
                                                <div class="col-xs-9">
                                                    <input name="name" class="form-control" style="border-radius: 4px; width: 95%" id="modal-name" readonly/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">NPWP:</label>
                                                <div class="col-xs-7">
                                                    <input name="npwp" class="form-control" style="border-radius: 4px; width: 95%" id="modal-npwp" readonly/>
                                                    <input name="taxId" class="hidden" id="modal-taxId" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Description:</label>
                                                <div class="col-xs-9">
                                                    <input name="description" class="form-control" style="border-radius: 4px; width: 95%" id="modal-description" readonly/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">List NOP:</label>
                                                <div class="col-lg-9">
                                                    <div class="list-group hidden" style="width:95%" id="list-nop">
                                                    </div>
                                                    <button type="button" onclick="clearModalAttribute()" class="btn btn-white" id="btn-addNop"><i class="fa fa-plus">&nbsp;</i>Add New NOP</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="row" style="margin-left:0px;margin-right:0px">
                                        <div class="form-horizontal form-reseller">
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">NOP:</label>
                                                <div class="col-xs-7">
                                                    <input name="nop" class="form-control" style="border-radius: 4px; width: 95%" id="modal-nop" value=""/>
                                                    <input name="attributeId" class="form-control hidden" id="modal-attributeId" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Price Per M<sup>2</sup>:</label>
                                                <div class="col-xs-5">
                                                    <input name="ppm" class="form-control" style="border-radius: 4px; width: 95%" id="modal-ppm" value=""/>
                                                </div>
                                                <label class="col-xs-1 control-label"style="padding-left:0px">Year:</label>
                                                <div class="col-xs-3" style="margin-left: -10px;margin-right: 10px;">
                                                    <input name="year" class="form-control" style="border-radius: 4px; width: 95%" id="modal-year" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Province:</label>
                                                <div class="col-xs-9">
                                                    <select name="provinceCode" id="modal-province" class="form-control" id="modal-province" style="width: 95%">
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">City:</label>
                                                <div class="col-xs-9">
                                                    <select name="cityCode" id="modal-city" class="form-control" id="modal-city" style="width: 95%">
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">District:</label>
                                                <div class="col-xs-9">
                                                    <select name="districtCode" id="modal-district" class="form-control" id="modal-district" style="width: 95%">
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Subdistrict:</label>
                                                <div class="col-xs-9">
                                                    <select name="subDistrictCode" id="modal-subDistrict" class="form-control" id="modal-subDistrict" style="width: 95%">
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">RT:</label>
                                                <div class="col-xs-3">
                                                    <input name="rt" class="form-control" style="border-radius: 4px; width: 95%" id="modal-rt" value=""/>
                                                </div>
                                                <label class="col-xs-1 control-label">RW:</label>
                                                <div class="col-xs-3">
                                                    <input name="rw" class="form-control" style="border-radius: 4px; width: 95%" id="modal-rw" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Street:</label>
                                                <div class="col-xs-9">
                                                    <input name="street" class="form-control" style="border-radius: 4px; width: 95%" id="modal-street" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Street Class:</label>
                                                <div class="col-xs-9">
                                                    <input name="streetClass" class="form-control" style="border-radius: 4px; width: 95%" id="modal-streetClass" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Sector:</label>
                                                <div class="col-xs-9">
                                                    <input name="sector" class="form-control" style="border-radius: 4px; width: 95%" id="modal-sector" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Zone:</label>
                                                <div class="col-xs-9">
                                                    <input name="zone" class="form-control" style="border-radius: 4px; width: 95%" id="modal-zone" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Attachment:</label>
                                                <div class="col-xs-9">
                                                    <div class="fileinput fileinput-new" data-provides="fileinput">
                                                        <span class="btn btn-default btn-file"><span class="fileinput-new">Select file</span>
                                                            <span class="fileinput-exists">Change</span><input type="file" name="attachments[0]"/></span>
                                                        <span class="fileinput-filename"></span>
                                                        <a href="#" class="close fileinput-exists" data-dismiss="fileinput" style="float: none">×</a>
                                                    </div> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">GPS Pos:</label>
                                                <div class="col-xs-9">
                                                    <input name="latLng" class="form-control" style="border-radius: 4px; width: 95%" id="modal-gps" readonly/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Input Date:</label>
                                                <div class="col-xs-4" style="padding-right: 0px">
                                                    <input name="dateInput" class="form-control" style="border-radius: 4px" id="modal-dateInput" readonly/>
                                                </div>
                                                <label class="col-xs-1 control-label">By:</label>
                                                <div class="col-xs-4">
                                                    <input class="form-control" style="border-radius: 4px; width: 87%" id="modal-userInput" value="${actionBean.userSession.username}" readonly/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" name="saveData" class="btn btn-white">Save</button>
                            <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                    </s:form>
                </div>
            </div>
            <div class="modal inmodal" id="modFilter" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-sm" style="width: 400px">
                    <div class="modal-content animated fadeIn">
                        <div class="modal-header" style="padding: 10px 10px">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                            <i class="fa fa-globe" style="zoom: 3;"></i>
                            <h3 id="mod-detail-name" style="margin:10px 0 0 0">Setup for Maps</h3>
                            <small id="mod-detail-location" class="font-bold">Filter based on below criterias:</small>
                        </div>
                        <div class="modal-body" style="padding: 10px 10px 5px 5px">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">Province:</label>
                                    <div class="col-xs-9">
                                        <select id="filter-province" class="form-control" style="width: 95%">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">City:</label>
                                    <div class="col-xs-9">
                                        <select id="filter-city" class="form-control" style="width: 95%">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">District:</label>
                                    <div class="col-xs-9">
                                        <select id="filter-district" class="form-control" style="width: 95%">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">Subdistrict:</label>
                                    <div class="col-xs-9">
                                        <select id="filter-subDistrict" class="form-control"style="width: 95%">
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-white" onclick="updateSettings()" data-dismiss="modal">Update</button>
                            <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</s:layout-component>
</s:layout-render>
<script>
    var attributes;
    var called=0;
    
    $('#modal-searchKey').select2({
        ajax: {
            url: '?getListNpwp=&',
            dataType: "json",
            delay: 500,
            data: function(params) {
                return{npwp: params.term, name: params.term};
            },
            processResults: function (data) {
                return {results: data};
            }
        },
        minimumInputLength: 5,
        escapeMarkup: function(markup) {
            return markup;
        },
        templateResult:function formatRepo (repo) {
            if (repo.loading) return repo.text;
            return repo.npwp + " - " + repo.name;
        },
        templateSelection: function formatRepoSelection (repo) {
            $("#modal-name").prop('readonly', true);
            $("#modal-npwp").prop('readonly', true);
            $("#modal-description").prop('readonly', true);

            $("#modal-npwp").val(repo.npwp);
            $("#modal-name").val(repo.name);
            $("#modal-taxId").val(repo.id);
            $("#modal-description").val(repo.description);
            attributes=getAttributes(repo.id);
            $("#list-nop").html('');
            if(attributes!==undefined&&attributes.length>0){
                $.each(attributes, function(k,v){
                    $("#list-nop").append('<a onclick="showAttribute(' + v.id + ')" class="list-group-item list-group-item-action"><span class="fa fa-chevron-right fa-sm">&nbsp;</span>' + v.nop + '</a>');
                });
                $("#list-nop").removeClass("hidden");
            } else {
                $("#list-nop").addClass("hidden");
            }
            return repo.npwp;
        }
    });

    $("#modal-searchKey").val(null).trigger("change");
    $("#modal-searchKey").empty().trigger("change");
    
    function showAttribute(nopId){
        $.each(attributes, function(k,v){
            if(v.id===nopId){
                $("#modal-attributeId").val(v.id);
                $("#modal-nop").val(v.nop);
                if(v.values!==null){
                    var tmpYear, tmpPpm;
                    $.each(v.values, function(x,y){
                        if(tmpYear===undefined||tmpYear<y.year){
                            tmpPpm=y.ppm;
                            tmpYear=y.year;
                        }
                    });
                    $("#modal-ppm").val(tmpPpm);
                    $("#modal-year").val(tmpYear);
                }
                $("#modal-street").val(v.street);
                $("#modal-streetClass").val(v.streetClass);
                $("#modal-zone").val(v.zone);
                $("#modal-sector").val(v.sector);
                $("#modal-rt").val(v.rt);
                $("#modal-rw").val(v.rw);
                $("#modal-dateInput").val(dateFormat(v.dateInput,"yyyy-mm-dd HH:MM:ss"));
                $("#modal-userInput").val(v.userInput);
                
                $("#modal-province").html('').select2({data: {id:null, text: null}});
                $.each(getProvinces(),function(index, item){
                    $("#modal-province").append(new Option(item.province, item.provinceCode));
                });
                $("#modal-province").val(v.masterArea.provinceCode);

                $("#modal-city").html('').select2({data: {id:null, text: null}});
                $.each(getCities(v.masterArea.provinceCode),function(index, item){
                    $("#modal-city").append(new Option(item.city, item.cityCode));
                });
                $("#modal-city").val(v.masterArea.cityCode);
                
                $("#modal-district").html('').select2({data: {id:null, text: null}});
                $.each(getDistricts(v.masterArea.provinceCode, v.masterArea.cityCode),function(index, item){
                    $("#modal-district").append(new Option(item.district, item.districtCode));
                });
                $("#modal-district").val(v.masterArea.districtCode);

                $("#modal-subDistrict").html('').select2({data: {id:null, text: null}});
                $.each(getSubDistricts(v.masterArea.provinceCode, v.masterArea.cityCode, v.masterArea.districtCode),function(index, item){
                    $("#modal-subDistrict").append(new Option(item.subDistrict, item.subDistrictCode));
                });
                $("#modal-subDistrict").val(v.masterArea.subDistrictCode);

            }
        });
    }
    
    function addTaxPerson(){
        $("#modal-name").val("");
        $("#modal-npwp").val("");
        $("#modal-description").val("");
        clearModalAttribute();
        $("#modal-name").prop('readonly', false);
        $("#modal-npwp").prop('readonly', false);
        $("#modal-description").prop('readonly', false);
        $("#modal-name").focus();
    }
    
    function clearModalAttribute(){
        $("#modal-attributeId").val("");
        $("#modal-nop").val("");
        $("#modal-ppm").val("");
        $("#modal-year").val("");
        $("#modal-street").val("");
        $("#modal-streetClass").val("");
        $("#modal-zone").val("");
        $("#modal-sector").val("");
        $("#modal-rt").val("");
        $("#modal-rw").val("");
        $("#modal-dateInput").val("");
        $("#modal-userInput").val("${actionBean.userSession.username}");
    }
    
    function getAttributes(taxId){
        var tmp;
        $.ajax({
            url: "?getListNop=&taxId=" + taxId,
            dataType: "json",
            async: false,
            success: function (data) {
                tmp=data;
            },
            timeout: 5000
        });
        return tmp;
    }
</script>
