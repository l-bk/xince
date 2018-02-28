package com.xc.test.entity;

import java.math.BigDecimal;
import java.util.Date;

public class XcTestAnswer {
    private Integer answerId;
    private String answerPic;
    private String answerSketch;
    private String answerKeyword;
    private String answerDetails;
    private BigDecimal answerPointlt;
    private BigDecimal answerPointGt;
    private String answerQRCode;
    private String delFlag;
    private Integer testId;
    private Date createTime;
    private Integer point;

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getAnswerPic() {
        return answerPic;
    }

    public void setAnswerPic(String answerPic) {
        this.answerPic = answerPic;
    }

    public String getAnswerSketch() {
        return answerSketch;
    }

    public void setAnswerSketch(String answerSketch) {
        this.answerSketch = answerSketch;
    }

    public String getAnswerKeyword() {
        return answerKeyword;
    }

    public void setAnswerKeyword(String answerKeyword) {
        this.answerKeyword = answerKeyword;
    }

    public String getAnswerDetails() {
        return answerDetails;
    }

    public void setAnswerDetails(String answerDetails) {
        this.answerDetails = answerDetails;
    }

    public BigDecimal getAnswerPointlt() {
        return answerPointlt;
    }

    public void setAnswerPointlt(BigDecimal answerPointlt) {
        this.answerPointlt = answerPointlt;
    }

    public BigDecimal getAnswerPointGt() {
        return answerPointGt;
    }

    public void setAnswerPointGt(BigDecimal answerPointGt) {
        this.answerPointGt = answerPointGt;
    }

    public String getAnswerQRCode() {
        return answerQRCode;
    }

    public void setAnswerQRCode(String answerQRCode) {
        this.answerQRCode = answerQRCode;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }
}
