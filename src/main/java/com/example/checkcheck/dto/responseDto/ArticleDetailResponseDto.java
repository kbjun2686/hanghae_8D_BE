package com.example.checkcheck.dto.responseDto;

import com.example.checkcheck.model.articleModel.Article;
import com.example.checkcheck.model.articleModel.Process;
import com.example.checkcheck.util.TimeStamped;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ArticleDetailResponseDto extends TimeStamped {

    private Long articleId;
    private int price;
    private String nickName;
    private Process process;
    private String title;
    private String userRank;
    private List<String> images;
    private Boolean isMyArticles;

    @Builder
    public ArticleDetailResponseDto(Article article, List<String> image, Boolean isMyArticles) {
        this.articleId = article.getArticleId();
        this.price = article.getPrice();
        this.nickName = article.getNickName();
        this.process = article.getProcess();
        this.title = article.getTitle();
        this.userRank = article.getUserRank();
        this.images = image;
        this.isMyArticles = isMyArticles;
    }
}
