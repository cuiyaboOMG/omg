package com.omg.util.excel;

import lombok.Data;

/**
 * excel信息
* @Author:         cyb
* @CreateDate:     2019/4/28 11:22
*/
@Data
public class ExcelInfo {
    /**excle中对应的行号*/
    private Integer rowNum;

    /**excle中对应的列号*/
    private Integer lineNum;

    /**excle中单元错误信息*/
    private String errorMessage;
}
