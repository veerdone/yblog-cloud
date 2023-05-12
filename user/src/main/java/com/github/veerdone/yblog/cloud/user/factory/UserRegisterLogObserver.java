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

package com.github.veerdone.yblog.cloud.user.factory;

import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.base.model.UserRegisterLog;
import com.github.veerdone.yblog.cloud.user.service.UserRegisterLogService;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;

@Component
public class UserRegisterLogObserver implements UserRegisterObserver {
    private final UserRegisterLogService userRegisterLogService;

    public UserRegisterLogObserver(UserRegisterLogService userRegisterLogService) {
        this.userRegisterLogService = userRegisterLogService;
    }

    @Override
    public void afterRegisterUser(UserInfo userInfo) {
        UserRegisterLog userRegisterLog = new UserRegisterLog();
        userRegisterLog.setUserId(userInfo.getId());
        LocalDate date = LocalDate.now();
        userRegisterLog.setCreateDate(date);
        userRegisterLog.setCreateYear(date.getYear());
        userRegisterLog.setCreateMonth(date.getMonthValue());
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
        userRegisterLog.setCreateYearweek(date.get(weekFields.weekOfYear()));
        userRegisterLogService.create(userRegisterLog);
    }
}
