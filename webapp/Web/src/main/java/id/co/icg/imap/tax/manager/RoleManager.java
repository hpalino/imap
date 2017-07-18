/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.manager;

import id.co.icg.imap.tax.dao.model.Menu;
import id.co.icg.imap.tax.dao.model.Role;
import java.util.List;

/**
 *
 * @author Fauzi Marjalih
 */
public interface RoleManager {
    Role  getRole(Integer id);
    List<Role> getRoles();
    List<Role> getRoles(Integer level);
    List<Menu>  getMenus(Integer id);
}
