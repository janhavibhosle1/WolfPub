package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class InvitedEmployee extends Employee {
	private int wage;

	public InvitedEmployee(String employeeName, Position empPosition, int wage, int userId) {
		super(employeeName, empPosition, "invited");
		this.setWage(wage);
		
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_invited_employee(?, ?, ?, ?, ?, ? )}");
			cstmt.setString(1, employeeName);
			cstmt.setString(2, empPosition.getPositionName());
			cstmt.setInt(3, wage);
			cstmt.setInt(4, userId);
			cstmt.registerOutParameter(5, Types.INTEGER);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.executeUpdate();
			this.setEmployeeId(cstmt.getInt(5));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
	}
	
	public InvitedEmployee() {
		super();
		this.wage = -1;
	}

	public static InvitedEmployee getInvitedEmployeeById(int id) {
		InvitedEmployee emp = new InvitedEmployee();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_invited_employee_by_id(?, ?, ?, ?, ? )}");
			cstmt.setInt(1, id);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.registerOutParameter(4, Types.INTEGER);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.executeUpdate();
			emp.setEmployeeName(cstmt.getString(2));
			emp.setEmployeeId(id);
			String positionName = cstmt.getString(3);
			Position empPos = Position.getPositionByName(positionName);
			emp.setEmpPosition(empPos);
			emp.setType("invited");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return emp;
	}
	
	public void updateWage(int id, int wage, int userId) {
		int empId = this.getEmployeeId();
		this.setWage(wage);
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_update_invited_employee(?, ?, ?, ? )}");
			cstmt.setInt(1, empId);
			cstmt.setInt(2, wage);
			cstmt.setInt(3, userId);
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

	public int getWage() {
		return wage;
	}

	public void setWage(int wage) {
		this.wage = wage;
	}

}
