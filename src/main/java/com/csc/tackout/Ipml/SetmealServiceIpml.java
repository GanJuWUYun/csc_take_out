package com.csc.tackout.Ipml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csc.tackout.common.CustomException;
import com.csc.tackout.dto.SetmealDto;
import com.csc.tackout.entity.Setmeal;
import com.csc.tackout.entity.SetmealDish;
import com.csc.tackout.mapper.SetmealMapper;
import com.csc.tackout.service.SetmealDishService;
import com.csc.tackout.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:SetmealServiceIpml
 * Package:com.csc.tackout.Ipml
 * Description:
 *
 * @Date:3/8/2022 17:32
 * @Author:2394279444@qq.com
 */
@Transactional
@Service
public class SetmealServiceIpml extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    /**
     * 新增套餐，同时保存套餐和菜品的关联信息
     * @param setmealDto
     */
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal表，执行insert操作
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存套餐和菜品的关联信息，操作setmeal_dish，执行insert操作
        setmealDishService.saveBatch(setmealDishes);

    }

    /**
     * 删除套餐
     * @param ids
     */
    @Override
    public void removeWithDish(List<Long> ids) {
        //先判断是否可以删除 在售的不能删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = (int) this.count(queryWrapper);

        if (count>0){
            //不能删除，抛出一个业务异常
            throw new CustomException("当前套餐在售，不能删除");
        }

        //可以删除，先删除套餐里的基本信息
        this.removeByIds(ids);
        //删除关系表里的数据
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);

         setmealDishService.remove(lambdaQueryWrapper);

    }

    @Override
    public void updateSetmealStatusByIds(Integer status, List<Long> ids) {
        //构造条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ids!=null,Setmeal::getId,ids);
        List<Setmeal> setmealList = this.list(queryWrapper);
        for (Setmeal setmeal : setmealList) {
            if(setmeal !=null){
                setmeal.setStatus(status);
                this.updateById(setmeal);
            }
        }

    }

    /**
     * 根据id查询套餐
     * @param id
     */
    @Override
    public SetmealDto getDate(Long id) {
        Setmeal setmeal = this.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(id!=null,SetmealDish::getSetmealId,id);
        if(setmeal!=null){
            BeanUtils.copyProperties(setmeal,setmealDto);
            List<SetmealDish> list = setmealDishService.list(queryWrapper);
            setmealDto.setSetmealDishes(list);
            return setmealDto;
        }
        return null;

    }


}
