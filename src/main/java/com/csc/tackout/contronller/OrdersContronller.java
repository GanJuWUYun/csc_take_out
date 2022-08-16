package com.csc.tackout.contronller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
    public R<Page> page(int page, int pageSize, String number,String beginTime,String endTime) {
        //分页构造器对象
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        //构造条件查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();

        //添加查询条件  动态sql  字符串使用StringUtils.isNotEmpty这个方法来判断
        //这里使用了范围查询的动态SQL，这里是重点！！！
        queryWrapper.like(number != null, Orders::getNumber, number)
                .gt(StringUtils.isNotEmpty(beginTime), Orders::getOrderTime, beginTime)
                .lt(StringUtils.isNotEmpty(endTime), Orders::getOrderTime, endTime);
        ordersService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }


    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.save(orders);
        return R.success("下单成功");
    }
}
