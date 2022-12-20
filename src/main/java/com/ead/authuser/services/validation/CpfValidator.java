package com.ead.authuser.services.validation;

import com.ead.authuser.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class CpfValidator implements ConstraintValidator<CpfValid, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(CpfValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {

        if(Boolean.TRUE.equals(userRepository.existsByCpf(cpf))){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("CPF já existente")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
