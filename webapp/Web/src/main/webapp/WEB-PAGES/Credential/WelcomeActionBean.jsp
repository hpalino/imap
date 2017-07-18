<%-- 
    Document   : WelcomeActionBean
    Created on : Oct 10, 2016, 1:55:22 PM
    Author     : ICG-PRG01
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-PAGES/taglibs.jsp" %>

<s:layout-render name="/WEB-PAGES/index.jsp" title="Welcome ${actionBean.userSession.role.name}">
    <s:layout-component name="wrapper">
        <div class="row">
            <div class="col-lg-12">
                <div class="text-center m-t-lg">
                    <h1>
                        Welcome in iMap - App Zona Nilai Tanah!
                    </h1>
                    <small>
                        It is an application skeleton for helping you to manage and engage our members quickly.
                    </small>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>