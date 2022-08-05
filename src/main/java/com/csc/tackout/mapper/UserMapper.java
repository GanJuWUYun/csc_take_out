package com.csc.tackout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csc.tackout.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName:UserMapper
 * Package:com.csc.tackout.mapper
 * Description:
 *
 * @Date:5/8/2022 16:32
 * @Author:2394279444@qq.com
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
