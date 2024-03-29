package com.github.mzz.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @author mengzz
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class SourceClass {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private int num;
}
