package com.mini.util;

public interface ObjectMapper {

    void setDateFormat(String dateFormat);

    void setDecimalFormat(String decimalFormat);

    String writerValueAsString(Object obj);


}
