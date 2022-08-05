package com.csc.tackout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ClassName:CSCApplication
 * Package:com.csc.tackout
 * Description:
 *
 * @Date:24/7/2022 11:57
 * @Author:2394279444@qq.com
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class CSCApplication {
    public static void main(String[] args) {
        SpringApplication.run(CSCApplication.class,args);
        log.info("项目启动中....");
    }
}
