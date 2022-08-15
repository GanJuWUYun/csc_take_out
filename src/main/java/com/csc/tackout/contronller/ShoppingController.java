package com.csc.tackout.contronller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.csc.tackout.common.BaseContext;
import com.csc.tackout.common.R;
import com.csc.tackout.entity.ShoppingCart;
import com.csc.tackout.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName:ShoppingController
 * Package:com.csc.tackout.contronller
 * Description:
 *
 * @Date:15/8/2022 11:40
 * @Author:2394279444@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("加入购物车:{}",shoppingCart);
        //设置用户id，指定哪个用户的购物车数据
        long id = BaseContext.getId();
        shoppingCart.setUserId(id);
        //查询当前菜品或者套餐是否在购物车中
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,shoppingCart.getUserId());
        if(dishId !=null){
           //添加购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);

        }else {
            //添加购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);

        if(cartServiceOne!=null){
            //如果已经存在就在原先基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number+1);
            shoppingCartService.updateById(cartServiceOne);
        }else {
            //如果不存在，则添加购物车，默认加1
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne= shoppingCart;
        }


        return R.success(cartServiceOne);
    }

    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 清除购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getId());
        shoppingCartService.remove(queryWrapper);
        return R.success("清除购物车成功");
    }
}
