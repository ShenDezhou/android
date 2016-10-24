package com.zls.springboot.request.form;

import com.zls.springboot.common.Pagination;

public class QueryUserFormBean {

  private Integer currPage; // 当前页,若不指定,则默认为1
  private Integer pageSize; // 当前页显示大小,若不指定,则给定默认值

  public Integer getCurrPage() {
    if (currPage == null || currPage.intValue() <= 0) {
      currPage = 1;
    }
    return currPage;
  }

  public void setCurrPage(Integer currPage) {
    this.currPage = currPage;
  }

  public Integer getPageSize() {
    if (pageSize == null || pageSize.intValue() == 0) {
      pageSize = Pagination.DEFAULT_SIZE;
    }
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  @Override
  public String toString() {
    return "QueryUserFormBean [currPage=" + currPage + ", pageSize=" + pageSize + "]";
  }
}
