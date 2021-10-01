package com.noriyuki.workshopmongo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.noriyuki.workshopmongo.domain.enums.Perfil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Document(collection = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String name;
    private String email;

    @JsonIgnore
    private String password;

    private Set<Integer> perfis = new HashSet<>();

    @DBRef(lazy = true)
    private List<Post> posts = new ArrayList<>();

    public User() {
        addPerfil(Perfil.USER);
    }

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        addPerfil(Perfil.USER);
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCod());
    }

    public void removePerfil(Perfil perfil) {
        perfis.remove(perfil.getCod());
    }
}
