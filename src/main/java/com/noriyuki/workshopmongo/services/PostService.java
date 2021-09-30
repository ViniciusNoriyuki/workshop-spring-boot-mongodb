package com.noriyuki.workshopmongo.services;

import com.noriyuki.workshopmongo.domain.Post;
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
}
