package com.noriyuki.workshopmongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 1, max = 280, message = "O tamanho deve ser entre 1 e 280 caracteres")
    private String title;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 1, max = 280, message = "O tamanho deve ser entre 1 e 280 caracteres")
    private String body;
}
