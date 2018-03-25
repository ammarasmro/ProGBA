package utils;

import com.google.common.base.CaseFormat;

public class TextUtils {

    public static String toLowerCamelCase(String sentence){
        sentence = String.join("_",sentence.split(" ")).toUpperCase();
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, sentence); // returns "constantName"
    }

    public static String toUpperCamelCase(String sentence){
        sentence = String.join("_",sentence.split(" ")).toUpperCase();
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, sentence); // returns "constantName"
    }

    public static String toUpperUnderscored(String sentence){
        return String.join("_",sentence.split(" ")).toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(toLowerCamelCase("hi ammar"));
        System.out.println(toUpperCamelCase("hi ammar"));
        System.out.println(toUpperUnderscored("hi ammar"));


    }
}
