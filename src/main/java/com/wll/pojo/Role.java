package com.wll.pojo;

import lombok.*;

import java.util.Date;

/**
 * @author wulele
 * <p>
 * 角色表
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {
    private Integer id;
    private String roleCode;
    private String roleName;
    private Integer createdBy;
    private Integer creationDate;
    private Integer modifyBy;
    private Date modifyDate;
}
