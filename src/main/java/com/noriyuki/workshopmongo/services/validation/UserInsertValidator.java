package com.noriyuki.workshopmongo.services.validation;

import com.noriyuki.workshopmongo.domain.User;
import com.noriyuki.workshopmongo.dto.UserNewDTO;
import com.noriyuki.workshopmongo.repository.UserRepository;
import com.noriyuki.workshopmongo.resources.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserInsertValidator implements ConstraintValidator<UserInsert, UserNewDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsert ann) {}

    @Override
    public boolean isValid(UserNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        User aux = userRepository.findByEmail(objDto.getEmail());
        if (aux != null) {
            list.add(new FieldMessage("email", "Email já existente!"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}