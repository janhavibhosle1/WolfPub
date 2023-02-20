package wolfPub;

import java.sql.*;

public class Position {
	private String positionName;
	private int positionId;
	
	public Position(String positionName, int userId) {
		this.setPositionName(positionName);
		
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_position(?, ? )}");
			cstmt.setString(1, positionName);
			cstmt.setInt(2, userId);
			cstmt.executeUpdate();
//			cstmt.execute();
			System.out.println("position added");
			
			cstmt = con.prepareCall("{call sp_get_position_id(?, ? )}");
			cstmt.setString(1, positionName);
			cstmt.registerOutParameter("o_position_id", Types.INTEGER);
			cstmt.executeUpdate();
//			cstmt.execute();
			this.positionId = cstmt.getInt("o_position_id");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
	}

	public Position() {
		this.positionName = "default";
		this.positionId = -1;
	}

	public static Position getPositionById(int id) {
		Position p = new Position();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_position_by_id(?, ? )}");
			cstmt.setInt(1, id);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.executeUpdate();
			p.positionName = cstmt.getString(2);
			p.positionId = id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return p;
	}
	
	public static Position getPositionByName(String name) {
		Position p = new Position();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_position_id(?, ? )}");
			cstmt.setString(1, name);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.executeUpdate();
			p.positionName = name;
			p.positionId = cstmt.getInt(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return p;
	}
	
	public void removePosition(int userId) {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_remove_position(?, ? )}");
			cstmt.setString(1, this.positionName);
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
	
	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
}
