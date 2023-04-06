package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static carsharing.UserInput.listen;

public class Menu {

    static void init() throws SQLException {
        textMain();

        int input = listen();
        if(input == 0) return;
        else if (input == 1) manager();
        else if (input == 2) CustomerMenu.customerList();
        else if (input == 3) CustomerMenu.createCustomer();

    }

    static void textMain(){
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
    }

    static void manager(){
        try {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");

            int input = listen();
            if (input == 0) init();
            else if (input == 1) {
                ResultSet companies = CompanyDao.getAllCompanies();
                int size = CompanyDao.printAllCompanies(companies);
                System.out.println("0. Back");
                System.out.print("Select company: ");
                int number = listen();
                if (number == 0) manager();
                else if (number > 0 && number <= size) {
                    CompanyMenu.company(number, companies);
                }
            } else if (input == 2) {
                System.out.println("Enter the company name:");
                Scanner scanner = new Scanner(System.in);
                String name = scanner.nextLine();
                CompanyDao.addCompany(name);
                manager();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}
