package com.cesar31.formsclient.control;

import com.cesar31.formsclient.model.ErrorResponse;
import com.cesar31.formsclient.model.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class HandleResponse {

    private List<Response> responses;
    private List<ErrorResponse> errors;

    private HashMap<String, String> params;

    public HandleResponse() {
        this.responses = new ArrayList<>();
        this.errors = new ArrayList<>();

        this.params = new HashMap<>();
    }

    public void createResponse(String type) {
        if (type.equals("ERROR")) {
            String lexema = getParam("lexema");
            String kind = getParam("type");
            int line = getParamInt("line");
            int column = getParamInt("column");
            String description = getParam("description");

            ErrorResponse e = new ErrorResponse(lexema, kind, line, column, description);
            errors.add(e);
            System.out.println(e.toString());
        } else {
            int number = getParamInt("request");
            int line = getParamInt("line");
            int column = getParamInt("column");
            String kind = getParam("type");
            String message = getParam("message");

            Response r = new Response(number, line, column, kind, message);
            responses.add(r);
            System.out.println(r.toString());
        }
        params.clear();
    }

    private int getParamInt(String param) {
        try {
            return Integer.valueOf(getParam(param));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String getParam(String param) {
        return params.remove(param);
    }

    public void setParam(String key, String value) {
        this.params.put(key, value);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List getList() {
        return (errors.isEmpty()) ? responses : errors;
    }
}
