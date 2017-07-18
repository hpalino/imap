package id.co.icg.imap.tax.filter;

import id.co.icg.imap.tax.function.ConstantaUtil;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import net.sourceforge.stripes.controller.DispatcherServlet;

public class StartupDispatcherServlet extends DispatcherServlet {

    private static final long serialVersionUID = 8196753317373597590L;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ConstantaUtil.WEB_CONTENT_LOCATION = config.getServletContext().getRealPath("/WEB-PAGES/") + "/";
        ConstantaUtil.WEB_INF_LOCATION = config.getServletContext().getRealPath("/WEB-INF/").replaceAll("\\\\", "/") + "/";
        ConstantaUtil.WEB_IMAGE_ICG = ConstantaUtil.WEB_CONTENT_LOCATION + "/images/ireload.jpg";

//      XmlParser xmlParser = new XmlParser(config.getServletContext().getRealPath("/WEB-INF/") + "/");
//      xmlParser.run();
    }

}
