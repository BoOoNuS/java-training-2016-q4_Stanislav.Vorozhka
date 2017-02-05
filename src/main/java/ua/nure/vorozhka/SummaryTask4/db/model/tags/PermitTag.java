package ua.nure.vorozhka.SummaryTask4.db.model.tags;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.User;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Stanislav on 22.01.2017.
 */
public class PermitTag extends TagSupport {

    private static final Logger LOG = Logger.getLogger(PermitTag.class);

    private String role;

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int doStartTag()
            throws JspException {

        LOG.debug("Tag starts");

        HttpSession session = pageContext.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null || !user.getRole().getName().equals(role)) {

            HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
            session.setAttribute("error", new AppException("Access denied"));

            try {
                response.sendRedirect(Paths.ERROR_SERVLET);
            } catch (IOException e) {
                LOG.error("Failed when redirect to error page", e);
            }
        }

        LOG.debug("Tag finished");
        return super.doStartTag();
    }
}
