package com.jodelin.javaguides.services;

import com.jodelin.javaguides.dtos.CommentDto;
import com.jodelin.javaguides.dtos.CommentResponse;
import com.jodelin.javaguides.entities.Comment;
import com.jodelin.javaguides.entities.Post;
import com.jodelin.javaguides.exceptions.BlogAPIException;
import com.jodelin.javaguides.exceptions.ResourceNotFoundException;
import com.jodelin.javaguides.repositories.CommentRepository;
import com.jodelin.javaguides.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;


    public CommentDto saveComment(Long idPost, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        // retrieve post entity by id
        Post post = postRepository.findById(idPost).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", idPost));

        // set post to comment entity
        comment.setPost(post);

        // comment entity to DB
        Comment newComment =  commentRepository.save(comment);

        return mapToDTO(newComment);
    }


    public CommentResponse findAll(int pageNumber, int size) {
        CommentResponse response = new CommentResponse();

        Pageable page = PageRequest.of(pageNumber, size);
        Page<Comment> content = commentRepository.findAll(page);
        response.setContent( Arrays.asList(mapper.map(content.getContent(), CommentDto[].class)));
        response.setLast(content.isLast());
        response.setPageNo(content.getNumber());
        response.setPageSize(content.getSize());
        response.setTotalElements(content.getTotalElements());
        response.setTotalPages(content.getTotalPages());

        return response;
    }

    public List<CommentDto> getCommentByPostId(Long idPost) {
        // retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(idPost);

        // convert list of comment entities to list of comment dto's
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public CommentDto getCommentById(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDTO(comment);
    }

    private Comment mapToEntity(CommentDto commentDto){

        return mapper.map(commentDto, Comment.class);
    }

    private CommentDto mapToDTO(Comment comment){

        return mapper.map(comment, CommentDto.class);
    }
}
