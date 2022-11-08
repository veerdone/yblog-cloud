package com.github.veerdone.yblog.cloud.base;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListTypeHandler extends BaseTypeHandler<List<Long>> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<Long> longs, JdbcType jdbcType) throws SQLException {
        if (longs.isEmpty()) {
            preparedStatement.setString(i, null);
        }
        StringBuilder sb = new StringBuilder();
        for (Long l : longs) {
            sb.append(l).append(",");
        }
        preparedStatement.setString(i, sb.substring(0, sb.length() - 1));
    }

    @Override
    public List<Long> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String str = resultSet.getString(s);

        return getList(str);
    }

    @Override
    public List<Long> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String str = resultSet.getString(i);

        return getList(str);
    }

    @Override
    public List<Long> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String str = callableStatement.getString(i);

        return getList(str);
    }

    private List<Long> getList(String str) {
        if (StringUtils.hasLength(str)) {
            String[] ids = str.split(",");
            List<Long> list = new ArrayList<>(ids.length);
            for (String id : ids) {
                list.add(Long.parseLong(id));
            }
            return list;
        }

        return Collections.emptyList();
    }
}
