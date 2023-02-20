package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

public class DistributorAsUser {
	private Distributor loggedInUser;
	
	public DistributorAsUser(int distributorID, Scanner sc) {
		
		this.loggedInUser = Distributor.getDistributorByID(distributorID);
		loop: while(true) {
			System.out.println("Please select an entity you would like to deal with");
			System.out.println("1: Order");
			System.out.println("2: Payment");
			System.out.println("exit: Exit the program");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				this.orderActions(sc);
				break;
			
			case "2":
				this.paymentActions(sc);
				break;
			case "exit":
				break loop;
			
			default:
				System.out.println("wrong selection");
			}
		}
	}
	public void orderActions(Scanner sc) {
		loop: while(true) {
			System.out.println("Please select the actions you would like to perform");
			System.out.println("1: Add Order");
			System.out.println("2: Review Order");
			System.out.println("3: Edit Order");
			System.out.println("4: Remove Order");
			System.out.println("5: Get All order");
			System.out.println("exit: Exit the program");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				this.addOrder(sc);
				break;
			case "2":
				this.getOrderByID(sc);
				break;
			case "3":
				this.updateOrder(sc);
				break;
			case "4":
				this.removeOrder(sc);
				break;
			case "5":
				this.getAllOrder();
				break;
			case "exit":
				break loop;
			default:
				System.out.println("wrong selection");
			}
		}
	}
	
	
	public void addOrder(Scanner sc) {
		System.out.println("Choose the publictaion ID you would like the order");
		this.choosePublication();
		int publicationId = Integer.parseInt(sc.nextLine());
		String publicationTitle = Publication.getPublicationById(publicationId).getPublicationTitle();
		System.out.println("Number of coppies needed:");
		int numberOfCoppies = Integer.parseInt(sc.nextLine());
		System.out.println("Add Shipping cost:");
		int shippingCost = Integer.parseInt(sc.nextLine());
		System.out.println(" The order for the publication : " + publicationTitle + "\n With number of copies: " +numberOfCoppies+ "\n shipping cost: "+shippingCost+ "\n will be placed, would you like to proceed (y/n)");
		int userID = this.loggedInUser.getDistributorID();
		Database db = new Database();
		Connection con = db.getConnection(false);
		String confirmation = sc.nextLine();
		if (confirmation.equals("n")) {
			System.out.println("Order has not been added");
			
		}
		else if (confirmation.equals("y")) {
		
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
			Statement statement = con.createStatement();
			ResultSet result = null;
			CallableStatement cstmt = con.prepareCall("{call sp_add_order(?, ?, ?, ?, ?, ?)}");
			cstmt.setString(1, this.loggedInUser.getDistributorName() );
			cstmt.setString(2, publicationTitle);
			cstmt.setInt(3, numberOfCoppies);
			cstmt.setInt(4, shippingCost);
			cstmt.setInt(5, userID);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.execute();
			String errMsg = cstmt.getString(6);
			
			if (errMsg != null){
				System.out.println(errMsg);
			}
			System.out.println("Order has been placed");
			int price = Publication.getPublicationById(publicationId).getPrice();
			this.loggedInUser.setBalance(this.loggedInUser.getBalance()+ price* numberOfCoppies);
		}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			finally {
				db.closeConnection(con);
			}
		}
		else {
			System.out.println("Wrong confirmation, Order has not been placed");
		}
	}
	public void updateOrder(Scanner sc) {
		
		this.getAllOrder();
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
			System.out.println("done: If you are done with update");
			
			
			String decision = sc.nextLine();
			switch(decision) {
			case  "1":
				
				this.choosePublication();
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
				cstmt.setInt(6, this.loggedInUser.getDistributorID());
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
			System.out.println("Distributor has not been added");
		}
		else {
			System.out.println("Wrong confirmation, Distributor has not been added");
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
		result = statement.executeQuery("SELECT order_id, publication_title, shipping_cost, number_of_copies FROM `order` INNER JOIN distributor ON `order`.distributor_id = distributor.distributor_id INNER JOIN publication ON publication.publication_id = `order`.publication_id WHERE distributor_name LIKE '"+this.loggedInUser.getDistributorName()+"' And `order`.is_active =1;");
		int number =1;
		while (result.next()) {
			int orderID = result.getInt("order_id");
			String publicationTitle =  result.getString("publication_title");
			int shippingCost= result.getInt("shipping_cost");
			int numberOfCoppies = result.getInt("number_of_copies");
			int price = Publication.getPublicationById(Publication.getPublicationIdByTitle(publicationTitle)).getPrice();
			int totalPrice = price * numberOfCoppies;
			System.out.println("***********************");
			System.out.println("Order ID "+ orderID);
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
	public void choosePublication() {
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		
		try {	
		Statement statement = con.createStatement();
		ResultSet result = null;
		result = statement.executeQuery("SELECT publication_id, publication_title, price, topic_name FROM publication INNER JOIN topic ON publication.topic_id = topic.topic_id WHERE publication.is_active = 1");
		int number =1;
		while (result.next()) {
			int publicationID = result.getInt("publication_id");
			
			String publicationTitle =  result.getString("publication_title");
			
			int price = result.getInt("price");
			String topicName = result.getString("topic_name"); 
			System.out.println("***********************");
			System.out.println("Publication number "+ number);
			System.out.println("Publication id: "+ publicationID);
			System.out.println("Publication Title: "+ publicationTitle);
			System.out.println("Publication price: "+ price);
			System.out.println("Publication topic_name: "+ topicName);
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
	public void getOrderByID(Scanner sc) {
		System.out.println("Select the id of the order you would like review : ");
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
			price = Publication.getPublicationById(Publication.getPublicationIdByTitle(publicationName)).getPrice();
			int totalPrice = price * numberOfCoppies;
			System.out.println("***********************");
			System.out.println("Order ID "+ orderId);
			System.out.println("Publication Title: "+ publicationName);
			System.out.println("Shipping Cost: "+ shippingCost);
			System.out.println("Number of Copies: "+ numberOfCoppies);
			
			System.out.println("Price: "+price);
			System.out.println("Total Price: "+  totalPrice);
			
			
			
		}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			finally {
				db.closeConnection(con);
			}

		
	}
	
	public void removeOrder(Scanner sc) {
		System.out.println("Select the id of the order you would like cancel : ");
		this.getAllOrder();
		int orderId = Integer.parseInt(sc.nextLine());
		Database db = new Database();
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
			cstmt.setInt(2, this.loggedInUser.getDistributorID());
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
			System.out.println("Wrong confirmation, Distributor has not been removed");
		}

	}
	
public void paymentActions(Scanner sc) {
	loop: while(true) {
		
		System.out.println("The outstanding balance is: "+this.loggedInUser.getBalance());
		System.out.println("Please select the actions you would like to perform");
		System.out.println("1: Make Payment");
		System.out.println("2: View All payment");

		System.out.println("exit: Exit the program");
		String decision = sc.nextLine();
		switch(decision) {
		case "1":
			this.makePayment(sc);
			break;
		case "2":
			this.viewPaymentHistory();
			break;
		case "exit":
			break loop;
		
		default:
			System.out.println("wrong selection");
		}
		}
	}
public void makePayment(Scanner sc) {

	System.out.println("How much you want to pay: ");
	int payment = Integer.parseInt(sc.nextLine());
	if (payment > this.loggedInUser.getBalance()) {
		System.out.print("Please enter a valid amount.");
	}
	System.out.println("Do you want to make payment of "+ payment+ "(y/n)");
	Database db = new Database();
	Connection con = db.getConnection(false);
	String confirmation = sc.nextLine();
	if  (confirmation.equals("y")) {
	if(con == null) {
		System.out.println("Failed to establish the connection");
		System.exit(0);
	}
	try {	
		CallableStatement cstmt = con.prepareCall("{call sp_record_payment_from_distributor(?, ?, ?)}");
		cstmt.setInt(1, this.loggedInUser.getDistributorID());
		cstmt.setInt(2, payment);
		cstmt.registerOutParameter(3, Types.VARCHAR);
		cstmt.execute();
		String errMsg =cstmt.getString(3);
		if (errMsg != null){
			System.out.println(errMsg);
			return;
		}	
		System.out.println("Payment made successfully");
		
	
	this.loggedInUser.setBalance(this.loggedInUser.getBalance()-payment);
	
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
	System.out.println("Payment not made yet.");
	}
else {
	System.out.println("Wrong confirmation, Payment has not been made");
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
	result = statement.executeQuery("SELECT payment_id,  amount, comment FROM received_payment INNER JOIN distributor ON received_payment.distributor_id = distributor.distributor_id WHERE received_payment.is_active =1  And received_payment.distributor_id= "+ this.loggedInUser.getDistributorID()+" ;");
	int number =1;
	while (result.next()) {
		int paymentId= result.getInt("payment_id");
		int amount= result.getInt("amount");
		String comment = result.getString("comment");
		System.out.println("***********************");
		System.out.println("Payment ID: "+ paymentId);
		System.out.println("Amount: "+ amount);
		System.out.println("Comment: "+ comment);
		number +=1;
	}
	System.out.println("***********************");
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
