package com.itheima.service.impl;

import java.util.List;

import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.ProductDaoImpl;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {
    /**
     * 查询最新
     */
    @Override
    public List<Product> findNew() throws Exception {
        ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
        return pd.findNew();
    }

    /**
     * 查询热门
     */
    @Override
    public List<Product> findHot() throws Exception {
        ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
        return pd.findHot();
    }

    /**
     * 查询单个商品
     */
    @Override
    public Product getById(String pid) throws Exception {
        ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
        return pd.getById(pid);
    }

    /**
     * 按类别分页查询商品
     */
    @Override
    public PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
        ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
        List<Product> list = pd.findByPage(currPage, pageSize, cid);
        // 总条数
        int totalCount = pd.getTotalCount(cid);

        return new PageBean<>(list, currPage, pageSize, totalCount);
    }

}
