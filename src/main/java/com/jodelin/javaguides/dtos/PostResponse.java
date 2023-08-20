package com.jodelin.javaguides.dtos;

import com.jodelin.javaguides.entities.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostResponse {

    private List<PostDto> content;
    private int pageNo;
    private  int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean isLast;


}
