package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

public class AuthorAsUser extends User{
	public AuthorAsUser(int userId, Scanner sc) {
		super(userId);
		loop: while(true) {
			System.out.println("Please select the task you would like to perform");
			System.out.println("1: Get  all books assigned to the author");
			System.out.println("2: Get all articles assigned to the author");
			System.out.println("3: Get current books assigned to the author");
			System.out.println("4: Get current articles assigned to the author");
			System.out.println("5: update book");
			System.out.println("6: update article");
			System.out.println("exit: Exit from Employee");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				allBooksAssigned(sc);
				break;
			case "2":
				allArticlesAssigned(sc);
				break;
				
			case "3":
				currentBooksAssigned(sc);
				break;
			case "4":
				currentArticlesAssigned(sc);
				break;
			case "5":
				updateBook(sc);
				break;
			case "6":
				updateArticle(sc);
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
	
	public void allBooksAssigned(Scanner sc) {
		System.out.println("Select the employee id for whom you want to see all Books assigned : ");
		int empId = sc.nextInt();
		sc.nextLine();
		Employee emp = Employee.getEmployee(empId);
		if (emp.isEmployeePresent(emp.getEmployeeName())){
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			
			try {	
				CallableStatement cstmt = con.prepareCall("{call sp_get_all_books_authored_by_employee(?, ?)}");
				cstmt.setInt(1, empId);
				cstmt.registerOutParameter(2, Types.VARCHAR);
				cstmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			finally {
		        db.closeConnection(con);
			}
		}
		else {
			System.out.println("Employee not present ");
			return ;
		}
	}
	
	public void allArticlesAssigned(Scanner sc) {
		System.out.println("Select the employee id for whom you want to see all Articles assigned : ");
		int empId = sc.nextInt();
		sc.nextLine();
		Employee emp = Employee.getEmployee(empId);
		if (emp.isEmployeePresent(emp.getEmployeeName())){
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			
			try {	
				CallableStatement cstmt = con.prepareCall("{call sp_get_all_articles_authored_by_employee(?, ?)}");
				cstmt.setInt(1, empId);
				cstmt.registerOutParameter(2, Types.VARCHAR);
				cstmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			finally {
		        db.closeConnection(con);
			}
		}
		else {
			System.out.println("Employee not present ");
			return ;
		}
	}
	
	public void currentBooksAssigned(Scanner sc) {
		System.out.println("Select the employee id for whom you want to see current Books assigned : ");
		int empId = sc.nextInt();
		sc.nextLine();
		Employee emp = Employee.getEmployee(empId);
		if (emp.isEmployeePresent(emp.getEmployeeName())){
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			
			try {	
				CallableStatement cstmt = con.prepareCall("{call sp_get_current_books_authored_by_employee(?, ?)}");
				cstmt.setInt(1, empId);
				cstmt.registerOutParameter(2, Types.VARCHAR);
				cstmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			finally {
		        db.closeConnection(con);
			}
		}
		else {
			System.out.println("Employee not present ");
			return ;
		}
	}
	
	public void currentArticlesAssigned(Scanner sc) {
		System.out.println("Select the employee id for whom you want to see current Articles assigned : ");
		int empId = sc.nextInt();
		sc.nextLine();
		Employee emp = Employee.getEmployee(empId);
		if (emp.isEmployeePresent(emp.getEmployeeName())){
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			
			try {	
				CallableStatement cstmt = con.prepareCall("{call sp_get_current_articles_authored_by_employee(?, ?)}");
				cstmt.setInt(1, empId);
				cstmt.registerOutParameter(2, Types.VARCHAR);
				cstmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			finally {
		        db.closeConnection(con);
			}
		}
		else {
			System.out.println("Employee not present ");
			return ;
		}
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
	
	public void updateBook(Scanner sc) {
		System.out.println("Select the ISBN of the book you would like to update : ");
		this.getAllBooks();
		int isbn = sc.nextInt();
		
		Book boo = Book.getBookByIsbn(isbn);
		if(boo == null) {
			System.out.println("Book with give ISBN does not exit.");
			return;
		}
		String title = boo.getBookTitle();
		int edition = boo.getEdition();
		String text = boo.getText();
		String pubDate = boo.getPublicationDate();
		Publication bookPub = boo.getBookPublication();
		
		
		loop: while(true) {
			System.out.println("Please select what you would like to update");
			System.out.println("1: Book Title");
			System.out.println("2: Book Edition");
			System.out.println("3: Book Text");
			System.out.println("4: Book Publication Date");
			System.out.println("5: Book Publication.");
			System.out.println("done: If you are done with update");
			
			
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				System.out.println("Enter the new title for the Book: ");
				title = sc.nextLine();
				break;
			case "2":
				System.out.println("Enter the new edition for the Book: ");
				edition = sc.nextInt();
				break;
			case "3":
				System.out.println("Enter the new text for the Book: ");
				text = sc.nextLine();
				break;
			case "4":
				System.out.println("Enter the new publication date for the Book: ");
				pubDate = sc.nextLine();
				break;
			case "5":
				System.out.println("Enter the new publication ID for the Book: ");
				int pubID = sc.nextInt();
				sc.nextLine();
				bookPub = Publication.getPublicationById(pubID);
				break;
			case "done":
				break loop;
			default:
				System.out.println("Wrong selection");
			
			}
			
		}
		System.out.println("The book :" + title + "\n edition : " + edition +"\n text "+text+ "\n publication date: " + pubDate +  "\n publication: "+ bookPub.getPublicationId()+ "\n would you like to proceed with Update (y/n)");
	}
	
	public void getAllBooks() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT book.isbn AS isbn, book.book_title AS book_title, book.edition AS edition, publication.publication_id AS publication_id, publication.publication_title AS publication_title FROM book INNER JOIN publication ON book.publication_id = publication.publication_id WHERE book.is_active = 1");
	        System.out.println("isbn\tBookTitle\tEdition\tpublicationId\tpublicationTitle");
	        while (result.next()) {
	        	int isbn = result.getInt("isbn");
	        	String bookTitle = result.getString("book_title");
	        	int edition = result.getInt("edition");
	            String publicationTitle = result.getString("publication_title");
	            int publicationId = result.getInt("publication_id");
	            System.out.println(isbn + "\t" + bookTitle + "\t" + edition+"\t"+ publicationId + "\t" + publicationTitle);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
	        db.closeConnection(con);
		}
	}

}
