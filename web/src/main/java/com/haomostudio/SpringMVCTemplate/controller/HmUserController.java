package com.haomostudio.SpringMVCTemplate.controller;

import com.haomostudio.SpringMVCTemplate.dao.impl.IHmUserService;
import com.haomostudio.SpringMVCTemplate.po.HmUser;
import com.haomostudio.SpringMVCTemplate.utils.ObjectIsNullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName(类名) : MatchString
 * @Description(描述) : TODO
 * @author(作者) ：lenovo
 * @date (开发日期)      ：2018/8/2
 */
@Controller
@RequestMapping("/hm_users")
public class HmUserController {
    @Autowired
    IHmUserService hmUserService;

    @RequestMapping("/new")
    @ResponseBody
    public Map craeteUser(HttpServletRequest request, HttpServletResponse response,@RequestBody HmUser hmUser){
        Map<String,Object> map = new HashMap<String,Object>();
        if (!ObjectIsNullUtil.isNullOrEmpty(hmUser)){
            HmUser hmUser2 = new HmUser();
            hmUser2.setMobile(hmUser.getMobile());
            hmUser2.setEmail(hmUser.getEmail());
            HmUser hmUser1 = hmUserService.selectByObject(hmUser2);
            if (ObjectIsNullUtil.isNullOrEmpty(hmUser1)){
                try {
                    hmUserService.insert(hmUser);
                }catch (Exception e){
                    map.put("code","9999");
                    map.put("msg","失败！");
                    return map;
                }
                map.put("code","0000");
                map.put("msg","成功！");
                return map;
            }
        }
        map.put("code","9999");
        map.put("msg","失败！");
        return map;
    }
    @RequestMapping(value = "/{hm_user_id}",method = RequestMethod.POST)
    @ResponseBody
    public Map selectUserById(HttpServletRequest request, HttpServletResponse response,@PathVariable String hmUserId){
        Map<String,Object> map = new HashMap<String,Object>();
        if (!ObjectIsNullUtil.isNullOrEmpty(hmUserId)){
            HmUser hmUser2 = new HmUser();
            hmUser2.setLoginid(hmUserId);
            HmUser hmUser1 = hmUserService.selectByObject(hmUser2);
            if (ObjectIsNullUtil.isNullOrEmpty(hmUser1)){
                map.put("code","0000");
                map.put("msg","存在此用户！");
                return map;
            }else{
                map.put("code","9999");
                map.put("msg","失败！");
                return map;
            }
        }
        map.put("code","9000");
        map.put("msg","查询参数有误！");
        return map;
    }

    @RequestMapping(value = "/{hm_user_id}/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map updateUserById(HttpServletRequest request, HttpServletResponse response,@RequestBody HmUser hmUser,@PathVariable String userId){
        Map<String,Object> map = new HashMap<String,Object>();
        if (!ObjectIsNullUtil.isNullOrEmpty(hmUser) && !ObjectIsNullUtil.isNullOrEmpty(userId)){
            hmUser.setLoginid(userId);
            try {
                hmUserService.update(hmUser);
            }catch (Exception e){
                map.put("code","9999");
                map.put("msg","更新失败！");
                return map;
            }
            map.put("code","0000");
            map.put("msg","更新成功！");
            return map;
        }
        map.put("code","9000");
        map.put("msg","查询参数有误！");
        return map;
    }

    @RequestMapping
    @ResponseBody
    public Map selectListByParam(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<String,Object>();

        String authToken;
        if (!ObjectIsNullUtil.isNullOrEmpty(request.getParameter("X-Auth-Token"))){
            authToken = request.getParameter("X-Auth-Token");
        }
        String pageNo;
        if (!ObjectIsNullUtil.isNullOrEmpty(request.getParameter("pageNo"))){
            pageNo = request.getParameter("pageNo");
        }
        String pageSize;
        if (!ObjectIsNullUtil.isNullOrEmpty(request.getParameter("pageSize"))){
            pageSize = request.getParameter("pageSize");
        }
        String sortItem;
        if (!ObjectIsNullUtil.isNullOrEmpty(request.getParameter("sortItem"))){
            sortItem = request.getParameter("sortItem");
        }

        String sortOrder;
        if (!ObjectIsNullUtil.isNullOrEmpty(request.getParameter("sortOrder"))){
            sortOrder = request.getParameter("sortOrder");
        }
        String filters;
        if (!ObjectIsNullUtil.isNullOrEmpty(request.getParameter("filters"))){
            filters = request.getParameter("filters");
        }
        String includes;
        if (!ObjectIsNullUtil.isNullOrEmpty(request.getParameter("includes"))){
            includes = request.getParameter("includes");
        }

        String refers;
        if (!ObjectIsNullUtil.isNullOrEmpty(request.getParameter("refers"))){
            refers = request.getParameter("refers");
        }

        String relates;
        if (!ObjectIsNullUtil.isNullOrEmpty(request.getParameter("relates"))){
            relates = request.getParameter("relates");
        }

        /*if (!ObjectIsNullUtil.isNullOrEmpty(hmUser)){
            hmUser.setLoginid(userId);
            try {
                hmUserService.update(hmUser);
            }catch (Exception e){
                map.put("code","9999");
                map.put("msg","更新失败！");
                return map;
            }
            map.put("code","0000");
            map.put("msg","更新成功！");
            return map;
        }*/
        map.put("code","9000");
        map.put("msg","查询参数有误！");
        return map;
    }
}
