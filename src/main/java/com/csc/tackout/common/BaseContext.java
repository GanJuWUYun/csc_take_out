package com.csc.tackout.common;

/**
 * ClassName:BaseContext
 * Package:com.csc.tackout.common
 * Description: ThreadLocal工具类 ，用户可以保存或获取当前id
 *
 * @Date:3/8/2022 14:32
 * @Author:2394279444@qq.com
 */

public class BaseContext {
    private static ThreadLocal<Long> threadLocal  =  new ThreadLocal<Long>();

    /**
     * 获取用户id
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获得用户id
     * @return
     */
    public static long getId(){

        return threadLocal.get();
    }
}
