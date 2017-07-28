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
</s:layout-component>
</s:layout-render>
