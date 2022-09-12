package com.hanyi.daily.common.util;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <p>
 * Lambda工具类
 * </p>
 *
 * @author wenchangwei
 * @since 2022/9/12 8:31 PM
 */
@ToString
@EqualsAndHashCode
public final class LambdaUtil<T> {

    /**
     * 属性值
     */
    private final T value;

    private LambdaUtil(T value) {
        this.value = value;
    }

    public static <T> LambdaUtil<T> of() {
        return of(null);
    }

    public static <T> LambdaUtil<T> of(T value) {
        return new LambdaUtil<>(value);
    }

    public T get() {
        return value;
    }

    /**
     * 获取或默认值
     *
     * @param defaultValue 默认值
     * @return {@link T}
     */
    public T getOrDefault(T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(value);
    }

    public LambdaUtil<T> predicate(Predicate<T> predicate) {
        return predicate.test(value) ? this : of();
    }

    public void consumer(Consumer<T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    public <U> LambdaUtil<U> function(Function<? super T, ? extends U> mapper) {
        if (value != null) {
            return of(mapper.apply(value));
        }
        return of();
    }

    public LambdaUtil<T> supplier(Supplier<? extends T> supplier) {
        if (value != null) {
            return of(supplier.get());
        }
        return of();
    }

    public void nullPredicateConsumer(Predicate<T> predicate, Consumer<T> consumer, Consumer<T> elseConsumer) {
        if (predicate.test(value)) {
            consumer.accept(value);
        } else {
            elseConsumer.accept(value);
        }
    }

    public void predicateConsumer(Predicate<T> predicate, Consumer<T> consumer, Consumer<T> elseConsumer) {
        Objects.requireNonNull(value);
        nullPredicateConsumer(predicate, consumer, elseConsumer);
    }

    public <U> LambdaUtil<U> predicateFunction(Predicate<T> predicate, Function<? super T, ? extends U> mapper,
                                               Function<? super T, ? extends U> otherMapper) {
        Objects.requireNonNull(value);
        if (predicate.test(value)) {
            return of(mapper.apply(value));
        } else {
            return of(otherMapper.apply(value));
        }
    }

    public LambdaUtil<T> nullPredicateSupplier(Predicate<T> predicate, Supplier<? extends T> supplier, Supplier<? extends T> other) {
        if (predicate.test(value)) {
            return of(supplier.get());
        } else {
            return of(other.get());
        }
    }

    public LambdaUtil<T> predicateSupplier(Predicate<T> predicate, Supplier<? extends T> supplier, Supplier<? extends T> other) {
        Objects.requireNonNull(value);
        return nullPredicateSupplier(predicate, supplier, other);
    }

}
