package com.csc.tackout.contronller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csc.tackout.common.R;
import com.csc.tackout.dto.SetmealDto;
import com.csc.tackout.entity.Category;
import com.csc.tackout.entity.Setmeal;
import com.csc.tackout.entity.SetmealDish;
import com.csc.tackout.service.CategoryService;
import com.csc.tackout.service.SetmealDishService;
import com.csc.tackout.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:SetmealController
 * Package:com.csc.tackout.contronller
 * Description:
 *
 * @Date:5/8/2022 10:38
 * @Author:2394279444@qq.com
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;



    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    /**
     * 套餐分类查询
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        //分页构造器
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null,Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();

            BeanUtils.copyProperties(item,setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据id查询对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();

                setmealDto.setCategoryName(categoryName);
            }
            return  setmealDto;

        }).collect(Collectors.toList());

        dtoPage.setRecords(list);

        return R.success(dtoPage);

    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
        @DeleteMapping
        public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("删除套餐成功");

        }

    /**
     * 查询所有数据
     * @return
     */

    @GetMapping("/list")
        public R<List<Setmeal>> list(Setmeal setmeal){
            //条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list();
        return  R.success(list);
        }

    /**
     * 批量启售
     * @return
     */
    @PostMapping("/status/{status}")
        public R<String> status(@PathVariable("status") Integer status,@RequestParam List<Long> ids){
            setmealService.updateSetmealStatusByIds(status,ids);
            return R.success("成功");

        }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */

    @GetMapping("/{id}")
        public R<SetmealDto> getData(@PathVariable Long id){
            SetmealDto setmealDto = setmealService.getDate(id);
            return R.success(setmealDto);
        }


    @PutMapping
    public R<String> edit(@RequestBody SetmealDto setmealDto){

        if (setmealDto==null){
            return R.error("请求异常");
        }

        if (setmealDto.getSetmealDishes()==null){
            return R.error("套餐没有菜品,请添加套餐");
        }
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        Long setmealId = setmealDto.getId();

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealId);
        setmealDishService.remove(queryWrapper);

        //为setmeal_dish表填充相关的属性
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealId);
        }
        //批量把setmealDish保存到setmeal_dish表
        setmealDishService.saveBatch(setmealDishes);
        setmealService.updateById(setmealDto);

        return R.success("套餐修改成功");
    }

}
