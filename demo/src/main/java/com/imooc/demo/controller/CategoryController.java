package com.imooc.demo.controller;

import com.imooc.demo.entity.Category;
import com.imooc.demo.entity.Result;
import com.imooc.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 1.查询所有分类
     */
    @RequestMapping("/findAll.do")
    @ResponseBody
    public List<Category> findAll() {
        List<Category> categories = null;
        try {
            categories = categoryService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    /***
     * @description 添加商品分类
     *
     * @param category  新添加的分类对象
     * @date 2019/7/17 9:58
     * @return com.imooc.demo.entity.Result
     */
    @RequestMapping("/addCategory.do")
    @ResponseBody
    public Result addCategory(@RequestBody Category category) {
        Result result = null;
        try {
            result = categoryService.addCategory(category);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, e.getMessage());
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
    @RequestMapping("/deleteCategory.do")
    @ResponseBody
    public Result deleteCategory(String cid) {
        Result result = null;
        try {
            result = categoryService.deleteCategory(cid);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, e.getMessage());
        }
        return result;
    }

}
