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

/**
 *
 * @author Greg
 */
@WebServlet(urlPatterns = {"/addSummoner"})
public class addSummoner extends HttpServlet {

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
        
        // Gather Inforamtion from Welcome Page
        String summonerName = request.getParameter("summonerName");
       
        // Create Factory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lolfriendsPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        
        // create League API
        LeagueAPI api = new LeagueAPI();
            
        // Declare variables used in try/catch
        String reply = null;
        String Error = null;
        Summoner newSummoner = null;
        
        // this could be an input that is being pulled from the user, brings back info for a new summoner
        try
        {
         reply = api.getSummoner(summonerName);
         // Build GSON
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Summoner.class, new SummonerDeserializer());
        Gson gson = gsonBuilder.create();
            
        // Pass in reply string which contains summoner info retreived with api.getSummoner
        newSummoner = gson.fromJson(reply, Summoner.class);
        
        }catch(Exception ex)
        {
            Error = "Summoner Does Not Exist: " + summonerName;
            request.getSession().setAttribute("Error", Error);
            request.getRequestDispatcher("Welcome.jsp").forward(request, response); 
        
        }

        
        // Start transction
        em.getTransaction().begin();
        
        Friend friend = new Friend();
        User user = (User)request.getSession().getAttribute("user");
        friend.setSummoner(newSummoner);
        friend.setUser(user);
        
        // Check to see if summonerName exists within the current users friends
        Summoner checkSummoner = (Summoner) em.createQuery("SELECT s FROM Summoner s INNER JOIN Friend f ON s.id=f.summoner_id WHERE s.summoner_name = :summonerName AND f.user_id = :userId").setParameter("summonerName", summonerName).setParameter("userId", user.getId()).getSingleResult();
        
        
        // 
        if(checkSummoner == null && newSummoner != null)
        {
           em.persist(newSummoner);
           em.persist(friend);
           em.getTransaction().commit();
           request.getRequestDispatcher("PullFriends").forward(request, response);
        }
        else
        {
            Error = "Already Following Summoner " + summonerName;
            request.getSession().setAttribute("Error", Error);
            request.getRequestDispatcher("Welcome.jsp").forward(request, response);        
        }

       
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
