package com.csc.tackout.contronller;

import com.csc.tackout.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:OrderDetailController
 * Package:com.csc.tackout.contronller
 * Description:
 *
 * @Date:15/8/2022 15:12
 * @Author:2394279444@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {

    private OrderDetailService orderDetailService;
}
