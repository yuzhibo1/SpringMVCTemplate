package com.haomostudio.SpringMVCTemplate.dao.impl;

import com.haomostudio.SpringMVCTemplate.po.HmUser;

/**
 * @ClassName(类名) : MatchString
 * @Description(描述) : TODO
 * @author(作者) ：lenovo
 * @date (开发日期)      ：2018/8/2
 */
public interface IHmUserService {
    int insert(HmUser hmUser);
    int update(HmUser hmUser);
    HmUser selectByObject(HmUser hmUserd);
}
