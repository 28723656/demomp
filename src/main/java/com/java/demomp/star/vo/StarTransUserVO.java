package com.java.demomp.star.vo;

import com.java.demomp.star.entity.StarTrans;

import lombok.Data;

/**

 @author 风往西边吹丶


 @create 2019-12-09 23:06

 */
@Data
public class StarTransUserVO extends StarTrans {
  
  private String userName;
  
  private String otherName;

}
