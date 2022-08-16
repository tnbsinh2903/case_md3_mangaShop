package com.mangashop.Utils;

import java.text.DecimalFormat;

public class FormatVND {

    public static String doubleToVND(double price){
        String patternVND = ",###đ";
        DecimalFormat decimalFormat = new DecimalFormat(patternVND);
        return decimalFormat.format(price);
    }
}


