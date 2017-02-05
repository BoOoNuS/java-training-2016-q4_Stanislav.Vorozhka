package ua.nure.vorozhka.SummaryTask4.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import ua.nure.vorozhka.SummaryTask4.db.connector.AbstractFactory;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.StationDAO;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.UserDAO;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.User;
import ua.nure.vorozhka.SummaryTask4.exception.DBException;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Stanislav on 23.01.2017.
 */
public class DAOFacadeTest {

    @Mock
    AbstractFactory mockAbstractFactory;

    @Mock
    UserDAO mockUserDAO;

    private DAOFacade daoFacade;

    @Before
    public void initFacade() {
        mockUserDAO = mock(UserDAO.class);
        mockAbstractFactory = mock(AbstractFactory.class);
        daoFacade = DAOFacade.getInstance(mockAbstractFactory);
    }

    @After
    public void destroyFacade() {
        daoFacade = null;
        mockAbstractFactory = null;
        mockUserDAO = null;
    }

    @Test
    public void getUserByLoginAndPasswordTest()
            throws DBException, SQLException {

        User expectedUser = new User();
        expectedUser.setFullName("Vorozhka Stanislav");
        expectedUser.setLogin("admin");
        expectedUser.setPassword("admin");

        when(mockAbstractFactory.getUserDAO()).thenReturn(mockUserDAO);

        when(mockUserDAO.getUser(mockAbstractFactory.getConnection(), "admin", "admin")).thenReturn(expectedUser);

        User actualUser = daoFacade.getUserByLoginAndPassword("admin", "admin");

        assertEquals(expectedUser, actualUser);
    }
}
