package com.ztianzeng.tree;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ztianzeng.tree.mapper")
public class MpttApplication {

  public static void main(String[] args) {
    SpringApplication.run(MpttApplication.class, args);
  }
}
