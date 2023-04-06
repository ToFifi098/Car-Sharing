package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyDao {

    static public ResultSet getAllCompanies() throws SQLException {
        return DbConn.query("SELECT * FROM COMPANY");
    }

    static public int printAllCompanies(ResultSet rs) throws SQLException {
        System.out.println("Company list");
        int i = 0;

        if (!rs.isBeforeFirst() ) {
            System.out.println("The company list is empty!");
            Menu.manager();
        }
        while (rs.next()) {
            i++;
            System.out.println("" + i + ". " + rs.getString("name"));
        }
        return i;
    }
    static public void addCompany(String name){
        DbConn.execute("INSERT INTO company (name) VALUES ('"+name+"')");
    }

    static public ArrayList<Car> checkAvailable(int id){
        try {
            ResultSet cars = CarDao.getAllCars(id);
            ArrayList<Car> availableCars = new ArrayList<>();

            while (cars.next()) {
                availableCars.add(new Car(
                        cars.getInt("id"),
                        cars.getString("name"),
                        cars.getInt("company_id")
                ));
            }

            ResultSet customers = CustomerDao.getAllCustomers();
            ArrayList<Integer> notAvailable = new ArrayList<>();

            while (customers.next()){
                String carId = customers.getString("rented_car_id");
                if(carId != null){
                    notAvailable.add(Integer.parseInt(carId));
                }
            }

            for(int i = 0; i < availableCars.size(); i++){
                for(int carId : notAvailable ){
                    if(availableCars.get(i).id == carId){
                        availableCars.remove(i);
                        i--;
                    }
                }
            }

            return availableCars;

        }
        catch (Exception e){
            System.out.println(e);
            return new ArrayList<Car>();
        }
    }

}
