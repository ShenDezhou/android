package com.zls.springboot.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.zls.springboot.common.adapt.BaseMsgView;

/**
 * 移动端响应结果.
 * 
 * @author 承项
 * @date 2015年9月24日下午6:37:50
 * @param <T>
 */
public class OperationResult {

  public static enum OperationResultCode {
    FAIL(1), SUCCESS(2);
    private OperationResultCode(Integer value) {
      this.value = value;
    }

    private Integer value;

    public Integer getValue() {
      return this.value;
    }
  }

  private OperationResultCode code;
  private Object data;
  private String description;
  private Pagination page;

  @JsonView(BaseMsgView.class)
  public OperationResultCode getCode() {
    return code;
  }

  public void setCode(OperationResultCode code) {
    this.code = code;
  }

  @JsonView(BaseMsgView.class)
  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  @JsonView(BaseMsgView.class)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @JsonView(BaseMsgView.class)
  public Pagination getPage() {
    return page;
  }

  public void setPage(Pagination page) {
    this.page = page;
  }

  /**
   * 构建成功响应.
   */
  public static OperationResult success(Object data) {
    return success(data, null);
  }
  
  /**
   * 构建带分页的成功响应.
   */
  public static OperationResult success(Object data, Pagination page) {
    OperationResult operationResult = new OperationResult();
    operationResult.setCode(OperationResultCode.SUCCESS);
    operationResult.setData(data);
    operationResult.setPage(page);
    return operationResult;
  }

  /**
   * 构建失败响应.
   */
  public static OperationResult fail(String desc) {
    OperationResult operationResult = new OperationResult();
    operationResult.setCode(OperationResultCode.FAIL);
    operationResult.setDescription(desc);
    return operationResult;
  }
}
