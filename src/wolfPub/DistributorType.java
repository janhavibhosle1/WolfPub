package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class DistributorType {
	private String typeName;
	private int typeId;
	public DistributorType(String typename, int userID) {
		this.typeName = typename;
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_distributor_type(?, ? )}");
			
			cstmt.setString(1,this.typeName);
			cstmt.setInt(2, userID);
			cstmt.executeUpdate();
			System.out.println("Type added");
			
			cstmt = con.prepareCall("{call sp_get_distributor_type_id(?,?)}");
			cstmt.setString(1,this.typeName);
			cstmt.registerOutParameter(2, Types.INTEGER);
			System.out.println(cstmt);
			
			 cstmt.execute();
			
			
			this.typeId = cstmt.getInt(2);
		}
		catch (Exception e){
			System.out.println(e);
		}
		finally {
	        db.closeConnection(con);
		}	
	}
	
	public DistributorType() {
		
	}
	
	public static DistributorType getTypeByName(String typeName) {
		DistributorType typeObj = new DistributorType();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_distributor_type(?, ? )}");
		
			cstmt = con.prepareCall("{call sp_get_distributor_type_id(?,?)}");
			cstmt.setString(1,typeName);
			cstmt.registerOutParameter(2, Types.INTEGER);
		
			cstmt.execute();
			typeObj.typeId = cstmt.getInt(2);
			typeObj.typeName = typeName;
		}
		catch (Exception e){
			System.out.println(e);
		}
		finally {
	        db.closeConnection(con);
		}	
		return typeObj;
	}
	public static void removeType(String typeName, int userID) {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			System.out.println("Type removed");
			CallableStatement cstmt = con.prepareCall("{call sp_remove_distributor_type(?,?)}");
			cstmt.setString(1,typeName);
			cstmt.setInt(2, userID);
			cstmt.executeUpdate();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}	
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	
	
}
