/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.co.icg.imap.tax.filter;

import id.co.icg.imap.tax.function.ConstantaUtil;
import net.sourceforge.stripes.config.RuntimeConfiguration;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Hatta Palino
 * 
 */
public class StartupActionBean extends RuntimeConfiguration {

    @Override
    public void init() {
        ConstantaUtil.ctx          = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        ConstantaUtil.rootPath     = getServletContext().getContextPath();
        super.init(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
