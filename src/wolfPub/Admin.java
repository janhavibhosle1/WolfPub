package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Admin extends User {
	public Admin(int userId, Scanner sc) {
		super(userId);
		loop: while(true) {
			System.out.println("Please select an entity you would like to deal with");
			System.out.println("1: Position");
			System.out.println("2: Employee");
			System.out.println("3: Audience");
			System.out.println("4: Category");
			System.out.println("5: Publication");
			System.out.println("6: City");
			System.out.println("7: Distributor Type");
			System.out.println("8: Distributor");
			System.out.println("9: Reports");
			System.out.println("10: Topics");
			System.out.println("11: Periodicity");
			System.out.println("12: Periodic Issue");
			System.out.println("13: Article");
			System.out.println("14: Work assignment");
			System.out.println("15: pay employees");
			System.out.println("16: Books");
			System.out.println("17: Chapter");
			System.out.println("18: Order");
			System.out.println("19: Payment");
			System.out.println("exit: Exit from Employee");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				positionActions(sc);
				break;
			case "2":
				employeeActions(sc);
				break;
			case "3":
				audienceActions(sc);
				break;
			case "4":
				categoryActions(sc);
				break;
			case "5":
				publicationActions(sc);
				break;
			case "6":
				cityActions(sc);
				break;
			case "7":
				distributorTypeActions(sc);
				break;
			case "8":
				distributorActions(sc);
				break;
			case "9":
				reportActions(sc);
				break;
			case "10":
				topicActions(sc);
				break;
			case "11":
				periodicityActions(sc);
				break;
			case "12":
				periodicIssueActions(sc);
				break;
			case "13":
				articleActions(sc);
				break;
			case "14":
				workAssignmentActions(sc);
				break;
			case "15":
				payEmployeesActions(sc);
				break;
			case "16":
				bookActions(sc);
				break;
			case "17":
				chapterActions(sc);
				break;
			case "18":
				orderActions(sc);
				break;
			case "19":
				paymentActions(sc);
				break;
			case "exit":
				break loop;
			default:
				System.out.println("wrong selection");
			}
		}
	}
	
	public void positionActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Add a position");
			System.out.println("2: Get the name of position by id");
			System.out.println("3: Get the id of the position by name");
			System.out.println("4: Remove a position");
			System.out.println("5: List all positions");
			System.out.println("exit: Exit from position");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addPosition(sc);
				break;
			case "2":
				getPositionNameById(sc);
				break;
			case "3":
				getPositionIdByName(sc);
				break;
			case "4":
				removePosition(sc);
				break;
			case "5":
				getAllPositions();
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void chapterActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Add a chapter");
			System.out.println("2: Get an chapter");
			System.out.println("4: Get all the chapters");
			System.out.println("5: Update a chapter");
			System.out.println("6: Remove a chapter");
			System.out.println("exit: Exit from chapter");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addChapter(sc);
				break;
			case "2":
				getChapter(sc);
				break;
			case "4":
				getAllChapters();
				break;
			case "5":
				updateChapter(sc);
				break;
			case "6":
				removeChapter(sc);
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void getAllChapters() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT chapter_number, chapter_name, isbn FROM chapter WHERE is_active = 1"); 
	        System.out.println("ChapterNo\tChapterName\tISBN"); 
	        while (result.next()) {
	            String chapterName = result.getString("chapter_name");
	            int chapterNo = result.getInt("chapter_number");
	            int isbn = result.getInt("isbn");
	            System.out.println(chapterNo + "\t" +chapterName + "\t" +isbn);
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
	
	public void getChapter(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		getAllBooks();
		System.out.println("Enter the ISBN of the book associated with the chapter : ");
		int bookisbn = sc.nextInt();
		sc.nextLine();
		
		System.out.println("Enter the chapter number : ");
		int chapterNumber = sc.nextInt();
		sc.nextLine();
		
		Chapter chap = Chapter.getChapter(chapterNumber, bookisbn);
		if(chap.getChapterName() != null) {
			System.out.println("Chapter number for chapter : " + chapterNumber);
			System.out.println("Chapter name for chapter : " + chap.getChapterName());
			System.out.println("Book associated with the chapter : " + chap.getChapterBook().getBookTitle());
			
		}
		else {
			System.out.println("chapter not found.");
		}
//		sc.close();
	}
	
	public void removeChapter(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		getAllBooks();
		System.out.println("Enter the ISBN of the book associated with the chapter : ");
		int isbn = sc.nextInt();
		sc.nextLine();
		Book b=Book.getBookByIsbn(isbn);
		
		System.out.println("Enter the chapter number to be removed : ");
		int chapterNumber = sc.nextInt();
		sc.nextLine();

		Chapter cha = Chapter.getChapter(chapterNumber, isbn);
		cha.removeChapter(this.getUserId());
		
		System.out.println("Chapter with article number : " + chapterNumber + " has been removed");
//		sc.close();
	}
	
	public void updateChapter(Scanner sc) {
		System.out.println("Select the book ISBN of the chapter you would like to update : ");
		getAllBooks();
		int bookisbn = sc.nextInt();
		sc.nextLine();
		if (bookisbn == 0) {
			System.out.println("The book doesnt exist");
			return;
		}
		
		System.out.println("Select the chapter number of the book you would like to update : ");
		getAllChapters();
		int chapNumber = sc.nextInt();
		sc.nextLine();
		
		Chapter ch = Chapter.getChapter(chapNumber, bookisbn);
		String name = ch.getChapterName();
		
		System.out.println("would you like to update the name for the chapter? (y/n)");
		String decision = sc.nextLine();
		if(decision.equals("y")) {
			System.out.println("Enter the new name for the chapter");
			name = sc.nextLine();
		}
		else {
			System.out.println("Title of the article will not be updated");
		}
		
		ch.updateChapter(name, this.getUserId());
		System.out.println("Chapter details have been updated ");
		return;
	}

	public void employeeActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Add an invited employee");
			System.out.println("2: Add a staff employee");
			System.out.println("3: Get an employee by id");
			System.out.println("4: Get all the employees");
			System.out.println("5: Update an employee");
			System.out.println("6: Remove an employee");
			System.out.println("exit: Exit from employee actions");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addInvitedEmployee(sc);
				break;
			case "2":
				addStaffEmployee(sc);
				break;
			case "3":
				getEmployeeById(sc);
				break;
			case "4":
				getAllEmployees();
				break;
			case "5":
				updateEmployee(sc);
				break;
			case "6":
				removeEmployee(sc);
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void audienceActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Add an audience");
			//System.out.println("2: Get the name of position by id");
			System.out.println("2: Get the id of the position by name");
			System.out.println("3: Remove an audience");
			System.out.println("4: List all audiences");
			System.out.println("exit: Exit from audience");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addAudience(sc);
				break;
			case "2":
				getAudienceIdByName(sc);
				break;
			case "3":
				removeAudience(sc);
				break;
			case "4":
				getAllAudiences();
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void categoryActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Add an category");
			System.out.println("2: Get the name of category by id");
			System.out.println("3: Get the id of the category by name");
			System.out.println("4: Remove an category");
			System.out.println("5: List all categories");
			System.out.println("exit: Exit from category");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addCategory(sc);
				break;
			case "3":
				getCategoryIdByName(sc);
				break;
			case "2":
				getCategoryNameById(sc);
				break;
			case "4":
				removeCategory(sc);
				break;
			case "5":
				getAllCategories();
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void publicationActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Add a publication");
			System.out.println("2: Get the publication details from ID");
			System.out.println("3: Get the id of the publication by title");
			System.out.println("4: Remove a publication");
			System.out.println("5: Update a publication");
			System.out.println("6: List all publications");
			System.out.println("exit: Exit from publication actions");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addPublication(sc);
				break;
			case "2":
				getPublicationById(sc);
				break;
			case "3":
				getPublicationIdByTitle(sc);
				break;
			case "4":
				removePublication(sc);
				break;
			case "5":
				updatePublication(sc);
				break;
			case "6":
				getAllPublications();
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void reportActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the report you would like to get");
			System.out.println("1: Number and total price of copies of each publication bought per distributor per month");
			System.out.println("2: Monthly Revenue");
			System.out.println("3: Monthly Expenses");
			System.out.println("4: Total current number of distributors");
			System.out.println("5: Total Revenue since inception per city");
			System.out.println("6: Total Revenue since inception per distributor");
			System.out.println("7: Total Revenue since inception per location");
			System.out.println("8: Total payment by work type");
			System.out.println("9: Total payment by time period");
			System.out.println("10: Total revenue since inception");
			System.out.println("exit: Exit from reports");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				distributorReport(sc);
				break;
			case "2":
				monthlyRevenue(sc);
				break;
			case "3":
				monthlyExpenses(sc);
				break;
			case "4":
				totalDistributors(sc);
				break;
			case "5":
				revenuePerCity(sc);
				break;
			case "6":
				revenuePerDistributor(sc);
				break;
			case "7":
				revenuePerLocation(sc);
				break;
			case "8":
				paymentPerWorkType(sc);
				break;
			case "9":
				paymentPerTimePeriod(sc);
				break;
			case "10":
				revenueSinceInception(sc);
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void topicActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform on topics");
			System.out.println("1: Add a topic");
			System.out.println("2: Get the id of topic by name");
			
			System.out.println("3: Remove a topic");
			System.out.println("4: List all topics");
			System.out.println("exit: Exit from topic");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addTopic(sc);
				break;
			case "2":
				getTopicIdByName(sc);
				break;
			case "3":
				removeTopic(sc);
				break;
			case "4":
				getAllTopics();
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void periodicityActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform on periodicity");
			System.out.println("1: Add a periodicity");
			//System.out.println("2: Get the name of periodicity by id");
			System.out.println("2: Get the id of the periodicity by name");
			System.out.println("3: Remove a periodicity");
			System.out.println("4: List all periodicities");
			System.out.println("exit: Exit from position");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addPeriodicity(sc);
				break;
			case "2":
				getPeriodicityIdByName(sc);
				break;
			case "3":
				removePeriodicity(sc);
				break;
			case "4":
				getAllPeriodicities();
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void periodicIssueActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Add a periodic issue");
			System.out.println("2: Get the periodic issue details from ID");
			System.out.println("3: Remove a periodic issue");
			System.out.println("4: Update a periodic issue");
			System.out.println("5: List all periodic issue");
			System.out.println("exit: Exit from category");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addPeriodicIssue(sc);
				break;
			case "2":
				getPeriodicIssueById(sc);
				break;
			case "3":
				removePeriodicIssue(sc);
				break;
			case "4":
				updatePeriodicIssue(sc);
				break;
			case "5":
				getAllPeriodicIssues();
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void articleActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			//System.out.println("1: Add an article");
			//System.out.println("2: Get an article");
			System.out.println("4: Get all the articles");
			//System.out.println("5: Update an article");
			//System.out.println("6: Remove an article");
			System.out.println("exit: Exit from article");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				//addArticle(sc);
				break;
			case "2":
				//getArticle(sc);
				break;
			case "4":
				getAllArticles();
				break;
			case "5":
				//updateArticle(sc);
				break;
			case "6":
				//removeArticle(sc);
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void workAssignmentActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Assign editor to publication");
			System.out.println("2: Remove editor from publication");
			System.out.println("3: Assign author to book");
			System.out.println("4: Remove author from book");
			System.out.println("5: Assign author to article");
			System.out.println("6: Remove author from article");
			System.out.println("exit: Exit from position");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				assignEditorToPubliation(sc);
				break;
			case "2":
				removeEditorFromPublication(sc);
				break;
			case "3":
				assignAuthorToBook(sc);
				break;
			case "4":
				removeAuthorFromBook(sc);
				break;
			case "5":
				assignAuthorToArticle(sc);
				break;
			case "6":
				removeAuthorFromArticle(sc);
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void payEmployeesActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Pay all the salarized employees");
			System.out.println("2: Pay an employee");
			System.out.println("exit: Exit from position");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				payAllSalary(sc);
				break;
			case "2":
				payEmployee(sc);
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void bookActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Add a book");
			System.out.println("2: Get the book from isbn");
			System.out.println("3: Update book details");
			System.out.println("4: Remove a book");
			System.out.println("5: List all books");
			System.out.println("exit: Exit from books");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addBook(sc);
				break;
			case "2":
				getBookByIsbn(sc);
				break;
			case "3":
				updateBook(sc);
				break;
			case "4":
				removeBook(sc);
				break;
			case "5":
				getAllBooks();
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void addPublication(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the title of the publication you would like to add : ");
		String publicationName = sc.nextLine();
		System.out.println("Enter the price of the publication you would like to add : ");
		int price = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the topic of the publication you would like to add : ");
		String publicationTopic = sc.nextLine();
		System.out.println("the publication :" + publicationName + "that costs "+price+"and contains the topic "+publicationTopic +" will be added, would you like to proceed (y/n)");
		String confirmation = sc.nextLine();
		int id = Publication.getPublicationIdByTitle(publicationName);
		Publication existingPub = Publication.getPublicationById(id);
		if(id > 0) {
			System.out.println("publication already exists");
			return;
		}
		if(confirmation.equals("y")) {
			Publication curPub = new Publication (publicationTopic,publicationName,price, this.getUserId());
			System.out.println("Publication has been added with id : " + curPub.getPublicationId());
		}
		else if(confirmation.equals("n")) {
			System.out.println("Audience has not been added");
		}
		else {
			System.out.println("Wrong confirmation, audience has not been added");
		}
//		sc.close();
	}
	
	public void getPublicationById(Scanner sc) {
		System.out.println("Enter the id of the publication you would like to access : ");
		int id = sc.nextInt();
		sc.nextLine();
		Publication curPub = Publication.getPublicationById(id);
		if (curPub.getPublicationTitle() == "NULL") {
			System.out.println("publication doesnt exists");
			return;
		}
		else {
			System.out.println("The publication details are given by");
			System.out.println(curPub.getPublicationTitle());
			System.out.println(curPub.getPrice());
			System.out.println(curPub.getPublicationTopic());
			//System.out.println("The publication details are given by");
			return;
		}
	}
	
	public void getPublicationIdByTitle(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the title of the publication you would like to get : ");
		String audience = sc.nextLine();
		int curAud = Publication.getPublicationIdByTitle(audience);
		if(curAud > 0) {
			System.out.println("Publication id for publication : " + audience + " is : " + curAud);
		}
		else {
			System.out.println("publication with publication title name : " + audience + " not found.");
		}
//		sc.close();
	}
	
	public void removePublication(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the publication you would like to remove : ");
		String publication = sc.nextLine();
		int id = Publication.getPublicationIdByTitle(publication);
		
		Publication pubToRemove = Publication.getPublicationById(id);
		//int audienceId = audToRemove.getAudienceId();
		pubToRemove.removePublication(this.getUserId());
		
		System.out.println("Publication with publication id : " + id + " has been removed");
//		sc.close();
	}
	
	public void updatePublication(Scanner sc) {
		System.out.println("Select the id of the publication you would like to update : ");
		getAllPublications();
		int pubId = sc.nextInt();
		sc.nextLine();
		if (pubId == 0) {
			System.out.println("The publication doesnt exist");
			return;
		}
		Publication pub = Publication.getPublicationById(pubId);
		String title = pub.getPublicationTitle();
		int price = pub.getPrice();
		//Topic t = pub.getPublicationTopic();
		String topic = pub.getPublicationTopic();
		System.out.println("would you like to update the title for the publication? (y/n)");
		String decision = sc.nextLine();
		if(decision.equals("y")) {
			System.out.println("Enter the new title for the publication");
			title = sc.nextLine();
		}
		else {
			System.out.println("Title of the publication will not be updated");
		}
		System.out.println("would you like to update the price for the publication? (y/n)");
		decision = sc.nextLine();
		if(decision.equals("y")) {
			System.out.println("Enter the new price for the publication");
			price = sc.nextInt();
			sc.nextLine();		}
		else {
			System.out.println("Price of the publication will not be updated");
		}
		System.out.println("would you like to update the topic for the publication? (y/n)");
		decision = sc.nextLine();
		if(decision.equals("y")) {
			System.out.println("Enter the new topic for the publication");
			topic = sc.nextLine();
		}
		else {
			System.out.println("Topic of the publication will not be updated");
		}
		pub.updatePublication(title, price, this.getUserId(), topic);
		System.out.println("Publication details have been updated to" + title+ " " + price + "" + topic +".");
		return;
	}
	
	public void getAllPublications() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT publication_id, publication_title FROM publication WHERE is_active = 1");
	        System.out.println("PublicationId\tPublicationName");
	        while (result.next()) {
	            String publicationTitle = result.getString("publication_title");
	            int publicationId = result.getInt("publication_id");
	            System.out.println(publicationId + "\t" + publicationTitle);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
	        db.closeConnection(con);
		}
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
	
	
	public void addAudience(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the audience you would like to add : ");
		String audienceName = sc.nextLine();
		System.out.println("the audience :" + audienceName + " will be added, would you like to proceed (y/n)");
		String confirmation = sc.nextLine();
		Audience existingAud = Audience.getAudienceByName(audienceName);
		if(existingAud.getAudienceId() > 0) {
			System.out.println("audience already exists");
			return;
		}
		if(confirmation.equals("y")) {
			Audience curAud = new Audience(audienceName, this.getUserId());
			System.out.println("Audience has been added with id : " + curAud.getAudienceId());
		}
		else if(confirmation.equals("n")) {
			System.out.println("Audience has not been added");
		}
		else {
			System.out.println("Wrong confirmation, audience has not been added");
		}
//		sc.close();
	}
	
	public void getAudienceIdByName(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the audience you would like to get : ");
		String audience = sc.nextLine();
		Audience curAud = Audience.getAudienceByName(audience);
		if(curAud.getAudienceId() > 0) {
			System.out.println("Position id for position : " + audience + " is : " + curAud.getAudienceId());
		}
		else {
			System.out.println("position with positoin name : " + audience + " not found.");
		}
//		sc.close();
	}
	
	public void removeAudience(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the audience you would like to remove : ");
		String audience = sc.nextLine();
		//sc.nextLine();
		Audience audToRemove = Audience.getAudienceByName(audience);
		int audienceId = audToRemove.getAudienceId();
		audToRemove.removeAudience(this.getUserId());
		
		System.out.println("Position with position id : " + audienceId + " has been removed");
//		sc.close();
	}
	
	public void getAllAudiences() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT audience_id, audience_name FROM audience WHERE is_active = 1");
	        System.out.println("AudienceId\tAudienceName");
	        while (result.next()) {
	            String audienceName = result.getString("audience_name");
	            int audienceId = result.getInt("audience_id");
	            System.out.println(audienceId + "\t" + audienceName);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
	        db.closeConnection(con);
		}
	}
	
	public void addPosition(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the position you would like to add : ");
		String positionName = sc.nextLine();
		System.out.println("the position :" + positionName + " will be added, would you like to proceed (y/n)");
		String confirmation = sc.nextLine();
		Position existingPos = Position.getPositionByName(positionName);
		if(existingPos.getPositionId() > 0) {
			System.out.println("position already exists");
			return;
		}
		if(confirmation.equals("y")) {
			Position curPos = new Position(positionName, this.getUserId());
			System.out.println("Position has been added with id : " + curPos.getPositionId());
		}
		else if(confirmation.equals("n")) {
			System.out.println("Position has not been added");
		}
		else {
			System.out.println("Wrong confirmation, position has not been added");
		}
//		sc.close();
	}
	
	public void getPositionNameById(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the id of the position you would like to get : ");
		int positionId = sc.nextInt();
		sc.nextLine();
		Position curPos = Position.getPositionById(positionId);
		if(curPos.getPositionId() > 0) {
			System.out.println("Position name for position id : " + positionId + " is : " + curPos.getPositionName());
		}
		else {
			System.out.println("position with positoin id : " + positionId + " not found.");
		}
//		sc.close();
	}
	
	public void getPositionIdByName(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the position you would like to get : ");
		String position = sc.nextLine();
		Position curPos = Position.getPositionByName(position);
		if(curPos.getPositionId() > 0) {
			System.out.println("Position id for position : " + position + " is : " + curPos.getPositionId());
		}
		else {
			System.out.println("position with positoin name : " + position + " not found.");
		}
//		sc.close();
	}
	
	public void removePosition(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the id of the position you would like to remove : ");
		int positionId = sc.nextInt();
		sc.nextLine();
		Position posToRemove = Position.getPositionById(positionId);
		posToRemove.removePosition(this.getUserId());
		System.out.println("Position with position id : " + positionId + " has been removed");
//		sc.close();
	}
	
	public void getAllPositions() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT position_id, position_name FROM position WHERE is_active = 1");
	        System.out.println("PositionId\tPositionName");
	        while (result.next()) {
	            String positionName = result.getString("position_name");
	            int positionId = result.getInt("position_id");
	            System.out.println(positionId + "\t" + positionName);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
	        db.closeConnection(con);
		}
	}
	
	public void addCategory(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the category you would like to add : ");
		String categoryName = sc.nextLine();
		System.out.println("the category :" + categoryName + " will be added, would you like to proceed (y/n)");
		String confirmation = sc.nextLine();
		Category existingCat = Category.getCategoryByName(categoryName);
		if(existingCat.getCategoryId() > 0) 
		{
			System.out.println("category already exists");
			return;
		}
		if(confirmation.equals("y")) 
		{
			Category curCat = new Category(categoryName, this.getUserId());
			System.out.println("Category has been added with id : " + curCat.getCategoryId());
		}
		else if(confirmation.equals("n")) 
		{
			System.out.println("Category has not been added");
		}
		else {
			System.out.println("Wrong confirmation, category has not been added");
		}
//		sc.close();
	}
	
	public void getCategoryNameById(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the id of the category you would like to get : ");
		int categoryId = sc.nextInt();
		sc.nextLine();
		Category curCat = Category.getCategoryById(categoryId);
		if(curCat.getCategoryId() > 0) {
			System.out.println("Position name for category id : " + categoryId + " is : " + curCat.getCategoryName());
		}
		else {
			System.out.println("position with category id : " + categoryId + " not found.");
		}
//		sc.close();
	}
	
	public void getCategoryIdByName(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the category you would like to get : ");
		String category = sc.nextLine();
		Category curCat = Category.getCategoryByName(category);
		if(curCat.getCategoryId() > 0) {
			System.out.println("Position id for position : " + category + " is : " + curCat.getCategoryId());
		}
		else {
			System.out.println("position with positoin name : " + category + " not found.");
		}
//		sc.close();
	}
	
	public void removeCategory(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the id of the category you would like to remove : ");
		int categoryId = sc.nextInt();
		sc.nextLine();
		Category posToRemove = Category.getCategoryById(categoryId);
		posToRemove.removeCategory(this.getUserId());
		System.out.println("Position with position id : " + categoryId + " has been removed");
//		sc.close();
	}
	
	public void getAllCategories() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT category_id, category_name FROM category WHERE is_active = 1");
	        System.out.println("CategoryId\tCategoryName");
	        while (result.next()) {
	            String categoryName = result.getString("category_name");
	            int categoryId = result.getInt("category_id");
	            System.out.println(categoryId + "\t" + categoryName);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
	        db.closeConnection(con);
		}
	}
	
