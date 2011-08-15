package com.mysite.aopsetprop.jpf.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * Introduces property changed aspect
 */
@Aspect
public class PropertyChangedAspect {
    @After(value = "execution (public void *.set*(..))")
    public void setterAspect() {
        System.out.println("[set aspect]!");
    }
}
