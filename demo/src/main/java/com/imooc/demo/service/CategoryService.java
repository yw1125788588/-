package com.imooc.demo.service;

import com.imooc.demo.entity.Category;
import com.imooc.demo.entity.Result;

import java.util.List;
public interface CategoryService {
    /** 1.查询所有分类 */
    List<Category> findAll();
    /** 2.添加新的分类 */
    Result addCategory(Category category);
    /** 3.删除分类 */
    Result deleteCategory(String cid);
}
