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
                            <h5>User Management</h5>
                            <div class="ibox-tools">
                            </div>
                        </div>
                        <div class="ibox-content" style="padding: 15px">
                            <div class="m-b-lg">
                                <s:form class="m-t" beanclass="id.co.icg.imap.tax.web.user.ListUserActionBean">
                                <div class="input-group">
                                    <input type="text" name="searchKey" placeholder="Find user ..." class=" form-control">
                                    <span class="input-group-btn">
                                        <s:submit class="btn btn-info" name="search">Search</s:submit>
                                        <s:submit class="btn btn-primary" name="add">New User</s:submit>
                                    </span>
                                </div>
                                </s:form>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-hover issue-tracker no-margins">
                                    <thead>
                                        <tr>
                                            <th width="5%"></th>
                                            <th width="13%">
                                                Full Name
                                            </th>
                                            <th width="8%">Phone</th>
                                            <th width="18%">KPP</th>
                                            <th width="9%">Role</th>
                                            <th width="10%">Registered By</th>
                                            <th width="13%">Registered Date</th>
                                            <th width="13%">Last Login</th>
                                            <th width="11%">
                                            </th>
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
                var data=${actionBean.listUsers};
                r='';
                $.each(data, function(index, item){
                    r +='<tr>' +
                        '    <td>' +
                        '        <img alt="image" class="img-sm" src="${pageContext.request.contextPath}/img/alpha_' + item.fullName.toString().charAt(0).toLowerCase() + '.png">' +
                        '    </td>' +
                        '    <td class="text-left" style="width:13%">' + item.fullName +
                        '        <small style="display: block">' + item.position + '</small>' +
                        '    </td>' +
                        '    <td>' + item.phone + '</td>' +
                        '    <td>' + item.kpp + '</td>' +
                        '    <td class="text-left">' + item.role.name + '</td>' +
                        '    <td>' + item.registerBy + '</td>' +
                        '    <td>' + dateFormat(item.registerDate, "yyyy-mm-dd HH:MM:ss") + '</td>' +
                        '    <td>' + dateFormat(item.lastLogin, "yyyy-mm-dd HH:MM:ss") + '</td>' +
                        '    <td class="text-right">' +
                        '        <form action="' + window.location.origin + window.location.pathname + '">' +
                        '            <input type="hidden" name="username" value="' + item.username + '">' +
                        '            <button name="edit" class="btn btn-primary btn-sm"> Edit</button>' +
                        '            <button name="remove" class="btn btn-danger btn-sm" onclick="return confirm(\'Do you really want to remove this user?\');"> Remove</button>' +
                        '        </form>' +
                        '    </td>' +
                        '</tr>';

                });
                $('.table-content').html(r===''?'<tr><td colspan="7">(There is no user under your role)</td></tr>':r);
            });
        </script>

    </s:layout-component>
</s:layout-render>
