package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

@SQLTableName(table = "t_error")
public class ErrorTable {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "error_id")
    private Integer errorId;
    @SQLColnum(colName = "stu_id")
    private Integer stuId;
    @SQLColnum(colName = "s_choice_id")
    private Integer sChoiceId;
    @SQLColnum(colName = "m_choice_id")
    private Integer mChoiceId;
    @SQLColnum(colName = "blank_id")
    private Integer blankId;
    @SQLColnum(colName = "judge_id")
    private Integer judgeId;
    @SQLColnum(colName = "exam_id")
    private Integer examId;
//    @SQLColnum(colName = "class_id")
//    private Integer classId;

    public ErrorTable() {
    }

    public ErrorTable(Integer stuId, Integer examId) {
        this.stuId = stuId;
        this.examId = examId;
    }

    public Integer getErrorId() {
        return errorId;
    }

    public void setErrorId(Integer errorId) {
        this.errorId = errorId;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getBlankId() {
        return blankId;
    }

    public void setBlankId(Integer blankId) {
        this.blankId = blankId;
    }

    public Integer getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(Integer judgeId) {
        this.judgeId = judgeId;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

	public Integer getSChoiceId() {
		return sChoiceId;
	}

	public void setSChoiceId(Integer sChoiceId) {
		this.sChoiceId = sChoiceId;
	}

	public Integer getMChoiceId() {
		return mChoiceId;
	}

	public void setMChoiceId(Integer mChoiceId) {
		this.mChoiceId = mChoiceId;
	}
    
    
}
