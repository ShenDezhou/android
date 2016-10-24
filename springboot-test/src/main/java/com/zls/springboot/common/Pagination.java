package com.zls.springboot.common;

import java.io.Serializable;

/**
 * 分页对象.
 * 
 * @author 承项
 * @date 2015年10月5日下午6:30:13
 */
public class Pagination implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final Integer DEFAULT_SIZE = 10; // 默认显示10条

  private Long totalCount; // 总数
  private Integer totalPage; // 总页数
  private Integer currPage; // 当前页码
  private Integer count; // 当前页记录数
  private Integer pageSize; // 每页大小
  private String sort; // 排序字段
  private Boolean next; // 是否有下一页

  /**
   * 默认构造器
   */
  public Pagination() {}


  public Pagination(Long totalCount, Integer currPage) {
    this(totalCount, currPage, null);
  }

  public Pagination(Long totalCount, Integer currPage, String sort) {
    this(totalCount, currPage, sort, DEFAULT_SIZE);
  }

  public Pagination(Long totalCount, Integer currPage, String sort, Integer pageSize) {
    this(totalCount, currPage, sort, pageSize, null);
  }
  
  public Pagination(Long totalCount, Integer currPage, String sort, Integer pageSize, Integer count) {
    this.totalCount = totalCount;
    this.currPage = currPage;
    this.sort = sort;
    this.pageSize = pageSize;
    this.count = count;
  }

  /**
   * 全值构造函数.
   */
  public Pagination(Long totalCount, Integer totalPage, Integer currPage, Integer count,
      Integer pageSize, String sort, boolean next) {
    super();
    this.totalCount = totalCount;
    this.totalPage = totalPage;
    this.currPage = currPage;
    this.count = count;
    this.pageSize = pageSize;
    this.sort = sort;
    this.next = next;
  }

  public Long getTotalCount() {
    if (totalCount == null) {
      throw new RuntimeException("分页的记录总数不能为null");
    }
    return totalCount;
  }

  public void setTotalCount(Long totalCount) {
    this.totalCount = totalCount;
  }

  public Integer getPageSize() {
    if (pageSize == null) {
      return DEFAULT_SIZE;
    }
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getCurrPage() {
    if (currPage == null) {
      return 1;
    }
    return currPage;
  }

  public void setCurrPage(Integer currPage) {
    this.currPage = currPage;
  }

  public Integer getCount() {
    return count;
  }


  public void setCount(Integer count) {
    this.count = count;
  }


  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public Integer getTotalPage() {
    if (totalPage == null) {
      Integer pageCount = (int) (getTotalCount() / getPageSize());
      Integer mod = (int) (getTotalCount() % getPageSize());
      if (pageCount <= 0) {
        totalPage = 1;
      } else {
        if (mod > 0) {
          totalPage = pageCount + 1;
        }
      }
    }
    return totalPage;
  }

  public void setTotalPage(Integer totalPage) {
    this.totalPage = totalPage;
  }

  public boolean isNext() {
    if (next == null) {
      if (getCurrPage() * getPageSize() < getTotalCount()) {
        next = true;
      } else {
        next = false;
      }
    }
    return next;
  }

  public void setNext(boolean next) {
    this.next = next;
  }
}
