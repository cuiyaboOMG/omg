package com.omg.enumerate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
* @Author:         cyb
* @CreateDate:     2019/5/8 18:59
*/
@JsonSerialize(using = OmgEnumSerializer.class)
public interface OmgEnum {
    String getChinese();

    String getValue();
}
