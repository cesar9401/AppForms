package com.cesar31.formsweb.parser.db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cesar31
 */
public class TestParserDB {

    public static void main(String[] args) {
        
        String match = null;
        
        String input1 = "http://localhost:8080/FormsWeb/Form?id=$form-test";
        String input2 = "$form-test";

        String input = "$_form-test";

        Pattern pat = Pattern.compile("^[$-_]([$-_]|[^\\n\\s\\f\\t\\r])+$");

        Pattern pat2 = Pattern.compile("^http:\\/\\/localhost:8080\\/FormsWeb\\/Form\\?id=([$-_]([S-_]|[^\\n\\s\\f\\t\\r])+)$");

        Matcher matcher = pat.matcher(input);
        if(matcher.find()) {
            match = matcher.group();
        }

        Matcher matcher2 = pat2.matcher(input);
        if (matcher2.find()) {
            match = matcher2.group(1);
        }
        
        if(match != null) {
            System.out.println(match);
        }
        
//        String in = " ";
//        in = in.trim();
//        System.out.println(in.isEmpty());

    }
}
