package app.ec.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
@Documented
@Constraint(validatedBy = VerifyFieldsValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidVerifyFields {
    String message() default "Invalid verify fields";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

