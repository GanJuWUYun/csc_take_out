package com.csc.tackout.Ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csc.tackout.entity.Employee;
import com.csc.tackout.mapper.EmployeeMapper;
import com.csc.tackout.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * ClassName:EmployeeServiceIpml
 * Package:com.csc.tackout.Ipml
 * Description:
 *
 * @Date:28/7/2022 20:52
 * @Author:2394279444@qq.com
 */
@Service
public class EmployeeServiceIpml extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
