package com.hanyi.daily.common.genericity;

import com.hanyi.daily.pojo.User;

/**
 * @PackAge: middleground com.hanyi.daily.common.genericity
 * @Author: weiwenchang
 * @Description: 泛型通配符
 * @CreateDate: 2020-02-21 22:29
 * @Version: 1.0
 */
public class GenericType<T> {

    private T t;

    private T get() {
        return t;
    }

    private void set(T t) {
        this.t = t;
    }

    private T get(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        GenericType<User> userGenericType = new GenericType<>();

        User user = User.builder().userId(1).userName("柯基").userAge(20).build();

        userGenericType.set(user);

        User t = userGenericType.get();

        User u = userGenericType.get(User.class);
        System.out.println(t+"||"+u);
    }

}
