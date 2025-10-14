package app.ec.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.*;

public class VerifyFieldsValidator implements ConstraintValidator<ValidVerifyFields, Map<String, String>> {

    private static final Set<String> ALLOWED_KEYS = Set.of("name", "nameEn", "dateOfBirth");

    @Override
    public boolean isValid(Map<String, String> verifyMap, ConstraintValidatorContext context) {
        if (verifyMap == null || verifyMap.isEmpty()) {
            return false;
        }

        for (Map.Entry<String, String> entry : verifyMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (!ALLOWED_KEYS.contains(key)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Invalid field: " + key)
                       .addConstraintViolation();
                return false;
            }

            if (value == null || value.trim().isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Empty value for field: " + key)
                       .addConstraintViolation();
                return false;
            }

            // Optional: Add format validation here, e.g. date format for "dateOfBirth"
            if ("dateOfBirth".equals(key)) {
                if (!value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Invalid date format for dateOfBirth (expected yyyy-MM-dd)")
                           .addConstraintViolation();
                    return false;
                }
            }
        }

        return true;
    }
}

