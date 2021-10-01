package com.noriyuki.workshopmongo.services;

import com.noriyuki.workshopmongo.domain.Post;
import com.noriyuki.workshopmongo.domain.User;
import com.noriyuki.workshopmongo.dto.AuthorDTO;
import com.noriyuki.workshopmongo.dto.PostDTO;
import com.noriyuki.workshopmongo.repository.PostRepository;
import com.noriyuki.workshopmongo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post insert(Post obj) {
        return postRepository.save(obj);
    }

    public Post findById(String id) {
        Optional<Post> obj = postRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<Post> findByTitle(String text) {
        return postRepository.searchTitle(text);
    }

    public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
        maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);    // Para comparar até as 24hrs daquele dia (em milissegundos)

        return postRepository.fullSearch(text, minDate, maxDate);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public Post fromDTO(User user, PostDTO objDto) {
        return new Post(null, new Date(System.currentTimeMillis()), objDto.getTitle(), objDto.getBody(), new AuthorDTO(user));
    }
}
