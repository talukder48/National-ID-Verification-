package app.ec.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = IdentifyFieldsValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIdentifyFields {
    String message() default "Invalid identify fields";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

