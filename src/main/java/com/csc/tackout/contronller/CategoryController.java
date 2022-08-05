package com.csc.tackout.contronller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csc.tackout.common.R;
import com.csc.tackout.entity.Category;
import com.csc.tackout.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:CategoryController
 * Package:com.csc.tackout.contronller
 * Description:
 *
 * @Date:3/8/2022 16:20
 * @Author:2394279444@qq.com
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){

        categoryService.save(category);
        return R.success("添加成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        //分页构造器
        Page<Category> pageInfo = new Page<>(page,pageSize);

        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();

        //添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);

    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */

    @DeleteMapping
    public R<String> delete(Long id){
       // categoryService.removeById(id);

        categoryService.remove(id);
        return R.success("分类信息删除成功");
    }

    /**
     * 修改分类信息
     */
    @PutMapping
    public R<String>  update(@RequestBody Category category){

        categoryService.updateById(category);
        return R.success("分类信息修改成功");
    }

    /**
     * 根据条件查询数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //添加条件构造器
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        lambdaQueryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        //添加排序条件
        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(lambdaQueryWrapper);

        return R.success(list);
    }
}
