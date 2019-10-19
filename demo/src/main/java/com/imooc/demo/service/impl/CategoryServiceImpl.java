package com.imooc.demo.service.impl;

import com.imooc.demo.dao.CategoryMapper;
import com.imooc.demo.entity.Category;
import com.imooc.demo.entity.CategoryExample;
import com.imooc.demo.entity.Result;
import com.imooc.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 1.查询所有分类
     */
    @Override
    public List<Category> findAll() {
        CategoryExample ce = new CategoryExample();
        ce.createCriteria().andCidIsNotNull();
        List<Category> categories = categoryMapper.selectByExample(ce);
        return categories;
    }

    /***
     * @description 添加商品分类
     *
     * @param category  新添加的分类对象
     * @date 2019/7/17 9:58
     * @return com.imooc.demo.entity.Result
     */
    @Override
    public Result addCategory(Category category) {
        Result result = null;
        if (category != null) {
            category.setCid(UUID.randomUUID().toString());
            categoryMapper.insert(category);
            result = new Result(true, "添加成功", category);
        } else {
            result = new Result(false, "分类不能为空");
        }
        return result;
    }


    /***
     * @description 删除单个分类
     *
     * @param cid  分类的id
     * @date 2019/7/17 9:58
     * @return com.imooc.demo.entity.Result
     */
    @Override
    public Result deleteCategory(String cid) {
        if (StringUtils.isEmpty(cid)) {
            return new Result(false, "参数不能为空");
        }
        categoryMapper.deleteByPrimaryKey(cid);
        return new Result(true, "删除成功");
    }
}
