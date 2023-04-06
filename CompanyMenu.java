package carsharing;

import java.sql.ResultSet;
import java.util.Scanner;

import static carsharing.UserInput.listen;

public class CompanyMenu {
    static void company(int company, ResultSet resultSet){
        String name = null;
        try {

            resultSet.beforeFirst();
            for(int i = 0; i < company; i++) resultSet.next();
            name = resultSet.getString("name");
            System.out.println(name + " company:");
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");

            int input = listen();

            if(input == 0) Menu.manager();
            else if(input == 1) {
                CarDao.printAllCars(resultSet.getInt("id"));

                System.out.println();
                company(company, resultSet);
            }
            else if(input == 2){
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter the car name:");
                String car_name = scanner.nextLine();
                CarDao.addCar(car_name, resultSet.getInt("id"));
                System.out.println("Car added");
                company(company, resultSet);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
