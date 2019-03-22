package com.omg.entity;

import com.omg.annotation.FieldValue;
import com.omg.annotation.Repetition;
import com.omg.enumerate.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by gp-0096 on 2018/8/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{

    private static final long serialVersionUID = -3249759973071839523L;

    @Id
    private Integer id;

    @FieldValue("姓名")
    @Repetition(message = "姓名已存在")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @FieldValue("年龄")
    @Repetition
    @NotNull(message = "年龄不能为空")
    private Integer age;

    private String password;

    private UserType type;
}
