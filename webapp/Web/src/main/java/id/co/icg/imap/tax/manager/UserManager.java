/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.manager;

//import id.co.icg.ireload.messenger.dao.model.User;
import id.co.icg.imap.tax.dao.model.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Fauzi Marjalih
 */
public interface UserManager {
    User        getUser(String username);
    User        getUser(String username, String password);

    List<User>  getUsers();
    List<User>  getUsers(Integer roleId);
    List<User>  getUsers(User userSession);
    
    int changePassword(String username, String oldPassword, String newPassword);
    int removeUser(String username);
    int saveUser(User user);
    boolean updateUser(User user);
    
}
