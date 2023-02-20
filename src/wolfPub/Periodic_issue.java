package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class Periodic_issue {
	
	private int issue_id;
	private String title;
	private Publication issue_publication;
	private Category issue_category;
	private Periodicity issue_periodicity;
	
	public Periodic_issue(String issue_title, int pubid, String periodicity, String Categoryy, int userID) {
		this.setIssueTitle(issue_title);
		Publication pub = Publication.getPublicationById(pubid);
		this.setIssuePublication(pub);
		Category cat = Category.getCategoryByName(Categoryy);
		this.setIssueCategory(cat);
		Periodicity p = Periodicity.getPeriodicityByName(periodicity);
		this.setIssuePeriodicity(p);
		
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_periodic_issue_to_publication(?, ?, ?, ?, ?, ?, ? )}");
			cstmt.setString(1, issue_title);
			cstmt.setInt(2, pub.getPublicationId());
			cstmt.setString(3, periodicity);
			cstmt.setString(4, Categoryy);
			cstmt.setInt(5, userID);
			cstmt.registerOutParameter(6, Types.INTEGER);
			cstmt.registerOutParameter(7, Types.VARCHAR);
			cstmt.execute();
			//this.issue_id = cstmt.getInt(5);
			System.out.println("periodic issue added"  );
			
			this.issue_id = cstmt.getInt(6);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		
		
		
	}
	public Periodic_issue() {
		this.issue_id = -1;
		this.title = null;
		this.issue_publication = null;
		this.issue_category = null;
		this.issue_periodicity = null;
	}
	
	public static Periodic_issue getPeriodicIssueById(int id) { //to be discussed
		
		Periodic_issue per = new Periodic_issue();
		Category t = new Category();
		Publication pub = new Publication();
		Periodicity p = new Periodicity();
		
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_periodic_issue_by_id(?, ?, ?, ?, ?, ? )}");
			cstmt.setInt(1, id);
			cstmt.registerOutParameter(2, Types.VARCHAR); //issue_title
			cstmt.registerOutParameter(3, Types.VARCHAR); //publication_title
			cstmt.registerOutParameter(4, Types.VARCHAR); //periodicity
			cstmt.registerOutParameter(5, Types.VARCHAR); //category
			cstmt.registerOutParameter(6, Types.VARCHAR); //error
			cstmt.executeUpdate();
			per.setIssueTitle(cstmt.getString(2));
			per.setIssueID(id);
			int issue_id = Publication.getPublicationIdByTitle(cstmt.getString(3));
			pub = Publication.getPublicationById(issue_id);
			per.setIssuePublication(pub);
			
			t = Category.getCategoryByName(cstmt.getString(5));
			per.setIssueCategory(t);
			
			p = Periodicity.getPeriodicityByName(cstmt.getString(4));
			per.setIssuePeriodicity(p);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return per;
	
	
	}
	
	public void removePeriodicIssue(int userId) {
		int perId = this.getIssueID();
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_remove_periodic_issue(?, ?, ? )}");
			cstmt.setInt(1, perId);
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
	
	public void updatePeriodicIssue(String title, int publication_id, int userId, String periodicity, String category ) {
		
		
		int id = this.getIssueID();
		this.setIssueTitle(title);
		Publication pub= Publication.getPublicationById(publication_id);
		this.setIssuePublication(pub);
		Periodicity p = Periodicity.getPeriodicityByName(periodicity);
		this.setIssuePeriodicity(p);
		Category c = Category.getCategoryByName(category);
		this.setIssueCategory(c);

		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_update_periodic_issue(?, ?, ?, ?, ?, ?, ? )}");
			cstmt.setInt(1, id);
			cstmt.setString(2, title);
			cstmt.setInt(3, this.issue_publication.getPublicationId());
			cstmt.setString(4, this.issue_periodicity.getPeriodicityName()); 
			cstmt.setString(5, this.issue_category.getCategoryName()); 
			cstmt.setInt(6, userId);
			cstmt.registerOutParameter(7, Types.VARCHAR);
			cstmt.executeUpdate();
			String err_msg = cstmt.getString(7);
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
	
	public void setIssueTitle(String issue_title) {
		this.title = issue_title;
	}
	
	public String getIssueTitle() {
		return title;
	}
	
	public void setIssuePublication(Publication pub) {
		this.issue_publication = pub;
	}
	
	public Publication getIssuePublication() {
		return issue_publication;
	}
	
	public void setIssueCategory(Category cat) {
		this.issue_category = cat;
	}
	
	public Category getIssueCategory() {
		return issue_category;
	}

	public void setIssuePeriodicity(Periodicity cat) {
		this.issue_periodicity = cat;
	}
	
	public Periodicity getIssuePeriodicity() {
		return issue_periodicity;
	}
	
	public void setIssueID(int id) {
		this.issue_id = id;
	}
	
	public int getIssueID() {
		return issue_id;
	}
	

}
