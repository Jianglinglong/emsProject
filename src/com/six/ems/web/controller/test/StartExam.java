package com.six.ems.web.controller.test;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.*;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.impl.users.UserServiceImpl;
import com.six.ems.web.service.interfaces.users.AdminService;

/**
 * Servlet implementation class StartExam
 */
public class StartExam extends BaseServlet {
    private static final long serialVersionUID = 1L;

    public StartExam() {
        super();
    }

    public String showExam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "WEB-INF/jsp/score/showExam.jsp";
    }

    public void getPapers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object userId = request.getSession().getAttribute("userId");
        Integer stuId = new UserServiceImpl().getUserByUserId((Integer) userId).getStuId();
        List<Exam> exams = new ArrayList<>();
        AdminServiceImpl adminService = new AdminServiceImpl();
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("stu_id", new SelectItem(stuId));

        UserClass stuClass = adminService.getTypeByCondition(UserClass.class, condition).get(0);
        condition.clear();
        condition.put("class_id",new SelectItem(stuClass.getClassId()));

        exams = adminService.getTypeByCondition(Exam.class,condition);
        condition.clear();
        //移除已经考试过后的科目
        condition.put("stu_id", new SelectItem(stuId));
        List<Answer> answers = adminService.getTypeByCondition(Answer.class, condition);
        for (Answer answer : answers) {
            for (Exam exam : exams) {
                if (answer.getPaperId() == exam.getPaperID()) {
                    exams.remove(exam);
                    break;
                }
            }
        }
        String json = CollectionUtils.creatDataGritJson(exams, exams.size());
        response.getWriter().print(json);
    }

}
