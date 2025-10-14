package app.ec.validation;

import java.util.Map;
import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdentifyFieldsValidator implements ConstraintValidator<ValidIdentifyFields, Map<String, String>> {

    private static final Set<String> ALLOWED_KEYS = Set.of("nid10Digit", "passportNumber");

    public boolean isValid(Map<String, String> identifyMap, ConstraintValidatorContext context) {
        if (identifyMap == null || identifyMap.isEmpty()) {
            return false;
        }

        for (Map.Entry<String, String> entry : identifyMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (!ALLOWED_KEYS.contains(key)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Invalid identify field: " + key)
                       .addConstraintViolation();
                return false;
            }

            if (value == null || value.trim().isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Empty value for identify field: " + key)
                       .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
