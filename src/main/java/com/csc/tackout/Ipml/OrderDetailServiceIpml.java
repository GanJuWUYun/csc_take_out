package com.csc.tackout.Ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csc.tackout.entity.OrderDetail;
import com.csc.tackout.mapper.OrderDetailMapper;
import com.csc.tackout.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * ClassName:OrderDetailServiceIpml
 * Package:com.csc.tackout.Ipml
 * Description:
 *
 * @Date:15/8/2022 15:10
 * @Author:2394279444@qq.com
 */
@Service
public class OrderDetailServiceIpml extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
