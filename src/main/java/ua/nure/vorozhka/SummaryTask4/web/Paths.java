package ua.nure.vorozhka.SummaryTask4.web;

/**
 * Created by Stanislav on 03.01.2017.
 */
public interface Paths {

    // Html pages
    String LOGIN_PAGE = "html/authentication.html";

    // Common pages
    String ERROR_PAGE = "WEB-INF/view/jsp/common/error_page.jsp";
    String MAIN_PAGE = "WEB-INF/view/jsp/common/main_page.jsp";

    // Admin pages station pages
    String STATION_PAGE = "WEB-INF/view/jsp/admin/station/station_page.jsp";
    String CREATE_STATION_PAGE = "WEB-INF/view/jsp/admin/station/create_station_page.jsp";
    String EDIT_STATION_PAGE = "WEB-INF/view/jsp/admin/station/edit_station_page.jsp";
    String REMOVE_STATION_CONFIRMATION_PAGE =
            "WEB-INF/view/jsp/admin/station/remove_station_confirmation_page.jsp";

    // Admin pages route pages
    String ROUTE_PAGE = "WEB-INF/view/jsp/admin/route/route_page.jsp";
    String CHANGE_EDGE_STATION_PAGE = "WEB-INF/view/jsp/admin/route/change_edge_station_page.jsp";
    String CREATE_ROUTE_PAGE = "WEB-INF/view/jsp/admin/route/create_route_page.jsp";
    String EDIT_ROUTE_PAGE = "WEB-INF/view/jsp/admin/route/edit_route_page.jsp";
    String REMOVE_ROUTE_CONFIRMATION_PAGE =
            "WEB-INF/view/jsp/admin/route/remove_route_confirmation_page.jsp";

    String CHANGE_WAY_STATION_PAGE = "WEB-INF/view/jsp/admin/route/change_way_station_page.jsp";
    String REMOVE_STATION_ON_ROUTE_CONFIRMATION_PAGE =
            "WEB-INF/view/jsp/admin/route/remove_station_on_route_confirm_page.jsp";

    String ADD_STATION_TO_ROUTE_PAGE = "WEB-INF/view/jsp/admin/route/add_station_to_route_page.jsp";

    // Commands
    String STATION_PAGE_COMMAND = "controller?command=toStation";
    String ROUTE_PAGE_COMMAND = "controller?command=toRoute";

    // Servlets
    String AUTHENTICATE_SERVLET = "authenticate";
    String CHANGE_STATION_SERVLET = "change_station";
    String CHANGE_ROUTE_SERVLET = "change_route";
    String ERROR_SERVLET = "error";
    String TICKET_SERVLET = "ticket";
    String REG_SERVLET = "reg";

    // PDF reports
    String PDF_REPORTS_PATH = "WEB-INF/resources/";
}
