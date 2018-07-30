package com.haomostudio.SpringMVCTemplate.common.util;

import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BeanHelper<T> {
    private final Class<T> clazz;

    public BeanHelper(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<T> convert(List<Map<String, Object>> mapList) {
        try {
            ArrayList e = new ArrayList();
            Iterator var4 = mapList.iterator();

            while(var4.hasNext()) {
                Map map = (Map)var4.next();
                Object tempClass = this.clazz.newInstance();
                this.setValue(tempClass, map);
                e.add(tempClass);
            }

            return e;
        } catch (Exception var6) {
            return null;
        }
    }

    public int setValue(Object bean, Map<?, ?> map) throws Exception {
        int cnt = 0;
        if(bean != null && map != null) {
            Iterator names = map.keySet().iterator();

            while(names.hasNext()) {
                String name = (String)names.next();
                if(name != null) {
                    Object value = map.get(name);
                    this.setProperty(bean, name, value);
                    ++cnt;
                }
            }

            return cnt;
        } else {
            return cnt;
        }
    }

    public void setProperty(Object bean, String name, Object value) throws Exception {
        try {
            BeanUtils.copyProperty(bean, name, value);
        } catch (Exception var5) {
            throw new Exception("Set Value Error, Value: " + value.toString() + " Fields: " + name + ", Reason:/r/n" + var5, var5);
        }
    }
}
