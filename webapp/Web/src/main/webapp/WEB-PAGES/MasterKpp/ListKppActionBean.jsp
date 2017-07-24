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
                            <h5>List of KPP</h5>
                            <div class="ibox-tools">
                            </div>
                        </div>
                        <div class="ibox-content" style="padding: 15px">
                            <div class="table-responsive">
                                <table class="table table-hover issue-tracker no-margins">
                                    <thead>
                                        <tr>
                                            <th width="5%"></th>
                                            <th width="50%">
                                                KPP
                                            </th>
                                            <th width="11%" align="right">
                                                Action
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
                var data=${actionBean.listKpps};
                r='';
                $.each(data, function(index, item){
                    r +='<tr>' +
                        '    <td>' +
                        '        <img alt="image" class="img-sm" src="${pageContext.request.contextPath}/img/alpha_' + item.kpp.toString().replace("KPP Pratama ","").charAt(0).toLowerCase() + '.png">' +
                        '    </td>' +
                        '    <td class="text-left" style="width:13%">' + item.kpp +
                        '    </td>' +
                        '    <td class="text-right">' +
                        '        <form action="' + window.location.origin + window.location.pathname + '">' +
                        '            <input type="hidden" name="kppId" value="' + item.id + '">' +
                        '            <button name="remove" class="btn btn-danger btn-sm" onclick="return confirm(\'Do you really want to remove this KPP?\');"> Remove</button>' +
                        '        </form>' +
                        '    </td>' +
                        '</tr>';

                });
                $('.table-content').html(r===''?'<tr><td colspan="7">(There is no KPP under your role)</td></tr>':r);
            });
        </script>

    </s:layout-component>
</s:layout-render>
