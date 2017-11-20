package com.six.ems.entity.tables;

import com.alibaba.fastjson.annotation.JSONField;
import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

import java.util.Date;

@SQLTableName(table = "t_blank")
public class Blank {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "blank_id")
    private Integer blankId;
    @SQLColnum(colName = "course_id")
    private Integer courseId;
    @SQLColnum(colName = "blank_title")
    private String blankTitle;
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

    public Integer getBlankId() {
        return blankId;
    }

    public void setBlankId(Integer blankId) {
        this.blankId = blankId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getBlankTitle() {
        return blankTitle;
    }

    public void setBlankTitle(String blankTitle) {
        this.blankTitle = blankTitle;
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
