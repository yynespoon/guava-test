package com.test.spring.scope;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Map;

/**
 * @author lixiaoyu
 * @since 2020/7/23
 */
public class ThreadLocalScope implements Scope {

    ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(() -> Maps.newHashMap());

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map<String, Object> stringObjectMap = threadLocal.get();
        Object o = stringObjectMap.get(name);
        if (o == null) {
            o = objectFactory.getObject();
            stringObjectMap.put(name, o);
        }
        return o;
    }

    @Override
    public Object remove(String name) {
        Map<String, Object> stringObjectMap = threadLocal.get();
        Object remove = stringObjectMap.remove(name);
        return remove;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        System.out.println(111);
    }

    @Override
    public Object resolveContextualObject(String key) {
        return threadLocal.get();
    }

    @Override
    public String getConversationId() {
        return String.valueOf(Thread.currentThread().getId());
    }
}
