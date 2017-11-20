package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

@SQLTableName(table = "t_exam")
public class Exam {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "exam_id")
    private Integer examID;
    @SQLColnum(colName = "course_id")
    private Integer courseID;
    @SQLColnum(colName = "paper_id")
    private Integer paperID;
    @SQLColnum(colName = "tea_id")
    private Integer teaID;
    @SQLColnum(colName = "exam_time")
    private String examTime;
    @SQLColnum(colName="class_id")
    private Integer classId;
    @SQLColnum(colName="exam_papertime")
    private Integer paperTime;
    @SQLColnum(colName = "available")
    private String available = "1";
    @SQLColnum(colName = "location_id")
    private Integer locationId;

   

	public String getAvailable() {
        return available;
    }

    public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public void setAvailable(String available) {
        this.available = available;
    }

    public Exam() {
    }

    public Exam(Integer examID) {
        this.examID = examID;
    }

    public Exam(Integer courseID, Integer teaID, 
    		Integer paperID, String examTime,String examAddr,Integer paperTime
    		,Integer locationId
    		) {
        this.courseID = courseID;
        this.paperID = paperID;
        this.teaID = teaID;
        this.examTime = examTime;
        this.paperTime=paperTime;
        this.locationId=locationId;
    }

    public Integer getExamID() {
        return examID;
    }
    public Integer getPaperTime() {
		return paperTime;
	}

    public Integer getCourseID() {
        return courseID;
    }

    public Integer getPaperID() {
        return paperID;
    }

    public Integer getTeaID() {
        return teaID;
    }

    public String getExamTime() {
        return examTime;
    }


    public void setExamID(Integer examID) {
        this.examID = examID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }
    public void setPaperTime(Integer paperTime) {
		this.paperTime = paperTime;
	}
    public void setPaperID(Integer paperID) {
        this.paperID = paperID;
    }

    public void setTeaID(Integer teaID) {
        this.teaID = teaID;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }


	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}
    
}
