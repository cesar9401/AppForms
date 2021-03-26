package com.cesar31.formsweb.servlet;

import com.cesar31.formsweb.control.HandlerDB;
import com.cesar31.formsweb.model.Component;
import com.cesar31.formsweb.model.FormData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author cesar31
 */
@WebServlet(name = "Form", urlPatterns = {"/Form"})
@MultipartConfig(maxFileSize = 16177215)
public class Form extends HttpServlet {

    private HandlerDB db = new HandlerDB();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        com.cesar31.formsweb.model.Form form = db.getForm(id);

        if (form != null) {
            request.setAttribute("form", form);
            request.getRequestDispatcher("form.jsp").forward(request, response);
        } else {
            System.out.println("formulario no existente");
            request.getRequestDispatcher("form-not-found.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            System.out.println(action);
        }
        com.cesar31.formsweb.model.Form form = db.getForm(action);
        if (form != null) {
            obtenerRespuest(form, request, response);
        } else {
            System.out.println("Formulario Null");
        }
    }

    private void obtenerRespuest(com.cesar31.formsweb.model.Form form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Component> cm = form.getComponents();
        HashMap<String, String> data = new HashMap<>();
        for (Component c : cm) {
            switch (c.getKind()) {
                case "CAMPO_TEXTO":
                    String r1 = request.getParameter(c.getId_component());
                    if (r1 != null) {
                        data.put(c.getFieldName(), r1);
                        //System.out.println("CAMPO_TEXTO: " + r1);
                    }
                    break;

                case "AREA_TEXTO":
                    String r2 = request.getParameter(c.getId_component());
                    if (r2 != null) {
                        data.put(c.getFieldName(), r2);
                        //System.out.println("AREA_TEXTO: " + r2);
                    }
                    break;

                case "RADIO":
                    String r3 = request.getParameter(c.getId_component());
                    if (r3 != null) {
                        data.put(c.getFieldName(), r3);
                        //System.out.println("RADIO: " + r3);
                    }
                    break;

                case "CHECKBOX":
                    String[] values = request.getParameterValues(c.getId_component());
                    if (values != null) {
                        data.put(c.getFieldName(), Arrays.toString(values));
                        for (int i = 0; i < values.length; i++) {
                            //System.out.println(i + " -> " + values[i]);
                        }
                    }
                    break;

                case "COMBO":
                    String r4 = request.getParameter(c.getId_component());
                    if (r4 != null) {
                        data.put(c.getFieldName(), r4);
                        //System.out.println("COMBO: " + r4);
                    }
                    break;

                case "FICHERO":
                    Part filePart = request.getPart(c.getId_component());
                    if (filePart != null) {
                        String url = saveFile(filePart);
                        data.put(c.getFieldName(), url);
                        //System.out.println("FILE: " + filePart.getSubmittedFileName());
                    }
                    break;
            }
        }

        FormData fd = new FormData(form.getId_form(), form.getName());
        fd.getData().add(data);
        
        // Enviar informacion a db
        db.writeFormData(fd);

    }

    private String saveFile(Part filePart) {
        String path = "/home/cesar31/Java/AppForms/FormsWeb/DB/Files/" + filePart.getSubmittedFileName();
        File file = new File(path);
        try {
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }

        return path;
    }

}
