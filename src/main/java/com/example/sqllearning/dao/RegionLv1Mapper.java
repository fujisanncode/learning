package com.example.sqllearning.dao;

import com.example.sqllearning.vo.mybatis.RegionLv1;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface RegionLv1Mapper {

    int deleteByPrimaryKey(Integer id);

    int insert(RegionLv1 record);

    int insertSelective(RegionLv1 record);

    RegionLv1 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RegionLv1 record);

    int updateByPrimaryKey(RegionLv1 record);

    RegionLv1 selectByRegion(@Param ("region") RegionLv1 region);

    void lockTable();

    void unLockTable();
}