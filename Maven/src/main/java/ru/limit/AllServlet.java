package ru.limit;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AllServlet
 */
public class AllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AllServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String DB_URL = "jdbc:postgresql://localhost:5432/practice";
		String User = "user";
		String Pass = "user";
		System.out.println("Testing connection to Postgresql JDBC");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		Connection connection = null;
		PrintWriter out=response.getWriter();
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return;
		}
		
		try {
			connection = DriverManager.getConnection(DB_URL, User, Pass);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		try {
			out.println("<html>");
		    out.println("<head><title>Самара</title></head>");
		    out.println("<body>");
		    out.println("<center><h1>Самара</h1>");
		    out.println("<h2>Расписание электричек (пригородных поездов)</h2>");
		    out.println("<table border='1'><tr><th>Id</th><th>Номер маршрута</th></th><th>Станция отправления</th><th>Станция прибытия</th></tr>");
		    
		    System.out.println("connection");
		    Statement stmt=connection.createStatement();
		     ResultSet rs = stmt.executeQuery("SELECT * FROM Route");
		    
		      while (rs.next()) {
		    	  out.print("<tr><td><center>");
		    	  out.println(rs.getInt("id_route"));
		    	  out.print("</center></td>");
		    	  out.print("<td><center>");
		    	  out.println(rs.getInt("route_num"));
		    	  out.print("</center></td>");
		    	  out.print("<td><center>");
		    	  out.println(rs.getString("departure_station"));
		    	  out.print("</center></td>");
		    	  out.print("<td><center>");
		    	  out.println(rs.getString("arrival_station"));
		    	  out.print("</center></td>");
		    	  out.print("</tr>");
		      }
		    
		    out.print("</table>");
		    out.println("</center>");
		    out.println("</body>");
		    out.println("</html>");
		    out.close();
		    stmt.close();
		    rs.close();
		}catch(SQLException es) {
			es.printStackTrace();
		}
		
	}

}
