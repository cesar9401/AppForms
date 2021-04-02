package com.cesar31.formsweb.servlet;

import com.cesar31.formsweb.control.HandleDB;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.glassfish.jersey.message.internal.ReaderWriter.BUFFER_SIZE;

/**
 *
 * @author cesar31
 */
@WebServlet(name = "ExportForm", urlPatterns = {"/ExportForm"})
public class ExportForm extends HttpServlet {

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
        String id = request.getParameter("id-form");
        if(id != null) {
            String form = db.exportForm(id);
            if(form != null) {
                response.setContentType("application/octet-strem");
                response.setHeader("Content-Disposition", "attachment;filename=formulario.form");

                OutputStream out;
                try (InputStream in = new ByteArrayInputStream(form.getBytes(StandardCharsets.UTF_8))) {
                    out = new DataOutputStream(response.getOutputStream());
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int sizeRead = 0;
                    while((sizeRead = in.read(buffer)) >= 0) {
                        out.write(buffer, 0, sizeRead);
                    }
                }
                out.close();
            }
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
    }
}
