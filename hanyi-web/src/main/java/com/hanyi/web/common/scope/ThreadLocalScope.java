package com.hanyi.web.common.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 自定义线程级别的scope
 * </p>
 *
 * @author wenchangwei
 * @since 4:09 下午 2021/4/18
 */
public class ThreadLocalScope implements Scope {

    /**
     * 作用域名称
     */
    public static final String SCOPE_NAME = "thread-local";

    private final NamedThreadLocal<Map<String, Object>> threadLocal = new NamedThreadLocal<Map<String, Object>>("thread-local-scope") {
        @Override
        public Map<String, Object> initialValue() {
            return new HashMap<>(1);
        }
    };

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        return this.getContext().computeIfAbsent(name, k -> objectFactory.getObject());
    }

    @NonNull
    private Map<String, Object> getContext() {
        return threadLocal.get();
    }

    @Override
    public Object remove(String name) {
        return this.getContext().remove(name);
    }

    /**
     * 销毁方法
     *
     * @param name     名称
     * @param callback 回调线程
     */
    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        this.getContext().clear();
    }

    @Override
    public Object resolveContextualObject(String key) {
        return this.getContext().get(key);
    }

    @Override
    public String getConversationId() {
        return String.valueOf(Thread.currentThread().getId());
    }
}
