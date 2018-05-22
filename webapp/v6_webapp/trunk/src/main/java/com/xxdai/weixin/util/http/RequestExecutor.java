package com.xxdai.weixin.util.http;

import com.xxdai.weixin.exception.WxErrorException;

/**
 * http请求执行器
 *
 * @param <T> 返回值类型
 * @param <E> 请求参数类型
 */
public interface RequestExecutor<T, E> {

  public T execute(String uri, E data) throws WxErrorException;
  
}
