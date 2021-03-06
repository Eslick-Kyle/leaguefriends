/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Greg
 */
@WebServlet(urlPatterns = {"/SignUpHandler"})
public class SignUpHandler extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Gather Inforamtion from SignUp Page
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String summonerName = request.getParameter("summonerName");
       
        // Create Factory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lolfriendsPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        
        // create League API
        LeagueAPI api = new LeagueAPI();
            
        // this could be an input that is being pulled from the user, brings back info for a new summoner
        String reply = api.getSummoner(summonerName);
            
        // Build GSON
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Summoner.class, new SummonerDeserializer());
        Gson gson = gsonBuilder.create();
            
        // Pass in reply string which contains summoner info retreived with api.getSummoner
        Summoner summoner = gson.fromJson(reply, Summoner.class);
        
        // Start transction
        em.getTransaction().begin();
        
        // Create New User
        User newUser = new User();
        
        // Check for username in DB
        List<User> checkUser = em.createQuery("SELECT u FROM User u WHERE u.user_name = :username").setParameter("username", username).getResultList();
        
        // Set Values for new entry into DB
        if(checkUser.isEmpty())
        {
            newUser.setUser_name(username);
        }
        else
        {
            request.setAttribute("Error", "Username or Password Invalid or in Use");
            request.getRequestDispatcher("SignUp.jsp").forward(request, response);       
        }
        
        
        // Hash Password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        
        // Store Hashed Password
        newUser.setPassword(hashedPassword);
        
        // set Riot_id by getting summonerID
        newUser.setRiot_id(Integer.toString(summoner.getId()));
        
        
        // Commit to DB
        em.persist(newUser);
        em.flush();
        em.getTransaction().commit();
        
        // Close transaction
        em.close();
        
        System.out.println("User Successfully Added");
        
        request.getSession().setAttribute("user", newUser);
        request.getRequestDispatcher("Welcome.jsp").forward(request, response);
        
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
