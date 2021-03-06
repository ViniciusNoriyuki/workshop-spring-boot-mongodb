package com.noriyuki.workshopmongo.services;

import com.noriyuki.workshopmongo.domain.Post;
import com.noriyuki.workshopmongo.domain.User;
import com.noriyuki.workshopmongo.domain.enums.Perfil;
import com.noriyuki.workshopmongo.dto.UserNewDTO;
import com.noriyuki.workshopmongo.dto.UserDTO;
import com.noriyuki.workshopmongo.repository.UserRepository;
import com.noriyuki.workshopmongo.security.UserSS;
import com.noriyuki.workshopmongo.services.exception.AuthorizationException;
import com.noriyuki.workshopmongo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = userRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public void authorizeOwnUserOrAdmin(String id) {
        UserSS userSS = authenticated();
        if (userSS == null || !userSS.hasHole(Perfil.ADMIN) && !id.equals(userSS.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<User> obj = userRepository.findById(id);

        obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Transactional
    public User insert(User obj) {
        return userRepository.save(obj);
    }

    public void delete(String id) {
        findById(id);

        userRepository.deleteById(id);
    }

    public User update(User obj) {
        User newObj = findById(obj.getId());
        updateData(newObj, obj);

        return userRepository.save(newObj);
    }

    private void updateData(User newObj, User obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public User fromDTO(UserDTO objDto) {
        return new User(objDto.getId(), objDto.getName(), objDto.getEmail(), null);
    }

    public User fromDTO(UserNewDTO objDto) {
        return new User(null, objDto.getName(), objDto.getEmail(), bCryptPasswordEncoder.encode(objDto.getPassword()));
    }

    public void updateReferenceUserNewPost(User user, Post newPost) {
        user.getPosts().addAll(Arrays.asList(newPost));
        userRepository.save(user);
    }

    public void removeReferenceUserPost(String id, Post obj) {
        User user = findById(id);
        user.getPosts().remove(obj);
        userRepository.save(user);
    }

    public static UserSS authenticated() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
