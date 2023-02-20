package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class Book {
	
	private int isbn;
	private String title;
	private int edition;
	private String text;
	private String publication_date;
	private Publication book_publication;
	
	public Book(int isbn, String book_title, int pubid, int edition,String text, String publication_date, int userID) {
		this.setBookTitle(book_title);
		this.setEdition(edition);
		this.setText(text);
		this.setPublicationDate(publication_date);
		Publication pub = Publication.getPublicationById(pubid);
		this.setBookPublication(pub);
		
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_book_to_publication(?, ?, ?, ?, ?, ?, ?, ? )}");
			cstmt.setInt(1, isbn);
			cstmt.setString(2, book_title);
			cstmt.setInt(3, edition);
			cstmt.setInt(4, pubid);
			cstmt.setString(5, text);
			cstmt.setString(6,publication_date);
			cstmt.setInt(7, userID);
			cstmt.registerOutParameter(8, Types.VARCHAR);
			cstmt.execute();
			System.out.println("book added"  );
			
			this.setIsbn(isbn);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		
	}
	
	public Book() {
		this.isbn = -1;
		this.title = null;
		this.edition = -1;
		this.text = null;
		this.publication_date = null;
		
	}
	
	public static Book getBookByIsbn(int isbn) { //to be discussed
		
		Book bo = new Book();
		Publication pub = new Publication();

		
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_book_by_isbn(?, ?, ?, ?, ?, ?, ? )}");
			cstmt.setInt(1, isbn);
			cstmt.registerOutParameter(2, Types.VARCHAR); //book_title
			cstmt.registerOutParameter(3, Types.INTEGER); //edition
			cstmt.registerOutParameter(4, Types.VARCHAR); //publicationtitle
			cstmt.registerOutParameter(5, Types.VARCHAR); //text
			cstmt.registerOutParameter(6, Types.VARCHAR); //pubdate
			cstmt.registerOutParameter(7, Types.VARCHAR); 
			cstmt.executeUpdate();
			bo.setBookTitle(cstmt.getString(2));
			bo.setIsbn(isbn);
			int id = Publication.getPublicationIdByTitle(cstmt.getString(4));
			pub = Publication.getPublicationById(id);
			bo.setBookPublication(pub);
			bo.setEdition(cstmt.getInt(3));
			bo.setText(cstmt.getString(5));
			bo.setPublicationDate(cstmt.getString(6));
			

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return bo;
	
	
	}
	
	public void removeBook(int userId) {
		int bookIsbn = this.getIsbn();
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_remove_book(?, ?, ? )}");
			cstmt.setInt(1, bookIsbn);
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
	
	public void updateBook(String book_title, int publication_id, int edition, String text, String publication_date, int userId) {
		
		
		int isbn = this.getIsbn();
		this.setBookTitle(title);
		Publication pub= Publication.getPublicationById(publication_id);
		this.setBookPublication(pub);
		this.setEdition(edition);
		this.setText(text);
		this.setBookTitle(title);
		this.setPublicationDate(publication_date);
		

		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_update_book(?, ?, ?, ?, ?, ?, ?,? )}");
			cstmt.setInt(1, isbn);
			cstmt.setString(2, title);
			cstmt.setInt(3, edition);
			cstmt.setInt(4, publication_id);
			cstmt.setString(5, text); 
			cstmt.setString(6, publication_date); //?????
			cstmt.setInt(7, userId);
			cstmt.registerOutParameter(8, Types.VARCHAR);
			cstmt.executeUpdate();
			String err_msg = cstmt.getString(8);
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
	
	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}
	
	public void setBookTitle(String book_title) {
		this.title = book_title;
	}
	
	public void setEdition(int edition) {
		this.edition = edition;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setPublicationDate(String publication_date) {
		this.publication_date = publication_date;
	}

	
	public int getIsbn() {
		return isbn;
	}
	public String getBookTitle() {
		return title;
	}
	
	public int getEdition() {
		return edition;
	}
	public String getText() {
		return text;
	}
	public String getPublicationDate() {
		return publication_date;
	}

	public void setBookPublication(Publication pub) {
		this.book_publication = pub;
	}
	
	public Publication getBookPublication() {
		return book_publication;
	}
		
	}