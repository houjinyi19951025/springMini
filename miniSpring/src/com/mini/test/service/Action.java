package com.mini.test.service;

public class Action implements IAction{
    @Override
    public void doAction() {
        System.out.println("...................真实方法");
    }
}
