/*
 * Copyright 2022
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.veerdone.yblog.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.base.KeyValue;
import com.github.veerdone.yblog.cloud.base.model.UserRegisterLog;
import com.github.veerdone.yblog.cloud.common.exception.ServiceException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceExceptionEnum;
import com.github.veerdone.yblog.cloud.user.mapper.UserRegisterLogMapper;
import com.github.veerdone.yblog.cloud.user.service.UserRegisterLogService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Objects;

@Service
public class UserRegisterLogServiceImpl implements UserRegisterLogService {
    private final UserRegisterLogMapper userRegisterLogMapper;

    public UserRegisterLogServiceImpl(UserRegisterLogMapper userRegisterLogMapper) {
        this.userRegisterLogMapper = userRegisterLogMapper;
    }


    @Override
    public Long countRegisterOfLastWeek() {
        LocalDate date = LocalDate.now().minusWeeks(1);
        int year = date.getYear();
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
        int yearWeek = date.get(weekFields.weekOfYear());
        LambdaQueryWrapper<UserRegisterLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRegisterLog::getCreateYear, year)
                .eq(UserRegisterLog::getCreateYearweek, yearWeek);
        return userRegisterLogMapper.selectCount(wrapper);
    }

    @Override
    public List<KeyValue<LocalDate, Long>> countGroupByDayInMonth(Integer month) {
        if (Objects.nonNull(month) && month > 12) {
            throw new ServiceException(ServiceExceptionEnum.PARAM_MISTAKE);
        }
        LocalDate date = LocalDate.now();
        if (Objects.isNull(month) || month == 0) {
            month = date.getMonthValue();
        }
        int year = date.getYear();

        return userRegisterLogMapper.countCurrentMonthGroupByDay(year, month);
    }

    @Override
    public List<KeyValue<LocalDate, Long>> countGroupByLastSevenDay() {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusWeeks(1);

        return userRegisterLogMapper.countGroupByLastSevenDay(start, end);
    }

    @Override
    public List<KeyValue<LocalDate, Long>> countGroupByMonthInYear(Integer year) {
        if (Objects.isNull(year) || year == 0) {
            year = LocalDate.now().getYear();
        }

        return userRegisterLogMapper.countGroupByMonth(year);
    }

    @Override
    public void create(UserRegisterLog userRegisterLog) {
        userRegisterLogMapper.insert(userRegisterLog);
    }
}
