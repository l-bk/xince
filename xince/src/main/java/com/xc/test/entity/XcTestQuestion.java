package com.xc.test.entity;

import java.math.BigDecimal;
import java.util.Date;

public class XcTestQuestion {

    private Integer questionId;
    private String questinDetails;
    private Integer testId;
    private Integer questionNum;
    private Date createTime;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestinDetails() {
        return questinDetails;
    }

    public void setQuestinDetails(String questinDetails) {
        this.questinDetails = questinDetails;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(Integer questionNum) {
        this.questionNum = questionNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
