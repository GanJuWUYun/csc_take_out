package com.csc.tackout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.csc.tackout.dto.DishDto;
import com.csc.tackout.entity.Dish;

/**
 * ClassName:DishService
 * Package:com.csc.tackout.service
 * Description:
 *
 * @Date:3/8/2022 17:27
 * @Author:2394279444@qq.com
 */

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getWithFlavor(Long id);

     public void updateWithFlavor(DishDto dishDto);
}
