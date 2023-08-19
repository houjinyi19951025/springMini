package com.mini.test;

/**
 * @program: miniSpring
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 15:45
 **/

public class AServiceImpl {

    private String userName;

    private String passWord;

    private BServiceImpl bServiceImpl;

    public void say(){
        System.out.println("说hello1111.........................userName"+userName+"passWord"+passWord);

        bServiceImpl.say();
    }

    public BServiceImpl getbServiceImpl() {
        return bServiceImpl;
    }

    public void setBServiceImpl(BServiceImpl bServiceImpl) {
        this.bServiceImpl = bServiceImpl;
    }

    public AServiceImpl(String passWord) {
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
