package carsharing;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerMenu {
    public static void createCustomer(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the customer name:");
        String name = scanner.nextLine();

        try{
            CustomerDao.addCustomer(name);
            Menu.init();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void customerList(){
        try{
            ResultSet resultSet = CustomerDao.getAllCustomers();
            int size = CustomerDao.printAllCustomers(resultSet);
            System.out.println("0. Back");
            System.out.print("Select customer: ");
            int number = UserInput.listen();
            if(number == 0) Menu.init();
            else if(number > 0 && number <= size){
                resultSet.absolute(number);
                customer(resultSet);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    static void customer(ResultSet resultSet){
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");

        try {
            int number = UserInput.listen();
            if (number == 0) Menu.init();
            if (number == 1) rentCar(resultSet.getString("name"), resultSet);
            if (number == 2) returnRented(resultSet.getString("name"), resultSet);
            if (number == 3) myRented(resultSet.getString("name"), resultSet);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    static void myRented(String name, ResultSet customer){
        ResultSet rs = CustomerDao.getRented(name);
        try {
            if(!CustomerDao.checkRented(name)){
                System.out.println("You didn't rent a car!");
                customer(customer);
            }
            else {
                rs.next();
                System.out.println("Your rented car:");
                System.out.println(rs.getString("car.name"));
                System.out.println("Company:");
                System.out.println(rs.getString("company.name"));

                customer(customer);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    static void returnRented(String name, ResultSet customer){
        CustomerDao.returnRented(name);
        customer(customer);
    }
    static void rentCar(String name, ResultSet customer){
        try {

            boolean rented = CustomerDao.checkRented(name);
            if(rented){
                System.out.println("You've already rented a car!");
                customer(customer);
            }

            ResultSet companies = CompanyDao.getAllCompanies();
            int number = CompanyDao.printAllCompanies(companies);
            if(number == 0) customer(customer);
            System.out.println("0. Back");
            int listen  = UserInput.listen();
            if(listen == 0) customer(customer);
            if(listen <= number){
                companies.absolute(listen);
                ArrayList<Car> cars = CompanyDao.checkAvailable(companies.getInt("id"));
                if(cars.size() == 0){
                    System.out.println("No available cars in the "+companies.getString("name")+" company");
                    customer(customer);
                }
                else {
                    for(int i = 0; i < cars.size(); i++){
                        System.out.println(i+1+". "+cars.get(i).name);
                    }
                    System.out.println("0. Back");
                    listen = UserInput.listen();
                    if(listen == 0) customer(customer);
                    if(listen <= cars.size()){
                        CustomerDao.addRented(cars.get(listen-1).id,name);
                        System.out.println("You rented '" + cars.get(listen-1).name +"'");
                        customer(customer);
                    }
                }
            }




        }
        catch (Exception e){
            System.out.println(e);
        }

    }

}
