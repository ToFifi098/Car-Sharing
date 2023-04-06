package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        try {
            DbConn.connect();
            DbConn.conn.setAutoCommit(true);
            DbConn.execute("CREATE TABLE COMPANY (id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR UNIQUE NOT NULL)");
            DbConn.execute("CREATE TABLE CAR (" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR UNIQUE NOT NULL," +
                    "company_id INT NOT NULL," +
                    "CONSTRAINT fk_company FOREIGN KEY (company_id)" +
                    "REFERENCES company(id))");
            DbConn.execute("CREATE TABLE CUSTOMER (id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR UNIQUE NOT NULL," +
                    "rented_car_id INT," +
                    "CONSTRAINT fk_car FOREIGN KEY (rented_car_id)" +
                    "REFERENCES car(id))");
        }
        catch (Exception e){
            System.out.println("DB error");
        }

        try {
            Menu.init();
        }
        catch (Exception e){
            System.out.println(e);
        }



        try {
            DbConn.close();
        } catch (SQLException e) {
            System.out.println("DB error");
        }

    }
}