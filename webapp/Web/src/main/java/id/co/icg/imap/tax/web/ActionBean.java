/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.web;

import id.co.icg.imap.tax.dao.model.Menu;
import id.co.icg.imap.tax.dao.model.User;
import id.co.icg.imap.tax.function.ConstantaUtil;
import java.io.IOException;
import java.util.List;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.controller.StripesFilter;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author ICG-PRG01
 */
public class ActionBean implements net.sourceforge.stripes.action.ActionBean {

    private BaseActionBeanContext ctx;

    private final String SESSION_USER = "SESSION_USER";
    private final String SESSION_LINK = "SESSION_LINK";
    private final String SESSION_NOTIF_SOUND = "SESSION_NOTIF_SOUND";
    protected int pageSize = 20;

    @Override
    public BaseActionBeanContext getContext() {
        return this.ctx;
    }

    @Override
    public void setContext(ActionBeanContext ctx) {
        this.ctx = (BaseActionBeanContext) ctx;
    }

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    protected String getView(String group, Class c) {
        return "/WEB-PAGES/" + group + "/" + c.getSimpleName() + ".jsp";
    }

    protected void setUserSession(User user) {
        getContext().setSession(SESSION_USER, user, 600);
    }

    public User getUserSession() {
        User user = (User) getContext().getSession(SESSION_USER);
        return user;
    }

    protected void setNotifSoundSession(Boolean notifSound) {
        getContext().setSession(SESSION_NOTIF_SOUND, notifSound);
    }

    public Boolean getNotifSoundSession() {
        Boolean notifSound = (Boolean) getContext().getSession(SESSION_NOTIF_SOUND);
        return notifSound;
    }

    public String getCurrentUrl() {
        String uri = StripesFilter.getConfiguration().getActionResolver().getUrlBinding(getClass());
        if (getContext().getRequest().getQueryString() != null) {
            uri += "?" + getContext().getRequest().getQueryString().replace("&page=" + pageSize, "").replace("page=" + pageSize, "");
        }
        return uri;
    }

    protected void setMenuSession(List<Menu> menus) {
        getContext().setSession(SESSION_LINK, menus);
    }

    public List<Menu> getUserMenus() {
        List<Menu> menus = (List<Menu>) getContext().getSession(SESSION_LINK);
        return menus;
    }
    
    public String getLink() {
        String menu = "";
        List<Menu> menus = getUserMenus();
        for (Menu menuz : menus) {
            if (menuz.getId().endsWith("0000") && menuz.getLink() != null) {
                menu += "<li><a href=\"" + ConstantaUtil.rootPath + menuz.getLink() + "\"><i class=\"" + menuz.getIcon() + "\"></i> <span class=\"nav-label\">" + menuz.getName() + "</span></a></li>";
            } else if (menuz.getId().endsWith("0000") && menuz.getLink() == null) {
                menu +="<li><a>";
                menu +="<i class=\"" + menuz.getIcon() + "\"></i>";
                menu +="<span class=\"nav-label\">" + menuz.getName() + "</span>";
                menu +="</a>";
                menu += "<ul class=\"nav nav-second-level collapse\">";
                for (Menu men : menus) {
                    if (!men.getId().endsWith("0000") && men.getId().endsWith("00") && men.getId().startsWith(menuz.getId().substring(0, 2))) {
                        menu += "<li><a href=\"" + ConstantaUtil.rootPath + men.getLink() + "\"><i class=\"" + men.getIcon() + "\"></i> <span>" + men.getName() + "</span></a></li>";
                    }
                }
                menu += "</ul></li>";
            }
        }
        return menu;
    }

    public void setPageSize(int page) {
        this.pageSize = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    protected String jSon(List lists) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object[] array = lists.toArray(new Object[lists.size()]);
            return mapper.writeValueAsString(array);
        } catch (IOException e) {
            return null;
        }
    }

    protected String jSon(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            return null;
        }
    }

}
