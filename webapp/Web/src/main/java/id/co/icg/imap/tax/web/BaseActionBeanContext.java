package id.co.icg.imap.tax.web;

import net.sourceforge.stripes.action.ActionBeanContext;

public class BaseActionBeanContext extends ActionBeanContext {

    public void setSession(String key, Object value, int timeout) {
        getRequest().getSession().setMaxInactiveInterval(timeout*60);
        getRequest().getSession().setAttribute(key, value);
    }
	
    public void setSession(String key, Object value) {
        getRequest().getSession().setAttribute(key, value);
    }
	
    public Object getSession(String key) {
        return getRequest().getSession().getAttribute(key);
    }
	
    public void removeSession(String key) {
        getRequest().getSession().removeAttribute(key);
    }
	
    public void destroySession() {
        getRequest().getSession().invalidate();
    }
    
}
