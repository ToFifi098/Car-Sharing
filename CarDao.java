package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CarDao {
    static public ResultSet getAllCars(int id){
        try {
            return DbConn.query("SELECT * FROM CAR WHERE company_id =" + id);
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    static public int printAllCars(int id){
        try {
            ResultSet rs = getAllCars(id);
            System.out.println("Car list");
            int i = 0;
            assert rs != null;
            if (!rs.isBeforeFirst() ) {
                System.out.println("The car list is empty!");
            }
            while (rs.next()) {
                i++;
                System.out.println("" + i + ". " + rs.getString("name"));
            }
            return i;
        }
        catch (Exception e){
            System.out.println(e);
            return 1;
        }
    }
    static public void addCar(String name, int id){
        DbConn.execute("INSERT INTO car (name, company_id) VALUES ('"+name+"', "+id+")");
    }
}
