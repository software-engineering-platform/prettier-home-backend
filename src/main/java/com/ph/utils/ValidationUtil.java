package com.ph.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ph.exception.customs.JsonValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

public class ValidationUtil {


    public static <T> T convertAndValidate(String jsonString, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();

        T requestObject ;
        try {
            requestObject = mapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(requestObject);


        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();



            for (ConstraintViolation<T> violation : violations) {

                errorMessage.append(violation.getPropertyPath().toString()).append("¨¨")
                        .append(violation.getMessage()).append("¨¨");

            }


            throw new JsonValidationException(errorMessage.toString());
        }


        return requestObject;
    }

}
