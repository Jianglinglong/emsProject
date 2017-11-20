package com.six.ems.web.controller.questions.subjective;

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
import com.six.ems.entity.tables.Course;
import com.six.ems.entity.tables.Judge;
import com.six.ems.entity.tables.Subjective;
import com.six.ems.utils.ExcelUtils;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.interfaces.users.AdminService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import com.six.ems.entity.tables.Subjective;
import com.six.ems.utils.CollectionUtils;

import com.six.ems.utils.SubExcelUtils;
import com.six.ems.utils.WebRequestUtil;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.questions.SubjectiveServiceImpl;

/**
 * Servlet implementation class SubjectiveServlet
 */
public class SubjectiveServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubjectiveServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public String initSubjective(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	System.out.println(123);
        return "/WEB-INF/jsp/questions/subjective.jsp";
    }

    //查询所有题目
    public void sbujectiveQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer rows = Integer.valueOf(request.getParameter("rows"));

        String bSubName = request.getParameter("bSubName");
        String daytimesub = request.getParameter("daytimesub");
        AdminService adminService = new AdminServiceImpl();
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        List<Subjective> subjectives = new ArrayList<>();
        List<Subjective> subjectiveList = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        if (bSubName != null & !"".equals(bSubName)) {
            condition.put("course_name", new SelectItem(bSubName, SelectItem.LikeSelect.CONTAIN));
            courses = adminService.getTypeByCondition(Course.class, condition);
        }
        if (condition.size() != 0) {
            condition.clear();
            if (daytimesub != null & !"".equals(daytimesub)) {
                condition.put("day_time", new SelectItem(daytimesub, SelectItem.LikeSelect.BEGIN));
            }
            for (Course course : courses) {
                condition.put("course_id", new SelectItem(course.getCourseId()));
                List<Subjective> Subjective = adminService.getTypeByCondition(Subjective.class, condition);
                if (CollectionUtils.isNotBlank(Subjective)) {
                    subjectiveList.addAll(Subjective);
                }
            }
        } else {
            // 时间
            if (daytimesub != null & !"".equals(daytimesub)) {
                condition.put("day_time", new SelectItem(daytimesub, SelectItem.LikeSelect.BEGIN));
            }
            subjectiveList = adminService.getTypeByCondition(Subjective.class, condition);
        }
        int size = subjectiveList.size();
        int begin = (page - 1) * rows;
        int end = page * rows;
        subjectives = subjectiveList.subList(begin, end >= size ? size : end);

        String json = CollectionUtils.creatDataGritJson(subjectives, size);
        response.getWriter().print(json);

    }


    public void jsonSubjective(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Subjective> subjectiveList = new SubjectiveServiceImpl().getAllSubjective();

        response.getWriter().print(CollectionUtils.creatDataGritJson(subjectiveList, subjectiveList.size()));

    }

    //删除
    public void deleteSubjective(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectiveId = request.getParameter("stuId");
        // 用逗号分隔id数组
        String[] cho = subjectiveId.split(",");
        int row = 0;// 定义全局变量
        // 遍历id个数
        AdminService adminService = new AdminServiceImpl();
        for (String ch : cho) {
            Subjective subjective = new Subjective();
            subjective.setDaytime(null);
            subjective.setSubId(Integer.valueOf(ch));
            if (adminService.delete(subjective)) {
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
    public void addSubjective(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类出入对象封装数据---注意(表单名必须与Choice对象的属性相同)
        Subjective subjective = WebRequestUtil.parseObject(Subjective.class, request);
        // 调用工具类存入数据
        int row = new SubjectiveServiceImpl().addSubjective(subjective);
        // 响应结果
        response.getWriter().print(row > 0 ? "OK" : "NO");
    }

    //修改
    public void updateSubjective(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类的封装参数
        Subjective subjective = WebRequestUtil.parseObject(Subjective.class, request);
        // 调用工具类的update方法更新数据
        int update = new SubjectiveServiceImpl().updateSubjective(subjective);
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
                String result = SubExcelUtils.importFile(name, in, Subjective.class);

                // 响应页面
                response.getWriter().print(result);
            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }
}


