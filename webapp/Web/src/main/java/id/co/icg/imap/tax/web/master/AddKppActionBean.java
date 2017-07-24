/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.web.master;

import id.co.icg.imap.tax.dao.model.Kpp;
import id.co.icg.imap.tax.dao.model.Role;
import id.co.icg.imap.tax.dao.model.User;
import id.co.icg.imap.tax.manager.RoleManager;
import id.co.icg.imap.tax.manager.UserManager;
import id.co.icg.imap.tax.function.EncryptUtil;
import id.co.icg.imap.tax.manager.AreaManager;
import id.co.icg.imap.tax.web.ActionBean;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

/**
 *
 * @author Fauzi Marjalih
 */
@UrlBinding("/kpp/add.html")
public class AddKppActionBean extends ActionBean {
    
    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    private String kpp;

    private User user;
    private User userSession;
    private UserManager userManager;
    private RoleManager roleManager;
    private AreaManager areaManager;

    @SpringBean("userManager")
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @SpringBean("roleManager")
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @SpringBean("areaManager")
    public void setAreaManager(AreaManager areaManager) {
        this.areaManager = areaManager;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoles() {
        return roleManager.getRoles(userSession.getRole().getLevel());
    }
    
    public List<Kpp> getKpps() {
        return areaManager.getKpps();
    }
    
    public Resolution save() {
        if(kpp!=null){
            Kpp k = new Kpp();
            k.setKpp(kpp);
            int status = areaManager.saveKpp(k);
            logger.info("add_kpp:" + status + ":" + k.getKpp());
        } else setResponse("Failed to save new KPP.");
        return view();
    }

    @ValidationMethod
    public void otherCheck(ValidationErrors errors) {
        if(user == null) {
            back();
        }
    }
    
    @DefaultHandler
    @DontValidate
    public Resolution view() {
        this.setUser(userManager.getUser(userSession.getUsername()));
        return new ForwardResolution("../WEB-PAGES/MasterKpp/AddKppActionBean.jsp");
    }

    @DontValidate
    public Resolution back() {
        return new ForwardResolution("../WEB-PAGES/MasterKpp/AddKppActionBean.jsp");
    }

    @Before
    public void init() {
        userSession = (User) getContext().getSession("SESSION_USER");
    }

    public String getKpp() {
        return this.kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

}
