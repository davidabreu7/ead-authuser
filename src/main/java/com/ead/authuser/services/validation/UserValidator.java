package com.ead.authuser.services.validation;

import com.ead.authuser.controllers.exceptions.FieldError;
import com.ead.authuser.dto.UserDto;
import com.ead.authuser.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserValidator implements ConstraintValidator<UsertValid, UserDto> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UsertValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDto dto, ConstraintValidatorContext context) {

        List<FieldError> list = new ArrayList<>();

        if (Boolean.TRUE.equals(userRepository.existsByUsername(dto.getUsername()))){
            list.add(new FieldError("Username", "Usuário ja existente"));
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(dto.getEmail()))){
            list.add(new FieldError("email", "Email ja existe"));
        }
        if (Boolean.TRUE.equals(userRepository.existsByCpf(dto.getCpf()))){
            list.add(new FieldError("cpf", "CPF ja existe"));
        }

        for (FieldError e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getError()).addPropertyNode(e.getField())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
