package ua.nure.vorozhka.SummaryTask4.web.listener;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by Stanislav on 03.01.2017.
 */

@WebListener("/*")
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log("Context init started");

        ServletContext servletContext = servletContextEvent.getServletContext();
        initLog4j(servletContext);
        initCommandContainer();

        log("Context init finished");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log("Context destroy started");
        // no op
        log("Context destroy finished");
    }

    private void initLog4j(ServletContext servletContext) {
        log("Log4j init started");

        PropertyConfigurator.configure(
                servletContext.getRealPath("WEB-INF/resources/log4j.properties"));

        log("Log4j init finished");
    }

    private void initCommandContainer() {
        log("CommandContainer initialization started");

        try {
            Class.forName("ua.nure.vorozhka.SummaryTask4.web.command.CommandContainer");
        } catch (ClassNotFoundException e) {
            log("Cannot initialize Command Container");
            throw new IllegalStateException("Cannot initialize Command Container");
        }

        log("CommandContainer initialization finished");
    }

    private void log(String msg) {
        System.out.println("[ContextListener] " + msg);
    }
}
