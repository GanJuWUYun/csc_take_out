package com.csc.tackout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.csc.tackout.entity.Orders;

/**
 * ClassName:OrdersService
 * Package:com.csc.tackout.service
 * Description:
 *
 * @Date:13/8/2022 10:54
 * @Author:2394279444@qq.com
 */

public interface OrdersService extends IService<Orders> {

    public void submit(Orders orders);
}
