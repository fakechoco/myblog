package com.pan.blog.service;

import com.pan.blog.vo.CategoryVo;
import com.pan.blog.vo.Result;

public interface CategoryService {

    CategoryVo findCategory(Long id);

    Result findAll();

    Result findAllDetail();

    Result findAllDetailById(Long id);
}