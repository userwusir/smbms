package com.wll.pojo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wulele
 *
 * 订单表
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Bill {
    private Integer id;
    private String billCode;
    private String productName;
    private String productDesc;
    private String productUnit;
    private BigDecimal productCount;
    private BigDecimal totalPrice;
    private Integer isPayment;
    private Integer providerId;
    private Integer createdBy;
    private Date creationDate;
    private Integer modifyBy;
    private Date modifyDate;

    private String providerName;
}
