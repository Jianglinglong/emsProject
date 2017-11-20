package com.six.ems.entity.tables;

import com.alibaba.fastjson.annotation.JSONField;
import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

import java.util.Date;

@SQLTableName(table = "t_judge")
public class Judge {
	@SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "judge_id")
    private Integer judgeId;
    @SQLColnum(colName = "course_id")
    private Integer courseId;
    @SQLColnum(colName = "judge_title")
    private String judgeTitle;
    @SQLColnum(colName = "answer")
    private String answer;
    
    @SQLColnum(colName = "available")
    private String available = "1";
    @SQLColnum(colName="day_time")
    @JSONField(format = "yyyy-MM-dd")
    private Date daytime =new Date();

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Integer getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(Integer judgeId) {
        this.judgeId = judgeId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getJudgeTitle() {
        return judgeTitle;
    }

    public void setJudgeTitle(String judgeTitle) {
        this.judgeTitle = judgeTitle;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

	public Date getDaytime() {
		return daytime;
	}

	public void setDaytime(Date daytime) {
		this.daytime = daytime;
	}

}
