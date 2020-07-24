package com.test.spring.processor;

import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * @author lixiaoyu
 * @since 2020/7/24
 */
public class MySmartInitializingSingleton implements SmartInitializingSingleton {
    /**
     * 在 spring bean 创建完成之后回调
     *
     * 此时 bean 状态已经确定
     */
    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("create bean finish");
    }
}
