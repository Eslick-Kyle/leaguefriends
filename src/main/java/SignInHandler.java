
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Shane
 */
@WebServlet(name = "SignInHandler", urlPatterns = {"/SignInHandler"})
public class SignInHandler extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Create Factory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lolfriendsPersistenceUnit");
        EntityManager em = emf.createEntityManager();

        String username = null;
        String password = null;
        String Error = null;
        User user = null;

        // Check to see if password or username are empty
        if (!request.getParameter("username").isEmpty()) {
            username = request.getParameter("username");
        } else {
            Error = "Username or Password Invalid";
            request.getSession().setAttribute("Error", Error);
            request.getRequestDispatcher("BadLogin.jsp").forward(request, response);
        }

        if (!request.getParameter("password").isEmpty()) {
            password = request.getParameter("password");
        } else {
            Error = "Username or Password Invalid";
            request.getSession().setAttribute("Error", Error);
            request.getRequestDispatcher("BadLogin.jsp").forward(request, response);
        }

        // Check for username in DB
        try { //Prepared Statement
            user = (User) em.createQuery("SELECT u FROM User u WHERE u.user_name = :username").setParameter("username", username).getSingleResult();
        } catch (Exception e) {
            Error = "Username or Password Invalid";
            request.getSession().setAttribute("Error", Error);
            request.getRequestDispatcher("BadLogin.jsp").forward(request, response);
        }

        // check to see if userpassword and entered password match
        if (BCrypt.checkpw(password, user.getPassword())) {
            // Put User Object on the session
            request.getSession().setAttribute("user", user);
            // User is LOGGED IN
            request.getSession().setAttribute("loggedIn", "TRUE");
            // Forward to Welcome
            request.getRequestDispatcher("Welcome.jsp").forward(request, response); 
        } else {
            Error = "Username or Password Invalid";
            request.getSession().setAttribute("Error", Error);
            request.getRequestDispatcher("BadLogin.jsp").forward(request, response);
        }

    }

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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
