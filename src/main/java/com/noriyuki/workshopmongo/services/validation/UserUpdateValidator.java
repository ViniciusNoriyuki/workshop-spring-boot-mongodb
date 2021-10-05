package com.noriyuki.workshopmongo.services.validation;

import com.noriyuki.workshopmongo.domain.User;
import com.noriyuki.workshopmongo.dto.UserDTO;
import com.noriyuki.workshopmongo.dto.UserNewDTO;
import com.noriyuki.workshopmongo.repository.UserRepository;
import com.noriyuki.workshopmongo.resources.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdate, UserDTO> {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserUpdate ann) {}

    @Override
    public boolean isValid(UserDTO objDto, ConstraintValidatorContext context) {
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String uriId = map.get("id");

        List<FieldMessage> list = new ArrayList<>();

        User aux = userRepository.findByEmail(objDto.getEmail());
        if (aux != null && !aux.getId().equals(uriId)) {
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