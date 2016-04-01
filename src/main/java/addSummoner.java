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
        // Get User object from session
        User user = (User)request.getSession().getAttribute("user");
       
        // Create Factory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lolfriendsPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        
        // create League API
        LeagueAPI api = new LeagueAPI();
            
        // Check to see if summonerName exists within the current users friends
        Summoner checkSummoner = null;
        Friend checkFriend = null;
        
        try{
            checkSummoner = (Summoner) em.createQuery("SELECT s FROM Summoner s WHERE s.summoner_name = :summonerName").setParameter("summonerName", summonerName).getSingleResult();
            System.out.println(summonerName + ' ' + checkSummoner.getName());
            checkFriend = (Friend) em.createQuery("SELECT f FROM Friend f WHERE f.user.id = :userId AND f.summoner.id = :summonerId").setParameter("userId", user.getId()).setParameter("summonerId", checkSummoner.getDbId()).getSingleResult();
            System.out.println(user.getId() + ' ' + checkSummoner.getDbId());
        }catch(Exception e)
        {
            System.out.println("Exception thrown in checking for users");
            e.printStackTrace();
        }
        
        // Now that we know the summoner name exists in our DB. see if it is an actual Summoner Name
        // Declare variables used in try/catch
        String reply = null;
        String Error = null;
        Summoner newSummoner = null;
        
        if(checkFriend == null)
        {
            try
            {
                System.out.println("made it to summoner retreval");
                // get summoner by name
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
        }
        
        em.getTransaction().begin();
        
        Friend friend = new Friend();
        friend.setSummoner(newSummoner);
        friend.setUser(user);
             
        // 
        if(checkFriend == null && newSummoner != null)
        {
            System.out.println("Made it to Persist");
            
           em.persist(newSummoner);
           em.persist(friend);
        // Start transction

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
