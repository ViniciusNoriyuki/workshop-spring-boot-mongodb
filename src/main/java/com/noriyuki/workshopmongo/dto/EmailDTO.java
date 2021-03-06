package com.noriyuki.workshopmongo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class EmailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento obrigatório!")
    @Email(message = "Email inválido!")
    private String email;
}
