package com.six.ems.entity.tables;

import com.alibaba.fastjson.annotation.JSONField;
import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

import java.util.Date;

@SQLTableName(table = "t_choice")
public class Choice {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "choice_id")
    private Integer choiceId;
    @SQLColnum(colName = "course_id")
    private Integer courseId;
    @SQLColnum(colName = "choice_title")
    private String choiceTitle;
    @SQLColnum(colName = "answer_a")
    private String answera;
    @SQLColnum(colName = "answer_b")
    private String answerb;
    @SQLColnum(colName = "answer_c")
    private String answerc;
    @SQLColnum(colName = "answer_d")
    private String answerd;
    @SQLColnum(colName = "answer")
    private String answer;
    @SQLColnum(colName = "choice_type")
    private Integer choiceType;
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

    public Choice() {
        super();
    }

    public Choice(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getChoiceTitle() {
        return choiceTitle;
    }

    public void setChoiceTitle(String choiceTitle) {
        this.choiceTitle = choiceTitle;
    }

    public String getAnswera() {
        return answera;
    }

    public void setAnswera(String answera) {
        this.answera = answera;
    }

    public String getAnswerb() {
        return answerb;
    }

    public void setAnswerb(String answerb) {
        this.answerb = answerb;
    }

    public String getAnswerc() {
        return answerc;
    }

    public void setAnswerc(String answerc) {
        this.answerc = answerc;
    }

    public String getAnswerd() {
        return answerd;
    }

    public void setAnswerd(String answerd) {
        this.answerd = answerd;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getChoiceType() {
        return choiceType;
    }

    public void setChoiceType(Integer choiceType) {
        this.choiceType = choiceType;
    }

	public Date getDaytime() {
		return daytime;
	}

	public void setDaytime(Date daytime) {
		this.daytime = daytime;
	}

}
