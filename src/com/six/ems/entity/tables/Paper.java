package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

@SQLTableName(table = "t_paper")
public class Paper {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "paper_id")
    private Integer paperID;
    @SQLColnum(colName = "course_id")
    private Integer courseID;
    @SQLColnum(colName = "paper_name")
    private String paperName;
	@SQLColnum(colName = "rule_id")
    private Integer ruleID;
    @SQLColnum(colName = "paper_time")
    private Integer paperTime;
//    @SQLColnum(colName = "class_id")
//    private Integer classId;
    @SQLColnum(colName = "available")
    private String available="1";
    
	public Paper() {
    }

    public Paper(Integer paperID) {
        this.paperID = paperID;
    }

    public Paper(Integer courseID, Integer ruleID, Integer paperTime) {
        this.courseID = courseID;
        this.ruleID = ruleID;
        this.paperTime = paperTime;
    }

    public Integer getPaperID() {
        return paperID;
    }

    public Integer getCourseID() {
        return courseID;
    }
    
    public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

    public Integer getRuleID() {
        return ruleID;
    }

    public Integer getPaperTime() {
        return paperTime;
    }

    public void setPaperID(Integer paperID) {
        this.paperID = paperID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public void setRuleID(Integer ruleID) {
        this.ruleID = ruleID;
    }

    public void setPaperTime(Integer paperTime) {
        this.paperTime = paperTime;
    }
    
    public String getAvailable() {
  		return available;
  	}

  	public void setAvailable(String available) {
  		this.available = available;
  	}

//	public Integer getClassId() {
//		return classId;
//	}
//
//	public void setClassId(Integer classId) {
//		this.classId = classId;
//	}
  	
  	

}
