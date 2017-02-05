package ua.nure.vorozhka.SummaryTask4.web.validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.nure.vorozhka.SummaryTask4.exception.validate.ValidateException;

/**
 * Created by Stanislav on 23.01.2017.
 */
public class LoginValidatorTest {

    private IValidator<String> loginValidator;

    @Before
    public void initValidator(){
        loginValidator = LoginValidator.getInstance();
    }

    @After
    public void destroyValidator(){
        loginValidator = null;
    }

    @Test(expected = ValidateException.class)
    public void validateTest1()
            throws ValidateException {

        loginValidator.validate("One");
    }

    @Test(expected = ValidateException.class)
    public void validateTest2()
            throws ValidateException {

        loginValidator.validate("1234567891011121314");
    }
}
