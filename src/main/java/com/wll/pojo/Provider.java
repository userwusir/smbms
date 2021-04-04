package com.wll.pojo;

import lombok.*;

import java.util.Date;

/**
 * @author wulele
 * <p>
 * 供应商表
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Provider {
    private Integer id;
    private String proCode;
    private String proName;
    private String proDesc;
    private String proContact;
    private String proPhone;
    private String proAddress;
    private Integer createdBy;
    private Date creationDate;
    private Integer modifyBy;
    private Date modifyDate;
}
