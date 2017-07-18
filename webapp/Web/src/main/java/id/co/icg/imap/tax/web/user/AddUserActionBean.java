/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.web.user;

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
@UrlBinding("/user/add.html")
public class AddUserActionBean extends ActionBean {
    
    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    private String password;
    private String rePassword;
    
    private String username;
    private String fullName;
    private Long kppId;
    private String position;
    private String phone;
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
    
    public Resolution save() {
        if(password!=null&&rePassword!=null&&password.equals(rePassword)){
            User u = new User();
            u.setUsername(username);
            u.setFullName(fullName);
            u.setPassword(EncryptUtil.MD5(password));
            u.setKpp(areaManager.getKpp(kppId));
            u.setPosition(position);
            u.setPhone(phone);
            u.setEmail(email);
            u.setRegisterBy(userSession.getUsername());
            u.setStatus(1);
            u.setRole(roleManager.getRole(role));

            int status = userManager.saveUser(u);
            logger.info("Add User:" + status + ":" + jSon(u));

            switch (status) {
                case 0:
                    setResponse("New user added: " + u.getUsername());
                    break;
                case 1:
                    setResponse("Failed. Username was exist.");
                    break;
                case 2:
                    setResponse("Failed. Data was not completed.");
                    break;
                default:
                    setResponse("Failed. Unknown error.");
                    break;
            }
        } else setResponse("Failed. Password should not be empty or Password was not match with Confirm Password.");
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
        return new ForwardResolution("../WEB-PAGES/UserManagement/AddUserActionBean.jsp");
    }

    @DontValidate
    public Resolution back() {
        return new ForwardResolution("../WEB-PAGES/UserManagement/AddUserActionBean.jsp");
    }

    @Before
    public void init() {
        userSession = (User) getContext().getSession("SESSION_USER");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
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

}
