package com.mysite.jdort.utils;

import java.io.IOException;

/**
 * Class loader that supports redefinition of any particular class
 */
public final class RedefinableClassLoader extends ClassLoader {
    public RedefinableClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * Define a class in this ClassLoader.
     * @param fullClassName the class name
     * @param bytes the bytes representation of the class
     */
    public synchronized void defineClass(String fullClassName, byte[] bytes) {
        defineClass(fullClassName, bytes, 0, bytes.length);
    }

    public <I, T extends I> I reloadAndCreate(Class<T> classInst) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        final String classPath = classInst.getName();
        defineClass(classPath, ClassUtils.loadClassAsByteArray(classPath));

        final Class reloadedClass = loadClass(classPath);
        final Object newInst = reloadedClass.newInstance();

        @SuppressWarnings("unchecked")
        final I interfaceInst = (I) newInst;

        return interfaceInst;
    }

    @Override
    protected Class<?> findClass(String s) throws ClassNotFoundException {
        return super.findClass(s);
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return super.loadClass(className);
    }

    @Override
    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
        return super.loadClass(className, resolve);
    }
}

