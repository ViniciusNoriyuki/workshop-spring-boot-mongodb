package com.noriyuki.workshopmongo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CredentialsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
}
