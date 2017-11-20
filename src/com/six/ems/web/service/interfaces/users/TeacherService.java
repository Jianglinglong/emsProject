package com.six.ems.web.service.interfaces.users;

import java.util.List;

import com.six.ems.entity.tables.Answer;
import com.six.ems.entity.tables.Teacher;
import com.six.ems.entity.tables.TeacherCourse;

/**
 * 实现TeacherDAO接口
 * @author qingge
 * @data 2017年10月12日
 */
public interface TeacherService {

    /*boolean addChoice(Choice choice);
    boolean addBlank(Blank blank);
    boolean addJudge(Judge judge);
    boolean addSubjective(Subjective subjective);
    boolean addPaperRule(PaperRule paperRule);
    boolean addPaper(Paper paper);
    boolean addExam(Exam exam);
    Answer getAnswerByID(Integer answerID);
    boolean readAnswer(Answer answer);
    boolean addScore(Student student , BigDecimal bigDecimal);*/
	  /**
	   * 查询所有老师
	   * @return
	   */
	public List<Teacher> teacherQuery();
	/**
	 * 查询老师方法
	 */
	public String teacherQuery(int page, int rows);
	
	/**
	 * 根据教师id查询信息
	 * @param teaId
	 * @return
	 */
	public Teacher getTeacherById(Integer teaId);
	
	/**
	 * 添加老师方法
	 */
	public int teacherAdd(Teacher teacher);
	
	/**
	 * 修改老师方法
	 */
	public int teacherUpdate(Teacher teacher);
	
	/**
	 * 删出老师方法
	 */
	public int teacherDelete(Teacher teacher);

	/**
	 * 根据编号获取答题卡
	 * @param answerID
	 * @return
	 */
	Answer getAnswerByAnswerID(Integer answerID);

	/**
	 * 根据学号获取答题卡
	 * @param stuID
	 * @return
	 */
	List<Answer> getAnswersByStuID(Integer stuID);

	/**
	 *根据试卷编号获取答题卡
	 * @param PaperID
	 * @return
	 */
	List<Answer> getAnswersByPaperID(Integer PaperID);

	/**
	 * 开始阅卷
	 * @param answerID 答题卡
	 * @return
	 */
	boolean readAnswer(Integer answerID);

    /**
     * 列举所有答题卡
     * @return
     */
	List<Answer> getAllAnswers();

	/**
	 * 根据老师id查询老师课程关系表
	 */
	List<TeacherCourse> geTeacherCoursesByTeaId(Integer teaId);
	
	/**
	 * 获取所有老师课程关系
	 */
	List<TeacherCourse> getTeacherCourses();
	
	/**
	 * 添加老师课程关系
	 */
	Integer addTeacherCourse(TeacherCourse teacherCourse);
	
	/**
	 * 修改老师课程关系
	 */
	Integer updateTeacherCourse(TeacherCourse teacherCourse);
	
	/**
	 * 删除老师课程关系
	 */
	Integer deleteTeacherCourse(TeacherCourse teacherCourse);
	
	List<TeacherCourse> getTeacherCoursesByPage(Integer pageSize, Integer page);
}
