package com.github.veerdone.yblog.cloud.interact_review.controller;

import com.github.veerdone.yblog.cloud.base.Dto.review.ArticleReviewFailDto;
import com.github.veerdone.yblog.cloud.base.Dto.review.ArticleReviewThroughDto;
import com.github.veerdone.yblog.cloud.base.convert.ReviewConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleReview;
import com.github.veerdone.yblog.cloud.interact_review.service.ArticleReviewService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/review/article")
public class ArticleReviewController {
    @Resource
    private ArticleReviewService articleReviewService;

    @PutMapping("/through")
    public void through(@RequestBody @Validated ArticleReviewThroughDto dto) {
        ArticleReview articleReview = ReviewConvert.INSTANCE.toArticleReview(dto);
        articleReviewService.throughOrFail(articleReview, 1);
    }

    @PutMapping("/fail")
    public void fail(@RequestBody @Validated ArticleReviewFailDto dto) {
        ArticleReview articleReview = ReviewConvert.INSTANCE.toArticleReview(dto);
        articleReviewService.throughOrFail(articleReview, 2);
    }
}
