package com.cesar31.formsclient;

import com.cesar31.formsclient.control.FileControl;
import com.cesar31.formsclient.parser.ResponseLex;
import com.cesar31.formsclient.parser.ResponseParser;
import java.io.StringReader;

/**
 *
 * @author cesar31
 */
public class TestForms {

    public static void main(String[] args) {
        // Write your code here
        FileControl control = new FileControl();
        String input = control.readData("response.indigo");
        
        //System.out.println(input);
            
        ResponseLex lex = new ResponseLex(new StringReader(input));
        ResponseParser parser = new ResponseParser(lex);
        try {
            parser.parse();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
}
