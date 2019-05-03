package com.omg.domain.qo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: cui
 * @Date: 2019-04-15 19:27
 * @Description:
 */
@Data
public class UserQo implements Serializable{
    private static final long serialVersionUID = -245320954124275567L;

    private Date beginTime;
}
