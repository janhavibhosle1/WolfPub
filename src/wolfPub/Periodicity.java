package wolfPub;

import java.sql.*;

public class Periodicity {
	private String PeriodicityName;
	private int PeriodicityId;
	
	public Periodicity(String PeriodicityName, int userId) {
		this.setPeriodicityName(PeriodicityName);
		
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_periodicity(?, ? )}");
			cstmt.setString(1, PeriodicityName);
			cstmt.setInt(2, userId);
			cstmt.executeUpdate();
			System.out.println("Periodicity added");
			
			cstmt = con.prepareCall("{call sp_get_periodicity_id(?, ? )}");
			cstmt.setString(1, PeriodicityName);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.executeUpdate();
			this.PeriodicityId = cstmt.getInt(2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
	}

	public Periodicity() {
		this.PeriodicityName = "default";
		this.PeriodicityId = -1;
	}
	
	public static Periodicity getPeriodicityByName(String name) {
		Periodicity p = new Periodicity();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_periodicity_id(?, ? )}");
			cstmt.setString(1, name);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.executeUpdate();
			p.PeriodicityName = name;
			p.PeriodicityId = cstmt.getInt(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return p;
	}
	
	public void removePeriodicity(int userId) {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_remove_periodicity(?, ? )}");
			cstmt.setString(1, this.PeriodicityName);
			cstmt.setInt(2, userId);
			cstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
	}
	
	public String getPeriodicityName() {
		return PeriodicityName;
	}

	public void setPeriodicityName(String Periodicity2Name) {
		this.PeriodicityName = Periodicity2Name;
	}

	public int getPeriodicityId() {
		return PeriodicityId;
	}

	public void setPeriodicityId(int Periodicity2Id) {
		this.PeriodicityId = Periodicity2Id;
	}

}
