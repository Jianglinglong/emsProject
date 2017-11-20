package com.six.ems.entity.tables;


import com.alibaba.fastjson.annotation.JSONField;
import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

import java.util.Date;

@SQLTableName(table = "t_subjective")
public class Subjective {
	@SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "sub_id")
    private Integer subId;
    @SQLColnum(colName = "course_id")
    private Integer courseId;
    @SQLColnum(colName = "sub_title")
    private String subTitle;
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

    public Subjective() {
        super();
    }

    public Subjective(Integer subId) {
        this.subId = subId;
    }

    public Subjective(Integer courseId, String subTitle, String answer) {
        super();
        this.courseId = courseId;
        this.subTitle = subTitle;
        this.answer = answer;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
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
