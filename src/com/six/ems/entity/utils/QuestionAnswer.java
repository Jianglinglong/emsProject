package com.six.ems.entity.utils;

public class QuestionAnswer {

    private Integer id;
    private String checked;
    private String title;
    private String answer;
    private Double score;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QuestionAnswer() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public Integer getId() {

        return id;
    }

    public String getChecked() {
        return checked;
    }
}
