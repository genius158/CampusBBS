package com.yan.campusbbs.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by yan on 2017/4/12.
 */

public class RegExpUtils {

    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^\\d{11}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
