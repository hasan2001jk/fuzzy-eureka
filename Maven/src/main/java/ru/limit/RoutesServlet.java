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
 * Servlet implementation class RoutesServlet
 */
public class RoutesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RoutesServlet() {
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
			PreparedStatement smnt=connection.prepareStatement("SELECT * FROM Stations");
			ResultSet rs=smnt.executeQuery();
			while(rs.next()) {
				Integer i=rs.getInt("id_route");
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
			    out.println("<head><title>Самара</title></head>");
			    out.println("<body>");
			    out.println("<center><h1>Самара</h1>");
			    out.println("<h2>Расписание электричек (пригородных поездов)</h2>");
			    out.println("<table border='1'><tr><th>Id</th><th>Номер маршрута</th></th><th>Станция</th><th>Время прибытия</th><th>Время стоянки</th><th>Время отправления</th><th>В пути</th></tr>");
			    
			    System.out.println("connection");
				PreparedStatement smt=connection.prepareStatement("SELECT * FROM Stations WHERE id_route=(?)");
				smt.setInt(1, Integer.parseInt(num));
				ResultSet rst=smt.executeQuery();
				
				while(rst.next()) {
					out.print("<tr><td><center>");
			    	  out.println(rst.getInt("id_record"));
			    	  out.print("</center></td>");
			    	  out.print("<td><center>");
			    	  out.println(rst.getInt("id_route"));
			    	  out.print("</center></td>");
			    	  out.print("<td><center>");
			    	  out.println(rst.getString("station_name"));
			    	  out.print("</center></td>");
			    	  out.print("<td><center>");
			    	  out.println(rst.getString("arrival_time"));
			    	  out.print("</center></td>");
			    	  out.print("<td><center>");
			    	  out.println(rst.getString("parking_time"));
			    	  out.print("</center></td>");
			    	  out.print("<td><center>");
			    	  out.println(rst.getString("departure_time"));
			    	  out.print("</center></td>");
			    	  out.print("<td><center>");
			    	  out.println(rst.getString("in_transit"));
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
				out.println("<head><title>Самара</title></head>");
				out.println("<body>");
			    out.println("<center>");
			    
			    out.println("<h1>Такого номера маршрута нет!</h1>");
			    
			    out.println("</center>");
			    out.println("</body>");
			    out.println("</html>");
			}
			
			
			
		}catch(SQLException es) {
			es.printStackTrace();
		}
			
	}
		
		
		
		
	}


