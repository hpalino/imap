<%-- 
    Document   : MemberRegistrationActionBean
    Created on : Sep 19, 2016, 10:02:03 AM
    Author     : Fauzi Marjalih
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-PAGES/taglibs.jsp" %>
<s:layout-render name="/WEB-PAGES/index.jsp" title="">
    <s:layout-component name="wrapper">
        <style>
            .ibox-title {padding: 7px 15px 7px; min-height: 36px;}
            .float-e-margins .btn {margin-bottom: 0px;}
            .wrapper-content {padding-top:10px; padding-bottom:20px;}
            .control-label {font-weight: 400;}
            .form-control {padding: 3px 12px;}
            .form-control.m-b-sm {height: 30px;}
            td {text-align: center;}
            th {text-align: center;}
            .html5buttons {padding-bottom: 8px;}
            .issue-info {width: 35%;}
        </style>
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins" style="margin-bottom: 5px">
                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>Add New User</h5>
                            <div class="ibox-tools">
                            </div>
                        </div>
                        <div class="ibox-content" style="padding: 15px">
                            <s:form class="m-t" beanclass="id.co.icg.imap.tax.web.user.AddUserActionBean">
                            <div class="row">
                                <label class="col-sm-2 control-label">User Name :</label>
                                <div class="col-sm-2">
                                    <input type="text" name="username" class="form-control input-sm m-b-sm" value=""/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">Password:</label>
                                <div class="col-sm-2">
                                    <s:password name="password" class="form-control input-sm m-b-sm"/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">Confirm Password:</label>
                                <div class="col-sm-2">
                                    <s:password name="rePassword" class="form-control input-sm m-b-sm"/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">Full Name:</label>
                                <div class="col-sm-3">
                                    <input type="text" name="fullName" class="form-control input-sm m-b-sm" value=""/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">Position:</label>
                                <div class="col-sm-4">
                                    <input type="text" name="position" class="form-control input-sm m-b-sm" value=""/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">Email Address:</label>
                                <div class="col-sm-3">
                                    <input type="text" name="email" class="form-control input-sm m-b-sm" value=""/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">Phone Number:</label>
                                <div class="col-sm-2">
                                    <input type="text" name="phone" class="form-control input-sm m-b-sm" value=""/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">KPP:</label>
                                <div class="col-sm-4">
                                    <s:select name="kppId" id="kpp" class="form-control m-b-sm" style="width: 316px">
                                        <c:forEach var="object" items="${actionBean.kpps}">
                                            <s:option value="${object.id}">${object.kpp}</s:option>
                                        </c:forEach>
                                    </s:select>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">Role:</label>
                                <div class="col-sm-4">
                                    <s:select name="role" id="role" class="form-control m-b-sm" style="width: 316px">
                                        <c:forEach var="object" items="${actionBean.roles}">
                                            <s:option value="${object.id}">${object.name}</s:option>
                                        </c:forEach>
                                    </s:select>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2"></label>
                                <div class="col-sm-4">
                                    <s:submit class="btn btn-primary btn-sm" name="save">Submit</s:submit>
                                </div>
                            </div>
                            </s:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <link href="${pageContext.request.contextPath}/css/plugins/datapicker/datepicker3.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/css/plugins/select2/select2.min.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet"/>

        <!-- Source -->
        <script src="${pageContext.request.contextPath}/js/date.format.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugins/datapicker/datepicker.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugins/select2/select2.full.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugins/dataTables/datatables.min.js"></script>
        <link href="${pageContext.request.contextPath}/css/plugins/dataTables/datatables.min.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/js/plugins/sweetalert/sweetalert.min.js"></script>

        <!-- Page-Level Scripts -->
        <script>
            $(document).ready(function(){
                $('#role').val($("#role option:first").val());
                $('#kpp').val($("#kpp option:first").val());
            });
        </script>
</s:layout-component>
</s:layout-render>

