package com.mini.test.service;

public class Action implements IAction{
    @Override
    public void doAction() {
        System.out.println("...................真实方法");
    }

    @Override
    public void doSomething() {
        System.out.println("第二个...................真实方法");
    }

    @Override
    public void say() {
        System.out.println("第三个.....................真实方法");
    }
}
