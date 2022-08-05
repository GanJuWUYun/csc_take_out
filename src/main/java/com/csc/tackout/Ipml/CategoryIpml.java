package com.csc.tackout.Ipml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csc.tackout.common.CustomException;
import com.csc.tackout.entity.Category;
import com.csc.tackout.entity.Dish;
import com.csc.tackout.entity.Setmeal;
import com.csc.tackout.mapper.CategoryMapper;
import com.csc.tackout.service.CategoryService;
import com.csc.tackout.service.DishService;
import com.csc.tackout.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:CategoryIpml
 * Package:com.csc.tackout.Ipml
 * Description:
 *
 * @Date:3/8/2022 16:18
 * @Author:2394279444@qq.com
 */
@Service
public class CategoryIpml extends ServiceImpl<CategoryMapper, Category>implements CategoryService {

    @Autowired
   private DishService dishService;

    @Autowired
    private SetmealService setmealService;


    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件 ，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = (int) dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联了菜品，如果已经关联，就抛出一个业务异常
        if(count1>0){
            //已经关联菜品，抛出业务异常
            throw  new CustomException("当前分类关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();

        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);

        int count2 = (int) setmealService.count(setmealLambdaQueryWrapper);

        if(count2>0){
            throw  new CustomException("当前分类关联了套餐，不能删除");
        }
        //正常删除分类
        super.removeById(id);

    }
}
