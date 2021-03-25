package com.cesar31.formsweb.servlet;

import com.cesar31.formsweb.control.HandlerDB;
import com.cesar31.formsweb.model.Component;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cesar31
 */
@WebServlet(name = "Form", urlPatterns = {"/Form"})
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
        com.cesar31.formsweb.model.Form form = db.getForm(action);
        if (form != null) {
            obtenerRespuest(form, request, response);
        } else {
            System.out.println("Formulario Null");
        }
    }

    private void obtenerRespuest(com.cesar31.formsweb.model.Form form, HttpServletRequest request, HttpServletResponse response) {
        List<Component> cm = form.getComponents();
        for (Component c : cm) {
            switch (c.getKind()) {
                case "CAMPO_TEXTO":
                    String r1 = request.getParameter(c.getId_component());
                    if (r1 != null) {
                        System.out.println("CAMPO_TEXTO: " + r1);
                    }
                    break;

                case "AREA_TEXTO":
                    String r2 = request.getParameter(c.getId_component());
                    if (r2 != null) {
                        System.out.println("AREA_TEXTO: " + r2);
                    }
                    break;

                case "RADIO":
                    String r3 = request.getParameter(c.getId_component());
                    if (r3 != null) {
                        System.out.println("RADIO: " + r3);
                    }
                    break;

                case "CHECKBOX":
                    String[] values = request.getParameterValues(c.getId_component());
                    if(values != null) {
                        for (int i = 0; i < values.length; i++) {
                            System.out.println(i + " -> " + values[i]);
                        }
                    }
                    break;
                    
                case "COMBO":
                    String r4 = request.getParameter(c.getId_component());
                    if(r4 != null) {
                        System.out.println("COMBO: " + r4);
                    }
                    break;
            }
        }
    }
}
