package com.nhsoft.share.mybatis.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CopyUtil {

    private static Map map = new HashMap();

    /**
     * 集合属性的赋值
     * @param sourceList 实体类集合
     * @param clazz dto类
     * @param <S> 实体类泛型
     * @param <T> dto泛型
     * @return dto的集合
     * @throws Exception
     */
    public static<S,T>  List<T>  toList(List<S> sourceList, Class<T> clazz) throws Exception {
        List<T> targetList=new ArrayList<>();
        for (S sourceTemp : sourceList) {
            T result= to(sourceTemp, clazz);
            targetList.add(result);
        }
        return targetList;
    }

    /**
     * 单个实体类的赋值
     * @param source 实体类
     * @param clazz dto类
     * @param <S> 实体类的泛型
     * @param <T> dto的泛型
     * @return dto
     * @throws Exception
     */
    public static <S,T> T to(S source, Class<T> clazz) throws Exception {

        BeanCopier copier = (BeanCopier)map.get(source.getClass().toString() + clazz.toString());
        if(copier == null){
            copier = BeanCopier.create(source.getClass(), clazz, false);
            map.put(source.getClass().toString() + clazz.toString(), copier);
        }

        T result = (T) Class.forName(clazz.getName()).newInstance();
        copier.copy(source, result, null);
        return result;
    }
}
