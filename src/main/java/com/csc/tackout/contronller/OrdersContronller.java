package com.csc.tackout.contronller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csc.tackout.common.R;
import com.csc.tackout.entity.Orders;
import com.csc.tackout.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName:OrdersContronller
 * Package:com.csc.tackout.contronller
 * Description:
 *
 * @Date:13/8/2022 10:58
 * @Author:2394279444@qq.com
 */

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersContronller {
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        Page<Orders> pageinfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.save(orders);
        return R.success("下单成功");
    }
}
