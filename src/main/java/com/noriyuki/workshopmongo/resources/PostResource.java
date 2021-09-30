package com.noriyuki.workshopmongo.resources;

import com.noriyuki.workshopmongo.domain.Post;
import com.noriyuki.workshopmongo.domain.User;
import com.noriyuki.workshopmongo.dto.PostDTO;
import com.noriyuki.workshopmongo.resources.util.URL;
import com.noriyuki.workshopmongo.services.PostService;
import com.noriyuki.workshopmongo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/user/{id}")
    public ResponseEntity<Void> insertPost(@PathVariable String id, @RequestBody PostDTO objDto) {
        User user = userService.findById(id);

        Post obj = postService.fromDTO(user, objDto);
        obj = postService.insert(obj);

        userService.updateReferenceUserNewPost(user, obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id) {
        Post obj = postService.findById(id);

        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/titlesearch")
    public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
        text = URL.decodeParam(text);
        List<Post> list = postService.findByTitle(text);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/fullsearch")
    public ResponseEntity<List<Post>> fullSearch(
            @RequestParam(value = "text", defaultValue = "") String text,
            @RequestParam(value = "minDate", defaultValue = "") String minDate,
            @RequestParam(value = "maxDate", defaultValue = "") String maxDate) {
        text = URL.decodeParam(text);
        Date min = URL.convertDate(minDate, new Date(0L));
        Date max = URL.convertDate(maxDate, new Date());

        List<Post> list = postService.fullSearch(text, min, max);

        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        Post post = postService.delete(id);
        userService.removeReferenceUserPost(post);

        return ResponseEntity.noContent().build();
    }
}
