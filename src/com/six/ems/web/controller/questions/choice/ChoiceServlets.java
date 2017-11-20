package com.six.ems.web.controller.questions.choice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.jll.jdbc.base.SelectItem;

import com.six.ems.entity.tables.Course;


import com.six.ems.utils.ExcelUtils;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.interfaces.users.AdminService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.six.ems.entity.tables.Choice;
import com.six.ems.utils.CollectionUtils;

import com.six.ems.utils.WebRequestUtil;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.questions.ChoiceServiceImpl;

/**
 * Servlet implementation class ChoiceServlet
 */
public class ChoiceServlets extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChoiceServlets() {
        super();
    }

    public String initChoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        return "/WEB-INF/jsp/questions/choice.jsp";

    }

    //查询所有题目
    public void queryChoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取传入当前页和每页显示数
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer rows = Integer.valueOf(request.getParameter("rows"));

        String bChoiceName = request.getParameter("bChoiceName");
        String bChoicedaytimeName = request.getParameter("bChoicedaytimeName");

        AdminService adminService = new AdminServiceImpl();
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        List<Choice> choices = new ArrayList<>();
        List<Choice> choiceArrayList = new ArrayList<>();
        List<Course> courses = new ArrayList<>();

        if (bChoiceName != null & !"".equals(bChoiceName)) {
            condition.put("course_name", new SelectItem(bChoiceName, SelectItem.LikeSelect.CONTAIN));
            courses = adminService.getTypeByCondition(Course.class, condition);
        }
        //时间
        if (courses.size() != 0) {
            condition.clear();
            if (bChoicedaytimeName != null & !"".equals(bChoicedaytimeName)) {
                condition.put("day_time", new SelectItem(bChoicedaytimeName, SelectItem.LikeSelect.CONTAIN));
            }
            for (Course course : courses) {
                condition.put("course_id", new SelectItem(course.getCourseId()));
                List<Choice> choice = adminService.getTypeByCondition(Choice.class, condition);
                if (CollectionUtils.isNotBlank(choice)) {
                    choiceArrayList.addAll(choice);
                }
            }
        }else {
            if (bChoicedaytimeName != null & !"".equals(bChoicedaytimeName)) {
                condition.clear();
                condition.put("day_time", new SelectItem(bChoicedaytimeName, SelectItem.LikeSelect.BEGIN));
                choiceArrayList.addAll(adminService.getTypeByCondition(Choice.class,condition));
            }
        }
        if (condition.size() == 0) {
            choiceArrayList = adminService.getTypeItems(Choice.class);
        }


        int begin = (page - 1) * rows;
        int end = page * rows;
        int size = choiceArrayList.size();
        choices = choiceArrayList.subList(begin, end >= size ? size : end);
        String json = CollectionUtils.creatDataGritJson(choices, size);
        response.getWriter().print(json);
    }


    public void jsonChoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Choice> choiceList = new ChoiceServiceImpl().getAllChoice();

        response.getWriter().print(CollectionUtils.creatDataGritJson(choiceList, choiceList.size()));

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    //删除
    public void deleteChoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String choiceId = request.getParameter("stuId");
//		System.out.println(choiceId);
        // 用逗号分隔id数组
        String[] cho = choiceId.split(",");
        int row = 0;// 定义全局变量
        // 遍历id个数
        for (String ch : cho) {
            Integer cId = Integer.valueOf(ch);
            row += new ChoiceServiceImpl().deleteChoice(cId);
        }
        // 判断是否成功
        if (row == cho.length) {
            response.getWriter().print("OK");
        } else if (row == 0) {
            response.getWriter().print("NO");
        }
    }

    //添加
    public void addChoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类出入对象封装数据---注意(表单名必须与Choice对象的属性相同)
        Choice choice = WebRequestUtil.parseObject(Choice.class, request);
        // 调用工具类存入数据
        int row = new ChoiceServiceImpl().addChoice(choice);
        // 响应结果
        response.getWriter().print(row > 0 ? "OK" : "NO");
    }

    //修改
    public void updateChoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类的封装参数
        Choice choice = WebRequestUtil.parseObject(Choice.class, request);
        // 调用工具类的update方法更新数据
        int update = new ChoiceServiceImpl().updateChoice(choice);
        // 判断  响应相应的结果
        response.getWriter().print(update > 0 ? "OK" : "NO");
    }


    /**
     * 定义把excel内容导入到数据库的方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void importExcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建文件项工厂类对象
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        fileItemFactory.setSizeThreshold(1024 * 1024 * 10);
        fileItemFactory.setRepository(new File("C:/temp"));
        // 上传的解析器对象
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        try {
            // 获取文件项对象
            List<FileItem> list = upload.parseRequest(request);
            if (CollectionUtils.isNotBlank(list)) {
                FileItem item = list.get(0);
                String name = item.getName();
                InputStream in = item.getInputStream();
                // 获取上传文件的名称
//				System.out.println("文件名："+item.getName());
                // 获取上传文件的输入流
//				System.out.println("文件流"+item.getInputStream());

                // 调用导入的方法
//				String result= ChoiceExcelUtils.importFile(name, in, Choice.class);
                String importFile = ExcelUtils.importFile(name, in, Choice.class);
                //String result = ChoiceExcelUtils.importFile(name, in,Choice.class);

                // 响应页面
                response.getWriter().print(importFile);
            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }
}

	


