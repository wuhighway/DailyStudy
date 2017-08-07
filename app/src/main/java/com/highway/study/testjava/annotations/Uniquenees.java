package com.highway.study.testjava.annotations;

import com.highway.study.testjava.annotations.Constraints;

/**
 * @author JH
 * @date 2017/8/7
 */


public @interface Uniquenees {
    Constraints constraints() default @Constraints(unique = true);
}
