package com.csc.tackout.Ipml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csc.tackout.common.CustomException;
import com.csc.tackout.dto.DishDto;
import com.csc.tackout.entity.Dish;
import com.csc.tackout.entity.DishFlavor;
import com.csc.tackout.mapper.DishMapper;
import com.csc.tackout.service.DishFlavorService;
import com.csc.tackout.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:DishServiceIpml
 * Package:com.csc.tackout.Ipml
 * Description:
 *
 * @Date:3/8/2022 17:28
 * @Author:2394279444@qq.com
 */
@Service
@Transactional
public class DishServiceIpml extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    /**
     * 新增菜品，同时保存相应的口味数据
     * @param dishDto
     */
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表
        this.save(dishDto);
        //菜品id
        Long disId = dishDto.getId();
        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
       flavors =  flavors.stream().map((item) -> {
            item.setDishId(disId);
            return item;
        }).collect(Collectors.toList());
       //保存口味数据到菜品口味表
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查询菜品信息的对应的口味信息
     * @param id
     * @return
     */
    @Override
    public DishDto getWithFlavor(Long id) {
        //查询菜品信息，从dish查询
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //查询对应的口味信息，从dish——flavor查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表的基本信息
        this.updateById(dishDto);
        //清理当前菜品对应口味数据---dish_flavor的delete的操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        //添加当前提交过来的数据------insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }


    @Override
    public void deleteByIds(List<Long> ids) {
        //构造条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //判断商品是否售卖，如果是抛出异常
        queryWrapper.in(ids!=null,Dish::getId,ids);
        List<Dish> dishList = this.list(queryWrapper);
        for (Dish dish : dishList) {
            Integer status = dish.getStatus();
            if (status == 0){
                this.removeById(dish.getId());
            }else {
                throw new CustomException("正在售卖，不能删除");
            }
        }

    }
}
