package com.mini.test;

/**
 * @program: miniSpring
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 15:45
 **/

public class BServiceImpl {

    private String userName;

    private String passWord;

    private TestServiceImpl testServiceImpl;


    public TestServiceImpl getTestServiceImpl() {
        return testServiceImpl;
    }

    public void setTestServiceImpl(TestServiceImpl testServiceImpl) {
    this.testServiceImpl = testServiceImpl;
    }

    public void say(){
        System.out.println("我是BServiceImpl我有TestServiceImpl"+testServiceImpl);
    }

    public BServiceImpl(String passWord) {
        System.out.println("create BServiceImpl ");
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
