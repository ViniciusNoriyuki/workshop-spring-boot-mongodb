package com.noriyuki.workshopmongo.services;

import com.noriyuki.workshopmongo.domain.Post;
import com.noriyuki.workshopmongo.domain.User;
import com.noriyuki.workshopmongo.dto.UserDTO;
import com.noriyuki.workshopmongo.repository.UserRepository;
import com.noriyuki.workshopmongo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = userRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
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
        return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
    }

    public void updateReferenceUserNewPost(User user, Post newPost) {
        user.getPosts().addAll(Arrays.asList(newPost));
        userRepository.save(user);
    }

    public void removeReferenceUserPost(Post obj) {
        User user = findById(obj.getAuthor().getId());
        user.getPosts().remove(obj);
        userRepository.save(user);
    }
}
