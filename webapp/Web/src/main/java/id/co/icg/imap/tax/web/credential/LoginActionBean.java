/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.web.credential;

import id.co.icg.imap.tax.dao.model.Menu;
import id.co.icg.imap.tax.dao.model.User;
import id.co.icg.imap.tax.filter.DoesNotRequireLogin;
import id.co.icg.imap.tax.manager.RoleManager;
import id.co.icg.imap.tax.manager.UserManager;
import id.co.icg.imap.tax.web.ActionBean;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

/**
 *
 * @author ICG-PRG01
 */
@DoesNotRequireLogin
@UrlBinding("/Login.html")
public class LoginActionBean extends ActionBean {

    protected static String beanLogin = "WEB-PAGES/Credential/LoginActionBean.jsp";
    private String response;
    
    @Validate(required = true, label = "Username")
    private String username;
    @Validate(required = true, label = "Password")
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    private UserManager userManager;

    @SpringBean("userManager")
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    private RoleManager roleManager;
    @SpringBean("roleManager")
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public Resolution login() {
        User user = userManager.getUser(username, password);
        if (user != null) {
            if (user.getStatus() == 1) {
                user.setPassword(user.getUsername()); //amankan password
                setUserSession(user);
                setNotifSoundSession(true);

                List<Menu> menus = roleManager.getMenus(user.getRole().getId());
                setMenuSession(menus);

                return new RedirectResolution(WelcomeActionBean.class);
            } else {
                setResponse("Your account has been disabled.");
                return view();
            }
        } else {
            setResponse("Incorrect username & password.");
            return view();
        }
    }

    @DontValidate
    public Resolution logout() {
        getContext().destroySession();
        return new RedirectResolution(LoginActionBean.class);
    }

    @DefaultHandler
    @DontValidate
    public Resolution view() {
        return new ForwardResolution(beanLogin);
    }

}
