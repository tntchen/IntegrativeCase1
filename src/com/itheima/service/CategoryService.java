package com.itheima.service;

import java.util.List;

import com.itheima.domain.Category;

public interface CategoryService {

    List<Category> findAll() throws Exception;

}
