package com.six.ems.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.jll.jdbc.base.*;

import java.sql.SQLException;

public class TomcatListener implements ServletContextListener{


    /**
     * 监听web容器关闭
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            MyDruidPoolConnection.destroy();
        } catch (SQLException e) {

        }
    }

    /**
     * 监听web容器启动
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

}