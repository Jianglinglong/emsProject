package com.six.ems.web.controller.questions.blank;

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
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.interfaces.users.AdminService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.six.ems.entity.tables.Blank;

import com.six.ems.utils.BlankExcelUtils;
import com.six.ems.utils.CollectionUtils;

import com.six.ems.utils.WebRequestUtil;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.questions.BlankServiceImpl;

/**
 * Servlet implementation class BlankServlet
 */
public class BlankServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BlankServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    public String initBlank(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        return "/WEB-INF/jsp/questions/blank.jsp";
    }

    // 查询所有题目
    public void queryBlank(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取传入当前页和每页显示数
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer rows = Integer.valueOf(request.getParameter("rows"));

        String bCourseName = request.getParameter("bCourseName");
        //
        String bBlankdaytimeName = request.getParameter("daytimeblank");
        AdminService adminService = new AdminServiceImpl();
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        List<Blank> blanks = new ArrayList<>();
        List<Blank> blankList = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        if (bCourseName != null & !"".equals(bCourseName)) {
            condition.put("course_name", new SelectItem(bCourseName, SelectItem.LikeSelect.CONTAIN));
            courses = adminService.getTypeByCondition(Course.class, condition);
        }
        if (courses.size() != 0) {
            condition.clear();
            if (bBlankdaytimeName != null & !"".equals(bBlankdaytimeName)) {
                condition.put("day_time", new SelectItem(bBlankdaytimeName, SelectItem.LikeSelect.BEGIN));
            }
            for (Course course : courses) {
                condition.put("course_id", new SelectItem(course.getCourseId()));
                List<Blank> blank = adminService.getTypeByCondition(Blank.class, condition);
                if (CollectionUtils.isNotBlank(blank)) {
                    blankList.addAll(blank);
                }
            }
        } else {
            // 时间
            if (bBlankdaytimeName != null & !"".equals(bBlankdaytimeName)) {
                condition.put("day_time", new SelectItem(bBlankdaytimeName, SelectItem.LikeSelect.BEGIN));
            }
            blankList = adminService.getTypeByCondition(Blank.class, condition);
            // blankList = adminService.getTypeItems(Blank.class);

        }
        int begin = (page - 1) * rows;
        int end = page * rows;
        int size = blankList.size();
        blanks = blankList.subList(begin, end >= size ? size : end);

        String json = CollectionUtils.creatDataGritJson(blanks, size);
        response.getWriter().print(json);
    }

    public void jsonBlank(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Blank> blankList = new BlankServiceImpl().getAllBlank();

        response.getWriter().print(CollectionUtils.creatDataGritJson(blankList, blankList.size()));

    }

    // 删除
    public void deleteBlank(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String blankId = request.getParameter("stuId");
        // 用逗号分隔id数组
        String[] cho = blankId.split(",");
        int row = 0;// 定义全局变量
        // 遍历id个数
        AdminService adminService = new AdminServiceImpl();
        for (String ch : cho) {
            // System.out.println(ch);
            Blank blank = new Blank();
            blank.setDaytime(null);
            blank.setBlankId(Integer.valueOf(ch));
            boolean delete = adminService.delete(blank);
            if (delete){
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

    // 添加
    public void addBlank(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 调用工具类出入对象封装数据---注意(表单名必须与Choice对象的属性相同)
        Blank blank = WebRequestUtil.parseObject(Blank.class, request);
        // 调用工具类存入数据
        int row = new BlankServiceImpl().addBlank(blank);
        // 响应结果
        response.getWriter().print(row > 0 ? "OK" : "NO");
    }

    // 修改
    public void updateBlank(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 调用工具类的封装参数
        Blank blank = WebRequestUtil.parseObject(Blank.class, request);
        // 调用工具类的update方法更新数据
        int update = new BlankServiceImpl().updateBlank(blank);
        // 判断 响应相应的结果
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
    public void importExcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                // System.out.println("文件名："+item.getName());
                // 获取上传文件的输入流
                // System.out.println("文件流"+item.getInputStream());

                // 调用导入的方法
                String result = BlankExcelUtils.importFile(name, in, Blank.class);

                // 响应页面
                response.getWriter().print(result);
            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }
}
