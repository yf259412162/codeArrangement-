package com.example.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)  //申明作用域,用在类上
@Retention(RetentionPolicy.CLASS) //申明生命周期,表示是在编译期  java --->class ---->JVM   就是说是在class阶段
public @interface BindPath {
    String value();
}
