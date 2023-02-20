package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;


public class Publication {
	
	//instance variables
	private int publicationId;
	private String publicationTitle;
	private int price;
	private Topic pubTopic;
	//private String type;
	
	public Publication(String Topicname, String PublicationTitle , int price, int userId) {
		this.setTitle(publicationTitle);
		//this.setPublicationId(publicationId);
		//this.setPubTopic(pubTopic);
		this.setPrice(price);
		Topic t = null;
		t = Topic.getTopicByName(Topicname);
		if (t.getTopicId() == 0) {
			t= new Topic(Topicname,userId);
		}
		this.setPubTopic(t);
		
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_publication(?, ?, ?, ?, ?, ? )}");
			cstmt.setString(1, PublicationTitle);
			cstmt.setInt(2, price);
			cstmt.setString(3, Topicname);
			cstmt.setInt(4, userId);
			cstmt.registerOutParameter(5, Types.INTEGER);
			cstmt.registerOutParameter(6, Types.VARCHAR);
//			cstmt.executeUpdate();
			cstmt.execute();
			System.out.println("publication added");
			
			/*cstmt = con.prepareCall("{call sp_get_publication_id_by_title(?, ?, ? )}");
			cstmt.setString(1, publicationTitle);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.registerOutParameter(3, Types.VARCHAR);
//			cstmt.executeUpdate();
			cstmt.execute();*/
			this.publicationId = cstmt.getInt(5);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		
	}
	
	public Publication() {
		this.publicationId = -1;
		this.publicationTitle = null;
		this.price = -1;
		this.pubTopic = null;
		
	}
	
	public void removePublication(int userId) {
		int pubId = this.getPublicationId();
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_remove_publication(?, ?, ? )}");
			cstmt.setInt(1, pubId);
			cstmt.setInt(2, userId);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
	}
	
	public static Publication getPublicationById(int id) { //to be discussed
		
		Publication pub = new Publication();
		Topic t = new Topic();
		
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_publication_by_id(?, ?, ?, ?, ? )}");
			cstmt.setInt(1, id);
			cstmt.registerOutParameter(2, Types.VARCHAR); //title
			cstmt.registerOutParameter(3, Types.INTEGER); //price
			cstmt.registerOutParameter(4, Types.VARCHAR); //topic name
			cstmt.registerOutParameter(5, Types.VARCHAR); //error message
			cstmt.executeUpdate();
			pub.setTitle(cstmt.getString(2));
			pub.setPrice(cstmt.getInt(3));
			t = Topic.getTopicByName(cstmt.getString(4));
			pub.setPubTopic(t);
			pub.setPublicationId(id);
//			pub.setTopicId(cstmt.getInt(4));
//			pub.publicationTopic()					to be discussed
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return pub;
	
	
	}
	
	public static int getPublicationIdByTitle(String pubTitle) {
		int pubId = -1;
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_publication_id_by_title(?, ?, ? )}");
			cstmt.setString(1, pubTitle);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.executeUpdate();
			pubId = cstmt.getInt(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		
		return pubId;
	}
	
	public void updatePublication(String title, int price, int userId, String topic ) {
		
		//Publication p = null;
		int pubId = this.getPublicationId();
		this.setTitle(title);
		this.setPrice(price);
		Topic p = Topic.getTopicByName(topic);
		this.setPubTopic(p);

		
//		int PublicationID = this.getPublicationId();
//		this.setTitle(title);
//		Topic PubTopic = Topic.getTopicByName(topic);
//		this.setTopicName(topic);
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_update_publication(?, ?, ?, ?, ?, ? )}");
			cstmt.setInt(1, pubId);
			cstmt.setString(2, title);
			cstmt.setInt(3, price);
			cstmt.setString(4, topic); //topic name
	
			cstmt.setInt(5, userId);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.executeUpdate();
			String err_msg = cstmt.getString(6);
			if(err_msg != null) {
				System.out.println(err_msg);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
	}
	
	public String getTitle(String publicationTitle) {
		return publicationTitle;
	}
	
	public void setTitle(String publicationTitle) {
		this.publicationTitle = publicationTitle;
	}
	
//	public Topic setTopicId(Topic topicId) {
//		this.publicationId = publicationId;
//	}
//	
	public void setPublicationId(int publicationId) {
		this.publicationId = publicationId;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}

	public String getPublicationTopic() { 
		return this.pubTopic.getTopicName();
	}
	public int getPublicationId() {
		return publicationId;
	}
	public String getPublicationTitle() {
		return publicationTitle;
	}
	public void setPubTopic(Topic pubTopic) {
		this.pubTopic = pubTopic;
	}
}