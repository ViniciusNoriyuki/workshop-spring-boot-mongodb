package com.noriyuki.workshopmongo.dto;

import com.noriyuki.workshopmongo.domain.User;
import com.noriyuki.workshopmongo.services.validation.UserUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@UserUpdate
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 3, max = 80, message = "O tamanho deve ser entre 3 e 80 caracteres")
    private String name;

    @NotEmpty(message = "Preenchimento obrigatório!")
    @Email(message = "Email inválido!")
    private String email;

    public UserDTO(User obj) {
        id = obj.getId();
        name = obj.getName();
        email = obj.getEmail();
    }
}
