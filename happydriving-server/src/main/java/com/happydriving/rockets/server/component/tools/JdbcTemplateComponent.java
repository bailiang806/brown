package com.happydriving.rockets.server.component.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 在本应用中用于简单查询sql语句的功能，由于并没有设置事务，仅能执行查询，也不能建立临时表
 *
 * @author mazhiqiang
 */
@Component
public class JdbcTemplateComponent {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public void executeQuery(String sql){
//        jdbcTemplate.queryForInt()
//    }

    public Map<String, Object> queryForMap(String sql) {
        return jdbcTemplate.queryForMap(sql);
    }

    public List<Map<String, Object>> queryForList(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

}
