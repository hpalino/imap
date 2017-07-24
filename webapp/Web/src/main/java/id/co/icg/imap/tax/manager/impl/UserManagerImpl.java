/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.manager.impl;

import id.co.icg.imap.tax.dao.model.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import id.co.icg.imap.tax.dao.model.User;
import id.co.icg.imap.tax.function.EncryptUtil;
import static id.co.icg.imap.tax.function.FunctionUtil.dateTimeFormat;
import id.co.icg.imap.tax.manager.AreaManager;
import id.co.icg.imap.tax.manager.RoleManager;
import id.co.icg.imap.tax.manager.UserManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fauzi Marjalih
 */
@Service("userManager")
public class UserManagerImpl implements UserManager {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Resource(name = "roleManager")
    private RoleManager     roleManager;

    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @Resource(name = "areaManager")
    private AreaManager     areaManager;

    public void setAreaManager(AreaManager areaManager) {
        this.areaManager = areaManager;
    }

    @Override
    public User getUser(String username) {
        String query = "select u.* from app_user u where u.username=?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{username}, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setFullName(rs.getString("full_name"));
                    user.setPhone(rs.getString("phone"));
                    user.setEmail(rs.getString("email"));
                    user.setPosition(rs.getString("position"));
                    user.setKpp(rs.getObject("kpp_id")!=null?areaManager.getKpp(rs.getLong("kpp_id")):null);
                    user.setStatus(rs.getInt("status"));
                    user.setRegisterBy(rs.getString("register_by"));
                    user.setRegisterDate(new java.util.Date(rs.getTimestamp("register_date").getTime()));
                    user.setLastLogin(rs.getObject("last_login")!=null ? new java.util.Date(rs.getTimestamp("last_login").getTime()):null);
                    user.setRole(roleManager.getRole(rs.getInt("role_id")));
                    return user;
                }
            });
        } catch (DataAccessException e) {
            logger.info("user_not_found:" + username);
            return null;
        }
    }

    @Override
    public User getUser(String username, String password) {
        String query = "select u.* from app_user u where u.username=? and u.password=?";
        String passwordmd5 = getHash(password, "MD5");
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{username, passwordmd5}, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setFullName(rs.getString("full_name"));
                    user.setPhone(rs.getString("phone"));
                    user.setEmail(rs.getString("email"));
                    user.setPosition(rs.getString("position"));
                    user.setKpp(rs.getObject("kpp_id")!=null?areaManager.getKpp(rs.getLong("kpp_id")):null);
                    user.setStatus(rs.getInt("status"));
                    user.setRegisterBy(rs.getString("register_by"));
                    user.setRegisterDate(new java.util.Date(rs.getTimestamp("register_date").getTime()));
                    user.setLastLogin(rs.getObject("last_login")!=null ? new java.util.Date(rs.getTimestamp("last_login").getTime()):null);
                    user.setRole(roleManager.getRole(rs.getInt("role_id")));
                    return user;
                }
            });
        } catch (DataAccessException e) {
            logger.info("user_not_found:" + username);
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        String query = " SELECT u.* FROM app_user u ";
        try {
            return jdbcTemplate.query(query, new Object[]{}, new RowMapper() {
                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setFullName(rs.getString("full_name"));
                    user.setKpp(rs.getObject("kpp_id")!=null?areaManager.getKpp(rs.getLong("kpp_id")):null);
                    user.setPosition(rs.getString("position"));
                    user.setPhone(rs.getString("phone"));
                    user.setEmail(rs.getString("email"));
                    user.setStatus(rs.getInt("status"));
                    user.setRegisterBy(rs.getString("register_by"));
                    user.setRegisterDate(new java.util.Date(rs.getTimestamp("register_date").getTime()));
                    user.setLastLogin(rs.getObject("last_login")!=null ? new java.util.Date(rs.getTimestamp("last_login").getTime()):null);
                    user.setRole(roleManager.getRole(rs.getInt("role_id")));
                    return user;
                }
            });
        } catch (DataAccessException e) {
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public List<User> getUsers(Integer roleId) {
        String query = " SELECT u.* FROM app_user u WHERE role_id=?";
        try {
            return jdbcTemplate.query(query, new Object[]{roleId}, new RowMapper() {
                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setFullName(rs.getString("full_name"));
                    user.setKpp(rs.getObject("kpp_id")!=null?areaManager.getKpp(rs.getLong("kpp_id")):null);
                    user.setPosition(rs.getString("position"));
                    user.setPhone(rs.getString("phone"));
                    user.setEmail(rs.getString("email"));
                    user.setStatus(rs.getInt("status"));
                    user.setRegisterBy(rs.getString("register_by"));
                    user.setRegisterDate(new java.util.Date(rs.getTimestamp("register_date").getTime()));
                    user.setLastLogin(rs.getObject("last_login")!=null ? new java.util.Date(rs.getTimestamp("last_login").getTime()):null);
                    user.setRole(roleManager.getRole(rs.getInt("role_id")));
                    return user;
                }
            });
        } catch (DataAccessException e) {
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public int changePassword(String username, String oldPassword, String newPassword) {
        String query;
        User user = getUser(username, oldPassword);
        if(user!=null){
            if(!(newPassword==null||newPassword.equals(oldPassword)||newPassword.equals(""))){
                query = "UPDATE app_user SET password=? WHERE username=?";
                int[] arg = {Types.VARCHAR, Types.VARCHAR};
                try {
                    jdbcTemplate.update(query, new Object[]{EncryptUtil.MD5(newPassword), user.getUsername()}, arg);
                    return 0;
                } catch (DataAccessException e){
                    logger.error(e.toString());
                    return 4;
                }
            } else return 1;
        } else return 2;
    }

    @Override
    public int removeUser(String username) {
        String query;
        User user = getUser(username);
        if(user!=null){
            query = "DELETE FROM app_user WHERE username=?";
            int[] arg = {Types.VARCHAR};
            try {
                jdbcTemplate.update(query, new Object[]{user.getUsername()}, arg);
                return 0;
            } catch (DataAccessException e){
                logger.error(e.toString());
                return 4;
            }
        } else return 1;
    }

    @Override
    public int saveUser(User user) {
        String query;
        if(getUser(user.getUsername())==null){
            if(user.getFullName()!=null
                &&user.getPassword()!=null
                &&user.getStatus()!=null
                &&user.getRole().getId()!=null){
                    query = " INSERT INTO app_user("
                            + " username, password, full_name, kpp_id, "
                            + " position, email, phone, role_id, "
                            + " status, register_by, register_date) "
                            + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    int[] arg = {   Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BIGINT, 
                                    Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, 
                                    Types.INTEGER, Types.VARCHAR, Types.TIMESTAMP};
                    try {
                        jdbcTemplate.update(query, new Object[]{
                            user.getUsername(), user.getPassword(), user.getFullName(), user.getKpp().getId(), 
                            user.getPosition(), user.getEmail(), user.getPhone(), user.getRole().getId(), 
                            user.getStatus(), user.getRegisterBy(), dateTimeFormat(new Date(), "yyyy-MM-dd HH:mm:ss")}, arg);
                        return 0;
                    } catch (DataAccessException e){
                        logger.error(e.toString());
                        return 3;
                    }
            } else return 2;
        } else return 1;
    }

    @Override
    public boolean updateUser(User user) {
        String query;
        if(getUser(user.getUsername())!=null){
            if(user.getFullName()!=null
                &&user.getStatus()!=null
                &&user.getRole()!=null){

                query = "UPDATE app_user SET full_name=?, kpp_id=?, position=?, email=?, phone=?, role_id=?, status=? WHERE username=?";
                int[] arg = {Types.VARCHAR, Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR};
                try {
                    jdbcTemplate.update(query, new Object[]{user.getFullName(), user.getKpp().getId(), user.getPosition(), user.getEmail(), user.getPhone(), user.getRole().getId(), user.getStatus(), user.getUsername()}, arg);
                    return true;
                } catch (DataAccessException e) {
                    logger.error(e.toString());
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean updateLastLogin(User user) {
        String query;
        if(getUser(user.getUsername())!=null){
            query = "UPDATE app_user SET last_login=? WHERE username=?";
            int[] arg = {Types.VARCHAR, Types.VARCHAR};
            try {
                jdbcTemplate.update(query, new Object[]{dateTimeFormat(new Date(), "yyyy-MM-dd HH:mm:ss"), user.getUsername()}, arg);
                return true;
            } catch (DataAccessException e) {
                logger.error(e.toString());
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<User> getUsers(User userSession) {
        Role role=roleManager.getRole(userSession.getRole().getId());
        if(role!=null){
            String query = "SELECT u.* FROM app_user u, app_role r WHERE r.id=u.role_id AND r.level>(SELECT DISTINCT `level` FROM app_role WHERE id=?)";
            try {
                return jdbcTemplate.query(query, new Object[]{role.getLevel()}, new RowMapper() {
                    @Override
                    public User mapRow(ResultSet rs, int i) throws SQLException {
                        User user = new User();
                        user.setUsername(rs.getString("username"));
                        user.setFullName(rs.getString("full_name"));
                        user.setKpp(rs.getObject("kpp_id")!=null?areaManager.getKpp(rs.getLong("kpp_id")):null);
                        user.setPosition(rs.getString("position"));
                        user.setPhone(rs.getString("phone"));
                        user.setEmail(rs.getString("email"));
                        user.setStatus(rs.getInt("status"));
                        user.setRegisterBy(rs.getString("register_by"));
                        user.setRegisterDate(new java.util.Date(rs.getTimestamp("register_date").getTime()));
                        user.setLastLogin(rs.getObject("last_login")!=null ? new java.util.Date(rs.getTimestamp("last_login").getTime()):null);
                        user.setRole(roleManager.getRole(rs.getInt("role_id")));
                        return user;
                    }
                });
            } catch (DataAccessException e){
                logger.error(e.toString());
                return null;
            }
        } else return null;
    }

    public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
