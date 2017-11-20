package com.six.ems.dao.impl.score;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.socre.ScoreDAO;
import com.six.ems.entity.tables.Score;
import com.six.ems.entity.tables.TeacherCourse;
import com.six.ems.utils.CollectionUtils;

/**
 * 成绩接口实现
 *
 * @author Direct
 * @data 2017年10月12日
 */
public class ScoreDAOImpl implements ScoreDAO {


    /**
     * 实现添加方法
     */
    public int scoreAdd(Score score) {
        return AdvanceUtil.insert(score);
    }

    /**
     * 实现修改方法
     */
    public int scoreUpdate(Score score) {
        return AdvanceUtil.update(score);
    }

    /**
     * 实现删除方法
     */
    public int scoreDelete(Score score) {
        return AdvanceUtil.delete(score);
    }

    /**
     * 根据学生id查询信息  分页
     */
    public List<Score> scoreQuery(Integer stuId, int page, int rows) {
        // 构建条件
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("stu_id", new SelectItem(stuId));
        // 调用方法返回集合信息
        return AdvanceUtil.select(Score.class, condition, page, rows);
    }

    /**
     * 查询
     */
    public List<Score> scoreQuery(int page, int rows) {
        return AdvanceUtil.select(Score.class, page, rows);
    }


    /**
     * 按学号获取成绩
     */
    public Score getScoreByStuId(Integer stuId, Integer paperId) {
        // 构建条件
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("stu_id", new SelectItem(stuId));
        condition.put("paper_id", new SelectItem(paperId));
        condition.put("available", new SelectItem(1));
        // 调用工具类根据学生id和试卷id查询学生成绩
        List<Score> list = AdvanceUtil.select(Score.class, condition);
        // 判断非空  返回集合
        return CollectionUtils.isNotBlank(list) ? list.get(0) : null;
    }

    /**
     * 通过学生id获取成绩
     */
    public List<Score> getScoreByStuId(Integer stuId) {
        // 构建条件
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("stu_id", new SelectItem(stuId));
        condition.put("available", new SelectItem(1));
//		System.out.println(condition);
        // 根据学生id查询学生信息
        List<Score> list = AdvanceUtil.select(Score.class, condition);
        return list;
    }

    /**
     * 查询所有信息  返回一个信息集合
     */
    public List<Score> getScores() {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available", new SelectItem(1));
        return AdvanceUtil.select(Score.class, condition);
    }

    /**
     * 根据成绩编号查询成绩
     */
    public Score getScoreByScoreId(Integer scoreId) {
        // 构建条件
        Map<String, SelectItem> condition = new HashMap<String, SelectItem>();
        condition.put("score_id", new SelectItem(scoreId));
        condition.put("available", new SelectItem(1));
        List<Score> query = AdvanceUtil.select(Score.class, condition);
        return CollectionUtils.isNotBlank(query) ? query.get(0) : null;
    }

//    @Override
//    public List<TeacherCourse> getTeacherCoursesByPage(Integer pageSize, Integer page) {
//        return null;
//    }

    /**
     * 实现查询方法
     */
    /*public String scoreQuery(Class<Score> score,int page, int rows) {
		// 调用工具类查询 所有信息信息
		List<Score> list = AdvanceUtil.select(Score.class, page, rows);
		// 调用工具查询信息总的条数
		int total = AdvanceUtil.select(Score.class).size();
		// 构建map集合条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", total);
		map.put("rows", list);
		//转为json字符串
		String jsonString = JSON.toJSONString(map);
		// 返回json字符串
		return jsonString;
	}*/

    /**
     * 实现通过学生id查询成绩id
     */
	/*public String getScoreIdByStuId1(Integer userId) {
		Map<String,SelectItem> condition = new HashMap<String,SelectItem>();
		condition.put("user_id", new SelectItem(userId));
		// 通过用户id查询学生id
		List<User> userList = AdvanceUtil.select(User.class, condition);
		// 得到查询的数据对象
		User user = userList.get(0);
		// 获取学生id
		Integer stuId = user.getStuId();
		// 构建条件
		Map<String,SelectItem> cond = new HashMap<String,SelectItem>();
		cond.put("stu_id", new SelectItem(stuId));
		// 查询 
		List<Score> list = AdvanceUtil.select(Score.class, cond);
		// 返回json字符串
		return JSON.toJSONString(list, true);
	}*/

}
