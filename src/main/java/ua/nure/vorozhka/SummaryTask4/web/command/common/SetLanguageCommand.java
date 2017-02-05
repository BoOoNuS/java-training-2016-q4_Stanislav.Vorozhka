package ua.nure.vorozhka.SummaryTask4.web.command.common;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.model.constant.Language;
import ua.nure.vorozhka.SummaryTask4.exception.UserInputException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav on 22.01.2017.
 */
public class SetLanguageCommand extends Command{

    private static final Logger LOG = Logger.getLogger(SetLanguageCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws UserInputException {

        LOG.debug("Command starts");

        setLanguageToSession(req);

        LOG.debug("Command finished");
        return Paths.MAIN_PAGE;
    }

    private void setLanguageToSession(HttpServletRequest req)
            throws UserInputException {

        Language language = Language.getLangByName(req.getParameter("language"));

        if(language == null) {
            throw new UserInputException("Incorrect language");
        }

        LOG.trace(String.format("Request parameter: language --> %s", language.getFullLangName()));
        req.getSession().setAttribute("language", language.getName());
    }
}
