package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class Distributor {
	private String distributorName;
	private String address;
	private String person_of_contact;
	private int balance=0;
	private long phonenumber;
	private City cityobj;
	private DistributorType distributortype;
	private int distributorID =-1;

	public Distributor(String distributorName,DistributorType type, String address,City cityname, long phonenumber, String person_of_contact, int userID) {
		super();
		this.distributorName = distributorName;
		this.address = address;
		this.person_of_contact = person_of_contact;
		this.balance =0;
		this.phonenumber = phonenumber;
		this.cityobj = cityname;
		this.distributortype = type;
		
		
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_distributor(?, ?, ?, ?, ?, ?, ?, ?,?)}");
			cstmt.setString(1, this.distributorName);
			// Can add a break statement if the city is not added into the database.
			cstmt.setString(2, type.getTypeName() );
			cstmt.setString(3, this.address);
			cstmt.setString(4, cityname.getCityname());
			cstmt.setLong(5, this.phonenumber);
			cstmt.setString(6,this.person_of_contact);
			cstmt.setInt(7,userID);
			cstmt.registerOutParameter(8, Types.INTEGER);
			cstmt.registerOutParameter(9, Types.VARCHAR);
			cstmt.execute();
			String addError = cstmt.getString(9);
			this.distributorID = cstmt.getInt(8);
			System.out.println(this.distributorID);
			if (addError != null){
				System.out.println(addError);
			}
			
//			
			
		}
		catch (Exception e){
			System.out.println(e);
		}
		finally {
	        db.closeConnection(con);
		}	
		
		
	}
	public Distributor() {
		
	}
	public static int getDistributorIDbyName(String name) {
		Database db = new Database();
		Connection con = db.getConnection(false);
		int distributorID = -1;
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_distributor_id_by_name(?, ?, ?)}");
			cstmt.setString(1, name);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.execute();
			distributorID =cstmt.getInt(2);
			String getError = cstmt.getString(3);
			if (getError != null){
				System.out.println(getError);
				return -1;
			}
			
		}
		catch (Exception e){
			System.out.println(e);
		}
		finally {
	        db.closeConnection(con);
		}	
		return distributorID;
	}
	
	public static Distributor getDistributorByID(int ID) {
		Database db = new Database();
		Connection con = db.getConnection(false);
		
		Distributor newDistributor = new Distributor();
		
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_distributor_by_id(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			cstmt.setInt(1, ID);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.registerOutParameter(6, Types.INTEGER);
			cstmt.registerOutParameter(7, Types.VARCHAR);
			cstmt.registerOutParameter(8, Types.INTEGER);
			cstmt.registerOutParameter(9, Types.VARCHAR);
			cstmt.execute();
			String getError = cstmt.getString(9);
			if (getError != null){
				System.out.println(getError);
				return null;
			}
			
			newDistributor.distributorID = ID;
			newDistributor.setDistributorName(cstmt.getString(2));
			newDistributor.setDistributortype(DistributorType.getTypeByName(cstmt.getString(3)));
			newDistributor.setAddress(cstmt.getString(4));
			newDistributor.setCityobj(City.getCityByName(cstmt.getString(5)));
			newDistributor.setPhonenumber(cstmt.getInt(6));
			newDistributor.setPerson_of_contact(cstmt.getString(7));
			newDistributor.setBalance(cstmt.getInt(8));
			
			
		}
		catch (Exception e){
			System.out.println(e);
		}
		finally {
	        db.closeConnection(con);
		}	
		return newDistributor;
	}
	
	
	public void removeDistributor( int userID) {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_remove_distributor(?, ?, ?)}");
			cstmt.setInt(1, this.distributorID);
			cstmt.setInt(2, userID);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.execute();
			String removeError = cstmt.getString(3);
			if(removeError != null) {
				System.out.print(removeError);
			}
		}
		catch (Exception e){
			System.out.println(e);
		}
		finally {
	        db.closeConnection(con);
		}	
	}
	
	public static boolean isDistributorPresent(String disName) {
		boolean isPresent = false;
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {	
			Statement statement = con.createStatement();
	        ResultSet result = null;
	        result = statement.executeQuery("SELECT COUNT(1) AS empCount FROM distributor WHERE distributor_name = '" + disName + "' and is_active=1;");
	        int disCount = 0;
	        while (result.next()) {
	            disCount = result.getInt("empCount");
	        }
	        if(disCount > 0) {
	        	isPresent = true;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return isPresent;
	}
	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPerson_of_contact() {
		return person_of_contact;
	}

	public void setPerson_of_contact(String person_of_contact) {
		this.person_of_contact = person_of_contact;
	}

	public int getBalance() {
		return this.balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public long getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(int phonenumber) {
		this.phonenumber = phonenumber;
	}

	public City getCityobj() {
		return cityobj;
	}

	public void setCityobj(City cityobj) {
		this.cityobj = cityobj;
	}

	public DistributorType getDistributortype() {
		return distributortype;
	}

	public void setDistributortype(DistributorType distributortype) {
		this.distributortype = distributortype;
	}

	public void updateDistributor(String distributorName, DistributorType type, String address,City cityName, long phonenumber, String person_of_contact,int Balance, int userID) {
		int disId = this.getDistributorID();
		this.setDistributorName(distributorName);
		
		this.setCityobj(cityName);
	
		this.setDistributortype(type);
		this.setBalance(Balance);
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_update_distributor(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			cstmt.setInt(1, disId);
			cstmt.setString(2, distributorName);
			cstmt.setString(3, type.getTypeName());
			cstmt.setString(4, address);
			cstmt.setString(5, cityName.getCityname());
			cstmt.setLong(6, phonenumber);
			cstmt.setString(7, person_of_contact);
			cstmt.setInt(8, balance);
			cstmt.setInt(9,userID);
			cstmt.registerOutParameter(10, Types.VARCHAR);
			cstmt.executeUpdate();
			String err_msg = cstmt.getString(10);
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
	public int getDistributorID() {
		return distributorID;
	}
	public void setDistributorID(int distributorID) {
		this.distributorID = distributorID;
	}
	
	
	
	
}
