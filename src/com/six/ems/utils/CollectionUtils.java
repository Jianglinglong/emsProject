package com.six.ems.utils;

import com.alibaba.fastjson.JSON;
import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.Classes;
import com.six.ems.entity.tables.Course;
import com.six.ems.entity.tables.Student;
import com.six.ems.entity.tables.Teacher;
import com.six.ems.entity.utils.QuestionPaper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 集合操作工具类
 *
 * @author Direct
 */
public class CollectionUtils {
    /**
     * 判断一个Collection集合不能为空
     *
     * @param collection 传人collection类型参数
     * @return boolean 返回判断结果
     */
    public static boolean isNotBlank(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }

    public static Map<String, SelectItem> getCondition() {
        return new HashMap<String, SelectItem>();
    }

//	public static String creatDataGritJson(Collection<?> collection) {
//		Map<String, Collection<?>> map =new HashMap<>();
//		map.put("rows", collection);
//		return JSON.toJSONString(map);
//	}
	public static Map<Integer,String> parseClass(List<Classes> classesList){
        Map<Integer,String> map = new HashMap<>();
        if (isNotBlank(classesList)){

            for (Classes classes : classesList){
                map.put(classes.getClassId(),classes.getClassName());
            }
        }
        return map;
    }
    public static Map<Integer,String> parseStudent(List<Student> students){
        Map<Integer,String> map = new HashMap<>();
        if (isNotBlank(students)){
            for (Student student : students){
                map.put(student.getStuId(),student.getStuRealName());
            }
        }
        return map;
    }
    public static Map<Integer,String> parseCourse(List<Course> courses){
        Map<Integer,String> map = new HashMap<>();
        if (isNotBlank(courses)){
            for (Course course : courses){
                map.put(course.getCourseId(),course.getCourseName());
            }
        }
        return map;
    }
    public static Map<Integer,String> parseTeacher(List<Teacher> teachers){
        Map<Integer,String> map = new HashMap<>();
        if (isNotBlank(teachers)){
            for (Teacher teacher : teachers){
                map.put(teacher.getTeaId(),teacher.getTeaRealName());
            }
        }
        return map;
    }

    public static Map<Integer,Double> parseQuestionPaper(List<QuestionPaper> questionPapers){
        Map<Integer,Double> map = new HashMap<>();
        if (isNotBlank(questionPapers)){
            for (QuestionPaper questionPaper : questionPapers){
                map.put(questionPaper.getId(),questionPaper.getScore());
            }
        }
        return map;
    }
    public static String creatDataGritJson(Collection<?> collection, Integer totalCount) {
        Map<String, Object> map = new HashMap<>();
        map.put("rows", collection);
        map.put("total", totalCount);
        return JSON.toJSONString(map);
    }
}
