package com.six.ems.web.service.impl.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import com.jll.jdbc.base.SelectItem;
import com.six.ems.dao.impl.answers.AnswerDAOImpl;
import com.six.ems.dao.impl.papers.PaperDAOImpl;
import com.six.ems.dao.impl.papers.PaperRuleDAOImpl;
import com.six.ems.dao.impl.users.TeacherDAOImpl;
import com.six.ems.dao.interfaces.answers.AnswerDao;
import com.six.ems.dao.interfaces.users.TeacherDAO;
import com.six.ems.entity.tables.Answer;
import com.six.ems.entity.tables.Paper;
import com.six.ems.entity.tables.PaperRule;
import com.six.ems.entity.tables.Teacher;
import com.six.ems.entity.tables.TeacherCourse;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.web.service.interfaces.users.TeacherService;

/**
 * 实现TeacherDAO接口
 *
 * @author qingge
 * @data 2017年10月12日
 */
public class TeacherServiceImpl implements TeacherService {

    // 创建TeacherDAO的对象
    private TeacherDAO teacherDao = new TeacherDAOImpl();
    private AnswerDao answerDao = new AnswerDAOImpl();
    
    /**
     * 查询教师方法
     */
    public String teacherQuery(int page, int rows) {
    	// 调用方法查询所有教师
    	List<Teacher> list = teacherDao.teacherQuery(page, rows);
    	// 调用方法查询教师总记录数
    	int total = teacherDao.getAllTeacher().size();
    	// 构建储存集合
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("rows", list);
    	map.put("total", total);
    	// 将查询到的信转为json字符串
    	String jsonString = JSON.toJSONString(map,true);
    	// 返回json字符串
    	return jsonString;
    }
    
    public List<Teacher> teacherQuery() {
		return teacherDao.getAllTeacher();
	}

    /**
	 * 根据教师id查询信息
	 * @param teaId
	 * @return
	 */
	public Teacher getTeacherById(Integer teaId) {
		return teacherDao.getTeacherById(teaId);
	}

    /**
     * 添加教师方法
     */
    public int teacherAdd(Teacher teacher) {
        return teacherDao.teacherAdd(teacher);
    }

    /**
     * 修改教师方法
     */
    public int teacherUpdate(Teacher teacher) {
        return teacherDao.teacherUpdate(teacher);
    }

    /**
     * 删除教师方法
     */
    public int teacherDelete(Teacher teacher) {
        return teacherDao.teacherDelete(teacher);
    }

    /**
     * 根据编号获取答题卡
     *
     * @param answerID
     * @return
     */
    public Answer getAnswerByAnswerID(Integer answerID) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("answer_id", new SelectItem(answerID));
        List<Answer> answers = answerDao.getAnswerByCondition(condition);
        return CollectionUtils.isNotBlank(answers) ? answers.get(0) : null;
    }

    /**
     * 根据学生获取答题卡
     *
     * @param stuID
     * @return
     */
    public List<Answer> getAnswersByStuID(Integer stuID) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("stu_id", new SelectItem(stuID));
        return answerDao.getAnswerByCondition(condition);
    }

    /**
     * 根据试卷编号获取答题卡
     *
     * @param PaperID
     * @return
     */
    public List<Answer> getAnswersByPaperID(Integer PaperID) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("paper_id", new SelectItem(PaperID));
        return answerDao.getAnswerByCondition(condition);
    }

    /**
     * 阅卷
     *
     * @param answerID 答题卡
     * @return
     */
    public boolean readAnswer(Integer answerID) {
        //获取答题卡相关信息
        Answer answer = getAnswerByAnswerID(answerID);
        Integer stuId = answer.getStuId();
        Integer paperId = answer.getPaperId();
        //获取答题卡关联的试卷规则
        Paper paperByID = new PaperDAOImpl().getPaperByID(paperId);
        PaperRule paerRuleByID = new PaperRuleDAOImpl().getPaerRuleByID(paperByID.getRuleID());
        return false;
    }

    @Override
    public List<Answer> getAllAnswers() {
        return answerDao.getAnswers();
    }

	@Override
	public List<TeacherCourse> geTeacherCoursesByTeaId(Integer teaId) {
		
		return teacherDao.geTeacherCoursesByTeaId(teaId);
	}


	@Override
	public List<TeacherCourse> getTeacherCoursesByPage(Integer pageSize, Integer page) {
		
		return teacherDao.getTeacherCoursesByPage(pageSize, page);
	}

	@Override
	public List<TeacherCourse> getTeacherCourses() {

		return teacherDao.getTeacherCourses();
	}

	@Override
	public Integer addTeacherCourse(TeacherCourse teacherCourse) {

		return teacherDao.addTeacherCourse(teacherCourse);
	}

	@Override
	public Integer updateTeacherCourse(TeacherCourse teacherCourse) {

		return teacherDao.updateTeacherCourse(teacherCourse);
	}

	@Override
	public Integer deleteTeacherCourse(TeacherCourse teacherCourse) {

		return teacherDao.deleteTeacherCourse(teacherCourse);
	}


}
