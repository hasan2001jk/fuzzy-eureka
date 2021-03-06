package ru.limit;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RouteServlet
 */
public class RouteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RouteServlet() {
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
		Connection connection=null;
		PrintWriter out=response.getWriter();
		
		boolean is_here=false;
		String num=request.getParameter("num");
//		out.println("<h1>"+num+"</h1>");
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return;
		}
		
		try {
			connection = DriverManager.getConnection(DB_URL, User, Pass);
			PreparedStatement smnt=connection.prepareStatement("SELECT * FROM Route");
			ResultSet rs=smnt.executeQuery();
			while(rs.next()) {
				Integer i=rs.getInt("route_num");
				if(i==Integer.parseInt(num)) {
					is_here=true;
					break;
				}
					
			}
			smnt.close();
			rs.close();
			
			if(is_here==true) {
				System.out.println("Truee!!!");
				out.println("<html>");
			    out.println("<head><title>????????????</title></head>");
			    out.println("<body>");
			    out.println("<center><h1>????????????</h1>");
			    out.println("<h2>???????????????????? ???????????????????? (?????????????????????? ??????????????)</h2>");
			    out.println("<table border='1'><tr><th>Id</th><th>?????????? ????????????????</th></th><th>?????????????? ??????????????????????</th><th>?????????????? ????????????????</th></tr>");
			    
			    System.out.println("connection");
				PreparedStatement smt=connection.prepareStatement("SELECT * FROM Route WHERE route_num=(?)");
				smt.setInt(1, Integer.parseInt(num));
				ResultSet rst=smt.executeQuery();
				
				while(rst.next()) {
					out.print("<tr><td><center>");
			    	  out.println(rst.getInt("id_route"));
			    	  out.print("</center></td>");
			    	  out.print("<td><center>");
			    	  out.println(rst.getInt("route_num"));
			    	  out.print("</center></td>");
			    	  out.print("<td><center>");
			    	  out.println(rst.getString("departure_station"));
			    	  out.print("</center></td>");
			    	  out.print("<td><center>");
			    	  out.println(rst.getString("arrival_station"));
			    	  out.print("</center></td>");
			    	  out.print("</tr>");
				}
				
				out.print("</table>");
			    out.println("</center>");
			    out.println("</body>");
			    out.println("</html>");
			    out.close();
			    smt.close();
			    rst.close();
			}else {
				out.println("<html>");
				out.println("<head><title>????????????</title></head>");
				out.println("<body>");
			    out.println("<center>");
			    
			    out.println("<h1>???????????? ???????????? ???????????????? ??????!</h1>");
			    
			    out.println("</center>");
			    out.println("</body>");
			    out.println("</html>");
			}
			
			
			
		}catch(SQLException es) {
			es.printStackTrace();
		}
			
		}
	

}
