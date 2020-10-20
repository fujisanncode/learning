package com.example.sqllearning.designPattern.observer.springlistener;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * @description: 用户注册事件
 * @author: hulei
 * @create: 2020-06-07 21:10:39
 */
@Data
@EqualsAndHashCode (callSuper = true)
public class UserRegisterEvent extends ApplicationEvent {

  private String userName;

  public UserRegisterEvent(Object source) {
    super(source);
  }
}
