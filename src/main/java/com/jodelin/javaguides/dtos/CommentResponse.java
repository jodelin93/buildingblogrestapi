package com.jodelin.javaguides.dtos;

import lombok.Data;

import java.util.List;
@Data
public class CommentResponse {
    private List <CommentDto> content;
    private int pageNo;
    private  int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean isLast;

}
