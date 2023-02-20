package wolfPub;

import java.util.Scanner;

public class WolfPub {
	public static void main(String[] args) {
		System.out.println("Welcom to WolfPub publication house!!");
		Scanner sc = new Scanner(System.in);
		loop: while(true) {
			System.out.println("Please select the user type");
			System.out.println("1: Employee");
			System.out.println("2: Distributor");
			System.out.println("exit: Exit the program");
			String decision = sc.nextLine();
			switch(decision) {
			case "1":
				System.out.println("Please enter your employee id");
				int empId = sc.nextInt();
				sc.nextLine();
				System.out.println(Employee.getEmployee(empId).getEmployeeName());
				System.out.println(Employee.getEmployee(empId).getEmpPosition().getPositionName());
				String empPosition = Employee.getEmployee(empId).getEmpPosition().getPositionName().toLowerCase();
				switch(empPosition) {
				case "admin":
					Admin curAdmin = new Admin(empId, sc);
					break;
				case "author":
					AuthorAsUser curAuthor = new AuthorAsUser(empId, sc);
					break;
				case "editor":
					EditorAsUser curEditor = new EditorAsUser(empId, sc);
					break;
				default:
					System.out.println("Employee not found");
					break;
				}
				break;
			case "2":
					
				System.out.println("Please enter your distributor id");
				int disId = Integer.parseInt(sc.nextLine());
				Distributor distObj = Distributor.getDistributorByID(disId);
				if (distObj == null) {
					System.out.println("Enter valid ID");
					break;
				}
				else {
					DistributorAsUser curDistributor = new DistributorAsUser(disId, sc);
				}
				break;
			case "exit":
				System.out.println("Good bye!!");
				break loop;
			default:
				System.out.println("Wrong input");
			}
		}
//		sc.close();
	}
}