//	public void bookActions(Scanner sc) {
//		loop: while(true) {
//			System.out.println("Please select the action you would like to perform");
//			System.out.println("1: Add a book");
//			System.out.println("2: Get the book details from ISBN");
//			System.out.println("3: Remove a book");
//			System.out.println("4: Update a book");
//			System.out.println("5: List all books");
//			System.out.println("exit: Exit from category");
//			String decision = sc.nextLine();
//			switch(decision) {
//			case "1":
//				addBook(sc);
//				break;
//			case "2":
//				getBookByIsbn(sc);
//				break;
//			case "3":
//				removeBook(sc);
//				break;
//			case "4":
//				updateBook(sc);
//				break;
//			case "5":
//				getAllBooks();
//				break;
//			case "exit":
//				break loop;
//			default:
//				System.out.println("Wrong selection");
//			}
//		}
//	}
	
	public void addBook(Scanner sc) {
		System.out.println("Enter the isbn of the book you would like to add : ");
		int isbn = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the name of the book you would like to add : ");
		String bookName = sc.nextLine();
		
		System.out.println("Enter the edition of the book you would like to add : ");
		int edition = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the text of the book you would like to add : ");
		String text = sc.nextLine();
		
		System.out.println("Enter the publication date of the book you would like to add : ");
		String pubDate = sc.nextLine();
		
		getAllPublications();
		System.out.println("Enter the id of the publication you would like to associate this book to ");
		int pubId = sc.nextInt();
		sc.nextLine();
		Publication pub = Publication.getPublicationById(pubId);
		if (pub.getPublicationTitle() == null) {
			System.out.println("Publication doesnt exist, do create a publication");
			return ;
		}

		System.out.println("the book :" + bookName + " will be added, would you like to proceed (y/n)");
		String confirmation = sc.nextLine();
		//Book existingBoo = Boo.getPeriodicISsueByName(issueName);
		//if(existingCat.getCategoryId() > 0) 
		//{
			//System.out.println("category already exists");
			//return;
		//}
		if(confirmation.equals("y")) 
		{
			Book boo = new Book(isbn,bookName, pubId, edition, text, pubDate, this.getUserId());
			System.out.println("Book has been added with ISBN : " + boo.getIsbn());
		}
		else if(confirmation.equals("n")) 
		{
			System.out.println("Book has not been added");
		}
		else {
			System.out.println("Wrong confirmation, Book has not been added");
		}
//		sc.close();
	}
	
	public void removeBook(Scanner sc)
	{
		System.out.println("Enter the ISBN of the book you would like to remove : ");
		int isbn = sc.nextInt();
		sc.nextLine();
		Book toRemove = Book.getBookByIsbn(isbn);
		toRemove.removeBook(this.getUserId());
		System.out.println("The Book with ISBN : " + isbn + " has been removed");
//		sc.close();
		
	}
	
	public void getBookByIsbn(Scanner sc) {
		System.out.println("Enter the ISBN of the book you would like to get : ");
		int isbn = sc.nextInt();
		sc.nextLine();
		Book resultBook =Book.getBookByIsbn(isbn);
	    
		if(resultBook.getBookTitle() != null) {

        	System.out.println("Book ISBN: "+ resultBook.getIsbn());
        	System.out.println("Book Title "+ resultBook.getBookTitle());
        	System.out.println("Book Edition "+resultBook.getEdition());
        	System.out.println("Book Text: " + resultBook.getText());
        	System.out.println("Book Publication Date: " + resultBook.getPublicationDate());
        	System.out.println("Book Publication Title: "+ resultBook.getBookPublication().getPublicationTitle());

  
        }
	        
		else {

			System.out.println("book publication with isbn : " + isbn + " not found.");
			System.out.println("book with ISBN : " + isbn + " not found.");
		}
//		sc.close();    
		
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

		
		
		String confirmation = sc.nextLine();
		if(confirmation.equals("y")) {
			boo.updateBook( title, bookPub.getPublicationId(), edition, text, pubDate,this.getUserId());
		}
		else if(confirmation.equals("n")) {
			System.out.println("Book has not been added");
		}
		else {
			System.out.println("Wrong confirmation, book has not been updated");
		}	
	}
	
	public void addInvitedEmployee(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the employee you would like to add : ");
		String employeeName = sc.nextLine();
		if(Employee.isEmployeePresent(employeeName)) {
			System.out.println("Employee with the given name already exists");
			return;
		}
		int position;
		while(true) {
			System.out.println("select the position id you would like the employee to be : ");
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			ArrayList<Integer> positionIds = new ArrayList<Integer>();
			try {	
				Statement statement = con.createStatement();
		        ResultSet result = null;
		        
		        result = statement.executeQuery("SELECT position_id, position_name FROM position WHERE is_active = 1");
		        System.out.println("positionId\tpositionName");
		        
		        while (result.next()) {
		            int positionId = result.getInt("position_id");
		            positionIds.add(positionId);
		            String positionName = result.getString("position_name");
		            System.out.println(positionId + ": \t" + positionName);
		        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			finally {
		        db.closeConnection(con);
			}
			position = sc.nextInt();
			sc.nextLine();
			if(positionIds.contains(position)) {
				break;
			}
			else {
				System.out.println("wrong position id, try again");
			}
		}
		System.out.println("Enter the wage per task you would like the employee to be : ");
		int wage = sc.nextInt();
		sc.nextLine();
		System.out.println("the employee :" + employeeName + " with position : " + position + " and wage " + wage + " will be added, would you like to proceed (y/n)");
		String confirmation = sc.nextLine();
		if(confirmation.equals("y")) {
			Position curPos = Position.getPositionById(position);
			InvitedEmployee curEmp = new InvitedEmployee(employeeName, curPos, wage, this.getUserId());
			System.out.println("An invited employee has been added with employee id : " + curEmp.getEmployeeId());
		}
		else if(confirmation.equals("n")) {
			System.out.println("employee has not been added");
		}
		else {
			System.out.println("Wrong confirmation, employee has not been added");
		}
//		sc.close();
	}

	public void addStaffEmployee(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the employee you would like to add : ");
		String employeeName = sc.nextLine();
		if(Employee.isEmployeePresent(employeeName)) {
			System.out.println("Employee with the given name already exists");
			return;
		}
		int position;
		while(true) {
			System.out.println("select the position id you would like the employee to be : ");
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			ArrayList<Integer> positionIds = new ArrayList<Integer>();
			try {	
				Statement statement = con.createStatement();
		        ResultSet result = null;
		        
		        result = statement.executeQuery("SELECT position_id, position_name FROM position WHERE is_active = 1");
		        System.out.println("positionId\tpositionName");
		        
		        while (result.next()) {
		            int positionId = result.getInt("position_id");
		            positionIds.add(positionId);
		            String positionName = result.getString("position_name");
		            System.out.println(positionId + ": \t" + positionName);
		        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			finally {
		        db.closeConnection(con);
			}
			position = sc.nextInt();
			sc.nextLine();
			if(positionIds.contains(position)) {
				break;
			}
			else {
				System.out.println("wrong position id, try again");
			}
		}
		System.out.println("Enter the salary per task you would like the employee to be : ");
		int salary = sc.nextInt();
		sc.nextLine();
		System.out.println("the employee :" + employeeName + " with position : " + position + " and salary : " + salary + " will be added, would you like to proceed (y/n)");
		String confirmation = sc.nextLine();
		if(confirmation.equals("y")) {
			Position curPos = Position.getPositionById(position);
			StaffEmployee curEmp = new StaffEmployee(employeeName, curPos, salary, this.getUserId());
			System.out.println("A staff employee has been added with employee id : " + curEmp.getEmployeeId());
		}
		else if(confirmation.equals("n")) {
			System.out.println("employee has not been added");
		}
		else {
			System.out.println("Wrong confirmation, employee has not been added");
		}
//		sc.close();
	}
	
	public void removeEmployee(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Select the id of the employee you would like to remove : ");
		getAllEmployees();
		int empId = sc.nextInt();
		sc.nextLine();
		Employee toRemove = Employee.getEmployee(empId);
		toRemove.removeEmployee(this.getUserId());
		System.out.println("Employee with employee id : " + empId + " has been removed");
//		sc.close();
	}
	
	public void getAllEmployees() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT employee.employee_id AS employee_id, employee.employee_name AS employee_name, position.position_name AS position_name, IF(employee.employee_id IN (SELECT employee_id FROM invited_employee), 'invited', 'staff') AS employee_type FROM employee INNER JOIN position ON employee.position_id = position.position_id WHERE employee.is_active = 1 AND position.is_active = 1;");
	        System.out.println("employeeId\temployeeName\tposition\ttype");
	        while (result.next()) {
	            int employeeId = result.getInt("employee_id");
	            String employeeName = result.getString("employee_name");
	            String positionName = result.getString("position_name");
	            String employeeType = result.getString("employee_type");
	            System.out.println(employeeId + "\t" + employeeName + "\t" + positionName + "\t" + employeeType);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
	        db.closeConnection(con);
		}
	}
	
	public void getAllEditors() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT employee.employee_id AS employee_id, employee.employee_name AS employee_name, position.position_name AS position_name, IF(employee.employee_id IN (SELECT employee_id FROM invited_employee), 'invited', 'staff') AS employee_type FROM employee INNER JOIN position ON employee.position_id = position.position_id WHERE employee.is_active = 1 AND position.is_active = 1 AND position.position_name = 'Editor';");
	        System.out.println("employeeId\temployeeName\tposition\ttype");
	        while (result.next()) {
	            int employeeId = result.getInt("employee_id");
	            String employeeName = result.getString("employee_name");
	            String positionName = result.getString("position_name");
	            String employeeType = result.getString("employee_type");
	            System.out.println(employeeId + "\t" + employeeName + "\t" + positionName + "\t" + employeeType);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
	        db.closeConnection(con);
		}
	}
	
	public void getAllAuthors() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT employee.employee_id AS employee_id, employee.employee_name AS employee_name, position.position_name AS position_name, IF(employee.employee_id IN (SELECT employee_id FROM invited_employee), 'invited', 'staff') AS employee_type FROM employee INNER JOIN position ON employee.position_id = position.position_id WHERE employee.is_active = 1 AND position.is_active = 1 AND position.position_name = 'Author';");
	        System.out.println("employeeId\temployeeName\tposition\ttype");
	        while (result.next()) {
	            int employeeId = result.getInt("employee_id");
	            String employeeName = result.getString("employee_name");
	            String positionName = result.getString("position_name");
	            String employeeType = result.getString("employee_type");
	            System.out.println(employeeId + "\t" + employeeName + "\t" + positionName + "\t" + employeeType);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
	        db.closeConnection(con);
		}
	}
	
	public void getEmployeeById(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the id of the employee you would like to update : ");
		int empId = sc.nextInt();
		sc.nextLine();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT employee.employee_id AS employee_id, employee.employee_name AS employee_name, position.position_name AS position_name, IF(employee.employee_id IN (SELECT employee_id FROM invited_employee), 'invited', 'staff') AS employee_type FROM employee INNER JOIN position ON employee.position_id = position.position_id WHERE employee.is_active = 1 AND position.is_active = 1 AND employee.employee_id = " + empId + ";");
	        System.out.println("employeeId\temployeeName\tposition\ttype");
	        while (result.next()) {
	            int employeeId = result.getInt("employee_id");
	            String employeeName = result.getString("employee_name");
	            String positionName = result.getString("position_name");
	            String employeeType = result.getString("employee_type");
	            System.out.println(employeeId + "\t" + employeeName + "\t" + positionName + "\t" + employeeType);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
	        db.closeConnection(con);
		}
	}
	
	public void updateEmployee(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Select the id of the employee you would like to update : ");
		getAllEmployees();
		int empId = sc.nextInt();
		sc.nextLine();
		String empType = Employee.getEmployeeTypeById(empId);
		System.out.println("The employee with id " + empId + " is a " + empType + " employee");
		if(empType != null && empType.equals("invited")) {
			InvitedEmployee curEmp = InvitedEmployee.getInvitedEmployeeById(empId);
			String newName = curEmp.getEmployeeName();
			String newPosition = curEmp.getEmpPosition().getPositionName();
			int newWage = curEmp.getWage();
			System.out.println("would you like to update the name for the employee? (y/n)");
			String decision = sc.nextLine();
			if(decision.equals("y")) {
				System.out.println("Enter the new name for the employee");
				newName = sc.nextLine();
			}
			else {
				System.out.println("Name of the employee will not be updated");
			}
			System.out.println("would you like to update the position for the employee? (y/n)");
			decision = sc.nextLine();
			if(decision.equals("y")) {
				int position;
				while(true) {
					System.out.println("select the position id you would like the employee to be : ");
					Database db = new Database();
					Connection con = db.getConnection(false);
					if(con == null) {
						System.out.println("Failed to establish the connection");
						System.exit(0);
					}
					ArrayList<Integer> positionIds = new ArrayList<Integer>();
					try {	
						Statement statement = con.createStatement();
				        ResultSet result = null;
				        
				        result = statement.executeQuery("SELECT position_id, position_name FROM position WHERE is_active = 1");
				        System.out.println("positionId\tpositionName");
				        
				        while (result.next()) {
				            int positionId = result.getInt("position_id");
				            positionIds.add(positionId);
				            String positionName = result.getString("position_name");
				            System.out.println(positionId + ": \t" + positionName);
				        }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					finally {
				        db.closeConnection(con);
					}
					position = sc.nextInt();
					sc.nextLine();
					if(positionIds.contains(position)) {
						break;
					}
					else {
						System.out.println("wrong position id, try again");
					}
				}
				Position curPos = Position.getPositionById(position);
				newPosition = curPos.getPositionName();
			}
			else{
				System.out.println("Position of the employee will not be updated");
			}
			System.out.println("would you like to update the wage for the employee? (y/n)");
			decision = sc.nextLine();
			if(decision.equals("y")) {
				System.out.println("Enter the new wage for the employee");
				newWage = sc.nextInt();
				sc.nextLine();
			}
			else {
				System.out.println("Wage for the employee will not be updated");
			}
			curEmp.updateEmployee(newName, newPosition, this.getUserId());
			curEmp.updateWage(empId, newWage, this.getUserId());
		}
		else if(empType != null && empType.equals("staff"))  {
			StaffEmployee curEmp = StaffEmployee.getStaffEmployeeById(empId);
			String newName = curEmp.getEmployeeName();
			String newPosition = curEmp.getEmpPosition().getPositionName();
			int newSalary = curEmp.getSalary();
			System.out.println("would you like to update the name for the employee? (y/n)");
			String decision = sc.nextLine();
			if(decision.equals("y")) {
				System.out.println("Enter the new name for the employee");
				newName = sc.nextLine();
			}
			else {
				System.out.println("Name of the employee will not be updated");
			}
			System.out.println("would you like to update the position for the employee? (y/n)");
			decision = sc.nextLine();
			if(decision.equals("y")) {
				int position;
				while(true) {
					System.out.println("select the position id you would like the employee to be : ");
					Database db = new Database();
					Connection con = db.getConnection(false);
					if(con == null) {
						System.out.println("Failed to establish the connection");
						System.exit(0);
					}
					ArrayList<Integer> positionIds = new ArrayList<Integer>();
					try {	
						Statement statement = con.createStatement();
				        ResultSet result = null;
				        
				        result = statement.executeQuery("SELECT position_id, position_name FROM position WHERE is_active = 1");
				        System.out.println("positionId\tpositionName");
				        
				        while (result.next()) {
				            int positionId = result.getInt("position_id");
				            positionIds.add(positionId);
				            String positionName = result.getString("position_name");
				            System.out.println(positionId + ": \t" + positionName);
				        }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					finally {
				        db.closeConnection(con);
					}
					position = sc.nextInt();
					sc.nextLine();
					if(positionIds.contains(position)) {
						break;
					}
					else {
						System.out.println("wrong position id, try again");
					}
				}
				Position curPos = Position.getPositionById(position);
				newPosition = curPos.getPositionName();
			}
			else {
				System.out.println("Positon of the employee will not be updated");
			}
			System.out.println("would you like to update the salary for the employee? (y/n)");
			decision = sc.nextLine();
			if(decision.equals("y")) {
				System.out.println("Enter the new salary for the employee");
				newSalary = sc.nextInt();
				sc.nextLine();
			}
			else {
				System.out.println("Salary of the employee will not be updated");
			}
			curEmp.updateEmployee(newName, newPosition, this.getUserId());
			curEmp.updateWage(empId, newSalary, this.getUserId());
		}
		else {
			System.out.println("Employee not found");
		}
//		sc.close();
	}
	public void getAllDistributors() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT distributor.distributor_id AS distributor_id, distributor.distributor_name AS distributor_name, distributor_type.distributor_type_name AS distributor_type_name FROM distributor INNER JOIN distributor_type ON distributor.distributor_type_id = distributor_type.distributor_type_id WHERE distributor.is_active = 1 AND distributor_type.is_active = 1;");
	        System.out.println("distributorId\tdistributorName\tdistributorType");
	        while (result.next()) {
	            int distributorId = result.getInt("distributor_id");
	            String distributorName = result.getString("distributor_name");
	            String distributorType = result.getString("distributor_type_name");
	            System.out.println(distributorId + "\t" + distributorName + "\t" + distributorType );
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
	        db.closeConnection(con);
		}
	}
	
	public void distributorReport(Scanner sc) {
		System.out.println("Select the id of the distributor you would like to get the reports for : ");
		getAllDistributors();
		int distId = sc.nextInt();
		System.out.println("Enter the month for which you want the report");
		int month = sc.nextInt();
		System.out.println("Enter the year for which you want the report");
		int year = sc.nextInt();
		sc.nextLine();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("call sp_report_by_distributor(" + distId + "," + month + "," + year + ",@err);");
	        System.out.println("distributorId\tdistributorName\tpublicationId\tpublicationTitle\tmonth\tyear\ttotalCopies\ttotalPrice");
	        while (result.next()) {
	            int distributorId = result.getInt("distributor_id");
	            String distributorName = result.getString("distributor_name");
	            int publicationId = result.getInt("publication_id");
	            String publicationName = result.getString("publication_title");
	            int o_month = result.getInt("month");
	            int o_year = result.getInt("year");
	            int totalCopies = result.getInt("number_of_copies");
	            int totalPrice = result.getInt("total_price");
	            System.out.println(distributorId + "\t" + distributorName + "\t" + publicationId + "\t" + publicationName + "\t" + o_month + "\t" + o_year + "\t" + totalCopies + "\t" + totalPrice);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
	}
	
	public void monthlyRevenue(Scanner sc) {
		System.out.println("Enter the month for which you want the report");
		int month = sc.nextInt();
		System.out.println("Enter the year for which you want the report");
		int year = sc.nextInt();
		sc.nextLine();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("call sp_report_revenue(" + month + "," + year + ",@err);");
	        System.out.println("month\tyear\trevenue");
	        while (result.next()) {
	            int o_month = result.getInt("month");
	            int o_year = result.getInt("year");
	            int revenue = result.getInt("revenue");
	            System.out.println(o_month + "\t" + o_year + "\t" + revenue);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
	}
	public void monthlyExpenses(Scanner sc) {
		System.out.println("Enter the month for which you want the report");
		int month = sc.nextInt();
		System.out.println("Enter the year for which you want the report");
		int year = sc.nextInt();
		sc.nextLine();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("call sp_report_expenses(" + month + "," + year + ",@err);");
	        System.out.println("month\tyear\texpenses");
	        while (result.next()) {
	            int o_month = result.getInt("month");
	            int o_year = result.getInt("year");
	            int expenses = result.getInt("expenses");
	            System.out.println(o_month + "\t" + o_year + "\t" + expenses);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
	}
	
	public void totalDistributors(Scanner sc) {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("select COUNT(1) AS distributor_count FROM distributor WHERE is_active = 1");
	        System.out.println("total distributors");
	        while (result.next()) {
	            int o_distributors = result.getInt("distributor_count");
	            
	            System.out.println(o_distributors);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}	
	}
	
	public void revenuePerCity(Scanner sc) {
		System.out.println("Enter the name of the city for which you want the report");
		String city = sc.nextLine();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("call sp_report_revenue_per_city('" + city + "', @err);");
	        System.out.println("cityId\tcityName\tRevenue");
	        while (result.next()) {
	            int cityId = result.getInt("city_id");
	            String cityName = result.getString("city_name");
	            int revenue = result.getInt("revenue");
	            System.out.println(cityId + "\t" + cityName + "\t" + revenue);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}	
	}
	
	public void revenueSinceInception(Scanner sc) {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT SUM(amount) AS revenue FROM received_payment;");
	        System.out.println("toatlRevenue");
	        while (result.next()) {
	            int revenue = result.getInt("revenue");
	            System.out.println(revenue);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}	
	}
	
	public void revenuePerDistributor(Scanner sc) {
		System.out.println("Enter the id of the distributor for which you want the report");
		getAllDistributors();
		int distributor_id = sc.nextInt();
		sc.nextLine();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("call sp_report_revenue_per_distributor('" + distributor_id + "', @err);");
	        System.out.println("distributorId\tdistributorName\tRevenue");
	        while (result.next()) {
	            int distId = result.getInt("distributor_id");
	            String distName = result.getString("distributor_name");
	            int revenue = result.getInt("revenue");
	            System.out.println(distId + "\t" + distName + "\t" + revenue);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}	
	}
	
	public void revenuePerLocation(Scanner sc) {
		System.out.println("Enter the name of the city for which you want the report");
		String city = sc.nextLine();
		System.out.println("Enter the address of the location for which you want the report");
		String location = sc.nextLine();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("call sp_report_revenue_per_location('" + city + "', " + location + "' , @err);");
	        System.out.println("cityId\tcityName\tstreet_address\tRevenue");
	        while (result.next()) {
	            int cityId = result.getInt("city_id");
	            String cityName = result.getString("city_name");
	            String streetAdd = result.getString("street_address");
	            int revenue = result.getInt("revenue");
	            System.out.println(cityId + "\t" + cityName + "\t" + streetAdd + "\t" + revenue);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}	
	}
	
	public void paymentPerWorkType(Scanner sc) {
		System.out.println("Select the type of the work for which you want the report");
		System.out.println("1: book authorship");
		System.out.println("2: article authroship");
		System.out.println("3: editorial work");
		int workTypeId = sc.nextInt();
		sc.nextLine();
		String workType;
		switch(workTypeId) {
		case 1:
			workType = "book_authorship";
			break;
		case 2:
			workType = "article_authorship";
			break;
		case 3:
			workType = "editorial_work";
			break;
		default:
			System.out.println("Wrong selection for work type");
			return;
		}
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("call sp_report_payment_per_work_type('" + workType + "', @err);");
	        System.out.println("workType\tPayment");
	        while (result.next()) {
	            String o_workType = result.getString("work_type");
	            int o_payment = result.getInt("payment");
	            System.out.println(o_workType + "\t" + o_payment);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}	
	}
	
	public void paymentPerTimePeriod(Scanner sc) {
		System.out.println("Select the start month for which you want the report");
		int startMonth = sc.nextInt();
		System.out.println("Select the start year for which you want the report");
		int startYear = sc.nextInt();
		System.out.println("Select the end month for which you want the report");
		int endMonth = sc.nextInt();
		System.out.println("Select the end year for which you want the report");
		int endYear = sc.nextInt();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("call sp_report_payment_per_time(" + startMonth + ", " + startYear + ", " + endMonth + ", " + endYear + ", @err);");
	        System.out.println("month\tyear\tpayment");
	        int totalPayment = 0;
	        while (result.next()) {
	            int o_month = result.getInt("month");
	            int o_year = result.getInt("year");
	            int o_payment = result.getInt("payment");
	            totalPayment += o_payment;
	            System.out.println(o_month + "\t" + o_year + "\t" + o_payment);
	        }
	        System.out.println("Total payment for selected duration: " + totalPayment);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}	
	}
	
	public void cityActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Add a City.");
			System.out.println("2: Get City id by name.");
			System.out.println("3: Remove city");
			System.out.println("4: List all names of city.");
			System.out.println("exit: Exit from City");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addCity(sc);
				break;
			case "2":
				getCityIdByName(sc);
				break;
			case "3":
				removeCity(sc);
				break;
			case "4":
				getAllCity();
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	
	
	public void distributorTypeActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Add distributor Type.");
			System.out.println("2: Get Distributor Type.");
			System.out.println("3: Remove distributor type.");
			System.out.println("4: List all types distributor.");
			System.out.println("exit: Exit from Distributor Type");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				this.addDistributorType(sc);
				break;
			case "2":
				this.getDistributorTypeIdByName(sc);
				break;
			case "3":
				this.removeDistributorType(sc);
				break;
			case "4":
				this.getAllDistributorType();
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	
	public void distributorActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: Add distributor.");
//			System.out.println("2: Get distributor id by name.");
			System.out.println("2: Get distributor by ID.");
			System.out.println("3: List all names of distributor.");
			System.out.println("4: Update distributor");
			System.out.println("5: Remove distributor.");
			System.out.println("exit: Exit from position");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				addDistributor(sc);
				break;
			case "2":
				getDistributorByID(sc);
				break;
//			case "3":
//				getPositionIdByName(sc);
//				break;
			case "3":
				getAllDistributor();
				break;
			case "4":
				updateDistributor(sc);
				break;
			case "5":
				removeDistributor(sc);
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			}
		}
	}
	public void addCity(Scanner sc) {
		
			System.out.println("Enter the city name: ");
			String inputCityName = sc.nextLine();	
			System.out.println("the city :" + inputCityName + " will be added, would you like to proceed (y/n)");
			String confirmation = sc.nextLine();
			City existingCity = City.getCityByName(inputCityName);
			if(existingCity.getCityId() > 0) {
				System.out.println("City already exists");
				return;
			}
			if(confirmation.equals("y")) {
				City curCity = new City(inputCityName, this.getUserId());
				System.out.println("City has been added with id : " + curCity.getCityId());
			}
			else if(confirmation.equals("n")) {
				System.out.println("City has not been added");
			}
			else {
				System.out.println("Wrong confirmation, City has not been added");
			}
	}
	
	
	public void getCityIdByName(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the City you would like to get : ");
		String cityName = sc.nextLine();
		City curCity = City.getCityByName(cityName);
		if(curCity.getCityId() > 0) {
			System.out.println("City id for city : " + cityName + " is : " + curCity.getCityId());
		}
		else {
			System.out.println("City with city name : " + cityName + " not found.");
		}
//		sc.close();
	}
	
	public void removeCity(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the city you would like to remove : ");
		String cityName = sc.nextLine();
		sc.nextLine();
		City.removeCity(cityName,this.getUserId());
		System.out.println("City with city id : " + cityName + " has been removed");
//		sc.close();
	}
	
	public void getAllCity() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT city_id, city_name FROM city WHERE is_active = 1");
	        System.out.println("CityId\tCityName");
	        while (result.next()) {
	            String cityName = result.getString("city_name");
	            int cityId = result.getInt("city_id");
	            System.out.println(cityId + "\t" + cityName);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
	        db.closeConnection(con);
		}
	}
	
	//********************Distributor Type*******************************	
	public void addDistributorType(Scanner sc) {
		
		System.out.println("Enter the Distributor Type: ");
		String dType = sc.nextLine();	
		System.out.println("The Distributor Type :" + dType + " will be added, would you like to proceed (y/n)");
		String confirmation = sc.nextLine();
		DistributorType existingCity = DistributorType.getTypeByName(dType);
		if(existingCity.getTypeId() > 0) {
			System.out.println("Distributor Type already exists");
			return;
		}
		if(confirmation.equals("y")) {
			DistributorType curDtype = new DistributorType(dType, this.getUserId());
			System.out.println("Distributor Type has been added with id : " + curDtype.getTypeId());
		}
		else if(confirmation.equals("n")) {
			System.out.println("Distributor Type has not been added");
		}
		else {
			System.out.println("Wrong confirmation, Distributor Type has not been added");
		}
}


	
public void getDistributorTypeIdByName(Scanner sc) {
//	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the name of the distributor type you would like to get : ");
	String dType = sc.nextLine();
	DistributorType curDT = DistributorType.getTypeByName(dType);
	if(curDT.getTypeId() > 0) {
		System.out.println("Distributor Type ID for Distributor type : " + dType + " is : " + curDT.getTypeId());
	}
	else {
		System.out.println("Distributor type : " + dType + " not found.");
	}
//	sc.close();
}

