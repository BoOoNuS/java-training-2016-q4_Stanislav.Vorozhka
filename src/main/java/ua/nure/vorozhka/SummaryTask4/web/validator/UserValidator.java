package ua.nure.vorozhka.SummaryTask4.web.validator;

import ua.nure.vorozhka.SummaryTask4.db.model.entity.User;
import ua.nure.vorozhka.SummaryTask4.exception.validate.ValidateException;

/**
 * Created by Stanislav on 20.01.2017.
 */
public class UserValidator implements IValidator<User> {

    private static final IValidator<String> FULL_NAME_VALIDATOR = FullNameValidator.getInstance();
    private static final IValidator<String> LOGIN_VALIDATOR = LoginValidator.getInstance();
    private static final IValidator<String> PASSWORD_VALIDATOR = PasswordValidator.getInstance();

    private static IValidator instance = new UserValidator();

    private UserValidator() {
    }

    public static IValidator getInstance(){
        return instance;
    }

    @Override
    public void validate(User user) throws ValidateException {
        FULL_NAME_VALIDATOR.validate(user.getFullName());
        LOGIN_VALIDATOR.validate(user.getLogin());
        PASSWORD_VALIDATOR.validate(user.getPassword());
    }
}
