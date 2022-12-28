package com.github.veerdone.yblog.cloud.user.controller;

import com.github.veerdone.yblog.cloud.base.KeyValue;
import com.github.veerdone.yblog.cloud.user.service.UserRegisterLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRegisterLogController {
    @Resource
    private UserRegisterLogService userRegisterLogService;

    @GetMapping("/count/register_last_week")
    public Long countRegisterOfLastWeek() {
        return userRegisterLogService.countRegisterOfLastWeek();
    }

    @GetMapping("/count/group_by_day")
    public List<KeyValue<LocalDate, Long>> countGroupByDayInMonth(
            @RequestParam(value = "month", defaultValue = "0") Integer month
    ) {
        return userRegisterLogService.countGroupByDayInMonth(month);
    }

    @GetMapping("/count/group_by_last_seven_day")
    public List<KeyValue<LocalDate, Long>> countGroupByLastSevenDay() {
        return userRegisterLogService.countGroupByLastSevenDay();
    }

    @GetMapping("/count/group_by_month")
    public List<KeyValue<LocalDate, Long>> countGroupByMonthInYear(
            @RequestParam(value = "year", defaultValue = "0") Integer year
    ) {
        return userRegisterLogService.countGroupByMonthInYear(year);
    }
}
