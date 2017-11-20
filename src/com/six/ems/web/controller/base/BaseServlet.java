package com.six.ems.web.controller.base;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends javax.servlet.http.HttpServlet {
    private static final long serialVersionUID = 8099575585358208170L;

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        Class<? extends BaseServlet> reqClass = this.getClass();
        req.setCharacterEncoding("utf-8");
        res.setContentType("text/html;charset=utf-8");

        String methodName = req.getParameter("method");
        try {
            Method method = reqClass.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            Object invoke = null;
            invoke = method.invoke(this, req, res);
            if (invoke != null) {
                req.getRequestDispatcher((String) invoke).forward(req, res);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
