package com.github.veerdone.yblog.cloud.article.controller;

import com.github.veerdone.yblog.cloud.common.response.Result.ListResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleLabelController {

    @GetMapping("/test")
    public ListResult<String> listResult() {
        List<String> list = new ArrayList<>();
        return ListResult.result(list, 1L);
    }
}
