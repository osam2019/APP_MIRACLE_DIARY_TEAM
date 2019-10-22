package com.miracle.miraclediary;

public class Singleton {
    // Private constructor prevents instantiation from other classes
    protected Singleton() {
    }

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class SingletonHolder {
        public static final Singleton INSTANCE = new Singleton();
    }
}
