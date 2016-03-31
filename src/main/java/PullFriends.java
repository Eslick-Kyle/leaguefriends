/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Greg
 */
@WebServlet(urlPatterns = {"/PullFriends"})
public class PullFriends extends HttpServlet {

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
        // recreate session
        HttpSession session = request.getSession();
        // assign and cast session user object
        User sessionUser = (User)session.getAttribute("user");
        
        // Create Factory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lolfriendsPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        
        // create query to pull ONLY the session user's friends
            Query query = em.createQuery("SELECT u FROM User u WHERE u.user_name = :username").setParameter("username", sessionUser.getUser_name());
            List<User> userFriends = query.getResultList();
            
           // create League API
            LeagueAPI api = new LeagueAPI();
            
           // Build GSON
           GsonBuilder gsonBuilder = new GsonBuilder();
           gsonBuilder.registerTypeAdapter(Summoner.class, new SummonerDeserializer());
           Gson gson = gsonBuilder.create();
        
           // Loop through user and his friends.
            for(User user : userFriends)
            {    
                // for each friend, pull his data from the API
                for(Friend friend : user.getFriends())
                {
                    // this could be an input that is being pulled from the user, brings back info for a new summoner
                    String reply = api.getSummoner(friend.getSummoner().getName());

                    // Pass in reply string which contains summoner info retreived with api.getSummoner
                    Summoner summoner = gson.fromJson(reply, Summoner.class);

                    // returns games of summoner from API
                    reply = api.getGames(Integer.toString(summoner.getId()));

                    // Add games to summoner class
                    summoner.addGames(reply);
                    
                    // Set the friend summoner to the summoner pulled from API
                    friend.setSummoner(summoner);

                }
            
            }
            
            // pass on user object to Welcome.jsp
            request.setAttribute("user", userFriends);
            request.getRequestDispatcher("Welcome.jsp").forward(request, response);
      
            em.close();
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
