package com.homofabers.shopezy.helpers;

import android.util.Log;

import java.util.regex.Pattern;

public class DataInputValidator {

//    for email
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*" +
                    "@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

//    for phone number
    private static final String MOBILE_REGEX = "^\\+(?:[0-9] ?){3,14}[0-9]$" ;
    private static final Pattern MOBILE_PATTERN = Pattern.compile(MOBILE_REGEX);



    public DataInputValidator(){
    }

    public boolean emailValidator(String email){
        if(EMAIL_PATTERN.matcher(email).matches()) return true;
        else return false;
    }

    public boolean mobileValidator(String phone){
        if(MOBILE_PATTERN.matcher(phone).matches()) return true;
        else return false;
    }

    public boolean textValidator(String text){
        if(text.length() >= 3) return true;
        else return false;
    }

}
