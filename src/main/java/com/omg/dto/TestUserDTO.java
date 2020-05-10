package com.omg.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author: CYB
 * @Date: 2020/5/10 11:06
 */
@Data
@ToString
public class TestUserDTO {

    private Long id;

    private List<String> names;
}
