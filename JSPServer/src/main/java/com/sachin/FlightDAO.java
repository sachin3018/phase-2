package com.sachin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlightDAO {
	public Connection con=null;
	public Statement st=null;

	public FlightDAO() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost/fly","root","12345678");
		System.out.println("connection established with database");
		st=con.createStatement();
	}
	
	public List<String[]> getAvailableFlights(String f, String t, String d,int noOfPassenger) {
		
		List<String[]> flights = new ArrayList<>();
		try{
			Statement statement = con.createStatement();
			String query = String.format
				("select airlines.company,flight.price from flight inner join airlines on flight.fid = airlines.id inner join places on flight.placeid = places.id where places.scode = '%s' and places.dcode = '%s';",
				f,t);
			ResultSet rs=statement.executeQuery(query);
			query = String.format("insert into numberOfP(count) values('%s');",Integer.valueOf(noOfPassenger));
			st.execute(query);
			if(rs.next()) {
				String[] flight=new String[2];
				flight[0]=rs.getString("company");
				flight[1]=rs.getString("price");
				flights.add(flight);
				
			}
			return flights;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public HashMap<String, String> checkUser(String email, String password) {
		

		HashMap<String,String> user=null;
		String query="select * from user where email='"+email+"' and password='"+password+"'";
		String query2 = "select * from numberofp;";
		try {
			Statement statement = con.createStatement();
			ResultSet rs=st.executeQuery(query);
			ResultSet rsp = statement.executeQuery(query2);
			if(rs.next()) {
				user=new HashMap<>();
				user.put("name", rs.getString("name"));
				user.put("email",rs.getString("email"));
				user.put("phno",rs.getString("phno"));
				user.put("adno",rs.getString("adno"));
			}
			if(rsp.next()) {
				user.put("passenger", rsp.getString("count"));
			}
			Statement statement2 = con.createStatement();
			query2 = "delete from numberofp;";
			statement2.execute(query2);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
}
	
	public boolean insertUser(HashMap<String, String> user) {

		String query="INSERT INTO user (email, password, name, phno, adno) values('"+user.get("email")+"','"+user.get("password")+"','"+user.get("name")+"','"+user.get("phno")+"','"+user.get("adno")+"')";                   
		
		try {
			st.execute(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean checkAdmin(String email, String password) {
		
		try {
			ResultSet rs=st.executeQuery("select * from admin where email='"+email+"' and password='"+password+"'");
			if(rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean changeAdminPassword(String email, String password) {

		try {
			ResultSet rs=st.executeQuery("select * from admin where email='"+email+"'");
			if(!rs.next()) {
				return false;
			}
			st.execute("update admin set password='"+password+"' where email='"+email+"'");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}