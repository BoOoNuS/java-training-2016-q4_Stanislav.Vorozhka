package ua.nure.vorozhka.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.web.command.common.*;
import ua.nure.vorozhka.SummaryTask4.web.command.common.authenticating.AuthenticateCommand;
import ua.nure.vorozhka.SummaryTask4.web.command.common.authenticating.LogoutCommand;
import ua.nure.vorozhka.SummaryTask4.web.command.common.authenticating.RegCommand;
import ua.nure.vorozhka.SummaryTask4.web.command.route.*;
import ua.nure.vorozhka.SummaryTask4.web.command.route.redirecting.*;
import ua.nure.vorozhka.SummaryTask4.web.command.station.CreateStationCommand;
import ua.nure.vorozhka.SummaryTask4.web.command.station.EditStationCommand;
import ua.nure.vorozhka.SummaryTask4.web.command.station.RemoveStationCommand;
import ua.nure.vorozhka.SummaryTask4.web.command.station.redirecting.ToStationCommand;
import ua.nure.vorozhka.SummaryTask4.web.command.station.redirecting.ToStationCreateCommand;
import ua.nure.vorozhka.SummaryTask4.web.command.station.redirecting.ToStationEditCommand;
import ua.nure.vorozhka.SummaryTask4.web.command.station.redirecting.ToStationRemoveCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanislav on 03.01.2017.
 */
public class CommandContainer {

    private static final Logger LOG = Logger.getLogger(CommandContainer.class);
    private static Map<String, Command> commands = new HashMap<>();

    static {
        // Authenticating command
        commands.put("authenticate", new AuthenticateCommand());
        commands.put("reg", new RegCommand());
        commands.put("logout", new LogoutCommand());

        // EdgeStation command
        commands.put("toStation", new ToStationCommand());
        commands.put("toStationCreate", new ToStationCreateCommand());
        commands.put("toStationEdit", new ToStationEditCommand());
        commands.put("editStation", new EditStationCommand());
        commands.put("toStationRemove", new ToStationRemoveCommand());
        commands.put("removeStation", new RemoveStationCommand());
        commands.put("createStation", new CreateStationCommand());

        // Route command
        commands.put("toRoute", new ToRouteCommand());
        commands.put("toChangeEdgeStation", new ToChangeEdgeStationCommand());
        commands.put("changeEdgeStation", new ChangeEdgeStationCommand());
        commands.put("toRouteCreate", new ToRouteCreateCommand());
        commands.put("createRoute", new CreateRouteCommand());
        commands.put("toRouteEdit", new ToRouteEditCommand());
        commands.put("editRoute", new EditRouteCommand());
        commands.put("toRouteRemove", new ToRouteRemoveCommand());
        commands.put("removeRoute", new RemoveRouteCommand());
        commands.put("toChangeWayStation", new ToChangeWayStationCommand());
        commands.put("toStationOnRouteRemove", new ToStationOnRouteRemove());
        commands.put("removeStationOnRoute", new RemoveStationOnRouteCommand());
        commands.put("toStationOnRouteAdd", new ToStationOnRouteAddCommand());
        commands.put("addStationToRoute", new AddStationToRouteCommand());

        // Common command
        commands.put("toMain", new ToMainCommand());
        commands.put("find", new FindCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("setLanguage", new SetLanguageCommand());

        // Command for authentication user
        commands.put("generateTicket", new GenerateTicketCommand());

        LOG.debug(String.format("All commands --> %s", commands.keySet()));
    }

    public static Command getCommand(String commandName){
        if(commands.containsKey(commandName)){
            return commands.get(commandName);
        }
        LOG.trace(String.format("Command not found, name --> %s", commandName));
        return commands.get("noCommand");
    }
}
