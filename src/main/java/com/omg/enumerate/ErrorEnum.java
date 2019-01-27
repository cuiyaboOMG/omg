package com.omg.enumerate;

/**
 * Created by gp-0096 on 2019/1/22.
 */
public enum ErrorEnum {
    COMMON_ERROR("10001", "处理异常"),
    COMMON_DATA_EMPTY("10002", "查询结果为空"),
    COMMON_REQUEST_OUT_API_ERROR("10003", "请求外部接口出错"),
    COMMON_REQUEST_OPERATION_ERROR("10003", "操作失败"),
    COMMON_TIMEFORMAT_ERROR("10004", "时间参数转换错误"),
    LOGIN_NOTMATCH("20001", "账号与密码不匹配"),
    LOGIN_NOTNODULE("20002", "账号没有开通权限模块"),
    LOGIN_NOTVALID("20003", "账号暂未启用"),
    LOGIN_NOTUSER("20004", "账号不存在"),
    LOGIN_VERIFYCODEERROR("20005", "验证码不匹配"),
    LOGIN_TOKENINVALID("20006", "token失效"),
    LOGIN_VERIFYCODENOTMATCH("20007", "验证码已失效"),
    RESOURCE_NOTFOUND("30001", "资源不存在"),
    RESOURCE_STATUSERROR("30002", "资源状态不正确"),
    COMM_DATA_NOFIELD("COMM_DATA_10001", "缺少必要数据项"),
    COMM_DATA_READFAILD("COMM_DATA_10002", "从流中读取requestBody失败"),
    COMM_DATA_CONVEREDFAILD("COMM_DATA_10003", "requestBody转换JSONObject失败"),
    COMM_DATA_ENCRYPTFAILD("COMM_DATA_10004", "解密失败"),
    COMM_DATA_REQUESTNO("COMM_DATA_10005", "requestNo无效"),
    COMM_DATA_BODYEMPTY("COMM_DATA_10006", "请求体中的数据为空"),
    COMM_DATA_FORMAT_ERROR("COMM_DATA_10007", "传输body体格式错误"),
    COMM_DATA_API_ERROR("COMM_DATA_10008", "请求API频率过快，请稍后再试！"),
    UNIONPAY_DATA_RESOURCE_NOT_EXISTS("UNIONPAY_DATA_10001", "请求的文件可能不存在");

    private String errCode;
    private String errMsg;

    private ErrorEnum(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
