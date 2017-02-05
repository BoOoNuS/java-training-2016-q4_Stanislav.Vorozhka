package ua.nure.vorozhka.SummaryTask4.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Stanislav on 03.01.2017.
 */

@WebFilter("/*")
public class EncodingFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(EncodingFilter.class);
    private static String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.debug("EncodingFilter initialization starts");

        getEncoding(filterConfig.getServletContext());
        LOG.trace(String.format("Encoding from props --> %s", encoding));

        LOG.debug("EncodingFilter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        LOG.debug("EncodingFilter starts");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String currentEncoding = req.getCharacterEncoding();
        if(currentEncoding == null){
            LOG.trace(String.format("Request encoding = null, set encoding --> %s", encoding));
            req.setCharacterEncoding(encoding);
        }

        LOG.debug("EncodingFilter finished");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        LOG.debug("EncodingFilter destruction starts");
        // no op
        LOG.debug("EncodingFilter destruction finished");
    }

    private void getEncoding(ServletContext context){
        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream(
                    context.getRealPath("WEB-INF/resources/settings.properties"));
            props.load(fis);
            encoding = props.getProperty("encoding");
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
        }
    }
}
