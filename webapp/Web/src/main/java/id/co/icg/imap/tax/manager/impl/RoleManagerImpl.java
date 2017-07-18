/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.manager.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import id.co.icg.imap.tax.dao.model.Menu;
import id.co.icg.imap.tax.dao.model.Role;
import id.co.icg.imap.tax.manager.RoleManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fauzi Marjalih
 */
@Service("roleManager")
public class RoleManagerImpl implements RoleManager {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<Menu> getMenuParents(String parent, Map<String, Menu> others) {
        List<Menu> menus = new ArrayList<Menu>();
        if(others.get(parent) == null) {
            Menu       menu  = getMenuParent(parent);
             while (menu != null) {
                menus.add(menu);
                if(menu.getParent() != null && others.get(menu.getParent()) == null) menu = getMenuParent(menu.getParent());
                else menu = null;
            }
        } 
        return menus;        
    }
    
    private Menu getMenuParent(String parent) {
        String query = "SELECT * FROM app_menu WHERE id=?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{parent}, new RowMapper<Menu>() {
                @Override
                public Menu mapRow(ResultSet rs, int i) throws SQLException {
                    Menu menu = new Menu();
                    menu.setId(rs.getString("id"));
                    menu.setName(rs.getString("name"));
                    menu.setLink(rs.getString("link"));
                    menu.setParent(rs.getString("parent"));
                    menu.setStatus(rs.getInt("status"));
                    menu.setIcon(rs.getString("icon"));
                    return menu;
                }
            });
        } catch (DataAccessException e) {
            return null;
        }
    }
    
    @Override
    public List<Menu> getMenus(Integer id) {
        String query = "SELECT m.* FROM app_menu m, app_role_menu r WHERE r.menu_id=m.id AND r.role_id=?";
        return jdbcTemplate.query(query, new Object[]{id}, new RowMapper() {
            @Override
            public Menu mapRow(ResultSet rs, int i) throws SQLException {
                Menu menu = new Menu();
                menu.setId(rs.getString("id"));
                menu.setName(rs.getString("name"));
                menu.setLink(rs.getString("link"));
                menu.setParent(rs.getString("parent"));
                menu.setStatus(rs.getInt("status"));
                menu.setIcon(rs.getString("icon"));
                return menu;
            }
        });
    }

    @Override
    public List<Role> getRoles() {
        String query = "SELECT * FROM app_role ";
        return jdbcTemplate.query(query, new Object[]{}, new RowMapper() {
            @Override
            public Role mapRow(ResultSet rs, int i) throws SQLException {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));
                role.setLevel(rs.getInt("level"));
                role.setParent(rs.getInt("parent"));
                return role;
            }
        });
    }

    @Override
    public Role getRole(Integer id) {
        String query = "SELECT * FROM app_role WHERE id=?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<Role>() {
                @Override
                public Role mapRow(ResultSet rs, int i) throws SQLException {
                    Role role = new Role();
                    role.setId(rs.getInt("id"));
                    role.setName(rs.getString("name"));
                    role.setLevel(rs.getInt("level"));
                    role.setParent(rs.getInt("parent"));
                    return role;
                }
            });
        } catch (DataAccessException e) {
            logger.info("role_not_found:" + id);
            return null;
        }
    }

    @Override
    public List<Role> getRoles(Integer level) {
        String query = "SELECT * FROM app_role WHERE level>?";
        try {
            return jdbcTemplate.query(query, new Object[]{level}, new RowMapper() {
                @Override
                public Role mapRow(ResultSet rs, int i) throws SQLException {
                    Role role = new Role();
                    role.setId(rs.getInt("id"));
                    role.setName(rs.getString("name"));
                    role.setLevel(rs.getInt("level"));
                    role.setParent(rs.getInt("parent"));
                    return role;
                }
            });
        } catch (DataAccessException e) {
            logger.error(e.toString());
            return null;
        }
    }

}
