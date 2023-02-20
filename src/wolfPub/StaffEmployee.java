package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class StaffEmployee extends Employee {
	private int salary;

	public StaffEmployee(String employeeName, Position empPosition, int salary, int userId) {
		super(employeeName, empPosition, "staff");
		this.setSalary(salary);
		
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_add_staff_employee(?, ?, ?, ?, ?, ? )}");
			cstmt.setString(1, employeeName);
			cstmt.setString(2, empPosition.getPositionName());
			cstmt.setInt(3, salary);
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
	
	public StaffEmployee() {
		super();
		this.salary = -1;
	}

	public static StaffEmployee getStaffEmployeeById(int id) {
		StaffEmployee emp = new StaffEmployee();
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_staff_employee_by_id(?, ?, ?, ?, ? )}");
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
			emp.setType("staff");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return emp;
	}
	
	public void updateWage(int id, int salary, int userId) {
		int empId = this.getEmployeeId();
		this.setSalary(salary);
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_update_staff_employee(?, ?, ?, ? )}");
			cstmt.setInt(1, empId);
			cstmt.setInt(2, salary);
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

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

}
