package com.six.ems.web.controller.questions.judge;

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
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.entity.tables.Judge;

import com.six.ems.entity.tables.Course;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.interfaces.users.AdminService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;




import com.six.ems.utils.CollectionUtils;
import com.six.ems.utils.JudgeExcelUtils;
import com.six.ems.utils.WebRequestUtil;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.questions.JudgeServiceImpl;

/**
 * Servlet implementation class JudgeServlet
 */
public class JudgeServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public JudgeServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public String initJudge(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        return "/WEB-INF/jsp/questions/judge.jsp";
    }

    //查询所有题目
    public void queryJudge(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer rows = Integer.valueOf(request.getParameter("rows"));

        String bJudgeName = request.getParameter("bJudgeName");
        String daytimejudge = request.getParameter("daytimejudge");

        AdminService adminService = new AdminServiceImpl();
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        List<Judge> judges = new ArrayList<>();
        List<Judge> judgeList = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        if (bJudgeName != null & !"".equals(bJudgeName)) {
			condition.put("course_name", new SelectItem(bJudgeName, SelectItem.LikeSelect.CONTAIN));
			courses = adminService.getTypeByCondition(Course.class, condition);
		}
		if (courses.size() != 0) {
            condition.clear();
			if (daytimejudge != null & !"".equals(daytimejudge)) {
				condition.put("day_time", new SelectItem(daytimejudge, SelectItem.LikeSelect.BEGIN));
			}
			for (Course course : courses) {
				condition.put("course_id", new SelectItem(course.getCourseId()));
				List<Judge> judge = adminService.getTypeByCondition(Judge.class, condition);
				if (CollectionUtils.isNotBlank(judge)) {
					judgeList.addAll(judge);
				}
			}
		} else {
			// 时间
			if (daytimejudge != null & !"".equals(daytimejudge)) {
				condition.put("day_time", new SelectItem(daytimejudge, SelectItem.LikeSelect.BEGIN));
			}
			judgeList = adminService.getTypeByCondition(Judge.class, condition);
			

		}
        
     
        int begin = (page - 1) * rows;
        int end = page * rows;
        int size = judgeList.size();
        judges=judgeList.subList(begin, end>=size?size:end);

        String json = CollectionUtils.creatDataGritJson(judges, size);
        response.getWriter().print(json);
    }

    public void jsonJudge(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Judge> judgeList = new JudgeServiceImpl().getAllJudge();

        response.getWriter().print(CollectionUtils.creatDataGritJson(judgeList, judgeList.size()));

    }

    //删除
    public void deleteJudge(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String JudgeId = request.getParameter("stuId");
        // 用逗号分隔id数组
        String[] cho = JudgeId.split(",");
        int row = 0;// 定义全局变量
        // 遍历id个数
        AdminService adminService = new AdminServiceImpl();
        for (String ch : cho) {
            Judge Judge = new Judge();
            Judge.setDaytime(null);
            Judge.setJudgeId(Integer.valueOf(ch));
            if (adminService.delete(Judge)){
                row++;
            }
        }
        // 判断是否成功
        if (row == cho.length) {
            response.getWriter().print("OK");
        } else if (row == 0) {
            response.getWriter().print("NO");
        } else {
            response.getWriter().print("NO");
        }
    }

    //添加
    public void addJudge(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类出入对象封装数据---注意(表单名必须与Choice对象的属性相同)
        Judge judge = WebRequestUtil.parseObject(Judge.class, request);
        // 调用工具类存入数据
        int row = new JudgeServiceImpl().addJudge(judge);
        // 响应结果
        response.getWriter().print(row > 0 ? "OK" : "NO");
    }

    //修改
    public void updateJudge(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类的封装参数
        Judge Judge = WebRequestUtil.parseObject(Judge.class, request);
        // 调用工具类的update方法更新数据
        int update = new JudgeServiceImpl().updateJudge(Judge);
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
//                System.out.println("文件名：" + item.getName());
                // 获取上传文件的输入流
//                System.out.println("文件流" + item.getInputStream());

                // 调用导入的方法
                String result = JudgeExcelUtils.importFile(name, in, Judge.class);

                // 响应页面
                response.getWriter().print(result);
            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }
}


