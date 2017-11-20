package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

import java.math.BigDecimal;

/**
 * 成绩查询类
 *
 * @author Direct
 */
@SQLTableName(table = "t_score")
public class Score {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "score_id")
    private Integer scoreId;
    @SQLColnum(colName = "stu_id")
    private Integer stuId;
    @SQLColnum(colName = "exam_id")
    private Integer paperId;
    @SQLColnum(colName = "course_id")
    private Integer courseId;
    @SQLColnum(colName = "auto_score")
    private Double autoScore;
    @SQLColnum(colName = "sub_score")
    private Double subScore;
    @SQLColnum(colName = "total_score")
    private Double totalScore;
    @SQLColnum(colName = "available")
    private String available = "1";

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Score() {
        super();
    }

    public Score(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Double getAutoScore() {
        return autoScore;
    }

    public void setAutoScore(Double autoScore) {
        this.autoScore = autoScore;
    }

    public Double getSubScore() {
        return subScore;
    }

    public void setSubScore(Double subScore) {
        this.subScore = subScore;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

}
