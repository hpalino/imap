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
import id.co.icg.imap.tax.manager.UserManager;
import id.co.icg.imap.tax.manager.RoleManager;
import id.co.icg.imap.tax.web.ActionBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

/**
 *
 * @author Fauzi Marjalih
 */
@UrlBinding("/user/edit.html")
public class EditUserActionBean extends ActionBean {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    private String username;
    private String fullName;
    private Long kppId;
    private String position;
    private String phone;
    private Integer status;
    private String email;
    private Integer role;

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
    
    public List<Map<String, Object>> getStatuses() {
        List list = new ArrayList();
        Map<String, Object> map = new HashMap();
        map.put("id", 1);
        map.put("status", "Active");
        list.add(map);
        map = new HashMap();
        map.put("id", 0);
        map.put("status", "Inactive");
        list.add(map);
        return list;
    }
    
    public Resolution save() {
        User u = userManager.getUser(getUsername());
        u.setFullName(fullName);
        u.setKpp(areaManager.getKpp(kppId));
        u.setPosition(position);
        u.setPhone(phone);
        u.setEmail(email);
        u.setStatus(status);
        u.setRole(roleManager.getRole(getRole()));
        boolean resp = userManager.updateUser(u);
        
        if(resp) setResponse("Update profile success.");
        else setResponse("Update profile failed.");
        return view();
    }

    @ValidationMethod
    public void otherCheck(ValidationErrors errors) {
        if(user == null) {
            cancel();
        }
    }
    
    @DefaultHandler
    @DontValidate
    public Resolution view() {
        this.setUser(userManager.getUser(getUsername()));
        if(userSession.getRole().getLevel()<user.getRole().getLevel()){
            return new ForwardResolution("../WEB-PAGES/UserManagement/EditUserActionBean.jsp");
        } else {
            return new RedirectResolution(ListUserActionBean.class);
        }
    }

    @DontValidate
    public Resolution cancel() {
        return new RedirectResolution(ListUserActionBean.class);
    }

    @Before
    public void init() {
        userSession = (User) getContext().getSession("SESSION_USER");
    }

    public String getUsername() {
        return username;
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

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
