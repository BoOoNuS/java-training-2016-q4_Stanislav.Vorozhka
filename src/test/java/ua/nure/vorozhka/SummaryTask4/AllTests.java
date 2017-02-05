package ua.nure.vorozhka.SummaryTask4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacadeTest;
import ua.nure.vorozhka.SummaryTask4.web.validator.LoginValidatorTest;

@RunWith(Suite.class)
@SuiteClasses({
        DAOFacadeTest.class,
        LoginValidatorTest.class
})
public class AllTests {

}
