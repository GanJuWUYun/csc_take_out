package com.csc.tackout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.csc.tackout.dto.SetmealDto;
import com.csc.tackout.entity.Setmeal;

import java.util.List;

/**
 * ClassName:SetmealService
 * Package:com.csc.tackout.service
 * Description:
 *
 * @Date:3/8/2022 17:28
 * @Author:2394279444@qq.com
 */

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐。同时保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto  setmealDto);

    /**
     * 删除套餐
     */
    public void  removeWithDish(List<Long> ids);
}
