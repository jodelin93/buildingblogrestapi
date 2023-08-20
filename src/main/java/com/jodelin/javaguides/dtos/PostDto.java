package com.jodelin.javaguides.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class PostDto {
    @NotEmpty
    @Size(max = 50,min = 5,message = "nombre de caractere invalide")
    private String title;
    @NotEmpty
    @NotBlank
    private String content;
    @NotEmpty
    @NotEmpty
    private String description;
    private List <CommentDto> comments;
}