public void removeDistributorType(Scanner sc) {
//	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the name of the Distributor Type you would like to remove : ");
	String dType = sc.nextLine();
	sc.nextLine();
	DistributorType.removeType(dType,this.getUserId());
	System.out.println("Distributor Type with Distributor Type id : " + dType + " has been removed");
//	sc.close();
}

public void getAllDistributorType() {
	Database db = new Database();
	Connection con = db.getConnection(false);
	if(con == null) {
		System.out.println("Failed to establish the connection");
		System.exit(0);
	}
	
	try {	
		Statement statement = con.createStatement();
        ResultSet result = null;
        result = statement.executeQuery("SELECT distributor_Type_id, distributor_Type_name FROM distributor_type WHERE is_active = 1");
        System.out.println("Distributor Type ID\tDistributor Type");
        while (result.next()) {
            String distributor_Type_name = result.getString("distributor_Type_name");
            int distributor_Type_id = result.getInt("distributor_Type_id");
            System.out.println(distributor_Type_id + "\t" + distributor_Type_name);
        }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	finally {
        db.closeConnection(con);
	}
}
//************************************************************************
//  						DistributorActions
//************************************************************************
public void addDistributor(Scanner sc) {
	//	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the name of the Distributor you would like to add : ");
	String disName = sc.nextLine();
	if(Distributor.isDistributorPresent(disName)) {
		System.out.println("Distributor with the given name already exists");
		return;
	}
	
//	String city;
//	Map<Integer,String> cityIds = new HashMap<Integer,String>();
//	while(true) {
//		System.out.println("select the City id of the new disrtibutor: ");
//		Database db = new Database();
//		Connection con = db.getConnection(false);
//		if(con == null) {
//			System.out.println("Failed to establish the connection");
//			System.exit(0);
//		}
//		
//		try {	
//			Statement statement = con.createStatement();
//			ResultSet result = null;
//
//			result = statement.executeQuery("SELECT city_id, city_name FROM city WHERE is_active = 1");
//			System.out.println("cityId\tcityName");
//
//			while (result.next()) {
//				int city_Id = result.getInt("city_id");
//				String city_Name = result.getString("city_name");
//				cityIds.put(city_Id,city_Name);
//				System.out.println(city_Id + ": \t" + city_Name);
//			}
//			
//			System.out.println("Type Add to add new City");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		finally {
//			db.closeConnection(con);
//		}
//		city = sc.nextLine();
//		if (city.equalsIgnoreCase("add")) {
//			this.addCity(sc);
//			
//		}
//		else if(cityIds.containsKey(Integer.parseInt(city))) {
//			break;
//		}
//		else {
//			System.out.println("wrong city id, try again");
//		}
//	}
	DistributorType curDisType = this.chooseDistributor(sc);
	City curCity= this.chooseCity(sc);
	System.out.println("Enter Address: ");
	String address = sc.nextLine();
	System.out.println("Enter Phone number: ");
	long phoneNumber = Long.parseLong(sc.nextLine());
	System.out.println("Enter Person of contact: ");
	String personOfContact = sc.nextLine();
	
	
	System.out.println("The distributor :" + disName + ", with type : " + curDisType.getTypeName() +", address "+address+ ", city: " + curCity.getCityname()+  ", phone number: "+ phoneNumber+ ", person of contact: "+personOfContact+" will be added, would you like to proceed (y/n)");
	
	String confirmation = sc.nextLine();
	if(confirmation.equals("y")) {
//		
//		City curCity = City.getCityByName(cityIds.get(Integer.parseInt(city)));
//		DistributorType curDisType= DistributorType.getTypeByName(dTypeIds.get(Integer.parseInt(dType)));
		Distributor curDis = new Distributor(disName,curDisType,address,curCity,phoneNumber,personOfContact,this.getUserId());
		System.out.println("The distributor has been added with distributor id : " + curDis.getDistributorID());
	}
	else if(confirmation.equals("n")) {
		System.out.println("Distributor has not been added");
	}
	else {
		System.out.println("Wrong confirmation, employee has not been added");
	}
	//	sc.close();
}
public void removeDistributor(Scanner sc){
	System.out.println("Enter the name of the Distributor ID you would like to remove : ");
	int dID = sc.nextInt();
	sc.nextLine();
	Distributor toRemove = Distributor.getDistributorByID(dID);
	toRemove.removeDistributor(this.getUserId());
	System.out.println("The Distributor with Distributor id : " + dID + " has been removed");
//	sc.close();
	
}

public void updateDistributor(Scanner sc) {
	System.out.println("Select the id of the distributor you would like to update : ");
	this.getAllDistributor();
	int disId = sc.nextInt();
	
	Distributor curdis = Distributor.getDistributorByID(disId);
	if(curdis == null) {
		System.out.println("Distributor with the given ID does not exits.");
		return;
	}
	String name = curdis.getDistributorName();
	DistributorType type = curdis.getDistributortype();
	String address = curdis.getAddress();
	City city = curdis.getCityobj();
	long phoneNumber = curdis.getPhonenumber();
	String personOfContact = curdis.getPerson_of_contact();
	int balance = curdis.getBalance();
	
	loop: while(true) {
		System.out.println("Please select what you would like to update");
		System.out.println("1: Distributor Name.");
		System.out.println("2: Distributor Type.");
		System.out.println("3: Distributor Address.");
		System.out.println("4: Distributor City.");
		System.out.println("5: Distributor Phone number.");
		System.out.println("6: Distributor Person of contact.");
		System.out.println("7: Distributor Balance.");
		System.out.println("done: If you are done with update");
		
		
		String decision = sc.nextLine();
		switch(decision) {
		case "1":
			System.out.println("Enter the new name of the Distributor: ");
			name = sc.nextLine();
			break;
		case "2":
			type = this.chooseDistributor(sc);
			break;
		case "3":
			System.out.println("Enter the new Address of the Distributor: ");
			address = sc.nextLine();
			break;
		case "4":
			city = this.chooseCity(sc);
			break;
		case "5":
			System.out.println("Enter the new Phone Number of the Distributor: ");
			phoneNumber = Long.parseLong(sc.nextLine());
			break;
		case "6":
			System.out.println("Enter the new Person of contact of the Distributor: ");
			personOfContact = sc.nextLine();
			break;
		case "7":
			System.out.println("Enter the new Balance of the Distributor: ");
			balance = Integer.parseInt(sc.nextLine());
			break;
		case "done":
			break loop;
		default:
			System.out.println("Wrong selection");
		
		}
		
	}
	System.out.println("The distributor :" + name + "\n type : " + type.getTypeName() +"\n address "+address+ "\n city: " + city.getCityname() +  "\n phone number: "+ phoneNumber+ "\n person of contact: "+personOfContact+"\n balance: "+ balance+"\n would you like to proceed with Update (y/n)");
	
	
	String confirmation = sc.nextLine();
	if(confirmation.equals("y")) {
		curdis.updateDistributor( name, type, address, city, phoneNumber,personOfContact, balance, this.getUserId());
	}
	else if(confirmation.equals("n")) {
		System.out.println("Distributor has not been added");
	}
	else {
		System.out.println("Wrong confirmation, employee has not been added");
	}
		
	
	
	
}
private City chooseCity(Scanner sc) {
	String city;
	Map<Integer,String> cityIds = new HashMap<Integer,String>();
	while(true) {
		System.out.println("select the City id of the new disrtibutor: ");
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
			ResultSet result = null;

			result = statement.executeQuery("SELECT city_id, city_name FROM city WHERE is_active = 1");
			System.out.println("cityId\tcityName");

			while (result.next()) {
				int city_Id = result.getInt("city_id");
				String city_Name = result.getString("city_name");
				cityIds.put(city_Id,city_Name);
				System.out.println(city_Id + ": \t" + city_Name);
			}
			
			System.out.println("Type Add to add new City");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			db.closeConnection(con);
		}
		city = sc.nextLine();
		if (city.equalsIgnoreCase("add")) {
			this.addCity(sc);
			
		}
		else if(cityIds.containsKey(Integer.parseInt(city))) {
			break;
		}
		else {
			System.out.println("wrong city id, try again");
		}
	}
	City curCity = City.getCityByName(cityIds.get(Integer.parseInt(city)));
	return curCity;
}
private DistributorType chooseDistributor(Scanner sc) {
	String dType;
	Map<Integer,String> dTypeIds = new HashMap<Integer,String>();
	while(true) {
		System.out.println("select the distributor type id of the new disrtibutor: ");
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
			ResultSet result = null;

			result = statement.executeQuery("SELECT distributor_type_id, distributor_type_name FROM distributor_type WHERE is_active = 1");
			System.out.println("distributorId\tdistributorName");

			while (result.next()) {
				int disTypeId = result.getInt("distributor_type_id");
				String disTypeName = result.getString("distributor_type_name");
				dTypeIds.put(disTypeId,disTypeName);
				System.out.println(disTypeId + ": \t" + disTypeName);
			}
			System.out.println("Type Add to add new Type");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			db.closeConnection(con);
		}
		dType = sc.nextLine();
		
		if (dType.equalsIgnoreCase("add")) {
			this.addDistributorType(sc);
			
		}
		else if(dTypeIds.containsKey(Integer.parseInt(dType))) {
			break;
		}
		else {
			System.out.println("wrong distributor type id, try again");
		}
		
	}
	
	DistributorType curDisType= DistributorType.getTypeByName(dTypeIds.get(Integer.parseInt(dType)));
	return curDisType;
}
public void getDistributorByID(Scanner sc) {
	System.out.println("Enter the id of the distributor you would like to get : ");
	int disId = sc.nextInt();
	sc.nextLine();
	Database db = new Database();
	Connection con = db.getConnection(false);
	if(con == null) {
		System.out.println("Failed to establish the connection");
		System.exit(0);
	}
	
	try {	
		Statement statement = con.createStatement();
        ResultSet result = null;
//        result = statement.executeQuery("SELECT employee.employee_id AS employee_id, employee.employee_name AS employee_name, position.position_name AS position_name, IF(employee.employee_id IN (SELECT employee_id FROM invited_employee), 'invited', 'staff') AS employee_type FROM employee INNER JOIN position ON employee.position_id = position.position_id WHERE employee.is_active = 1 AND position.is_active = 1 AND employee.employee_id = " + empId + ";");
        Distributor resultDis =Distributor.getDistributorByID(disId);
        System.out.println("employeeId\temployeeName\tposition\ttype");
       
        	System.out.println("***************");
        	
        	System.out.println("Distributor ID: "+ resultDis.getDistributorID());
        	System.out.println("Distributor Name "+ resultDis.getDistributorName());
        	System.out.println("Distributor Type "+resultDis.getDistributortype().getTypeName());
        	System.out.println("Distributor Address: " + resultDis.getAddress());
        	System.out.println("Distributor City: " + resultDis.getCityobj().getCityname());
        	System.out.println("Distributor Phone number: "+ resultDis.getPhonenumber());
        	System.out.println("Disttibutor Person of contact: "+ resultDis.getPerson_of_contact());
        	System.out.println("Distributor Balance: "+ resultDis.getBalance());
  
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	finally {
        db.closeConnection(con);
	}
	
}
public void getAllDistributor() {
	Database db = new Database();
	Connection con = db.getConnection(false);
	if(con == null) {
		System.out.println("Failed to establish the connection");
		System.exit(0);
	}
	
	try {	
		Statement statement = con.createStatement();
        ResultSet result = null;
        result = statement.executeQuery("Select distributor.distributor_id AS distributor_id, distributor.distributor_name AS distributor_name, distributor_type.distributor_type_name AS distributor_type, distributor.street_address AS street_address, city.city_name As city_name , distributor.phone_number As phone_number, distributor.contact_person As contact_person, distributor.balance As balance FROM distributor INNER JOIN city ON city.city_id = distributor.city_id INNER JOIN distributor_type ON distributor_type.distributor_type_id = distributor.distributor_type_id WHERE distributor.is_active = 1");
        System.out.println("List of Active dristributors is the system.");
        int x=1;
        while (result.next()) {
        	
        	System.out.println("***************");
        	System.out.println("Distributor "+x);
        	System.out.println("Distributor ID: "+ result.getInt("distributor_id"));
        	System.out.println("Distributor Name "+ result.getString("distributor_name"));
        	System.out.println("Distributor Type "+result.getString("distributor_type"));
        	System.out.println("Distributor Address: " + result.getString("street_address"));
        	System.out.println("Distributor City: " + result.getString("city_name"));
        	System.out.println("Distributor Phone number: "+ result.getInt("phone_number"));
        	System.out.println("Disttibutor Person of contact: "+ result.getString("contact_person"));
        	System.out.println("Distributor Balance: "+ result.getInt("balance"));
            
            x++;
           
        }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	finally {
        db.closeConnection(con);
	}
}

public void addTopic(Scanner sc) {
//	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the name of the topic you would like to add : ");
	String topicName = sc.nextLine();
	System.out.println("the topic :" + topicName + " will be added, would you like to proceed (y/n)");
	String confirmation = sc.nextLine();
	Topic existingTop = Topic.getTopicByName(topicName);
	if(existingTop.getTopicId() > 0) {
		System.out.println("topic already exists");
		return;
	}
	if(confirmation.equals("y")) {
		Topic curTop = new Topic(topicName, this.getUserId());
		System.out.println("Topic has been added with id : " + curTop.getTopicId());
	}
	else if(confirmation.equals("n")) {
		System.out.println("Topic has not been added");
	}
	else {
		System.out.println("Wrong confirmation, topic has not been added");
	}
//	sc.close();
}

public void getTopicIdByName(Scanner sc) {
//	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the name of the topic you would like to get : ");
	String topic = sc.nextLine();
	Topic curTop = Topic.getTopicByName(topic);
	if(curTop.getTopicId() > 0) {
		System.out.println("Topic id for topic : " + topic + " is : " + curTop.getTopicId());
	}
	else {
		System.out.println("topic with topic name : " + topic + " not found.");
	}
//	sc.close();
}

public void removeTopic(Scanner sc) {
//	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the name of the topic you would like to remove : ");
	String topic = sc.nextLine();
	//sc.nextLine();
	Topic topToRemove = Topic.getTopicByName(topic);
	int topicId = topToRemove.getTopicId();
	topToRemove.removeTopic(this.getUserId());
	
	System.out.println("Topic with topic id : " + topicId + " has been removed");
//	sc.close();
}

public void getAllTopics() {
	Database db = new Database();
	Connection con = db.getConnection(false);
	if(con == null) {
		System.out.println("Failed to establish the connection");
		System.exit(0);
	}
	
	try {	
		Statement statement = con.createStatement();
        ResultSet result = null;
        result = statement.executeQuery("SELECT topic_id, topic_name FROM topic WHERE is_active = 1");
        System.out.println("TopicId\tTopicName");
        while (result.next()) {
            String audienceName = result.getString("topic_name");
            int audienceId = result.getInt("topic_id");
            System.out.println(audienceId + "\t" + audienceName);
        }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	finally {
        db.closeConnection(con);
	}
}

public void addPeriodicity(Scanner sc) {
//	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the name of the periodicity you would like to add : ");
	String perName = sc.nextLine();
	System.out.println("the periodicity :" + perName + " will be added, would you like to proceed (y/n)");
	String confirmation = sc.nextLine();
	Periodicity existingPer = Periodicity.getPeriodicityByName(perName);
	if(existingPer.getPeriodicityId() > 0) {
		System.out.println("periodicity already exists");
		return;
	}
	if(confirmation.equals("y")) {
		Periodicity curPer = new Periodicity(perName, this.getUserId());
		System.out.println("Periodicity has been added with id : " + curPer.getPeriodicityId());
	}
	else if(confirmation.equals("n")) {
		System.out.println("Periodicity has not been added");
	}
	else {
		System.out.println("Wrong confirmation, periodicity has not been added");
	}
//	sc.close();
}

public void getPeriodicityIdByName(Scanner sc) {
//	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the name of the periodicity you would like to get : ");
	String per = sc.nextLine();
	Periodicity curPer = Periodicity.getPeriodicityByName(per);
	if(curPer.getPeriodicityId() > 0) {
		System.out.println("Periodicity id for topic : " + per + " is : " + curPer.getPeriodicityId());
	}
	else {
		System.out.println("Periodicity with name : " + per + " not found.");
	}
//	sc.close();
}

public void removePeriodicity(Scanner sc) {
//	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the name of the periodicity you would like to remove : ");
	String per = sc.nextLine();
	//sc.nextLine();
	Periodicity perToRemove = Periodicity.getPeriodicityByName(per);
	int perId = perToRemove.getPeriodicityId();
	perToRemove.removePeriodicity(this.getUserId());
	
	System.out.println("Topic with topic id : " + perId + " has been removed");
//	sc.close();
}

public void getAllPeriodicities() {
	Database db = new Database();
	Connection con = db.getConnection(false);
	if(con == null) {
		System.out.println("Failed to establish the connection");
		System.exit(0);
	}
	
	try {	
		Statement statement = con.createStatement();
        ResultSet result = null;
        result = statement.executeQuery("SELECT periodicity_id, periodicity_name FROM periodicity WHERE is_active = 1");
        System.out.println("PeriodicityId\tPeriodicityName");
        while (result.next()) {
            String audienceName = result.getString("periodicity_name");
            int audienceId = result.getInt("periodicity_id");
            System.out.println(audienceId + "\t" + audienceName);
        }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	finally {
        db.closeConnection(con);
	}
}

public void addPeriodicIssue(Scanner sc) {
//	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the name of the periodic issue you would like to add : ");
	String issueName = sc.nextLine();
	
	getAllPublications();
	System.out.println("Enter the id of the publication you would like to associate this periodic issue to ");
	int pubId = sc.nextInt();
	sc.nextLine();
	Publication pub = Publication.getPublicationById(pubId);
	if (pub.getPublicationTitle() == null) {
		System.out.println("Publication doesnt exist, do create a publication");
		return ;
	}
	
	getAllPeriodicities();
	System.out.println("Enter the name of the periodicity you would like to associate this periodic issue to ");
	String issuePer = sc.nextLine();
	Periodicity p = Periodicity.getPeriodicityByName(issuePer);
	if (p.getPeriodicityId() == 0) {
		System.out.println("the periodicity :" + issuePer + " will be added to the periodicity, would you like to proceed (y/n)");
		String con = sc.nextLine();
		if(con.equals("y")) 
		{
			Periodicity curPer = new Periodicity(issuePer, this.getUserId());
			System.out.println("Periodicity has been added with id : " + curPer.getPeriodicityId());
		}
		else {
			System.out.println("Wrong confirmation, periodicity has not been added");
			return;
		}
	}
	
	getAllCategories();
	System.out.println("Enter the name of the category you would like to associate this periodic issue to ");
	String issueCat = sc.nextLine();
	Category c = Category.getCategoryByName(issueCat);
	if (c.getCategoryId() == 0) {
		System.out.println("the category :" + issueCat + " will be added to the category, would you like to proceed (y/n)");
		String conn = sc.nextLine();
		if(conn.equals("y")) 
		{
			Category curCat = new Category(issueCat, this.getUserId());
			System.out.println("Category has been added with id : " + curCat.getCategoryId());
		}
		else {
			System.out.println("Wrong confirmation, category has not been added");
			return;
		}
	}
	
	
	System.out.println("the periodic issue :" + issueName + " will be added, would you like to proceed (y/n)");
	String confirmation = sc.nextLine();
	//Periodic_issue existingIss = Periodic_issue.getPeriodicISsueByName(issueName);
	//if(existingCat.getCategoryId() > 0) 
	//{
		//System.out.println("category already exists");
		//return;
	//}
	if(confirmation.equals("y")) 
	{
		Periodic_issue curIss = new Periodic_issue(issueName, pubId, issuePer, issueCat, this.getUserId());
		System.out.println("Periodic issue has been added with id : " + curIss.getIssueID());
	}
	else if(confirmation.equals("n")) 
	{
		System.out.println("Periodic issue has not been added");
	}
	else {
		System.out.println("Wrong confirmation, category has not been added");
	}
//	sc.close();
}

public void getPeriodicIssueById(Scanner sc) {
//	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the id of the periodic issue you would like to get : ");
	int issId = sc.nextInt();
	sc.nextLine();
	Periodic_issue curIss = Periodic_issue.getPeriodicIssueById(issId);
	if(curIss.getIssueTitle() != null) {
		System.out.println("Periodic Issue Id  : " + curIss.getIssueID() );
		System.out.println("Periodic Issue title  : " + curIss.getIssueTitle() );
		System.out.println("Periodic Issue publication title  : " + curIss.getIssuePublication().getPublicationTitle() );
		System.out.println("Periodic Issue category  : " + curIss.getIssueCategory().getCategoryName() );
		System.out.println("Periodic Issue periodicity  : " + curIss.getIssuePeriodicity().getPeriodicityName() );
	}
	else {
		System.out.println("periodic issue with issue id : " + issId + " not found.");
	}
//	sc.close();
}

public void removePeriodicIssue(Scanner sc) {
//	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the id of the periodic issue you would like to remove : ");
	int issueId = sc.nextInt();
	sc.nextLine();
	Periodic_issue issToRemove = Periodic_issue.getPeriodicIssueById(issueId);
	issToRemove.removePeriodicIssue(this.getUserId());
	System.out.println("Position with position id : " + issueId + " has been removed");
//	sc.close();
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

public void updatePeriodicIssue(Scanner sc) {
	System.out.println("Select the id of the periodic issue you would like to update : ");
	getAllPeriodicIssues();
	int issId = sc.nextInt();
	sc.nextLine();
	if (issId == 0) {
		System.out.println("The periodic issue doesnt exist");
		return;
	}
	Periodic_issue iss = Periodic_issue.getPeriodicIssueById(issId);
	String title = iss.getIssueTitle();
	Publication issuePub = iss.getIssuePublication();
	Category issueCat = iss.getIssueCategory();
	Periodicity issuePer = iss.getIssuePeriodicity();
	
	System.out.println("would you like to update the title for the periodic issue? (y/n)");
	String decision = sc.nextLine();
	if(decision.equals("y")) {
		System.out.println("Enter the new title for the periodic issue");
		title = sc.nextLine();
	}
	else {
		System.out.println("Title of the periodic issue will not be updated");
	}
	
	
	System.out.println("would you like to update the publication associated with the periodic issue? (y/n)");
	decision = sc.nextLine();
	if(decision.equals("y")) {
		getAllPublications();
		System.out.println("Enter the new publication ID for the periodic issue");
		int pubID = sc.nextInt();
		sc.nextLine();
		issuePub = Publication.getPublicationById(pubID);
		}
	else {
		System.out.println("Publication associated with this periodic issue will not be updated");
	}
	
	System.out.println("would you like to update the category associated with the periodic issue? (y/n)");
	decision = sc.nextLine();
	if(decision.equals("y")) {
		getAllCategories();
		System.out.println("Enter the new category ID for the periodic issue");
		int catID = sc.nextInt();
		sc.nextLine();
		issueCat = Category.getCategoryById(catID);
		}
	else {
		System.out.println("Publication associated with this periodic issue will not be updated");
	}
	
	System.out.println("would you like to update the periodicity associated with the periodic issue? (y/n)");
	decision = sc.nextLine();
	if(decision.equals("y")) {
		getAllPeriodicities();
		System.out.println("Enter the new periodicity name for the periodic issue");
		String perID = sc.nextLine();
		issuePer = Periodicity.getPeriodicityByName(perID);
		}
	else {
		System.out.println("Periodicity associated with this periodic issue will not be updated");
	}
	iss.updatePeriodicIssue(title, issuePub.getPublicationId(), this.getUserId(), issuePer.getPeriodicityName(), issueCat.getCategoryName());
	System.out.println("Periodic Issue  details have been updated ");
	return;
}

	public void assignEditorToPubliation(Scanner sc) {
		System.out.println("Select publication for which you would like to add editor");
		getAllPublications();
		int pubId = sc.nextInt();
		Publication curPub = Publication.getPublicationById(pubId);
		System.out.println("Select employee you would like to assign for editing the publicaiton");
		getAllEditors();
		int empId = sc.nextInt();
		sc.nextLine();
		Employee curEmp = Employee.getEmployee(empId);
		if(curPub.getPublicationId() > 0 && curEmp.getEmployeeId() > 0 && curEmp.getEmpPosition().getPositionName().equals("editor")) {
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			try {
				CallableStatement cstmt = con.prepareCall("{call sp_add_editor_to_publication(?, ?, ?, ? )}");
				cstmt.setInt(1, empId);
				cstmt.setInt(2, pubId);
				cstmt.setInt(3, this.getUserId());
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.executeUpdate();
				String err = cstmt.getString(4);
				if(err != "NULL") {
					System.out.println(err);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        db.closeConnection(con);
			}
		}
		else if(curPub.getPublicationId() < 0) {
			System.out.println("Wrong publicaiton selection");
		}
		else if(curEmp.getEmployeeId() < 0 || ! curEmp.getEmpPosition().getPositionName().equals("editor")) {
			System.out.println("Wrong editor selection");
		}
		else {
			System.out.println("Failed to assign editor to publication");
		}
	}
	
	public void removeEditorFromPublication(Scanner sc) {
		System.out.println("Select publication for which you would like to remove editor");
		getAllPublications();
		int pubId = sc.nextInt();
		Publication curPub = Publication.getPublicationById(pubId);
		System.out.println("Select employee you would like to remove from editing the publicaiton");
		getAllEditors();
		int empId = sc.nextInt();
		sc.nextLine();
		Employee curEmp = Employee.getEmployee(empId);
		if(curPub.getPublicationId() > 0 && curEmp.getEmployeeId() > 0 && curEmp.getEmpPosition().getPositionName().equals("editor")) {
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			try {
				CallableStatement cstmt = con.prepareCall("{call sp_remove_editor_from_publication(?, ?, ?, ? )}");
				cstmt.setInt(1, empId);
				cstmt.setInt(2, pubId);
				cstmt.setInt(3, this.getUserId());
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.executeUpdate();
				String err = cstmt.getString(4);
				if(err != "NULL") {
					System.out.println(err);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        db.closeConnection(con);
			}
		}
		else if(curPub.getPublicationId() < 0) {
			System.out.println("Wrong publicaiton selection");
		}
		else if(curEmp.getEmployeeId() < 0 || !curEmp.getEmpPosition().getPositionName().equals("editor")) {
			System.out.println("Wrong editor selection");
		}
		else {
			System.out.println("Failed to remove editor from publication");
		}
	}
	
	public void assignAuthorToBook(Scanner sc) {
		System.out.println("Select book for which you would like to add author");
		getAllBooks();
		int isbn = sc.nextInt();
		Book curBook = Book.getBookByIsbn(isbn);
		System.out.println("Select employee you would like to assign for authoring the book");
		getAllAuthors();
		int empId = sc.nextInt();
		sc.nextLine();
		Employee curEmp = Employee.getEmployee(empId);
		if(curBook.getIsbn() > 0 && curEmp.getEmployeeId() > 0 && curEmp.getEmpPosition().getPositionName().equals("author")) {
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			try {
				CallableStatement cstmt = con.prepareCall("{call sp_add_author_to_book(?, ?, ?, ? )}");
				cstmt.setInt(1, empId);
				cstmt.setInt(2, isbn);
				cstmt.setInt(3, this.getUserId());
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.executeUpdate();
				String err = cstmt.getString(4);
				if(err != "NULL") {
					System.out.println(err);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        db.closeConnection(con);
			}
		}
		else if(curBook.getIsbn() < 0) {
			System.out.println("Wrong publicaiton selection");
		}
		else if(curEmp.getEmployeeId() < 0 || !curEmp.getEmpPosition().getPositionName().equals("author")) {
			System.out.println("Wrong author selection");
		}
		else {
			System.out.println("Failed to assign author to book");
		}
	}
	
	public void removeAuthorFromBook(Scanner sc) {
		System.out.println("Select book for which you would like to remove author");
		getAllBooks();
		int isbn = sc.nextInt();
		Book curBook = Book.getBookByIsbn(isbn);
		System.out.println("Select employee you would like to remove from authoring the book");
		getAllAuthors();
		int empId = sc.nextInt();
		sc.nextLine();
		Employee curEmp = Employee.getEmployee(empId);
		if(curBook.getIsbn() > 0 && curEmp.getEmployeeId() > 0 && curEmp.getEmpPosition().getPositionName().equals("author")) {
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			try {
				CallableStatement cstmt = con.prepareCall("{call sp_remove_author_from_book(?, ?, ?, ? )}");
				cstmt.setInt(1, empId);
				cstmt.setInt(2, isbn);
				cstmt.setInt(3, this.getUserId());
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.executeUpdate();
				String err = cstmt.getString(4);
				if(err != "NULL") {
					System.out.println(err);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        db.closeConnection(con);
			}
		}
		else if(curBook.getIsbn() < 0) {
			System.out.println("Wrong publicaiton selection");
		}
		else if(curEmp.getEmployeeId() < 0 || !curEmp.getEmpPosition().getPositionName().equals("author")) {
			System.out.println("Wrong author selection");
		}
		else {
			System.out.println("Failed to remove author from book");
		}
	}
	
	public void assignAuthorToArticle(Scanner sc) {
		System.out.println("Select article for which you would like to add author");
		getAllArticles();
		System.out.println("Enter the issue id for article");
		int issueId = sc.nextInt();
		System.out.println("Enter the article number for article");
		int articleNo = sc.nextInt();
		Article curArticle = Article.getArticle(issueId, articleNo, this.getUserId());
		System.out.println("Select employee you would like to assign for authoring the article");
		getAllAuthors();
		int empId = sc.nextInt();
		sc.nextLine();
		Employee curEmp = Employee.getEmployee(empId);
		if(curArticle.getArticleNumer() > 0 && curEmp.getEmployeeId() > 0 && curEmp.getEmpPosition().getPositionName().equals("author")) {
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			try {
				CallableStatement cstmt = con.prepareCall("{call sp_add_author_to_article(?, ?, ?, ?, ? )}");
				cstmt.setInt(1, empId);
				cstmt.setInt(2, articleNo);
				cstmt.setInt(3, issueId);
				cstmt.setInt(4, this.getUserId());
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.executeUpdate();
				String err = cstmt.getString(5);
				if(err != "NULL") {
					System.out.println(err);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        db.closeConnection(con);
			}
		}
		else if(curArticle.getArticleNumer() < 0) {
			System.out.println("Wrong publicaiton selection");
		}
		else if(curEmp.getEmployeeId() < 0 || !curEmp.getEmpPosition().getPositionName().equals("author")) {
			System.out.println("Wrong author selection");
		}
		else {
			System.out.println("Failed to assign author to article");
		}
	}
	
	public void removeAuthorFromArticle(Scanner sc) {
		System.out.println("Select article for which you would like to remove author");
		getAllArticles();
		System.out.println("Enter the issue id for article");
		int issueId = sc.nextInt();
		System.out.println("Enter the article number for article");
		int articleNo = sc.nextInt();
		Article curArticle = Article.getArticle(issueId, articleNo, this.getUserId());
		System.out.println("Select employee you would like to remove from authoring the article");
		getAllAuthors();
		int empId = sc.nextInt();
		sc.nextLine();
		Employee curEmp = Employee.getEmployee(empId);
		if(curArticle.getArticleNumer() > 0 && curEmp.getEmployeeId() > 0 && curEmp.getEmpPosition().getPositionName().equals("author")) {
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			try {
				CallableStatement cstmt = con.prepareCall("{call sp_remove_author_from_article(?, ?, ?, ?, ? )}");
				cstmt.setInt(1, empId);
				cstmt.setInt(2, articleNo);
				cstmt.setInt(3, issueId);
				cstmt.setInt(4, this.getUserId());
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.executeUpdate();
				String err = cstmt.getString(5);
				if(err != "NULL") {
					System.out.println(err);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        db.closeConnection(con);
			}
		}
		else if(curArticle.getArticleNumer() < 0) {
			System.out.println("Wrong publicaiton selection");
		}
		else if(curEmp.getEmployeeId() < 0 || !curEmp.getEmpPosition().getPositionName().equals("author")) {
			System.out.println("Wrong author selection");
		}
		else {
			System.out.println("Failed to assign author to article");
		}
	}
	
	public void payAllSalary(Scanner sc) {
		System.out.println("Enter the comment for salary payment");
		String comment = sc.nextLine();
		System.out.println("Are you sure you would like to pay all the employees? (y/n)");
		String confirmation = sc.nextLine();
		if(confirmation == "y") {
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			try {
				CallableStatement cstmt = con.prepareCall("{call sp_pay_employees(?, ?)}");
				cstmt.setString(1, comment);
				cstmt.registerOutParameter(2, Types.VARCHAR);
				cstmt.executeUpdate();
				String err = cstmt.getString(2);
				if(err != "NULL") {
					System.out.println(err);
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
	
	public void payEmployee(Scanner sc) {
		System.out.println("Select the employee you would like to pay");
		getAllEmployees();
		int empId = sc.nextInt();
		Employee curEmp = Employee.getEmployee(empId);
		if(curEmp.getEmployeeId() > 0) {
			System.out.println("Enter the amount you would like to pay this employee");
			int amount = sc.nextInt();
			System.out.println("Enter comment for payment");
			String comment = sc.nextLine();
			Database db = new Database();
			Connection con = db.getConnection(false);
			if(con == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			
			try {	
				Statement statement = con.createStatement();
		        ResultSet result = null;
		        result = statement.executeQuery("INSERT INTO payment_history(employee_id, amount, comment, created_by) VALUES (" + empId + ", " + amount + ", " + comment + "," + this.getUserId() + ")");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			finally {
		        db.closeConnection(con);
			}
		}
		else {
			System.out.println("Employee not found");
		}
	}
	public void orderActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: View Orders.");
			System.out.println("2: Cancel Order.");
			System.out.println("3: Update Order.");
			
			System.out.println("exit: Exit from position");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				getAllOrder();
				break;
			case "2":
				cancelOrder(sc);
				break;
			case "3":
				updateOrder(sc);
				break;
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			
			}
		}
		
	}
	
	public void cancelOrder(Scanner sc) {
		
		this.getAllOrder();
		System.out.println("Select the id of the order you would like cancel: ");
		int orderId = Integer.parseInt(sc.nextLine());
		
		Database db = new Database();
		System.out.println("Select the id of the distributor you would like cancel: ");
		int distributor_id = Integer.parseInt(sc.nextLine());
		Connection con = db.getConnection(false);
		System.out.println("Are you sure you want to cancel the order with orderID "+orderId+ "(y/n)");
		
		String confirmation = sc.nextLine();
		if  (confirmation.equals("y")) {
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			CallableStatement cstmt = con.prepareCall("{call sp_remove_order(?, ?, ?)}");
			cstmt.setInt(1, orderId);
			cstmt.setInt(2, distributor_id);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.execute();
			String errMsg = cstmt.getString(3);
			if (errMsg != null){
				System.out.println(errMsg);
				return;
			}	
			System.out.println("The order with order Id "+ orderId+ " is removed succefully");
			
			
		}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			finally {
				db.closeConnection(con);
			}
		}
		else if (confirmation.equals("n")) {
			System.out.println("Order has not been added");
			
		}
		else {
			System.out.println("Wrong confirmation, order has not been added");
		}
	}
	
	public void getAllOrder() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
		Statement statement = con.createStatement();
		ResultSet result = null;
		result = statement.executeQuery("SELECT `order`.distributor_id, order_id, publication_title, shipping_cost, number_of_copies FROM `order` INNER JOIN distributor ON `order`.distributor_id = distributor.distributor_id INNER JOIN publication ON publication.publication_id = `order`.publication_id WHERE`order`.is_active =1;");
		int number =1;
		while (result.next()) {
			int orderID = result.getInt("order_id");
			String publicationTitle =  result.getString("publication_title");
			int shippingCost= result.getInt("shipping_cost");
			int numberOfCoppies = result.getInt("number_of_copies");
			int price = Publication.getPublicationById(Publication.getPublicationIdByTitle(publicationTitle)).getPrice();
			int distributorID = result.getInt("distributor_id");
			int totalPrice = price * numberOfCoppies;
			System.out.println("***********************");
			System.out.println("Order ID: "+ orderID);
			System.out.println("Distributor ID: "+ distributorID);
			System.out.println("Publication Title: "+ publicationTitle);
			System.out.println("Shipping Cost: "+ shippingCost);
			System.out.println("Number of Copies: "+ numberOfCoppies);
			System.out.println("Price: "+price);
			System.out.println("Total Price: "+  totalPrice);
			number +=1;
		}
		}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			finally {
				db.closeConnection(con);
			}
		
	}
public void updateOrder(Scanner sc) {
		
		this.getAllOrder();
		System.out.println("Select the order id: ");
		int orderId = Integer.parseInt(sc.nextLine());
		
		Database db = new Database();
		Connection con = db.getConnection(false);
		String distributorName =null;
		String publicationName= null;
		int price = 0;
		int shippingCost= -1;
		int numberOfCoppies=-1;
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			CallableStatement cstmt = con.prepareCall("{call sp_get_order_by_id(?, ?, ?, ?, ?, ?)}");
			cstmt.setInt(1,  orderId);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.registerOutParameter(4, Types.INTEGER);
			cstmt.registerOutParameter(5, Types.INTEGER);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.execute();
			String errMsg = cstmt.getString(6);
			
			if (errMsg != null){
				System.out.println("Order with the given ID does not exits.");
				return;
			}	
			distributorName = cstmt.getString(2);
			publicationName = cstmt.getString(3);
			shippingCost = cstmt.getInt(4);
			numberOfCoppies = cstmt.getInt(5);
			price= Publication.getPublicationById(Publication.getPublicationIdByTitle(publicationName)).getPrice();
			
			
		}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			finally {
				db.closeConnection(con);
			}

		
		loop: while(true) {
			System.out.println("Please select what you would like to update");
			System.out.println("1: Publication Name.");
			System.out.println("2: Shipping Cost.");
			System.out.println("3: Number of Copies.");
			System.out.println("4: Enter the name of distributor.");
			System.out.println("done: If you are done with update");
			
			
			String decision = sc.nextLine();
			switch(decision) {
			case  "1":
				
				this.getAllPublications();
				System.out.println("Enter the ID of the publication: ");
				int newPubID = Integer.parseInt(sc.nextLine());
				publicationName = Publication.getPublicationById(newPubID).getPublicationTitle();
				price = Publication.getPublicationById(newPubID).getPrice();
				break;
			case "2":
				System.out.println("Enter the Cost of shipping: ");
				shippingCost = Integer.parseInt(sc.nextLine());
				break;
			case "3":
				System.out.println("Enter the number of copies: ");
				numberOfCoppies= Integer.parseInt(sc.nextLine());
				break;
			case "4":
				this.getAllDistributor();
				System.out.println("Enter the ID of the distributor: ");
				int disId = Integer.parseInt(sc.nextLine());
				distributorName = Distributor.getDistributorByID(disId).getDistributorName();
				break;
			case "done":
				break loop;
			
			}
		}
		int totalPrice = price * numberOfCoppies;
		System.out.println("Review the update for order #"+ orderId);
		System.out.println("Publication title: "+publicationName);
		System.out.println("Publication shipping cost: "+shippingCost);
		System.out.println("Number of coppies: "+ numberOfCoppies);
		System.out.println("Price: "+price);
		System.out.println("Total Price: "+  totalPrice);
		System.out.println("Would you like to proceed with update (y/n)");
		String confirmation = sc.nextLine();
		
		if(confirmation.equals("y")) {
			Connection newcon = db.getConnection(false);
			if(newcon == null) {
				System.out.println("Failed to establish the connection");
				System.exit(0);
			}
			
			try {	
				
				CallableStatement cstmt = newcon.prepareCall("{call sp_update_order(?, ?, ?, ?, ?, ?, ?)}");
				cstmt.setInt(1,  orderId);
				cstmt.setString(2, distributorName );
				cstmt.setString(3, publicationName);
				cstmt.setInt(4, numberOfCoppies);
				cstmt.setInt(5, shippingCost);
				cstmt.setInt(6, this.getUserId());
				cstmt.registerOutParameter(7, Types.VARCHAR);
				cstmt.execute();
				String errMsg = cstmt.getString(7);
				
				if (errMsg != null){
					System.out.println(errMsg);
					return;
				}	
				
				System.out.println("the order has been successfully updated");
				
			}
			 catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				finally {
					db.closeConnection(newcon);
				}
			
			
			
		}
		else if(confirmation.equals("n")) {
			System.out.println("Order is not updated");
		}
		else {
			System.out.println("Wrong confirmation, order has not been updated");
		}
		
	}
	public void paymentActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the action you would like to perform");
			System.out.println("1: View Payment History.");
			
			
			System.out.println("exit: Exit from position");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				viewPaymentHistory();
				break;
			
			case "exit":
				break loop;
			default:
				System.out.println("Wrong selection");
			
			}
		}
		
	}
	public void viewPaymentHistory() {
		System.out.println("The payment done by the distributors till now.");
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
		Statement statement = con.createStatement();
		ResultSet result = null;
		result = statement.executeQuery("SELECT payment_id, received_payment.distributor_id, amount, comment FROM received_payment INNER JOIN distributor ON received_payment.distributor_id = distributor.distributor_id WHERE received_payment.is_active =1;");
		int number =1;
		while (result.next()) {
			int paymentId= result.getInt("payment_id");
			int distributorId =  result.getInt("distributor_id");
			int amount= result.getInt("amount");
			String comment = result.getString("comment");
			System.out.println("***********************");
			System.out.println("Payment ID: "+ paymentId);
			System.out.println("Distributor ID: "+ distributorId);
			System.out.println("Amount: "+ amount);
			System.out.println("Comment: "+ comment);
			System.out.println("***********************");
			number +=1;
		}
		}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			finally {
				db.closeConnection(con);
			}
	}
}
 
