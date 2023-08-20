package com.jodelin.javaguides.controllers;
import com.jodelin.javaguides.dtos.PostDto;
import com.jodelin.javaguides.dtos.PostResponse;
import com.jodelin.javaguides.services.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/posts")
@SecurityRequirement(name = "Bear authentication")
public class PostController {
    private final PostService postService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public PostDto createPost(@Valid @RequestBody  PostDto postDto){
       return postService.createPost(postDto) ;
    }
@GetMapping("")
    public PostResponse getPosts(
            @RequestParam(name = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(name = "size",defaultValue = "0",required = false) int size,
            @RequestParam(name = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(name = "sortDir",defaultValue = "asc",required = false) String sortDir
){
        return postService.findAllPost(pageNumber, size,sortBy,sortDir);
    }

    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable Long id){
        return postService.findPost(id);
    }

    @PutMapping("/{id}")
    public PostDto updatePost(@PathVariable Long id,@Valid @RequestBody PostDto postDto){
        return postService.updatePost(id,postDto);
    }

    @DeleteMapping("/{id}")
    public void updatePost(@PathVariable Long id){
       postService.deletePost(id);
    }
}
