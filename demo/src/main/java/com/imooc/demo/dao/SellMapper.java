package com.imooc.demo.dao;

import com.imooc.demo.entity.Sell;
import com.imooc.demo.entity.SellExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SellMapper {
    int countByExample(SellExample example);

    int deleteByExample(SellExample example);

    int deleteByPrimaryKey(String id);

    int insert(Sell record);

    int insertSelective(Sell record);

    List<Sell> selectByExample(SellExample example);

    Sell selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Sell record, @Param("example") SellExample example);

    int updateByExample(@Param("record") Sell record, @Param("example") SellExample example);

    int updateByPrimaryKeySelective(Sell record);

    int updateByPrimaryKey(Sell record);
}