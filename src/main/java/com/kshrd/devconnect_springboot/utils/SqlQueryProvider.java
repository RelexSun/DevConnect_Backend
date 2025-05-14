package com.kshrd.devconnect_springboot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SqlQueryProvider {
    public String getAllProject(Integer page, Integer size, String name, UUID skill) {
        StringBuilder sql = new StringBuilder("SELECT p.* FROM projects p\n" +
                "    JOIN project_skills ps ON p.project_id = ps.project_id\n" +
                "    JOIN skills s ON s.skill_id = ps.skill_id\n");

        if (name != null || skill != null) {
            sql.append(" WHERE ");
            if (name != null && skill != null) {
                sql.append("s.skill_id = '" + skill + "' AND p.title LIKE '" + name + "%'");
            } else if (name != null) {
                sql.append("p.title LIKE '" + name + "%'");
            } else {
                sql.append("s.skill_id = '" + skill + "'");
            }
        }

        sql.append(" OFFSET " + page + " LIMIT " + size);
        return sql.toString();
    }

    public String getAllProjectByUser(UUID userId, Integer page, Integer size, String name, UUID skill) {
        StringBuilder sql = new StringBuilder("SELECT p.* FROM projects p\n" +
                "    JOIN project_skills ps ON p.project_id = ps.project_id\n" +
                "    JOIN skills s ON s.skill_id = ps.skill_id\n" + " WHERE p.user_id = '" + userId + "'");

        if (name != null || skill != null) {
            sql.append(" AND ");
            if (name != null && skill != null) {
                sql.append("s.skill_id = '" + skill + "' AND p.title LIKE '" + name + "%'");
            } else if (name != null) {
                sql.append("p.title LIKE '" + name + "%'");
            } else {
                sql.append("s.skill_id = '" + skill + "'");
            }
        }
        sql.append(" OFFSET " + page + " LIMIT " + size);
        return sql.toString();
    }

}
