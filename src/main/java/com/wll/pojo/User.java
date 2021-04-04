package com.wll.pojo;

import lombok.*;

import java.util.Calendar;
import java.util.Date;

/**
 * @author wulele
 * <p>
 * 用户表
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private Integer id;
    private String userCode;
    private String userName;
    private String userPassword;
    private Integer gender;
    private Date birthday;
    private String phone;
    private String address;
    private Integer userRole;
    private Integer createdBy;
    private Date creationDate;
    private Integer modifyBy;
    private Date modifyDate;

    private Integer age;
    private String userRoleName;

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public Integer getAge() {
        Calendar calendar = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        date.setTime(this.birthday);
        return calendar.get(Calendar.YEAR) - date.get(Calendar.YEAR);
    }
}
