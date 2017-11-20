package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

import java.math.BigDecimal;

@SQLTableName(table = "t_paper_rule")
public class PaperRule {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "rule_id")
    private Integer ruleID;
    @SQLColnum(colName = "course_id")
    private Integer courseID;
    @SQLColnum(colName = "rule_name")
    private String ruleName;
	@SQLColnum(colName = "single_choice_ck")
    private String singleChoiceChecked;
    @SQLColnum(colName = "single_choice_num")
    private Integer singleChoiceNum;
    @SQLColnum(colName = "single_choice_score")
    private BigDecimal singleScore;
    @SQLColnum(colName = "mul_choice_ck")
    private String mulChoiceChecked;
    @SQLColnum(colName = "mul_choice_num")
    private Integer mulChoiceNum;
    @SQLColnum(colName = "mul_choice_score")
    private BigDecimal mulScore;
    @SQLColnum(colName = "fill_blank_ck")
    private String fileBlankChoiceChecked;
    @SQLColnum(colName = "fill_blank_num")
    private Integer fileBlankChoiceNum;
    @SQLColnum(colName = "fill_blank_score")
    private BigDecimal fileBlankScore;
    @SQLColnum(colName = "judge_ck")
    private String judgeChecked;
    @SQLColnum(colName = "judge_num")
    private Integer judgeNum;
    @SQLColnum(colName = "judge_score")
    private BigDecimal judgeScore;
    @SQLColnum(colName = "sub_question_ck")
    private String subChoiceChecked;
    @SQLColnum(colName = "sub_question_num")
    private Integer subChoiceNum;
    @SQLColnum(colName = "sub_question_score")
    private BigDecimal subScore;
    @SQLColnum(colName = "rule_type")
    private Integer ruleType;
    @SQLColnum(colName = "paper_score")
    private Integer paperScore;
    @SQLColnum(colName = "available")
    private String available="1";


	public PaperRule() {
    }

    public PaperRule(Integer ruleID) {
        this.ruleID = ruleID;
    }

    public Integer getRuleID() {
        return ruleID;
    }

    public Integer getCourseID() {
        return courseID;
    }
    
    public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

    public String getSingleChoiceChecked() {
        return singleChoiceChecked;
    }

    public Integer getSingleChoiceNum() {
        return singleChoiceNum;
    }

    public BigDecimal getSingleScore() {
        return singleScore;
    }

    public String getMulChoiceChecked() {
        return mulChoiceChecked;
    }

    public Integer getMulChoiceNum() {
        return mulChoiceNum;
    }

    public BigDecimal getMulScore() {
        return mulScore;
    }

    public String getFileBlankChoiceChecked() {
        return fileBlankChoiceChecked;
    }

    public Integer getFileBlankChoiceNum() {
        return fileBlankChoiceNum;
    }

    public BigDecimal getFileBlankScore() {
        return fileBlankScore;
    }

    public String getJudgeChecked() {
        return judgeChecked;
    }

    public Integer getJudgeNum() {
        return judgeNum;
    }

    public BigDecimal getJudgeScore() {
        return judgeScore;
    }

    public String getSubChoiceChecked() {
        return subChoiceChecked;
    }

    public Integer getSubChoiceNum() {
        return subChoiceNum;
    }

    public BigDecimal getSubScore() {
        return subScore;
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public Integer getPaperScore() {
        return paperScore;
    }

    public void setRuleID(Integer ruleID) {
        this.ruleID = ruleID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public void setSingleChoiceChecked(String singleChoiceChecked) {
        this.singleChoiceChecked = singleChoiceChecked;
    }

    public void setSingleChoiceNum(Integer singleChoiceNum) {
        this.singleChoiceNum = singleChoiceNum;
    }

    public void setSingleScore(BigDecimal singleScore) {
        this.singleScore = singleScore;
    }

    public void setMulChoiceChecked(String mulChoiceChecked) {
        this.mulChoiceChecked = mulChoiceChecked;
    }

    public void setMulChoiceNum(Integer mulChoiceNum) {
        this.mulChoiceNum = mulChoiceNum;
    }

    public void setMulScore(BigDecimal mulScore) {
        this.mulScore = mulScore;
    }

    public void setFileBlankChoiceChecked(String fileBlankChoiceChecked) {
        this.fileBlankChoiceChecked = fileBlankChoiceChecked;
    }

    public void setFileBlankChoiceNum(Integer fileBlankChoiceNum) {
        this.fileBlankChoiceNum = fileBlankChoiceNum;
    }

    public void setFileBlankScore(BigDecimal fileBlankScore) {
        this.fileBlankScore = fileBlankScore;
    }

    public void setJudgeChecked(String judgeChecked) {
        this.judgeChecked = judgeChecked;
    }

    public void setJudgeNum(Integer judgeNum) {
        this.judgeNum = judgeNum;
    }

    public void setJudgeScore(BigDecimal judgeScore) {
        this.judgeScore = judgeScore;
    }

    public void setSubChoiceChecked(String subChoiceChecked) {
        this.subChoiceChecked = subChoiceChecked;
    }

    public void setSubChoiceNum(Integer subChoiceNum) {
        this.subChoiceNum = subChoiceNum;
    }

    public void setSubScore(BigDecimal subScore) {
        this.subScore = subScore;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public void setPaperScore(Integer paperScore) {
        this.paperScore = paperScore;
    }
    

    public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}
}
