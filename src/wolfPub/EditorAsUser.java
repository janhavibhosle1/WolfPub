package wolfPub;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EditorAsUser extends User {
	public EditorAsUser(int userId, Scanner sc) {
		super(userId);
		loop: while(true) {
			System.out.println("Please select the task you would like to perform");
			System.out.println("1: Add chapter to book");
			System.out.println("2: Add/remove article to periodic issue");
			System.out.println("3: Get the publication you are responsible for editing");
			System.out.println("exit: Exit from Employee");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addChapter(sc);
				break;
			case "2":
				articleActions(sc);
				break;
			case "3":
				getResponsiblePublications(sc);
				break;
			case "exit":
				break loop;
			default:
				System.out.println("wrong selection");
			}
		}
	}
	
	public void getAllPeriodicIssues() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT publication_id, issue_id, issue_title, periodicity_id, category_id FROM periodic_issue WHERE is_active = 1");
	        System.out.println("PublicationId\tIssueId\tIssueTitle\tPeriodicityID\tCategoryId");
	        while (result.next()) {
	        	int issueId = result.getInt("issue_id");
	            int publicationId = result.getInt("publication_id");
	            String issueTitle = result.getString("issue_title");
	            int periodicityId = result.getInt("periodicity_id");
	            int categoryId = result.getInt("category_id");
	            System.out.println(publicationId + "\t" + issueId + "\t" + issueTitle + "\t" +periodicityId + "\t" + categoryId);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
	        db.closeConnection(con);
		}
	}
	
	public void articleActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Add an article");
			System.out.println("2: Get an article");
			System.out.println("4: Get all the articles");
			System.out.println("5: Update an article");
			System.out.println("6: Remove an article");
			System.out.println("exit: Exit from article");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addArticle(sc);
				break;
			case "2":
				getArticle(sc);
				break;
			case "4":
				getAllArticles();
				break;
			case "5":
				updateArticle(sc);
				break;
			case "6":
				removeArticle(sc);
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void addArticle(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the title of the article you would like to add : ");
		String articleTitle = sc.nextLine();
		System.out.println("Enter the article number you would like to add : ");
		int number = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the periodic issue id you would like to aassociate this article to : ");
		int issue = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the text of the article you would like to add : ");
		String articleText = sc.nextLine();
		System.out.println("Enter the audience you would like to add : ");
		String articleAud = sc.nextLine();
		System.out.println("the article :" + articleTitle + "with number "+number+"and contains the text "+articleText +" will be added, would you like to proceed (y/n)");
		String confirmation = sc.nextLine();
		//int id = Publication.getPublicationIdByTitle(publicationName);
		Periodic_issue per = Periodic_issue.getPeriodicIssueById(issue);
		if(per.getIssueTitle() == null) {
			System.out.println("periodic issue doesnt exists");
			return;
		}
		if(confirmation.equals("y")) {
			Article curArc = new Article (number,articleTitle,issue,articleText,articleAud, this.getUserId());
			System.out.println("Article has been added");
		}
		else if(confirmation.equals("n")) {
			System.out.println("Article has not been added");
		}
		else {
			System.out.println("Wrong confirmation, article has not been added");
		}
//		sc.close();
	}

	public void getArticle(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		getAllPeriodicIssues();
		System.out.println("Enter the id of the periodic issue associated with the article : ");
		int issue = sc.nextInt();
		sc.nextLine();
		
		System.out.println("Enter the article number : ");
		int articleNumber = sc.nextInt();
		sc.nextLine();
		
		Article art = Article.getArticle(issue, articleNumber, this.getUserId());
		if(art.getArticleTitle() != null) {
			System.out.println("Article number for article : " + articleNumber);
			System.out.println("Article title for article : " + art.getArticleTitle());
			System.out.println("Article text for article : " + art.getArticleText());
			System.out.println("Periodic Issue associated with the article article : " + art.getArticlePeriodicIssue().getIssueTitle());
			System.out.println("Audience for this article : " + art.getArticleAudience().getAudienceName());
		}
		else {
			System.out.println("article  not found.");
		}
//		sc.close();
	}

	public void removeArticle(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		getAllPeriodicIssues();
		System.out.println("Enter the id of the periodic issue associated with the article : ");
		int issue = sc.nextInt();
		sc.nextLine();
		Periodic_issue p=Periodic_issue.getPeriodicIssueById(issue);
		
		System.out.println("Enter the article number to be removed : ");
		int articleNumber = sc.nextInt();
		sc.nextLine();

		Article art = Article.getArticle(issue, articleNumber, this.getUserId());
		art.removeArticle(this.getUserId());
		
		System.out.println("Article with article number : " + articleNumber + " has been removed");
//		sc.close();
	}

	public void updateArticle(Scanner sc) {
		System.out.println("Select the periodic issue id of the article you would like to update : ");
		getAllPeriodicIssues();
		int perId = sc.nextInt();
		sc.nextLine();
		if (perId == 0) {
			System.out.println("The periodic issue doesnt exist");
			return;
		}
		
		System.out.println("Select the article number of the article you would like to update : ");
		getAllPeriodicIssues();
		int artNo = sc.nextInt();
		sc.nextLine();
		
		Article art = Article.getArticle(perId, artNo, this.getUserId());
		String title = art.getArticleTitle();
		String text = art.getArticleText();
		Audience aud = art.getArticleAudience();
		System.out.println("would you like to update the title for the article? (y/n)");
		String decision = sc.nextLine();
		if(decision.equals("y")) {
			System.out.println("Enter the new title for the publication");
			title = sc.nextLine();
		}
		else {
			System.out.println("Title of the article will not be updated");
		}
		System.out.println("would you like to update the text for the article? (y/n)");
		decision = sc.nextLine();
		if(decision.equals("y")) {
			System.out.println("Enter the new text for the publication");
			text = sc.nextLine();		}
		else {
			System.out.println("Text of the article will not be updated");
		}
		System.out.println("would you like to update the audience for the publication? (y/n)");
		decision = sc.nextLine();
		if(decision.equals("y")) {
			System.out.println("Enter the new audience  for the article");
			String audie = sc.nextLine();
			aud = Audience.getAudienceByName(audie);
		}
		else {
			System.out.println("Audience of the article will not be updated");
		}
		art.updateArticle(title, this.getUserId(), aud.getAudienceName() , text);
		System.out.println("Article details have been updated ");
		return;
	}

	public void getAllArticles() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT article_no, article_title, periodic_issue_id, text, audience_id FROM article WHERE is_active = 1");
	        System.out.println("ArticleNo\tArticleTitle\tIssueID\ttext\tAudienceID");
	        while (result.next()) {
	            String articleTitle = result.getString("article_title");
	            int articleNo = result.getInt("article_no");
	            int periodicIssueID = result.getInt("periodic_issue_id");
	            int audience_id = result.getInt("audience_id");
	            String text = result.getString("text");
	            System.out.println(articleNo + "\t" +articleTitle + "\t" + periodicIssueID+"\t"+ audience_id+"\t"+ text);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
	        db.closeConnection(con);
		}
	}
	
	void getResponsiblePublications(Scanner sc) {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("call sp_get_current_publications_edited_by_employee(" + this.getUserId() + ",@err);");
	        System.out.println("publicationId\tpublicationTitle");
	        while (result.next()) {
	            int publicationId = result.getInt("publication_id");
	            String publicationName = result.getString("publication_title");
	            System.out.println(publicationId + "\t" + publicationName);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
	}
	
	public void addChapter(Scanner sc) { 
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the chapter you would like to add : ");
		String chapterName = sc.nextLine();
		System.out.println("Enter the chapter number you would like to add : ");
		int number = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the book ISBN you would like to aassociate this chapter to : ");
		int bookisbn = sc.nextInt();
		sc.nextLine();
		
		System.out.println("the chapter :" + chapterName + "with number "+number+" will be added, would you like to proceed (y/n)");
		String confirmation = sc.nextLine();
		//int id = Publication.getPublicationIdByTitle(publicationName);
		Book boo = Book.getBookByIsbn(bookisbn);
		if(boo.getBookTitle() == null) {
			System.out.println("book doesn't exists");
			return;
		}
		if(confirmation.equals("y")) {
			Chapter curChap = new Chapter (number, chapterName,bookisbn,this.getUserId());
			System.out.println("Chapter has been added");
		}
		else if(confirmation.equals("n")) {
			System.out.println("Chapter has not been added");
		}
		else {
			System.out.println("Wrong confirmation, chapter has not been added");
		}
//		sc.close();
	}
}
