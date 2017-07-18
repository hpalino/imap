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
                            <h5>My Profile</h5>
                            <div class="ibox-tools">
                            </div>
                        </div>
                        <div class="ibox-content" style="padding: 15px">
                            <s:form class="m-t" beanclass="id.co.icg.imap.tax.web.user.MyProfileActionBean">
                            <div class="row">
                                <label class="col-sm-2 control-label">User Name :</label>
                                <div class="col-sm-4">
                                    <input type="text" name="username" disabled="true" class="form-control input-sm m-b-sm" value="${actionBean.user.username}"/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">Full Name:</label>
                                <div class="col-sm-4">
                                    <input type="text" name="fullName" class="form-control input-sm m-b-sm" value="${actionBean.user.fullName}"/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">Position:</label>
                                <div class="col-sm-4">
                                    <input type="text" name="position" class="form-control input-sm m-b-sm" value="${actionBean.user.position}"/>
                                </div>
                            </div>
                                
                            <div class="row">
                                <label class="col-sm-2 control-label">Email Address:</label>
                                <div class="col-sm-2">
                                    <input type="text" name="email" class="form-control input-sm m-b-sm" value="${actionBean.user.email}"/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">Phone Number:</label>
                                <div class="col-sm-2">
                                    <input type="text" name="phone" class="form-control input-sm m-b-sm" value="${actionBean.user.phone}"/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">KPP:</label>
                                <div class="col-sm-4">
                                    <input type="text" name="kpp" class="form-control input-sm m-b-sm" value="${actionBean.user.kpp.kpp}" disabled="true"/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2"></label>
                                <div class="col-sm-4">
                                    <s:submit class="btn btn-primary btn-sm" name="save">Save</s:submit>
                                </div>
                            </div>
                            </s:form>
                        </div>
                    </div>
                </div>
                <div class="ibox float-e-margins" style="margin-bottom: 5px">
                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>Change Password</h5>
                            <div class="ibox-tools">
                            </div>
                        </div>
                        <div class="ibox-content" style="padding: 15px">
                            <s:form class="m-t" beanclass="id.co.icg.imap.tax.web.user.MyProfileActionBean">
                            <div class="row">
                                <label class="col-sm-2 control-label">Old Password:</label>
                                <div class="col-sm-3">
                                    <s:password name="oldPassword" class="form-control input-sm m-b-sm"/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">New Password:</label>
                                <div class="col-sm-3">
                                    <s:password name="newPassword" class="form-control input-sm m-b-sm"/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label">Confirm New Password:</label>
                                <div class="col-sm-3">
                                    <s:password name="renewPassword" class="form-control input-sm m-b-sm"/>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2"></label>
                                <div class="col-sm-3">
                                    <s:submit class="btn btn-primary btn-sm" name="changePassword">Change</s:submit>
                                </div>
                            </div>
                            </s:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <link href="${pageContext.request.contextPath}/css/plugins/datapicker/datepicker3.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet"/>

        <!-- Source -->
        <script src="${pageContext.request.contextPath}/js/date.format.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugins/datapicker/datepicker.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugins/dataTables/datatables.min.js"></script>
        <link href="${pageContext.request.contextPath}/css/plugins/dataTables/datatables.min.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/js/plugins/sweetalert/sweetalert.min.js"></script>

        <!-- Page-Level Scripts -->
        <script>
            $(document).ready(function(){
                $('#role').val(${actionBean.user.role.id});
            });
        </script>
</s:layout-component>
</s:layout-render>

