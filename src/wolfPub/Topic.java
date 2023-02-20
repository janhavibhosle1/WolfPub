package wolfPub;

import java.sql.*;

public class Topic {

	private String topicName;
	private int topicId;
	
	public Topic(String topicName, int userId) {
		this.setTopicName(topicName);
		
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_topic(?, ? )}");
			cstmt.setString(1, topicName);
			cstmt.setInt(2, topicId);
			cstmt.executeUpdate();
			System.out.println("topic added");
			
			cstmt = con.prepareCall("{call sp_get_topic_id(?, ? )}");
			cstmt.setString(1, topicName);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.executeUpdate();
			this.topicId = cstmt.getInt(2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		
	}
	
	public Topic() {
		this.topicName = "default";
		this.topicId = -1;
	}
	
	public static Topic getTopicByName(String name) {
		Topic t = new Topic();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_topic_id(?, ? )}");
			cstmt.setString(1, name);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.executeUpdate();
			t.topicName = name;
			t.topicId = cstmt.getInt(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return t;
	}
	
	public void removeTopic(int userId) {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_remove_topic(?, ? )}");
			cstmt.setString(1, this.topicName);
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
	
	public String getTopicName() {
		return topicName;
	}
	
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	public int getTopicId() {
		return topicId;
	}
	
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
}
