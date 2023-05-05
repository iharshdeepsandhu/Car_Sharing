package carsharing;



import javax.swing.plaf.nimbus.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CompanyDao {
    private static ConnectionFactory factory;

    public CompanyDao(String fileName) {
        this.factory = new ConnectionFactory(fileName);




    }
    public void createCompanyTable() {
        try (Statement statement = factory.getConnection()){
            String sql =  "CREATE TABLE IF NOT EXISTS COMPANY " +
                    "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME VARCHAR(255) NOT NULL UNIQUE);";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void createCarTable() {
        try (Statement statement = factory.getConnection()){
            String sql = "CREATE TABLE IF NOT EXISTS CAR " +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR NOT NULL UNIQUE," +
                    "COMPANY_ID INTEGER NOT NULL," +
                    "CONSTRAINT fk_COMPANY FOREIGN KEY (COMPANY_ID)" +
                    "REFERENCES COMPANY(ID));";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void createCustomerTable(){
        try (Statement statement = factory.getConnection()){
            String sql = "CREATE TABLE IF NOT EXISTS CUSTOMER " +
                    "(ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR NOT NULL UNIQUE," +
                    "RENTED_CAR_ID INTEGER DEFAULT NULL," +
                    "CONSTRAINT fk_CAR FOREIGN KEY(RENTED_CAR_ID)" +
                    "REFERENCES CAR(ID)" +
                    ");";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void addCompany(String name){
        try (Statement statement = factory.getConnection()){
            String sql = "INSERT INTO COMPANY (NAME)" +
                    " VALUES('"+name+"');";
            statement.executeUpdate(sql);
            System.out.println("The company was created!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getAllCompanyList(){
        try (Statement statement = factory.getConnection()) {
            String sql = "SELECT * FROM COMPANY";
            try (ResultSet result = statement.executeQuery(sql)){
                ArrayList<String> list = new ArrayList<>();
                int number = 0;
                while (result.next()){
                    String companyName = result.getString("NAME" );
                    list.add(companyName);
                }
                return list;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void addCar(String name, int a){
        try (Statement statement = factory.getConnection()){
            String sql = "INSERT INTO CAR (NAME,COMPANY_ID)" +
                    " VALUES('"+name+"',"+a+");";
            statement.executeUpdate(sql);
            System.out.println("The Car was added!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getCompanyCars(int id){
        //experimentMethod();
        try (Statement statement = factory.getConnection()){
            String sql = "SELECT * FROM CAR WHERE COMPANY_ID = "+id;
            try (ResultSet result = statement.executeQuery(sql)){

                ArrayList<String> list = new ArrayList<>();

                while (result.next()){
                    String car = result.getString("NAME");
                    int carId = result.getInt("ID");
                    /*if (isCarRented(carId)){
                        continue;
                    }*/
                    list.add(car);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getCompanyId(String companyName) {
        try (Statement statement = factory.getConnection()){
            String sql = "SELECT ID FROM COMPANY WHERE NAME = '"+companyName+ "';";
            int companyId = 0;
            try(ResultSet result = statement.executeQuery(sql)){
                while(result.next()){
                    companyId = result.getInt("ID");
                }
            }
            return companyId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void createCustomer(String name) {
        try (Statement statement = factory.getConnection()){
            String sql = "INSERT INTO CUSTOMER(NAME)" +
                    " VALUES('"+name +"');";
            statement.executeUpdate(sql);
            System.out.println("The customer was added!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getCustomer(){
        try (Statement statement = factory.getConnection()){
            String sql = "SELECT NAME FROM CUSTOMER";
            ArrayList<String> list = new ArrayList<>();
            try (ResultSet result = statement.executeQuery(sql)){
                while (result.next()){
                    String s = result.getString("NAME");

                    list.add(s);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public static String getMyRentedCarID(String customerName) {
        try (Statement statement = factory.getConnection()){
            String sql = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE NAME ='"+customerName+"'";
            String rentedCarID = "";

            try(ResultSet result = statement.executeQuery(sql)){


                while(result.next()){
                    rentedCarID = result.getString("RENTED_CAR_ID");
                    if (rentedCarID == null){
                        //

                    }

                }
                    //System.out.println("You didn't rent a car!");

                /*}else {
                    System.out.println("else case");
                }*/



            }
            return rentedCarID;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public static int getCarCompanyId(String name){
        try (Statement statement = factory.getConnection()){
            String sql = "SELECT COMPANY_ID FROM CAR WHERE NAME ='"+name+"';";
            //System.out.println(sql);

            try (ResultSet result = statement.executeQuery(sql)){
                int carId= 0;
                while(result.next()){
                 carId = result.getInt("COMPANY_ID");
                }
                while (result.next()){
                    System.out.println(result.getString("NAME"));
                    System.out.println(result.getString("COMPANY_ID"));
                }
//                int carId = result.getInt("COMPANY_ID");
//                System.out.println(carId);
                return carId;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public int getcarId(int carCompanyId,String carName){
        try (Statement statement = factory.getConnection()){

            String sql = "SELECT ID FROM CAR WHERE COMPANY_ID = "+carCompanyId+" AND NAME = '"+carName+"';";
            try (ResultSet result = statement.executeQuery(sql)){
                int carId= 0;
                while(result.next()){
                    carId = result.getInt("ID");

                }
                //System.out.println(carId);
                return carId;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setRentedCar(int carId, String customerName, String rentedCar) {
        try (Statement statement = factory.getConnection()){
            //System.out.println(customerName);
            //System.out.println(carId);
            String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = "+carId+" WHERE NAME = '"+customerName+"';";
            statement.executeUpdate(sql);
            System.out.println("You rented '"+rentedCar+ "'");



        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public static String getRentedcarName(String customerName) {
        int carId = Integer.parseInt(getMyRentedCarID(customerName));
        try (Statement statement = factory.getConnection()){
            String sql = "SELECT NAME FROM CAR WHERE ID = "+carId+";";
            try (ResultSet resultSet = statement.executeQuery(sql)){
                String name ="";
                while (resultSet.next()){
                    name = resultSet.getString("NAME");
                }
                return name;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public static String getRentCompanyName(String name){

        int carCompanyid = getCarCompanyId(name);
        try (Statement statement = factory.getConnection()){

            String companyName = "";
            String sql = "SELECT NAME FROM COMPANY WHERE ID = "+carCompanyid+";";
            try (ResultSet result = statement.executeQuery(sql)){
                while(result.next()){
                    companyName = result.getString("NAME");
                }
                return companyName;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void experimentMethod(){
        try (Statement statement = factory.getConnection()){
            String sql = "Select * from CUSTOMER";
            try (ResultSet result = statement.executeQuery(sql)){
                while(result.next()){
                    String name = result.getString("NAME");
                    int id = result.getInt("ID");
                    int companyId = result.getInt("RENTED_CAR_ID");
                    System.out.println("|"+id+"|"+name+"|"+companyId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void experimentMethod2(){
        try (Statement statement = factory.getConnection()){
            String sql = "Select * from CAR";
            try (ResultSet result = statement.executeQuery(sql)){
                while(result.next()){
                    String name = result.getString("NAME");
                    int id = result.getInt("ID");
                    int companyId = result.getInt("COMPANY_ID");
                    System.out.println("|"+id+"|"+name+"|"+companyId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isrented(String customerName){
        String carId = getMyRentedCarID(customerName);
        if (carId == null){

            return true;

        }else{

            return false;
        }

    }
    public static void returnCar(String customerName) {
        try (Statement statement = factory.getConnection()){
            //System.out.println(customerName);

            String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = "+null+" WHERE NAME = '"+customerName+"';";
            statement.executeUpdate(sql);
            System.out.println();
            System.out.println("You've returned a rented car!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isCarRented(int rentedCarId){
        try (Statement statement = factory.getConnection()){
            experimentMethod();
            String sql = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE RENTED_CAR_ID = "+rentedCarId+";";
            try (ResultSet result = statement.executeQuery(sql)) {
                //System.out.println(result.getInt("RENTED_CAR_ID"));
                //int a = Integer.parseInt(null);
                if (result.getString("RENTED_CAR_ID")==null){
                    return false;
                }else{
                    return true;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}


