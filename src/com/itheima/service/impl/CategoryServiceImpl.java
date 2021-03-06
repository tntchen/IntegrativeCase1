package com.itheima.service.impl;

import java.io.InputStream;
import java.util.List;

import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CategoryServiceImpl implements CategoryService {

    @Override
    public List<Category> findAll() throws Exception {
        // 1.创建缓存管理器
        CacheManager cm = CacheManager
                .create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));

        // 2.获取指定的缓存
        Cache cache = cm.getCache("categoryCache");

        // 3.通过缓存获取数据 将cache看作map集合
        Element element = cache.get("clist");
        List<Category> list = null;

        // 4.判断数据
        if (element == null) {
            // 从数据库中获取
            CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
            list = cd.findAll();
            // 将list放入缓存
            cache.put(new Element("clist", list));

            //System.out.println("缓存中无数据,正从数据库中取出");
        } else {
            // 直接从缓存中拿出数据返回
            list = (List<Category>) element.getObjectValue();

            //System.out.println("缓存中有数据");
        }

        return list;
    }

/*    public static void main(String[] args) {
        InputStream is = CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
        System.out.println(is);
    }*/

}
