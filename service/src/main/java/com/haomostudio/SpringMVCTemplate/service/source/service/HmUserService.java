package com.haomostudio.SpringMVCTemplate.service.source.service;

import com.haomostudio.SpringMVCTemplate.dao.impl.IHmUserService;
import com.haomostudio.SpringMVCTemplate.po.HmUser;
import com.haomostudio.SpringMVCTemplate.service.source.dao.HmUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName(类名) : MatchString
 * @Description(描述) : TODO
 * @author(作者) ：lenovo
 * @date (开发日期)      ：2018/8/2
 */
@Component
public class HmUserService implements IHmUserService{
    @Autowired
    private HmUserMapper hmUserMapper;
    @Override
    public int insert(HmUser hmUser) {
        Integer i = hmUserMapper.insertSelective(hmUser);
        return i;
    }

    @Override
    public int update(HmUser hmUser) {
        Integer i = hmUserMapper.updateByPrimaryKeySelective(hmUser);
        return i;
    }

    @Override
    public HmUser selectByObject(HmUser hmUser){
        return hmUserMapper.selectByObject(hmUser);
    }

    /*@Override
    public List<HmUser> listByObject(HmUser hmUser){
        return hmUserMapper.selectByObject(hmUser);
    }*/
}
