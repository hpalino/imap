/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.co.icg.imap.tax.filter;

import id.co.icg.imap.tax.web.credential.LoginActionBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.exception.ActionBeanNotFoundException;
import net.sourceforge.stripes.exception.DefaultExceptionHandler;

/**
 *
 * @author Hatta Palino
 */
public class MyExceptionHandler extends DefaultExceptionHandler{

//    private static final String VIEW = "/TEMPLATE-WEB/authentification/exception.jsp";
    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    public Resolution handle(ActionBeanNotFoundException e, HttpServletRequest request, HttpServletResponse response) {
        // general exception handling
        logger.error("ERROR : " + e);
        
//        if (request.getSession().getAttribute(FilterConstanta.SESSION_LOGIN) == null) {
//            return new RedirectResolution(LoginActionBean.class);
//        } else {
//            if(request.getServletPath().substring(1).toLowerCase().equals("akurapopo.html")) {
//                return new RedirectResolution(LogoutActionBean.class);
//            } else {
//                return new ForwardResolution(VIEW);
//            }
//            
//        }
        //return new ForwardResolution(VIEW);
        return new RedirectResolution(LoginActionBean.class);
    }       
    
}
