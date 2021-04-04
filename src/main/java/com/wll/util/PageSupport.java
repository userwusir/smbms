package com.wll.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wulele
 */
@Getter
@Setter
public class PageSupport {
    /**
     * 当前页码-来自于用户输入
     */
    private int currentPageNo = 1;

    /**
     * 总数量（表）
     */
    private int totalCount = 0;

    /**
     * 页面容量
     */
    private int pageSize = 0;

    /**
     * 总页数-totalCount/pageSize（+1）
     */
    private int totalPageCount = 1;

    public void setCurrentPageNo(int currentPageNo) {
        if (currentPageNo > 0) {
            this.currentPageNo = currentPageNo;
        }
    }

    public void setTotalCount(int totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;
            //设置总页数
            this.setTotalPageCountByRs();
        }
    }

    public void setPageSize(int pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
    }

    public void setTotalPageCountByRs() {
        if (this.totalCount % this.pageSize == 0) {
            this.totalPageCount = this.totalCount / this.pageSize;
        } else if (this.totalCount % this.pageSize > 0) {
            this.totalPageCount = this.totalCount / this.pageSize + 1;
        } else {
            this.totalPageCount = 0;
        }
    }

}