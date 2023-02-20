package wolfPub;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

public class Employee {
	private String employeeName;
	private int employeeId;
	private Position empPosition;
	private String type;
	
	public Employee(String employeeName, Position empPosition, String type) {
		this.setEmployeeName(employeeName);
		this.setEmpPosition(empPosition);
		this.setType(type);
	}
	
	public void removeEmployee(int userId) {
		Database db = new Database();
		
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_remove_employee(?, ?, ? )}");
			cstmt.setInt(1, this.employeeId);
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
	
	public Employee() {
		this.employeeName = null;
		this.employeeId = -1;
		this.empPosition = null;
		this.type = null;
	}
	
	public static boolean isEmployeePresent(String empName) {
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
	        result = statement.executeQuery("SELECT COUNT(1) AS empCount FROM employee WHERE employee_name = '" + empName + "';");
	        int empCount = 0;
	        while (result.next()) {
	            empCount = result.getInt("empCount");
	        }
	        if(empCount > 0) {
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
	
	public static Employee getEmployee(int id) {
		Employee emp = null;
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_employee_type_by_id(?, ?, ? )}");
			cstmt.setInt(1, id);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.executeUpdate();
			String empType = cstmt.getString(2);
			if(empType != null && empType.equals("invited")) {
				emp = InvitedEmployee.getInvitedEmployeeById(id);
			}
			else if(empType != null && empType.equals("staff")) {
				emp = StaffEmployee.getStaffEmployeeById(id);
			}
			else {
				emp = new Employee();
				System.out.println("employee not found");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return emp;
	}
	
	public static String getEmployeeTypeById(int id) {
		String empType = null;
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_get_employee_type_by_id(?, ?, ? )}");
			cstmt.setInt(1, id);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.executeUpdate();
			empType = cstmt.getString(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        db.closeConnection(con);
		}
		return empType;
	}
	
	public void updateEmployee(String name, String position, int userId) {
		int empId = this.getEmployeeId();
		this.setEmployeeName(name);
		Position empPosition = Position.getPositionByName(position);
		this.setEmpPosition(empPosition);
		Database db = new Database();
		Connection con = db.getConnection(false);
		if(con == null) {
			System.out.println("Failed to establish the connection");
			System.exit(0);
		}
		try {
			CallableStatement cstmt = con.prepareCall("{call sp_update_employee(?, ?, ?, ?, ? )}");
			cstmt.setInt(1, empId);
			cstmt.setString(2, name);
			cstmt.setString(3, position);
			cstmt.setInt(4, userId);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.executeUpdate();
			String err_msg = cstmt.getString(5);
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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Position getEmpPosition() {
		return empPosition;
	}

	public void setEmpPosition(Position empPosition) {
		this.empPosition = empPosition;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
