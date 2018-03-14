package com.xc.test.entity;

import java.math.BigDecimal;
import java.util.Date;

public class XcTestOptions {
    private Integer optionsId;
    private String optionsKeyword;
    private String optionsDetails;
    private BigDecimal optionsPoint;
    private Integer testQuestionId;
    private Date createTime;
    private String ifSkip;
    private Integer skipQuestionId;
    private String ifReturn;
    private Integer returnAnswerId;

    public Integer getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(Integer optionsId) {
        this.optionsId = optionsId;
    }

    public String getOptionsKeyword() {
        return optionsKeyword;
    }

    public void setOptionsKeyword(String optionsKeyword) {
        this.optionsKeyword = optionsKeyword;
    }

    public String getOptionsDetails() {
        return optionsDetails;
    }

    public void setOptionsDetails(String optionsDetails) {
        this.optionsDetails = optionsDetails;
    }

    public BigDecimal getOptionsPoint() {
        return optionsPoint;
    }

    public void setOptionsPoint(BigDecimal optionsPoint) {
        this.optionsPoint = optionsPoint;
    }

    public Integer getTestQuestionId() {
        return testQuestionId;
    }

    public void setTestQuestionId(Integer testQuestionId) {
        this.testQuestionId = testQuestionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIfSkip() {
        return ifSkip;
    }

    public void setIfSkip(String ifSkip) {
        this.ifSkip = ifSkip;
    }

    public Integer getSkipQuestionId() {
        return skipQuestionId;
    }

    public void setSkipQuestionId(Integer skipQuestionId) {
        this.skipQuestionId = skipQuestionId;
    }

    public String getIfReturn() {
        return ifReturn;
    }

    public void setIfReturn(String ifReturn) {
        this.ifReturn = ifReturn;
    }

    public Integer getReturnAnswerId() {
        return returnAnswerId;
    }

    public void setReturnAnswerId(Integer returnAnswerId) {
        this.returnAnswerId = returnAnswerId;
    }
}
