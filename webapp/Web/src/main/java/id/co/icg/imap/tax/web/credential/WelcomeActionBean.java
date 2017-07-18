/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.web.credential;

import id.co.icg.imap.tax.web.ActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 *
 * @author ICG-PRG01
 */
@UrlBinding("/Welcome.html")
public class WelcomeActionBean extends ActionBean{
    @DefaultHandler
    @DontValidate
    public Resolution view() { 
      return new ForwardResolution("WEB-PAGES/Credential/WelcomeActionBean.jsp");
    }
}
