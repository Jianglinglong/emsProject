package com.six.ems.dao.impl.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.users.StudentDAO;
import com.six.ems.entity.tables.Student;
import com.six.ems.entity.tables.StudentCourse;
import com.six.ems.utils.CollectionUtils;

/**
 * 实现studentDAO接口
 *
 * @author qingge
 * @data 2017年10月12日
 */
public class StudentDAOImpl implements StudentDAO {

    /**
     * 实现查询方法
     */
    public List<Student> studentQuery(int page, int rows) {
        return AdvanceUtil.select(Student.class, page, rows);
    }

    /**
     * 查询所有学生信息
     */
    public List<Student> getAllStudent() {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available",new SelectItem(1));
        List<Student> list = AdvanceUtil.select(Student.class);
        return list;
    }

    /**
     * 根据学生id查询学生信息
     */
    public Student getStudentById(Integer stuId) {
        // 构建条件
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("stu_id", new SelectItem(stuId));
        condition.put("available",new SelectItem(1));
        // 调用工具类查询学生信息
        List<Student> list = AdvanceUtil.select(Student.class, condition);
        // 调用工具类判断集合是否非空  并获取查到的对象
        return CollectionUtils.isNotBlank(list) ? list.get(0) : null;
    }

    /**
     * 实现添加方法
     */
    public int studentAdd(Student student) {
        // 调用工具类存入数据
        return AdvanceUtil.insert(student);
    }

    /**
     * 实现修改方法
     */
    public int studentUpdate(Student student) {
        // 调用工具类的update方法更新数据
        return AdvanceUtil.update(student);
    }

    /**
     * 实现删除方法
     */
    public int studentDelete(Student student) {
        // 返回删除影响的行数
        return AdvanceUtil.delete(student);
    }

	@Override
	public List<StudentCourse> getStudentCourseByStuId(Integer studentId) {
		Map<String, SelectItem> map = new HashMap<>();
		map.put("stu_id", new SelectItem(studentId));
        map.put("available",new SelectItem(1));
		List<StudentCourse> studentCourses = AdvanceUtil.select(StudentCourse.class, map);
		return CollectionUtils.isNotBlank(studentCourses) ? studentCourses : null;
	}


}
