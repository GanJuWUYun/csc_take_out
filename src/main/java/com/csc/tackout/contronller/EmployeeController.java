package com.csc.tackout.contronller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csc.tackout.common.R;
import com.csc.tackout.entity.Employee;
import com.csc.tackout.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName:EmployeeController
 * Package:com.csc.tackout.contronller
 * Description:
 *
 * @Date:28/7/2022 20:55
 * @Author:2394279444@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     */
    @PostMapping("/login")
    public R login(HttpServletRequest request, @RequestBody Employee employee){

        // 将提交的password进行MD5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据页面提交的用户名username来查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //如果没有查询到返回登录失败页面
        if(emp == null){
            return R.error("登录失败");
        }

        //密码比对，如果不一致就返回登录失败页面
        if (!emp.getPassword().equals(password)){
            return R.error("登录失败！");
        }
        //查看员工状态，如果是禁用状态，返回禁用结果
        if(emp.getStatus() == 0){
            return R.error("账号已禁用");
        }

        //登录成功，将员工id存入session，并返回结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 用户退出页面
     */
    @PostMapping("/logout")
    public R<String> logOut(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        //设置初始密码123456 MD5加密处理·
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
       // employee.setCreateTime(LocalDateTime.now());
       // employee.setUpdateTime(LocalDateTime.now());

        //获得当前用户的ID
       // Long empId = (Long) request.getSession().getAttribute("employee");
       // employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }
        /**
         * 员工分页查询
         */
    @GetMapping("/page")
    public R<Page> page(int page ,int pageSize, String name){
        //构造分页构造器
        Page page1 =  new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(page1,queryWrapper);

        return R.success(page1);
    }

    /**
     * 根据id修改员工信息
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
       // Long empId = (Long) request.getSession().getAttribute("employee");
      //  employee.setUpdateTime(LocalDateTime.now());
       // employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * 根据id编辑员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return  R.success(employee);
        }
            return R.error("没有查询到员工");

    }
}
