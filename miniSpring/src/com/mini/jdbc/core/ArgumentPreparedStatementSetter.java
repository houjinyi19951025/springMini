package com.mini.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class ArgumentPreparedStatementSetter {

    private final Object[] args;

    public ArgumentPreparedStatementSetter(Object[] args) {
        this.args = args;
    }

    public void setValues(PreparedStatement pst) throws SQLException {
        if(this.args !=null){
            for(int i = 0;i<this.args.length;i++){
                Object arg = args[i];
                doSetValue(pst,i+1,arg);
            }
        }



    }

    private void doSetValue(PreparedStatement pst, int parameterPosition, Object arg) throws SQLException {
        if(arg instanceof String){
            pst.setString(parameterPosition, (String) arg);
        }else if(arg instanceof  Integer){
            pst.setInt(parameterPosition, (Integer) arg);
        } else if (arg instanceof Date) {
            pst.setDate(parameterPosition, (java.sql.Date) arg);
        }else{
            System.out.println("暂时不支持的类型设置");
        }
    }

}
