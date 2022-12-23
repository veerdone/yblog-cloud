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

package com.github.veerdone.yblog.cloud.base.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@TableName("yblog_user_register_log")
public class UserRegisterLog implements Serializable {
    private static final Long serialVersionUID = 434324324234343L;

    private Long id;

    private Long userId;

    private LocalDate createDate;

    private Integer createYear;

    private Integer createMonth;

    private Integer createYearweek;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateYear() {
        return createYear;
    }

    public void setCreateYear(Integer createYear) {
        this.createYear = createYear;
    }

    public Integer getCreateYearweek() {
        return createYearweek;
    }

    public void setCreateYearweek(Integer createYearweek) {
        this.createYearweek = createYearweek;
    }

    public Integer getCreateMonth() {
        return createMonth;
    }

    public void setCreateMonth(Integer createMonth) {
        this.createMonth = createMonth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRegisterLog that = (UserRegisterLog) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(createDate, that.createDate) && Objects.equals(createYear, that.createYear) && Objects.equals(createMonth, that.createMonth) && Objects.equals(createYearweek, that.createYearweek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, createDate, createYear, createMonth, createYearweek);
    }

    @Override
    public String toString() {
        return "UserRegisterLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", createDate=" + createDate +
                ", createYear=" + createYear +
                ", createMonth=" + createMonth +
                ", createYearweek=" + createYearweek +
                '}';
    }
}
