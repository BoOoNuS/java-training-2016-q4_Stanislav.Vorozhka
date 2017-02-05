package ua.nure.vorozhka.SummaryTask4.web.validator;

import ua.nure.vorozhka.SummaryTask4.exception.validate.ValidateException;

/**
 * Created by Stanislav on 09.01.2017.
 */
public interface IValidator<T> {

    void validate(T field) throws ValidateException;
}
