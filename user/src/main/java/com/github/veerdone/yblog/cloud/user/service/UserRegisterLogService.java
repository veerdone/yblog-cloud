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

package com.github.veerdone.yblog.cloud.user.service;

import com.github.veerdone.yblog.cloud.base.KeyValue;
import com.github.veerdone.yblog.cloud.base.model.UserRegisterLog;

import java.time.LocalDate;
import java.util.List;

public interface UserRegisterLogService {
    void create(UserRegisterLog userRegisterLog);

    /**
     * the total number of registered users last week
     * @return total number
     */
    Long countRegisterOfLastWeek();

    /**
     * number of registered users in each day of this month
     * @return key is date, value is total number of everyday
     */
    List<KeyValue<LocalDate, Long>> countCurrentMonthGroupByDay();

    /**
     * number of registered users per day in the past 7 days
     * @return key is date, value is total number of everyday
     */
    List<KeyValue<LocalDate, Long>> countGroupByLastSevenDay();

    /**
     * number of registered users per month
     * @return key is month, value is total number of month
     */
    List<KeyValue<LocalDate, Long>> countGroupByMonth();
}
