package com.jodelin.javaguides.repositories;

import com.jodelin.javaguides.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(Long idPost);
}
