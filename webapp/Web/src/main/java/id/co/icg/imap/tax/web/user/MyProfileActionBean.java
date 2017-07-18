/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.web.user;

import id.co.icg.imap.tax.dao.model.Kpp;
import id.co.icg.imap.tax.dao.model.Role;
import id.co.icg.imap.tax.dao.model.User;
import id.co.icg.imap.tax.manager.AreaManager;
import id.co.icg.imap.tax.manager.RoleManager;
import id.co.icg.imap.tax.manager.UserManager;
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
@UrlBinding("/user/myprofile.html")
public class MyProfileActionBean extends ActionBean {
    
    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    private String oldPassword;
    private String newPassword;
    private String renewPassword;
    
    private String username;
    private String fullName;
    private Long kppId;
    private String position;
    private String phoneNumber;
    private String email;

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
        User u = userManager.getUser(userSession.getUsername());
        u.setFullName(fullName);
        u.setKpp(areaManager.getKpp(kppId));
        u.setPosition(position);
        u.setPhone(phoneNumber);
        u.setEmail(email);
        u.setRole(userSession.getRole());
        boolean status = userManager.updateUser(u);
        
        if(status) setResponse("Update profile success.");
        else setResponse("Update profile failed.");
        return view();
    }

    public Resolution changePassword() {
        if(newPassword!=null&&renewPassword!=null&&newPassword.equals(renewPassword)){
            int status = userManager.changePassword(getUserSession().getUsername(), oldPassword, newPassword);
            logger.info("Change password: " + status);
            if(status==0) setResponse("Success. Password has been changed.");
            else if(status==1) setResponse("Failed. New password was same with old password or new password was blank.");
            else if(status==2) setResponse("Failed. Old password was incorrect.");
            else if(status>2) setResponse("Failed. Unknown error");
        } else if(newPassword==null||renewPassword==null){
            setResponse("Failed. New password was blank.");
        } else {
            setResponse("Failed. Confirm new password was not same with new password.");
        }
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
        return new ForwardResolution("../WEB-PAGES/UserManagement/MyProfileActionBean.jsp");
    }

    @DontValidate
    public Resolution back() {
        return new ForwardResolution("../WEB-PAGES/UserManagement/MyProfileActionBean.jsp");
    }

    @Before
    public void init() {
        userSession = (User) getContext().getSession("SESSION_USER");
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRenewPassword() {
        return renewPassword;
    }

    public void setRenewPassword(String renewPassword) {
        this.renewPassword = renewPassword;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getKppId() {
        return kppId;
    }

    public void setKppId(Long kppId) {
        this.kppId = kppId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
