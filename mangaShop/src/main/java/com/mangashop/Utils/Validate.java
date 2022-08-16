package com.mangashop.Utils;

import java.util.regex.Pattern;

public class Validate {
    public static final String NAME_REGEX = "^([A-Z]+[a-z]*[ ]?)+$";
    public static final String PHONE_REGEX = "^[0-9\\-\\+]{1,10}$";
    public static final String EMAIL_REGEX = "^([\\w\\.\\-]+[a-z/0-9])@([\\w\\-]+)((\\.(\\w){2,3})+)$";
    public static final String PRICE_REGEX = "^\\d{0,8}(\\.\\d{1,7})?$";
    public static final String AGE_REGEX = "[0-1]{1}[0-9]{0,2}";
    public static final String NAMEtv_REGEX = "[A-zỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđ']";


    public static boolean isNameValidate(String name){
        return Pattern.compile(NAME_REGEX).matcher(name).matches();
    }
    public static boolean isPhoneValidate (String phone){
        return Pattern.compile(PHONE_REGEX).matcher(phone).matches();
    }
    public static boolean isEmailValidate (String email){
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
    public static boolean isPriceValidate (String price){
        return Pattern.compile(PRICE_REGEX).matcher(price).matches();
    }
    public static boolean isAgeValidate (String age){
        return Pattern.compile(AGE_REGEX).matcher(age).matches();
    }
    public static boolean isNameTvValidate (String tv){
        return Pattern.compile(NAMEtv_REGEX).matcher(tv).matches();
    }

}
