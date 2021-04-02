package com.cesar31.formsweb.servlet;

import com.cesar31.formsweb.control.HandleDB;
import com.cesar31.formsweb.model.Component;
import com.cesar31.formsweb.model.FormData;
import com.cesar31.formsweb.model.User;
import java.io.IOException;
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

    private HandleDB db = new HandleDB();

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
            com.cesar31.formsweb.model.Form form = db.getForm(action);
            if (form != null) {
                obtenerRespuest(form, request, response);

                //Redirigir a perfil o index
                setProfile(request, response);

            } else {
                request.getRequestDispatcher("form-not-found.jsp").forward(request, response);
            }

        } else {
            request.getRequestDispatcher("form-not-found.jsp").forward(request, response);
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
                        r1 = new String(r1.getBytes("ISO-8859-1"), "UTF-8");
                        data.put(c.getFieldName(), r1);
                    }
                    break;

                case "AREA_TEXTO":
                    String r2 = request.getParameter(c.getId_component());
                    if (r2 != null) {
                        r2 = new String(r2.getBytes("ISO-8859-1"), "UTF-8");
                        data.put(c.getFieldName(), r2);
                    }
                    break;

                case "RADIO":
                    String r3 = request.getParameter(c.getId_component());
                    if (r3 != null) {
                        data.put(c.getFieldName(), r3);
                    }
                    break;

                case "CHECKBOX":
                    String[] values = request.getParameterValues(c.getId_component());
                    if (values != null) {
                        data.put(c.getFieldName(), Arrays.toString(values));
                    }
                    break;

                case "COMBO":
                    String r4 = request.getParameter(c.getId_component());
                    if (r4 != null) {
                        data.put(c.getFieldName(), r4);
                    }
                    break;

                case "FICHERO":
                    Part filePart = request.getPart(c.getId_component());
                    if (filePart != null) {
                        String url_file = db.saveFile(filePart);
                        data.put(c.getFieldName(), url_file);
                    }
                    break;
            }
        }

        FormData fd = new FormData(form.getId_form(), form.getName());
        fd.getData().add(data);


        // Enviar informacion a db
        db.addData(fd);
    }

    private void setProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = (String) request.getSession().getAttribute("id");
        request.setAttribute("answer", true);
        if (id != null) {

            User u = db.getUser(id);
            if (u != null) {
                List<com.cesar31.formsweb.model.Form> fms = db.getForms(u.getUser());

                request.getSession().setAttribute("id", u.getUser());
                request.setAttribute("user", u);
                request.setAttribute("forms", fms);
                request.getRequestDispatcher("user.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("index.jsp").forward(request, response);

            }
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
