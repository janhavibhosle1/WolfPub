package wolfPub;

import java.sql.*;

public class Category{
	private String categoryName;
	private int categoryId;
	
	public Category(String categoryName, int userId) {
		this.setCategoryName(categoryName);
		
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_category(?, ? )}");
			cstmt.setString(1, categoryName);
			cstmt.setInt(2, userId);
			cstmt.executeUpdate();
			System.out.println("category added");
			
			cstmt = con.prepareCall("{call sp_get_category_id(?, ? )}");
			cstmt.setString(1, categoryName);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.executeUpdate();
			this.categoryId = cstmt.getInt(2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
	}

	public Category() {
		this.categoryName= "default";
		this.categoryId = -1;
	}

	public static Category getCategoryById(int id) {
	 Category p = new Category();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_category_id(?, ? )}");
			cstmt.setInt(1, id);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.executeUpdate();
			p.categoryName= cstmt.getString(2);
			p.categoryId = id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return p;
	}
	
	public static Category getCategoryByName(String name) {
		Category p = new Category();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_category_id(?, ? )}");
			cstmt.setString(1, name);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.executeUpdate();
			p.categoryName= name;
			p.categoryId = cstmt.getInt(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return p;
	}
	
	public void removeCategory(int userId) {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_remove_category(?, ? )}");
			cstmt.setString(1, this.categoryName);
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
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName= categoryName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

}
