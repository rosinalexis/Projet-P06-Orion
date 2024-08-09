package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentDto {

    private Integer id;
    @NotNull
    @NotBlank
    @NotEmpty
    private String content;

    @NotNull
    @Positive
    @JsonProperty("author_id")
    private Integer authorId;
    @NotNull
    @Positive
    @JsonProperty("article_id")
    private Integer articleId;

    @JsonProperty("created_at")
    private LocalDate createdAt;

    @JsonProperty("updated_at")
    private LocalDate updatedAt;

    public static CommentDto fromEntity(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorId(comment.getAuthor().getId())
                .articleId(comment.getArticle().getId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public static List<CommentDto> fromEntity(List<Comment> comments) {
        List<CommentDto> commentDtos = new ArrayList<>();
        if (comments != null) {
            commentDtos = comments.stream().map(
                    CommentDto::fromEntity
            ).collect(Collectors.toList());
        }
        return commentDtos;
    }

    public static Comment toEntity(CommentDto dto) {
        return Comment.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .author(
                        User.builder()
                                .id(dto.getAuthorId())
                                .build()
                )
                .article(Article.builder()
                        .id(dto.getArticleId())
                        .build()
                )
                .build();
    }
}
