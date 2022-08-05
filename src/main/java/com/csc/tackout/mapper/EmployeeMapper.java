package com.csc.tackout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csc.tackout.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName:EmployeeMapper
 * Package:com.csc.tackout.mapper
 * Description:
 *
 * @Date:28/7/2022 20:47
 * @Author:2394279444@qq.com
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
