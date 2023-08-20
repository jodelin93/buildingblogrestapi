package com.jodelin.javaguides.services;

import com.jodelin.javaguides.dtos.PostDto;
import com.jodelin.javaguides.dtos.PostResponse;
import com.jodelin.javaguides.entities.Post;
import com.jodelin.javaguides.exceptions.ResourceNotFoundException;
import com.jodelin.javaguides.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public PostDto createPost(PostDto postDto){
        Post post= mapToEntity(postDto);
        Post postSaved= postRepository.save(post);
        return mapToDTO(postSaved);
    }

    public PostResponse findAllPost(int pageNumber, int size,String sortBy,String sortDir) {
    PostResponse postResponse= new PostResponse();
        Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
       Pageable pageable= PageRequest.of(pageNumber,size, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts =posts.getContent();
        List<PostDto> content= listOfPosts.stream().map(this::mapToDTO).collect(Collectors.toList());
        postResponse.setContent(content);
        postResponse.setLast(posts.isLast());
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());

        return postResponse;
    }

    public PostDto findPost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    public PostDto updatePost(long id, PostDto postDto) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    public void deletePost(long id) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    private PostDto mapToDTO(Post post){
        return mapper.map(post, PostDto.class);
    }

    // convert DTO to entity
    private Post mapToEntity(PostDto postDto){
        return mapper.map(postDto, Post.class);
    }
}
