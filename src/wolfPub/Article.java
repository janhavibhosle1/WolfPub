package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
public class Article {
	
	private int articleNo;
	private String articletitle;
	private Periodic_issue articleIssue;
	private String text;
	private Audience articleAud;
	
	public Article(int number, String title, int issueId, String text, String articleAud, int userId )
	{
		this.setArticleNumber(number);
		this.setArticleTitles(title);
		this.setArticletext(text);
		Periodic_issue per = Periodic_issue.getPeriodicIssueById(issueId);
		this.setArticlePeriodicIssue(per);
		Audience aud = Audience.getAudienceByName(articleAud);
		if (aud.getAudienceId() == 0) {
			aud = new Audience(articleAud,userId);
		}
		this.setArticleAudience(aud);
		
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_article_to_issue(?, ?, ?, ?, ?, ?, ? )}");
			cstmt.setInt(1, issueId);
			cstmt.setInt(2, number);
			cstmt.setString(3, title);
			cstmt.setString(4, text);
			cstmt.setString(5, articleAud);
			cstmt.setInt(6, userId);
			cstmt.registerOutParameter(7, Types.VARCHAR);
			cstmt.execute();
			System.out.println("article for issue " + issueId + "added");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		
	}
	
	public Article() {
		this.articleNo = -1;
		this.articletitle = null;
		this.articleIssue = null;
		this.text = null;
		this.articleAud = null;
	}
	
	public static Article getArticle(int issue_id, int article_no, int userId) {
		Article curArc = new Article();
		Periodic_issue iss = Periodic_issue.getPeriodicIssueById(issue_id);
		curArc.setArticlePeriodicIssue(iss);
		curArc.setArticleNumber(article_no);
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_article(?, ?, ?, ?, ?, ? )}");
			cstmt.setInt(1, issue_id);
			cstmt.setInt(2, article_no);
			cstmt.registerOutParameter(3, Types.VARCHAR); //title
			cstmt.registerOutParameter(4, Types.VARCHAR); //price
			cstmt.registerOutParameter(5, Types.VARCHAR); //topic name
			cstmt.registerOutParameter(6, Types.VARCHAR); //error message
			cstmt.executeUpdate();
			curArc.setArticleAudience(Audience.getAudienceByName(cstmt.getString(5)));
			curArc.setArticletext(cstmt.getString(4));
			curArc.setArticleTitles(cstmt.getString(3));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return curArc;
	}
	
	public void removeArticle(int userId) {
		int perId = this.getArticlePeriodicIssue().getIssueID();
		int no = this.getArticleNumer();
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_remove_article(?, ?, ? , ?)}");
			cstmt.setInt(1, perId);
			cstmt.setInt(2, no);
			cstmt.setInt(3, userId);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
	}
	
	public void updateArticle(String title, int userId, String audience, String text ) {
		
		
		int id = this.getArticlePeriodicIssue().getIssueID();
		this.setArticleTitles(title);
		//this.setArticleNumber(articleNumber);
		Audience aud= Audience.getAudienceByName(audience);
		this.setArticleAudience(aud);
		this.setArticletext(text);

		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_update_article(?, ?, ?, ?, ?, ?, ? )}");
			cstmt.setInt(1, id);
			cstmt.setInt(2, this.getArticleNumer());
			cstmt.setString(3, title);
			cstmt.setString(4, text); 
			cstmt.setString(5, audience); 
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
	
	public void setArticleNumber(int number) {
		this.articleNo = number;
	}
	
	public int getArticleNumer() {
		return articleNo;
	}
	
	public String getArticleTitle() {
		return articletitle;
	}
	
	public void setArticleTitles(String title) {
		this.articletitle=title;
	}
	
	public void setArticlePeriodicIssue(Periodic_issue iss) {
		this.articleIssue = iss;
	}
	
	public Periodic_issue getArticlePeriodicIssue() {
		return articleIssue;
	}
	
	public void setArticleAudience(Audience aud) {
		this.articleAud = aud;
	}
	
	public Audience getArticleAudience() {
		return articleAud;
	}
	
	public void setArticletext(String text) {
		this.text = text;
	}
	
	public String getArticleText() {
		return this.text;
	}

}
