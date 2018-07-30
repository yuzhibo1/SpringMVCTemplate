package com.haomostudio.SpringMVCTemplate.common.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class SuperBeanHelper<T> {
    private final Class<T> clazz;

    public SuperBeanHelper(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<String> getPropertyName(Object obj) {
        ArrayList list = new ArrayList();
        Field[] fs = obj.getClass().getDeclaredFields();
        Field[] var7 = fs;
        int var6 = fs.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            Field f = var7[var5];
            list.add(f.getName());
        }

        return list;
    }

    public List<T> convert(List<Map<String, Object>> res) {
        ArrayList list = new ArrayList();

        try {
            Iterator var4 = res.iterator();

            while(var4.hasNext()) {
                Map e = (Map)var4.next();
                Object tempClass = this.clazz.newInstance();
                List propertyName = this.getPropertyName(tempClass);

                for(int i = 0; i < propertyName.size(); ++i) {
                    Field f = tempClass.getClass().getDeclaredField(((String)propertyName.get(i)).toString());
                    f.setAccessible(true);

                    try {
                        if(f.getType().getName().equals("int")) {
                            f.set(tempClass, Integer.valueOf(Integer.parseInt(e.get(((String)propertyName.get(i)).toString().trim()).toString())));
                        } else if(f.getType().getName().equals("java.util.Date")) {
                            SimpleDateFormat e1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date d = e1.parse(e.get(((String)propertyName.get(i)).toString().trim()).toString());
                            f.set(tempClass, d);
                        } else {
                            f.set(tempClass, e.get(((String)propertyName.get(i)).toString().trim()));
                        }
                    } catch (Exception var11) {
                        System.out.println(var11.getMessage());
                    }
                }

                list.add(tempClass);
            }
        } catch (Exception var12) {
            System.out.println(var12.getMessage());
        }

        return list;
    }
}
