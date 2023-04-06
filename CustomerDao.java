package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDao {

    static public ResultSet getAllCustomers() throws SQLException {
        return DbConn.query("SELECT * FROM CUSTOMER");
    }

    static public int printAllCustomers(ResultSet rs) throws SQLException {
        System.out.println("Chose a customer");
        int i = 0;

        if (!rs.isBeforeFirst() ) {
            System.out.println("The customer list is empty!");
            Menu.init();
        }
        while (rs.next()) {
            i++;
            System.out.println("" + i + ". " + rs.getString("name"));
        }
        return i;
    }
    static public void addCustomer(String name){
        DbConn.execute("INSERT INTO customer (name, rented_car_id) VALUES ('"+name+"', NULL)");
    }

    static public ResultSet getRented(String name){
        try {
            return DbConn.query("SELECT car.name, company.name FROM car, company, customer WHERE customer.name = '"+name+"' ");
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    static public void addRented(int carId, String name){
        DbConn.execute("UPDATE customer SET rented_car_id = "+carId+" WHERE name ='"+name+"'");
    }

    static public boolean checkRented(String name){
        try {
            ResultSet rs = DbConn.query("SELECT rented_car_id FROM customer WHERE name = '"+name+"'");
            rs.next();
            return rs.getString("rented_car_id") != null;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    static public void returnRented(String name){
        try {
            boolean state = checkRented(name);
            if(!state){
                System.out.println("You didn't rent a car!");
            }
            else {
                DbConn.execute ("UPDATE customer SET rented_car_id = NULL WHERE name ='"+name+"'");
                System.out.println("You've returned a rented car!");
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
