package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

@SQLTableName(table = "t_read")
public class Read {
	@SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "read_id")
    private Integer readId;
    @SQLColnum(colName = "answer_id")
    private Integer answerId;
    @SQLColnum(colName = "sub_id")
    private Integer subId;
    @SQLColnum(colName = "sub_score")
    private Integer subScore;
    @SQLColnum(colName = "available")
    private String available = "1";

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }


    public Read() {
        super();
    }

    public Read(Integer readId) {
        this.readId = readId;
    }

    public Integer getReadId() {
        return readId;
    }

    public void setReadId(Integer readId) {
        this.readId = readId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public Integer getSubScore() {
        return subScore;
    }

    public void setSubScore(Integer subScore) {
        this.subScore = subScore;
    }

}
