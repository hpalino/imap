/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.web.user;

import id.co.icg.imap.tax.dao.model.User;
import id.co.icg.imap.tax.function.FunctionUtil;
import id.co.icg.imap.tax.manager.UserManager;
import id.co.icg.imap.tax.web.ActionBean;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 *
 * @author Fauzi Marjalih
 */
@UrlBinding("/user/management.html")
public class ListUserActionBean extends ActionBean {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    private String username;
    private String searchKey;
    private User userSession;
    
    private UserManager     userManager;

    @SpringBean("userManager")
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public String getListUsers(){
        List<User> users = userManager.getUsers(userSession);
        List<User> result = new ArrayList<User>();
        if(getSearchKey()!=null){
            for (User user : users) {
                if(user.getFullName().toUpperCase().contains(getSearchKey().toUpperCase())||
                        user.getUsername().toUpperCase().contains(getSearchKey().toUpperCase())||
                        user.getKpp().getKpp().toUpperCase().contains(getSearchKey().toUpperCase())||
                        user.getEmail().toUpperCase().contains(getSearchKey().toUpperCase())||
                        user.getPhone().toUpperCase().contains(getSearchKey().toUpperCase())){
                    result.add(user);
                }
            }
        } else {
            result = users;
        }
        return FunctionUtil.jSon(result);
    }

    public Resolution edit(){
        return new RedirectResolution(EditUserActionBean.class).addParameter("username",username);
    }

    public Resolution add(){
        return new RedirectResolution(AddUserActionBean.class);
    }

    public Resolution remove(){
        Integer status = userManager.removeUser(username);
        logger.info("Remove User:" + username + ":" + status);
        switch (status) {
            case 0:
                setResponse("User " + username + " has been removed.");
                break;
            case 1:
                setResponse("Failed. User " + username + " was not found or has been removed.");
                break;
            default:
                setResponse("Failed. Unknown error.");
                break;
        }
        return view();
    }

    public Resolution search(){
        return view();
    }

    @DefaultHandler
    @DontValidate
    public Resolution view() {
        return new ForwardResolution("../WEB-PAGES/UserManagement/ListUserActionBean.jsp");
    }

    @Before
    public void init() {
        userSession = (User) getContext().getSession("SESSION_USER");
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
