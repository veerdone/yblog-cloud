package com.github.veerdone.yblog.cloud.interact_review.controller;

import com.github.veerdone.yblog.cloud.base.Dto.thumbsUp.ThumbsUpDto;
import com.github.veerdone.yblog.cloud.base.convert.InteractConvert;
import com.github.veerdone.yblog.cloud.base.model.ThumbsUp;
import com.github.veerdone.yblog.cloud.common.util.async.AsyncExecutor;
import com.github.veerdone.yblog.cloud.interact_review.service.ThumbsUpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interact/thumbs_up")
public class ThumbsUpController {
    private final ThumbsUpService thumbsUpService;

    public ThumbsUpController(ThumbsUpService thumbsUpService) {
        this.thumbsUpService = thumbsUpService;
    }

    @PostMapping("/save")
    public void save(@RequestBody ThumbsUpDto dto) {
        ThumbsUp thumbsUp = InteractConvert.INSTANCE.thumbsUp(dto);
        AsyncExecutor.execute(() -> thumbsUpService.create(thumbsUp));
    }

    @PostMapping("/cancel")
    public void cancel(@RequestBody ThumbsUpDto dto) {
        ThumbsUp thumbsUp = InteractConvert.INSTANCE.thumbsUp(dto);
        AsyncExecutor.execute(() -> thumbsUpService.cancel(thumbsUp));
    }

}
