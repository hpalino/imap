<%-- 
    Document   : MemberRegistrationActionBean
    Created on : Sep 19, 2016, 10:02:03 AM
    Author     : Fauzi Marjalih
--%>

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
        <script src="${pageContext.request.contextPath}/js/map.tagging.js"></script>

        <style>
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
            .col-xs-3.control-label {padding-right: 0px; text-align: left}
        </style>
        <div class="row">
            <div class="col-lg-12" style="padding-left:5px; padding-right: 5px; padding-bottom: 15px">
                <div class="ibox float-e-margins" style="margin-bottom: 5px">
                    <div class="ibox-title">
                        <h5>Filter by:<small></small></h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content" style="padding: 15px">
                        <div class="row" style="padding-bottom: 10px">
                            <div class="col-lg-5">
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">Province:</label>
                                    <div class="col-xs-9">
                                        <select name="province" id="filter-province" class="form-control" style="width: 95%">
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" style="padding-bottom: 10px">
                            <div class="col-lg-5">
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">City:</label>
                                    <div class="col-xs-9">
                                        <select name="city" id="filter-city" class="form-control" style="width: 95%">
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" style="padding-bottom: 10px">
                            <div class="col-lg-5">
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">District:</label>
                                    <div class="col-xs-9">
                                        <select name="district" id="filter-district" class="form-control" style="width: 95%">
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" style="padding-bottom: 10px">
                            <div class="col-lg-5">
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">Sub District:</label>
                                    <div class="col-xs-9">
                                        <select name="subdistrict" id="filter-subdistrict" class="form-control" style="width: 95%">
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" style="padding-bottom: 10px">
                            <div class="col-lg-5">
                                <div class="form-group">
                                    <label class="col-xs-3 control-label"></label>
                                    <div class="col-xs-9">
                                        <button type="submit" class="btn btn-white">Submit</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12" style="padding-left:5px; padding-right: 5px">
                <div class="ibox float-e-margins" style="margin-bottom: 5px">
                    <div class="ibox-title">
                        <h5>Google Map Picker<small></small></h5>
                    </div>
                    <div class="ibox-content" style="padding: 15px">
                        <div id="map-container">
                            <div id="map" style="width: 1065px; height: 550px"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12" style="padding-left:5px; padding-right: 5px; padding-bottom: 15px">
                <div class="ibox float-e-margins" style="margin-bottom: 5px">
                    <div class="ibox-title">
                        <h5>Data<small></small></h5>
                    </div>
                    <div class="ibox-content" style="padding: 15px" id="data-table">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover dataTables-maprecord" >
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>NPWP</th>
                                        <th>Description</th>
                                        <th>Attribute</th>
                                        <th>GPS Position</th>
                                        <th>Attachment</th>
                                        <th>File Type</th>
                                        <th>Input Date</th>
                                        <th>Input User</th>
                                    </tr>
                                </thead>
                                <tbody class="table-content">
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div>
            <div class="modal inmodal" id="modRecord" tabindex="-1" role="dialog" aria-hidden="true">
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
                                                <label class="col-xs-3 control-label">Name:</label>
                                                <div class="col-xs-9">
                                                    <input name="name" class="form-control" style="border-radius: 4px; width: 95%" id="modal-name" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">NPWP:</label>
                                                <div class="col-xs-7">
                                                    <input name="npwp" class="form-control" style="border-radius: 4px; width: 95%" id="modal-npwp" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Description:</label>
                                                <div class="col-xs-9">
                                                    <input name="description" class="form-control" style="border-radius: 4px; width: 95%" id="modal-description"/>
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
                                                    <input name="nop" class="form-control" style="border-radius: 4px; width: 95%" id="modal-npwp" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Price Per M<sup>2</sup>:</label>
                                                <div class="col-xs-5">
                                                    <input name="ppm" class="form-control" style="border-radius: 4px; width: 95%" id="modal-npwp" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Province:</label>
                                                <div class="col-xs-9">
                                                    <select name="province" id="modal-province" class="form-control" style="width: 95%">
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">City:</label>
                                                <div class="col-xs-9">
                                                    <select name="city" id="modal-city" class="form-control" style="width: 95%">
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">District:</label>
                                                <div class="col-xs-9">
                                                    <select name="district" id="modal-district" class="form-control" style="width: 95%">
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Subdistrict:</label>
                                                <div class="col-xs-9">
                                                    <select name="subDistrict" id="modal-subDistrict" class="form-control" style="width: 95%">
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">RT:</label>
                                                <div class="col-xs-3">
                                                    <input name="rt" class="form-control" style="border-radius: 4px; width: 95%" id="modal-npwp" value=""/>
                                                </div>
                                                <label class="col-xs-1 control-label">RW:</label>
                                                <div class="col-xs-3">
                                                    <input name="rw" class="form-control" style="border-radius: 4px; width: 95%" id="modal-npwp" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Street:</label>
                                                <div class="col-xs-9">
                                                    <input name="street" class="form-control" style="border-radius: 4px; width: 95%" id="modal-npwp" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Street Class:</label>
                                                <div class="col-xs-9">
                                                    <input name="streetClass" class="form-control" style="border-radius: 4px; width: 95%" id="modal-npwp" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Sector:</label>
                                                <div class="col-xs-9">
                                                    <input name="sector" class="form-control" style="border-radius: 4px; width: 95%" id="modal-npwp" value=""/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">Zone:</label>
                                                <div class="col-xs-9">
                                                    <input name="zone" class="form-control" style="border-radius: 4px; width: 95%" id="modal-npwp" value=""/>
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
                                                <div class="col-xs-4">
                                                    <input name="dateTime" class="form-control" style="border-radius: 4px" id="modal-datetime" readonly/>
                                                </div>
                                                <label class="col-xs-1 control-label">By:</label>
                                                <div class="col-xs-4">
                                                    <input class="form-control" style="border-radius: 4px; width: 87%" id="modal-user" value="${actionBean.userSession.username}" readonly/>
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
        </div>
</s:layout-component>
</s:layout-render>
