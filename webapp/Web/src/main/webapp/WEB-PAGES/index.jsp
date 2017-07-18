<%@ include file="/WEB-PAGES/taglibs.jsp" %>

<s:layout-definition>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

        <head>

            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">

            <title>iMap - Apps Zona Nilai Tanah</title>

            <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">

            <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
            <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/ireload.ico">

            <!-- Mainly scripts -->
            <script src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
            <script src="${pageContext.request.contextPath}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

            <!-- Custom and plugin javascript -->
            <script src="${pageContext.request.contextPath}/js/inspinia.js"></script>
            <script src="${pageContext.request.contextPath}/js/plugins/pace/pace.min.js"></script>

            <!-- Clock -->
            <script src="${pageContext.request.contextPath}/js/moment.min.js"></script>

            <!-- General -->
            <script src="${pageContext.request.contextPath}/js/general.js"></script>

            <style>
                .footer {padding: 5px 10px;}
                .chat-view {margin-bottom: 5px}
                .chat-user {
                    padding: 7px 10px;
                    border-left: 1px solid #e7eaec;
                }
                .chat-users {margin-left: -15px; height: 425px}
                /*.chat-discussion {height: 425px}*/
                textarea {resize: none}
            </style>
        </head>

        <body class="pace-done fixed-sidebar" cz-shortcut-listen="true">
            <div id="wrapper">
                <nav class="navbar-default navbar-static-side" role="navigation">
                    <div class="sidebar-collapse">
                        <ul class="nav metismenu" id="side-menu">
                            <li class="nav-header">
                                <div class="dropdown profile-element">
                                    <span><img alt="image" class="img-circle" src="${pageContext.request.contextPath}/img/user_pajak.png" /></span>
                                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                        <span class="clear"> <span class="block m-t-xs"> <strong id="user-fullname" class="font-bold">${actionBean.userSession.fullName}</strong>
                                            </span> <span class="text-muted text-xs block"> ${actionBean.userSession.role.name} <b class="caret"></b></span> </span> </a>
                                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                        <li><a href="${pageContext.request.contextPath}/Login.html?logout=1">Logout</a></li>
                                    </ul>
                                </div>
                                <div class="logo-element">
                                    <span><img alt="image" class="img-circle" src="${pageContext.request.contextPath}/img/ireload.jpg" /></span>
                                </div>
                            </li>
                            
                            <!-- Main Menu -->
                            ${actionBean.link}
                        </ul>

                    </div>
                </nav>

                <div id="page-wrapper" class="gray-bg">
                    <div class="row border-bottom">
                        <nav class="navbar navbar-static-top white-bg" role="navigation" style="margin-bottom: 0">
                            <div class="navbar-header">
                                <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                            </div>
                            <ul class="nav navbar-top-links navbar-left">
                                <li>
                                    <a>
                                        ${title}
                                    </a>
                                </li>
                            </ul>
                            <ul class="nav navbar-top-links navbar-right desktop">
                                <li>
                                    <a id="now-datetime">
                                        <i class="fa fa-clock-o"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="${pageContext.request.contextPath}/Login.html?logout=1">
                                        <i class="fa fa-sign-out"></i> Log out
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                    <div class="wrapper wrapper-content animated fadeInRight">
                        <c:if test="${not empty actionBean.response}">
                            <div class="row">
                                <div class="col-lg-12">
                            <c:choose>
                                <c:when test="${fn:containsIgnoreCase(actionBean.response, 'fail')}">
                                    <div class="alert alert-danger alert-dismissable">
                                </c:when>
                                <c:otherwise>
                                    <div class="alert alert-success alert-dismissable">
                                </c:otherwise>
                            </c:choose>
                            <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                            ${actionBean.response}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <s:layout-component name="wrapper" />
                    </div>
                    <div class="footer">
                        <div>
                            <strong>Copyright</strong> PT Indo Cipta Guna &copy; 2017
                        </div>
                    </div>

                </div>
            </div>
        </body>
    </html>
</s:layout-definition>
