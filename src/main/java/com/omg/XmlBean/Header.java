package com.omg.XmlBean;

import javax.xml.bind.annotation.*;

/**
 * Created by gp-0096 on 2018/8/29.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Header")
@XmlType
public class Header {
    /**接口版本*/
    @XmlElement(name="Version")
    private String version;

    /**请求业务类型*/
    @XmlElement(name="TransactionType")
    private String transactionType;

    /**渠道业务系统生成唯一识别码*/
    @XmlElement(name="TransactionId")
    private String transactionId;

    /**（仅在返回时出现）错误代码*/
    @XmlElement(name="ErrorCode")
    private String errorCode;

    /**（仅在返回时出现）错误详细信息*/
    @XmlElement(name="ErrorMessage")
    private String errorMessage;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
