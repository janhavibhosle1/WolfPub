package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class Audience {
	private String audienceName;
	private int audienceId;
	
	public Audience(String audienceName, int userId) {
		this.setAudienceName(audienceName);
		
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_audience(?, ? )}");
			cstmt.setString(1, audienceName);
			cstmt.setInt(2, userId);
//			cstmt.executeUpdate();
			cstmt.execute();
			System.out.println("audience added");
			
			cstmt = con.prepareCall("{call sp_get_audience_id(?, ? )}");
			cstmt.setString(1, audienceName);
			cstmt.registerOutParameter(2, Types.INTEGER);
//			cstmt.executeUpdate();
			cstmt.execute();
			this.audienceId = cstmt.getInt(2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
	}

	public Audience() {
		this.audienceName = "default";
		this.audienceId = -1;
	}

//	public static Audience getAudienceById(int id) {
//		Audience p = new Audience();
//		Database db = new Database();
//		Connection con = db.getConnection(false);
//		if(con == null) {
//			System.out.println("Failed to establish the connection");
//			System.exit(0);
//		}
//		try {
//			CallableStatement cstmt = con.prepareCall("{call sp_get_audience_by_id(?, ? )}");
//			cstmt.setInt(1, id);
//			cstmt.registerOutParameter(2, Types.VARCHAR);
//			cstmt.executeUpdate();
//			p.positionName = cstmt.getString(2);
//			p.positionId = id;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		finally {
//	        db.closeConnection(con);
//		}
//		return p;
//	}
	
	public static Audience getAudienceByName(String name) {
		Audience a = new Audience();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_audience_id(?, ? )}");
			cstmt.setString(1, name);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.executeUpdate();
			a.audienceName = name;
			a.audienceId = cstmt.getInt(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return a;
	}
	
	public void removeAudience(int userId) {
		Database db = new Database();
		String aud = this.getAudienceName();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_remove_audience(?, ? )}");
			cstmt.setString(1, aud);
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
	
	public String getAudienceName() {
		return audienceName;
	}

	public void setAudienceName(String audienceName) {
		this.audienceName = audienceName;
	}

	public int getAudienceId() {
		return audienceId;
	}

	public void setAudienceId(int audienceId) {
		this.audienceId = audienceId;
	}
}
