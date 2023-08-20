package com.jodelin.javaguides.repositories;

import com.jodelin.javaguides.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
