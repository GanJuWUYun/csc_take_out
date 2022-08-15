package com.csc.tackout.Ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csc.tackout.entity.ShoppingCart;
import com.csc.tackout.mapper.ShoppingCartMapper;
import com.csc.tackout.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * ClassName:ShoppingServiceIpml
 * Package:com.csc.tackout.Ipml
 * Description:
 *
 * @Date:15/8/2022 11:38
 * @Author:2394279444@qq.com
 */

@Service
public class ShoppingServiceIpml extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
