package com.myprojects.myportfolio.clients.general.views;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

public interface IView {

    @Target({ElementType.TYPE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface View {
        @AliasFor("name")
        String value() default "";

        @AliasFor("value")
        String name() default "";
    }

    default boolean isAtLeast(IView view) {
        if (view == null) {
            throw new IllegalArgumentException("View argument cannot be null");
        }

        return isAtLeast(view.getClass());
    }

    default boolean isAtLeast(Class<? extends IView> view) {
        if (view == null) {
            throw new IllegalArgumentException("View argument cannot be null");
        }

        return view.isInstance(this);
    }

}
