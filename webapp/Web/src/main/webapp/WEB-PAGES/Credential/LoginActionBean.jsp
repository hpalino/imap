<%-- 
    Document   : LoginActionBean
    Created on : Oct 11, 2016, 9:05:14 AM
    Author     : ICG-PRG01
--%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-PAGES/taglibs.jsp" %>


<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>iMap - Apps Zona Nilai Tanah | Login</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/ireload.ico">

    </head>

    <body class="gray-bg">
        <div class="modal-dialog">
            <div class="modal-content modal-sm middle-box text-center loginscreen animated fadeInDown" style="margin-top: 50px; padding-top: 10px; width:360px">
                <div class="modal-header" style="padding: 10px 20px">
                    <div>
                        <span><img class="" src="${pageContext.request.contextPath}/img/pajak.png" style="width: 70%"></img></span>
                    </div>
                    <br/>
                    <h3 style="margin-left:-5px; margin-right: -5px">Welcome to iMap - Apps Zona Nilai Tanah</h3>
                    </small>
                </div>
                <div class="modal-body" style="padding: 15px 20px 10px 20px">
                    <c:if test="${not empty actionBean.response}">
                        <div class="alert alert-danger alert-dismissable">
                            <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>${actionBean.response}
                        </div>
                    </c:if>
                    <p>Please login in.</p>
                    <s:form class="m-t" beanclass="id.co.icg.imap.tax.web.credential.LoginActionBean">
                        <div class="form-group">
                            <input type="text" class="form-control" name="username" placeholder="Username" required=""/>
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" name="password" placeholder="Password" required=""/>
                        </div>
                        <s:submit class="btn btn-primary block full-width m-b" name="login" />
                    </s:form>
                </div>
                <div class="modal-footer" style="text-align: center">
                    <small>PT. Indo Cipta Guna &copy; 2017</small> 
                </div>
            </div>
        </div>
                        
        <!-- Mainly scripts -->
        <script src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>

