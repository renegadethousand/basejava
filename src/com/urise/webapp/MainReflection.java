package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume();
        Field declaredField = resume.getClass().getDeclaredFields()[0];
        System.out.println(declaredField.getName());
        declaredField.setAccessible(true);
        Object o = declaredField.get(resume);
        declaredField.set(resume, "new");
        Object o1 = declaredField.get(resume);
        Method toString = resume.getClass().getDeclaredMethod("toString");
        toString.invoke(resume);
        // TODO : invoke resume.toString via reflection
        System.out.println(resume);
    }
}
