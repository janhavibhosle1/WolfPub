package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class Chapter {
	
	private int chapterNo;
	private String chapterName;
	private Book chapterBook;

	
	public Chapter(int number, String name, int isbn, int userId )
	{
		this.setChapterNumber(number);
		this.setChapterName(name);
		
		Book boo = Book.getBookByIsbn(isbn);
		this.setChapterBook(boo);		
		
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_chapter_to_book(?, ?, ?, ?, ? )}");
			cstmt.setInt(1, isbn);
			cstmt.setInt(2, chapterNo);
			cstmt.setString(3, chapterName);
			cstmt.setInt(4, userId);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.execute();
			System.out.println("chapter for book " + isbn + "added");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		
	}
	
	public Chapter() {
		this.chapterNo = -1;
		this.chapterName = null;
	}
	
	public static Chapter getChapter(int chapter_no, int isbn) {
		Chapter c = new Chapter();
		c.setChapterNumber(chapter_no);
		Book b = Book.getBookByIsbn(isbn);
		c.setChapterBook(b);
		
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_chapter_by_chapter_number(?, ?, ?, ? )}");
			cstmt.setInt(1, chapter_no);
			cstmt.setInt(2, isbn);
			cstmt.registerOutParameter(3, Types.VARCHAR); //chapter_name
			cstmt.registerOutParameter(4, Types.VARCHAR); //publicationtitle

			cstmt.executeUpdate();
			c.setChapterName(cstmt.getString(3));
			

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return c;
	}

	
	public void removeChapter(int userId) {
		int bookisbn = this.getChapterBook().getIsbn();
		int no = this.getChapterNumber();
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_remove_chapter(?, ?, ? , ?)}");
			cstmt.setInt(1, bookisbn);
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
	
	public void updateChapter(String chapter_name, int userId) {
		
		this.setChapterName(chapter_name);
		int isbn = this.getChapterBook().getIsbn();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_update_chapter(?, ?, ?, ?)}");
			cstmt.setInt(1, this.getChapterNumber());
			cstmt.setInt(2, isbn);
			cstmt.setString(3, chapter_name);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.executeUpdate();
			String err_msg = cstmt.getString(4);
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

	
	public void setChapterNumber(int number) {
		this.chapterNo = number;
	}
	
	public int getChapterNumber() {
		return chapterNo;
	}
	
	public String getChapterName() {
		return chapterName;
	}
	
	public void setChapterName(String name) {
		this.chapterName = name;
	}
	
	public void setChapterBook(Book bo) {
		this.chapterBook = bo;
	}
	
	public Book getChapterBook() {
		return chapterBook;
	}
	

}