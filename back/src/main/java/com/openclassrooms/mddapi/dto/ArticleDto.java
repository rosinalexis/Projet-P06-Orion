package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class ArticleDto {

    private Integer id;

    @NotBlank
    @NotNull
    private String title;

    @NotBlank
    @NotNull
    @NotEmpty
    private String content;

    //@JsonProperty("author_id")
    private Integer authorId;

    @JsonProperty("topic_id")
    private Integer topicId;

    private List<CommentDto> comments;

    @JsonProperty("created_at")
    private LocalDate createdAt;

    @JsonProperty("updated_at")
    private LocalDate updatedAt;

    public static ArticleDto fromEntity(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .authorId(article.getAuthor().getId())
                .topicId(article.getTopic().getId())
                .comments(CommentDto.fromEntity(article.getComments()))
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }

    public static List<ArticleDto> fromEntity(List<Article> articles) {

        List<ArticleDto> articlesList = new ArrayList<>();
        if (articles != null) {
            articlesList = articles.stream().map(
                    ArticleDto::fromEntity
            ).collect(Collectors.toList());
        }
        return articlesList;
    }

    public static Article toEntity(ArticleDto articleDto) {
        return Article.builder()
                .id(articleDto.getId())
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .author(
                        User.builder()
                                .id(articleDto.getAuthorId())
                                .build()
                )
                .topic(
                        Topic.builder()
                                .id(articleDto.getTopicId())
                                .build()
                )
                .build();
    }


}
