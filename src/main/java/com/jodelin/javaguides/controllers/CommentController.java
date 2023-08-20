package com.jodelin.javaguides.controllers;

import com.jodelin.javaguides.dtos.CommentDto;
import com.jodelin.javaguides.dtos.CommentResponse;
import com.jodelin.javaguides.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class CommentController {


    private final CommentService commentService;

    @PostMapping("/{idPost}/comments")
    public CommentDto createComment(
            @PathVariable Long idPost,
          @Valid @RequestBody CommentDto commentDto){
      return  commentService.saveComment(idPost,commentDto);
    }

    @GetMapping("/{idPost}/comments")
    public List<CommentDto>  getCommentByPostId(
            @PathVariable Long idPost)
    {
        return  commentService.getCommentByPostId(idPost);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public CommentDto getCommentById(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "id") Long commentId)
    {
        return  commentService.getCommentById(postId, commentId);
    }

    @GetMapping("/comments")
    public CommentResponse getComments(
            @RequestParam(name = "pageNumber",required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "size",required = false,defaultValue = "2")int size
    )
    {
        return  commentService.findAll(pageNumber,size);
    }
}
