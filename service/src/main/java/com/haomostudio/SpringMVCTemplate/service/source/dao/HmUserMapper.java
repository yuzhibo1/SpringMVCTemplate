package com.haomostudio.SpringMVCTemplate.service.source.dao;


import com.haomostudio.SpringMVCTemplate.po.HmUser;

public interface HmUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(HmUser record);

    int insertSelective(HmUser record);

    HmUser selectByPrimaryKey(String id);

    HmUser selectByObject(HmUser hmUser);

    int updateByPrimaryKeySelective(HmUser record);

    int updateByPrimaryKey(HmUser record);
}