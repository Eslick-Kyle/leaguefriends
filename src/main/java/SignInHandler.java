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
@WebServlet(name="SignInHandler", urlPatterns={"/SignInHandler"})
public class SignInHandler extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        // Create Factory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lolfriendsPersistenceUnit");
        EntityManager em = emf.createEntityManager();
                
        boolean found = false;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        
        
        if(username.equals(' ') || username.isEmpty() || password.equals(' ') || password.isEmpty())
        {
            response.sendRedirect("BadLogin.jsp"); 
        }
        
        
        // Check for username in DB
        List<User> checkUser = em.createQuery("SELECT u FROM User u WHERE u.user_name = :username").setParameter("username", username).getResultList();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        if(!checkUser.isEmpty())
        {
            if(BCrypt.checkpw(password, hashedPassword))
            {
                request.getSession().setAttribute("username", username);
                response.sendRedirect("Welcome.jsp");
            }
            else
            {
                response.sendRedirect("BadLogin.jsp"); 
            }
        }
        else
        {
         response.sendRedirect("BadLogin.jsp"); 
        }
            
        
        /* Need to get data from the database to compare
        String dbPass = select password from db where username = ${username};
        if (password == dbPass)
        {
            found = true;
        }
        */
        
        
//        if (username.equals("test") && password.equals("test"))
//        {
//            found = true;
//        }
//        
//        if (found) 
//        {
//            request.getSession().setAttribute("username", username);
//            response.sendRedirect("Welcome.jsp");
//        } 
//        else 
//        {
//            response.sendRedirect("BadLogin.jsp");
//        }
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