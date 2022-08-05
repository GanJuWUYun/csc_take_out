package com.csc.tackout.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * ClassName:MyMetaObjectHandler
 * Package:com.csc.tackout.common
 * Description:
 *
 * @Date:2/8/2022 22:17
 * @Author:2394279444@qq.com
 */

/**
 *  自定义元数据处理器
 */

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 进行插入操作时，填充数据
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("createUser",BaseContext.getId());
        metaObject.setValue("updateUser",BaseContext.getId());

    }

    /**
     * 进行更新操作时，填充数据
     * @param metaObject
     */

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getId());


    }
}
