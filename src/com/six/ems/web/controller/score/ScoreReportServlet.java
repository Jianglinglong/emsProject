package com.six.ems.web.controller.score;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.consts.SQLOperator;
import com.jll.jdbc.tools.BaseUtil;
import com.six.ems.entity.tables.Classes;
import com.six.ems.entity.tables.Course;
import com.six.ems.entity.tables.Exam;
import com.six.ems.entity.tables.Paper;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.utils.DownLoad;
import com.six.ems.utils.StringUtils;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.paper.PaperServiceImpl;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.interfaces.users.AdminService;

/**
 * 定义报表的servlet
 * 
 * @author Direct
 *
 */
public class ScoreReportServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public ScoreReportServlet() {
	}

	/**
	 * 定义初始化的方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String initReport(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/WEB-INF/jsp/score/scoreReport.jsp";
		/// emsProject/WebContent/WEB-INF/jsp/score/scoreReport.jsp
	}

	/**
	 * 定义查询所有的方法
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showReport(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * 定义查询试卷的方法
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getPaper(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String courseId = request.getParameter("courseId");
		Map<String, SelectItem> condition = CollectionUtils.getCondition();
		condition.put("course_id", new SelectItem(courseId));
		List<Exam> papers = new AdminServiceImpl().getTypeByCondition(Exam.class, condition);
		List<Map<String, Object>> paperList = new ArrayList<>();
		PaperServiceImpl paperService = new PaperServiceImpl();
		for (Exam list : papers) {
			Map<String, Object> map = new HashMap<>();
			Integer paperID = list.getPaperID();
			Paper paper = paperService.getPaperById(paperID);
			map.put("id", list.getPaperID());
			map.put("name", paper.getPaperName());
			paperList.add(map);
		}

		String jsonString = JSON.toJSONString(paperList);
		response.getWriter().print(jsonString);
	}

	/**
	 * 定义查询班级的方法
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getClass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String courseId = request.getParameter("courseId");
		String paperId = request.getParameter("paperId");

		Map<String, SelectItem> condition = CollectionUtils.getCondition();
		condition.put("course_id", new SelectItem(courseId));
		condition.put("paper_id", new SelectItem(paperId));
		AdminServiceImpl adminService = new AdminServiceImpl();
		List<Exam> exams = adminService.getTypeByCondition(Exam.class, condition);
		List<Map<String, Object>> paperList = new ArrayList<>();
		for (Exam list : exams) {
			condition.clear();
			Map<String, Object> map = new HashMap<>();
			map.put("id", list.getClassId());

			int classId = list.getClassId();
			condition.put("class_id", new SelectItem(classId));
			Classes classes = adminService.getTypeByCondition(Classes.class, condition).get(0);
			map.put("name", classes.getClassName());
			paperList.add(map);
		}

		String jsonString = JSON.toJSONString(paperList);
		response.getWriter().print(jsonString);
	}

	/**
	 * 定义查询班级的方法
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getClassJson(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String paperId = request.getParameter("paperId");
		Map<String, SelectItem> condition = CollectionUtils.getCondition();
		condition.put("paper_id", new SelectItem(paperId));
		AdminService adminService = new AdminServiceImpl();
		Paper paper = adminService.getTypeByCondition(Paper.class, condition).get(0);
		condition.clear();
		// condition.put("class_id", new SelectItem(paper.getClassId()));
		Classes classes = adminService.getTypeByCondition(Classes.class, condition).get(0);

		Map<String, Object> map = new HashMap<>();
		map.put("id", classes.getClassId());
		map.put("name", classes.getClassName());
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(map);

		response.getWriter().print(JSON.toJSONString(list));
	}

	/**
	 * 通过学生班级id获取学生
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public String toChartPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		return "/WEB-INF/jsp/score/Chart.jsp";
	}

	/**
	 * 班级成绩概况
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void reportAllClass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String courseId = request.getParameter("courseId");
		String paperId = request.getParameter("paperId");
		// [score_id, stu_id, paper_id, course_id, auto_score, sub_score, total_score,
		// available]
		// [course_id, course_name, enable, available]
		// [paper_id, course_id, paper_name, rule_id, paper_time, class_id, available]
		// [class_id, class_name, available]
		List<String> params = new ArrayList<>();
		StringBuffer sql = new StringBuffer(SQLOperator.SELECT.getOperator());
		sql.append(
				" COUNT(total_score) sumNum,sum(if(total_score<60 ,0,1 )) passNum,MIN(total_score) minScore,MAX(total_score) maxScore,");
		sql.append(" AVG(total_score) avgScore,pap.paper_name,class_name,exa.exam_id,course_name");
		sql.append(SQLOperator.FROM.getOperator());
		sql.append("t_score sco left join t_exam exa on sco.exam_id = exa.exam_id ");
		sql.append(" left join t_class cla on exa.class_id = cla.class_id ");
		sql.append(" left join t_course cou on sco.course_id = cou.course_id ");
		sql.append(" LEFT JOIN t_paper pap on exa.paper_id = pap.paper_id ");
		// sql.append(SQLOperator.WHERE.getOperator());
		StringBuffer sqlCondition = new StringBuffer();
		if (StringUtils.isNotEmpty(courseId)) {
			sqlCondition.append(" sco.course_id=? ");
			params.add(courseId);
		}
		if (paperId != null && !"".equals(paperId)) {
			sqlCondition.append("and exa.paper_id =? ");
			params.add(paperId);
		}
		if (params.size() > 0) {
			sql.append(SQLOperator.WHERE.getOperator());
			sql.append(sqlCondition);
		}
		// sql.append("sco.sub_score > 0");
		sql.append("GROUP BY exa.exam_id");
		List<Map<String, Object>> select = BaseUtil.select(sql.toString(), params.toArray());

		List<String> title = new ArrayList<>();
		title.add("平均分");
		title.add("最高分");
		title.add("最低分");
		title.add("及格率");
		// System.out.println(title.toString());
		// System.out.println(courseId + " " + paperId);

		StringBuffer json = new StringBuffer("[" + JSON.toJSONString(title));
		for (Map<String, Object> map : select) {
			System.out.println(map);
			json.append(",[\"" + map.get("class_name") + "\"]");
			List<Double> data = new ArrayList<>();
			// key {paper_name=null, minScore=null, avgScore=null, course_name=null,
			// sumNum=0, maxScore=null, class_name=null, passNum=null}
			data.add((Double) map.get("avgScore"));
			data.add((Double) map.get("maxScore"));
			data.add((Double) map.get("minScore"));
			Double passNum = ((BigDecimal) map.get("passNum")).doubleValue();
			Long sumNum = (Long) map.get("sumNum");
			data.add(100 * passNum / sumNum);
			json.append("," + JSON.toJSONString(data));
		}
		json.append("]");
		response.getWriter().print(json);
	}

	/**
	 * 定义查询所有的成绩的概率
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void reportQuestionAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String courseId = request.getParameter("courseId");
		String paperId = request.getParameter("paperId");
		String classId = request.getParameter("classId");
		StringBuffer sql = new StringBuffer(SQLOperator.SELECT.getOperator());
		sql.append("exam.exam_id, class.class_name, COUNT(error.stu_id) 'stuNum',"
				+ "COUNT(error.blank_id) 'blankNum', rule.fill_blank_num blank,"
				+ "COUNT(error.s_choice_id) 'schoiceNum', rule.single_choice_num schoice,"
				+ "COUNT(error.m_choice_id) 'mchoiceNum', rule.mul_choice_num mchoice,"
				+ "COUNT(error.judge_id) 'judgeNum', rule.judge_num judgeNum, course.course_name "
				+ "cName FROM t_error error LEFT JOIN t_exam exam ON error.exam_id ="
				+ "exam.exam_id LEFT JOIN t_course course ON exam.course_id = course.course_id "
				+ "LEFT JOIN t_class class ON exam.class_id = class.class_id LEFT JOIN t_paper "
				+ "paper ON exam.paper_id = paper.paper_id LEFT JOIN t_paper_rule rule ON "
				+ "paper.rule_id = rule.rule_id LEFT JOIN t_user_class u ON error.stu_id =u.stu_id "
				+ "WHERE class.class_id = ? AND exam.paper_id = ? AND course.course_id = ?" + "GROUP BY exam.exam_id");

		List<Map<String, Object>> select = BaseUtil.select(sql.toString(), classId, paperId, courseId);
		/**
		 * {mchoiceNum=5, fill_blank_num=3, mul_choice_num=3, schoiceNum=5, judge_num=3,
		 * stuNum=6, course_name=UI设计, blankNum=6, judgeNum=5, class_name=YC92,
		 * single_choice_num=3, exam_id=1}
		 */
		List<String> title = new ArrayList<>();
		title.add("单选错题率");
		title.add("多选错题率");
		title.add("填空错题率");
		title.add("判断错题率");
		StringBuffer json = new StringBuffer();
		json.append("[" + JSON.toJSONString(title));
		List<Object> data = new ArrayList<>();
		List<Object> chartTitle = new ArrayList<>();
		for (Map<String, Object> map : select) {
			Long totalNum = (Long) map.get("stuNum");
			Integer singleChioceTotal = (Integer) map.get("single_choice_num");
			Integer mulChioceTotal = (Integer) map.get("mul_choice_num");
			Integer blankTotal = (Integer) map.get("fill_blank_num");
			Integer judgeTotal = (Integer) map.get("judge_num");
			Long errorSchoiceNum = (Long) map.get("schoiceNum");
			Long errorMchoiceNum = (Long) map.get("mchoiceNum");
			Long errorBlankNum = (Long) map.get("blankNum");
			Long errorJudgeNum = (Long) map.get("judgeNum");
			chartTitle.add(map.get("class_name"));
			chartTitle.add(map.get("course_name"));
			json.append("," + JSON.toJSONString(chartTitle));
			chartTitle.clear();
			double sLevle = 0;
			double mLevle = 0;
			double bLevle = 0;
			double jLevle = 0;
			if (singleChioceTotal != 0) {
				sLevle = 100 * (errorSchoiceNum == null ? 0 : errorSchoiceNum.doubleValue()) / totalNum
						/ singleChioceTotal;
			}
			if (mulChioceTotal != 0) {
				mLevle = 100 * (errorMchoiceNum == null ? 0 : errorMchoiceNum.doubleValue()) / totalNum
						/ mulChioceTotal;
			}
			if (blankTotal != 0) {
				bLevle = 100 * (errorBlankNum == null ? 0 : errorBlankNum.doubleValue()) / totalNum / blankTotal;
			}
			if (judgeTotal != 0) {
				jLevle = 100 * (errorJudgeNum == null ? 0 : errorJudgeNum.doubleValue()) / totalNum / judgeTotal;
			}
			data.add(sLevle);
			data.add(mLevle);
			data.add(bLevle);
			data.add(jLevle);
			json.append("," + JSON.toJSONString(data));
			data.clear();
		}
		json.append("]");
		System.out.println(json.toString());
		response.getWriter().print(json.toString());
	}

	/**
	 * 定义根据课程试卷班级获取所有学生的成绩
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getViewScore(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String courseId = request.getParameter("courseId");
		String classId = request.getParameter("classId");
		String paperId = request.getParameter("paperId");

		// 获取试卷id
		StringBuffer sqlExam = new StringBuffer(SQLOperator.SELECT.getOperator());
		sqlExam.append(" exam_id from t_exam where course_id = ? and paper_id = ? and class_id = ?");
		List<Map<String, Object>> examList = BaseUtil.select(sqlExam.toString(), courseId, paperId, classId);
		StringBuffer sql = new StringBuffer(SQLOperator.SELECT.getOperator());
		List<Map<String, Object>> select = new ArrayList<Map<String, Object>>();
		// 定义变量
		Integer examId = 0;
		for (Map<String, Object> list : examList) {
			examId = (Integer) list.get("exam_id");
			sql.append(" stu.stu_id, stu.stu_real_name, score.auto_score, score.sub_score,score.total_score ");
			sql.append("FROM t_score score LEFT JOIN t_student stu ON stu.stu_id = score.stu_id ");
			sql.append("WHERE score.stu_id IN ( SELECT stu_id FROM t_user_class WHERE class_id = ? ) ");
			sql.append("AND course_id = ? AND exam_id = ?");
			select.addAll(BaseUtil.select(sql.toString(), classId, courseId, examId));
		}

		String creatDataGritJson = CollectionUtils.creatDataGritJson(select, select.size());

		response.getWriter().print(creatDataGritJson);

	}

	public void getScoreExcel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String courseId = request.getParameter("courseId");
		String classId = request.getParameter("classId");
		String paperId = request.getParameter("paperId");
		// 获取试卷id
		StringBuffer sqlExam = new StringBuffer(SQLOperator.SELECT.getOperator());
		sqlExam.append(" exam_id from t_exam where course_id = ? and paper_id = ? and class_id = ?");
		List<Map<String, Object>> examList = BaseUtil.select(sqlExam.toString(), courseId, paperId, classId);
		StringBuffer sql = new StringBuffer(SQLOperator.SELECT.getOperator());
		List<Map<String, Object>> select = new ArrayList<Map<String, Object>>();
		// 定义变量
		Integer examId = 0;
		for (Map<String, Object> list : examList) {
			examId = (Integer) list.get("exam_id");
			sql.append(" stu.stu_id, stu.stu_real_name, score.auto_score, score.sub_score,score.total_score ");
			sql.append("FROM t_score score LEFT JOIN t_student stu ON stu.stu_id = score.stu_id ");
			sql.append("WHERE score.stu_id IN ( SELECT stu_id FROM t_user_class WHERE class_id = ? ) ");
			sql.append("AND course_id = ? AND exam_id = ?");
			select.addAll(BaseUtil.select(sql.toString(), classId, courseId, examId));
		}
		// stu.stu_id, stu.stu_real_name, score.auto_score,
		// score.sub_score,score.total_score
		AdminService adminService = new AdminServiceImpl();
		Map<String, SelectItem> condition = CollectionUtils.getCondition();
		condition.put("course_id", new SelectItem(classId));
		Course course = adminService.getTypeByCondition(Course.class, condition).get(0);
		condition.clear();
		condition.put("class_id", new SelectItem(classId));
		Classes classes = adminService.getTypeByCondition(Classes.class, condition).get(0);
		condition.clear();
		condition.put("paper_id", new SelectItem(paperId));
		Paper paper = adminService.getTypeByCondition(Paper.class, condition).get(0);
		List<String> title = new ArrayList<>();
		title.add("学号");
		title.add("姓名");
		title.add("客观题");
		title.add("主观题");
		title.add("总分");
		String fileName = classes.getClassName() + course.getCourseName() + paper.getPaperName();
		HSSFWorkbook sheets = DownLoad.getHSSFWorkbook(fileName, title, select);
		response.setContentType("application/octet-stream;charset=ISO8859-1");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
		response.addHeader("Pargam", "no-cache");
		response.addHeader("Cache-Control", "no-cache");
		OutputStream os = response.getOutputStream();
		sheets.write(os);
		os.flush();
		os.close();
	}
}
