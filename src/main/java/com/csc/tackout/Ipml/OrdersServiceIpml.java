package com.csc.tackout.Ipml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csc.tackout.common.BaseContext;
import com.csc.tackout.common.CustomException;
import com.csc.tackout.entity.*;
import com.csc.tackout.mapper.OrdersMapper;
import com.csc.tackout.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * ClassName:OrdersServiceIpml
 * Package:com.csc.tackout.Ipml
 * Description:
 *
 * @Date:13/8/2022 10:57
 * @Author:2394279444@qq.com
 */
@Service
@Transactional
public class OrdersServiceIpml extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Override
    public void submit(Orders orders) {
        //获得当前用户id
        long id = BaseContext.getId();
        //查询当前用户购物车数据
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,id);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        if(list ==null || list.size() ==0){
            throw new  CustomException("购物车为空,不能下单");
        }
        //查询用户数据
        User user = userService.getById(id);
        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if(addressBook == null){
            throw new CustomException("地址为空,不能下单");
        }

        long id1 = IdWorker.getId();//订单号

        AtomicInteger atomicInteger = new AtomicInteger(0);
        List<OrderDetail> orderDetails = list.stream().map((item)->{
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(id1);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            atomicInteger.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        //向订单表插入数据，一条数据
        orders.setId(id1) ;
        orders.setOrderTime(LocalDateTime.now() ) ;
        orders.setCheckoutTime(LocalDateTime. now() );
        orders.setStatus(2) ;
        orders.setAmount(new BigDecimal(atomicInteger.get()));
        orders.setNumber(String. valueOf(id1));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName () == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ?"" : addressBook.getCityName())
                + (addressBook. getDistrictName() == null ? "" : addressBook.getDistrictName())+ (addressBook.getDetail() == null ? "" : addressBook.getDetail())) ;
        this.save(orders);

        //向订单详情表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);
        //清除购物车数据
        shoppingCartService.remove(queryWrapper);
    }
}
