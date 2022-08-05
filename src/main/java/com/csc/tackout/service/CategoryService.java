package com.csc.tackout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.csc.tackout.entity.Category;

/**
 * ClassName:CategoryService
 * Package:com.csc.tackout.service
 * Description:
 *
 * @Date:3/8/2022 16:17
 * @Author:2394279444@qq.com
 */

public interface CategoryService extends IService<Category> {

    public void remove(Long id);

}
