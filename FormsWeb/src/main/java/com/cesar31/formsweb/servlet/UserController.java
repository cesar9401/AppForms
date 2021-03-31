package com.cesar31.formsweb.servlet;

import com.cesar31.formsweb.control.HandleDB;
import com.cesar31.formsweb.model.User;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cesar31
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

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
        String action = request.getParameter("action");
        switch (action) {
            case "profile":
                setPage(request, response);
                break;

            case "signoff":
                signoff(request, response);
                break;
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
        switch (action) {
            case "login":
                login(request, response);
                break;
            case "searchForm":
                searchForm(request, response);
                break;
        }
    }

    /**
     * Iniciar sesion
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String pass = request.getParameter("password");

        User u = db.getUser(user, pass);
        if (u != null) {
            setProfile(u, request, response);
        } else {
            request.setAttribute("error", true);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    /**
     * Redirigir al perfil del usuario
     *
     * @param u
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void setProfile(User u, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<com.cesar31.formsweb.model.Form> fms = db.getForms(u.getUser());

        request.getSession().setAttribute("id", u.getUser());
        request.setAttribute("user", u);
        request.setAttribute("forms", fms);
        request.getRequestDispatcher("user.jsp").forward(request, response);
    }

    /**
     * Perfil del usuario o index
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void setPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = (String) request.getSession().getAttribute("id");
        if (id != null) {
            User u = db.getUser(id);
            if (u != null) {
                setProfile(u, request, response);
            } else {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    /**
     * Cerrar sesion
     *
     * @param request
     * @param response
     */
    private void signoff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * Buscar formulario
     *
     * @param request
     * @param response
     */
    private void searchForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String input = request.getParameter("id-form");
        com.cesar31.formsweb.model.Form form = db.getFormByRegex(input);

        if (form != null) {
            request.setAttribute("form", form);
            request.getRequestDispatcher("form.jsp").forward(request, response);
        } else {
            String id = (String) request.getSession().getAttribute("id");

            if (id != null) {
                User u = db.getUser(id);
                if (u != null) {
                    request.setAttribute("noMatch", true);
                    setProfile(u, request, response);
                } else {
                    request.setAttribute("noMatch", true);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }

            } else {
                request.setAttribute("noMatch", true);
                request.getRequestDispatcher("index.jsp").forward(request, response);

            }
        }
    }
}
