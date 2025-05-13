package com.kshrd.devconnect_springboot.utils;

public class SqlQueryProvider {
    public String getAllProject() {
        StringBuilder sql = new StringBuilder("SELECT * FROM projects");

        return sql.toString();
    }

}
