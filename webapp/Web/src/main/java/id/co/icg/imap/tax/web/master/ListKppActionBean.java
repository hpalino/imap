/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.web.master;

import id.co.icg.imap.tax.dao.model.Kpp;
import id.co.icg.imap.tax.dao.model.User;
import id.co.icg.imap.tax.function.FunctionUtil;
import id.co.icg.imap.tax.manager.AreaManager;
import id.co.icg.imap.tax.manager.UserManager;
import id.co.icg.imap.tax.web.ActionBean;
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
@UrlBinding("/kpp/list.html")
public class ListKppActionBean extends ActionBean {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    private Long kppId;
    private User userSession;
    
    private UserManager     userManager;
    private AreaManager     areaManager;

    @SpringBean("userManager")
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @SpringBean("areaManager")
    public void setAreaManager(AreaManager areaManager) {
        this.areaManager = areaManager;
    }

    public Resolution add(){
        return new RedirectResolution(AddKppActionBean.class);
    }

    public String getListKpps(){
        List<Kpp> kpps = areaManager.getKpps();
        return FunctionUtil.jSon(kpps);
    }

    public Resolution remove(){
        String tmpKpp = "";
        tmpKpp = areaManager.getKpp(kppId).getKpp();
        Integer status = areaManager.removeKpp(getKppId());
        logger.info("remove_kpp:" + kppId + ":" + tmpKpp + ":" + status);
        switch (status) {
            case 0:
                setResponse("KPP " + kppId + ":" + tmpKpp + " has been removed.");
                break;
            case 1:
                setResponse("Failed. KPP " + kppId + ":" + tmpKpp + " was not found or has been removed.");
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
        return new ForwardResolution("../WEB-PAGES/MasterKpp/ListKppActionBean.jsp");
    }

    @Before
    public void init() {
        userSession = (User) getContext().getSession("SESSION_USER");
    }

    public Long getKppId() {
        return kppId;
    }

    public void setKppId(Long kppId) {
        this.kppId = kppId;
    }

}
