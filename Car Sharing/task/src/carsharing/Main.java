package carsharing;
import java.lang.reflect.Array;
import java.net.CacheRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String dbName = "test";
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-databaseFileName")){
                dbName = args[i + 1];
            }
        }
        //System.out.println(dbName);
        CompanyDao dao = new CompanyDao(dbName);
        dao.createCompanyTable();
        dao.createCarTable();
        dao.createCustomerTable();
        //dao.addCar("swift", 1);

        String input1 = null;
        mainMenu();
        while(scanner.hasNext() && !(input1 = scanner.next()).equals("0")) {
            switch (input1){
                //manager case
                case "1":
                    managerLogIn();
                    String input2 = null;
                    while(scanner.hasNext() && !(input2 = scanner.next()).equals("0")){
                        switch (input2){
                            case "1":
                                ArrayList<String> companyList = showCompanyList(dao.getAllCompanyList());
                                if (companyList.isEmpty()){
                                    managerLogIn();
                                    break;
                                }
                                String companySelection = null;
                                //carlist

                                while(scanner.hasNext() && !(companySelection = scanner.next()).equals("0")){
                                    String comapanyName = dao.getAllCompanyList().get(Integer.parseInt(companySelection)-1);
                                    showCompanyMenu(comapanyName);
                                    String inputCar = null;
                                    while(scanner.hasNext() && !(inputCar = scanner.next()).equals("0")){
                                        switch (inputCar){
                                            case "1":
                                                showComapanyCars(dao.getCompanyCars(Integer.parseInt(companySelection)));
                                                showCompanyMenu(comapanyName);

                                                break;
                                            case "2":
                                                System.out.println("Enter the car name:");
                                                String carName = scanner.next();
                                                carName += scanner.nextLine();
                                                dao.addCar(carName, Integer.parseInt(companySelection));
                                                showCompanyMenu(comapanyName);
                                        }

                                    }
                                    if (inputCar.equals("0")){
                                        managerLogIn();
                                        break;
                                    }




                                }
                                if (Integer.parseInt(companySelection) == 0)
                                managerLogIn();
                                break;
                            case "2":
                                System.out.println("Enter the company name:");
                                String companyName = scanner.next();
                                companyName += scanner.nextLine();
                                dao.addCompany(companyName);
                                managerLogIn();
                                break;
                        }

                    }
                    if (input2.equals("0")){
                        mainMenu();
                        break;

                    }

                case "2":
                    //log in as a customer
                    ArrayList<String> list = showCustomerList(dao.getCustomer());
                    if (list.isEmpty()){
                        mainMenu();
                        break;
                    }
                    //System.out.println(dao.getCustomer());
                    String inputCustomer = null;
                    while(scanner.hasNext() && !(inputCustomer = scanner.next()).equals("0")){
                        String customerName = dao.getCustomer().get(Integer.parseInt(inputCustomer)-1);
                        //System.out.println(customerName);
                        showCustomerMenu();
                        String customerMenuInput = null;
                        while (scanner.hasNext() && !(customerMenuInput = scanner.next()).equals("0")){
                            switch(customerMenuInput){
                                case "1":
                                    //rent a car
                                    if(!dao.isrented(customerName)){
                                        System.out.println("You've already rented a car!");
                                        showCustomerMenu();
                                        break;
                                    }
                                    ArrayList<String> companyList = showCompanyList(dao.getAllCompanyList());
                                    String companySelect = null;
                                    while(scanner.hasNext() && !(companySelect = scanner.next()).equals("0")) {
                                        //System.out.println(companyList.get(Integer.parseInt(companySelect) - 1));
                                        ArrayList<String> companyCarsForRent = showComapanyCarsRent(dao.getCompanyCars(Integer.parseInt(companySelect)));
                                        //System.out.println(companyCarsForRent);
                                        int rentCarSelect = scanner.nextInt();
                                        if(rentCarSelect == 0){
                                            showCompanyList(dao.getAllCompanyList());
                                            break;
                                        }else{
                                            String carName = companyCarsForRent.get(rentCarSelect -1);
                                            //System.out.println(carName);
                                            int carId = dao.getcarId(dao.getCarCompanyId(carName),carName);
                                            //System.out.println(carId);
                                            //dao.experimentMethod();
                                            dao.setRentedCar(carId, customerName, carName);
                                            showCustomerMenu();
                                            break;

                                        }


                                    }
                                    if (companySelect.equals(0)){
                                        showCustomerMenu();
                                        break;
                                    }
                                    break;

                                case "2":
                                    String returnCarID = dao.getMyRentedCarID(customerName);
                                    if(returnCarID == null) {
                                        System.out.println("You didn't rent a car!");
                                        showCustomerMenu();
                                        break;
                                    }else{
                                        if (!dao.isrented(customerName)){
                                            dao.experimentMethod();
                                            dao.experimentMethod2();
                                            dao.returnCar(customerName);
                                            dao.experimentMethod();
                                        }else{

                                        }
                                        break;
                                    }

                                case "3":
                                   String myRentedCarID = dao.getMyRentedCarID(customerName);
                                    if(myRentedCarID == null){
                                        System.out.println("You didn't rent a car!");
                                        showCustomerMenu();
                                        break;
                                    }else{
                                        System.out.println("Your rented car:");
                                        System.out.println(dao.getRentedcarName(customerName));
                                        System.out.println("Company:");
                                        //dao.experimentMethod();
                                        System.out.println(dao.getRentCompanyName(dao.getRentedcarName(customerName)));
                                        showCustomerMenu();
                                        break;
                                    }

                            }
                        }
                        if (customerMenuInput.equals("0")){
                            showCustomerList(dao.getCustomer());
                            break;
                        }

                    }
                    if (inputCustomer.equals("0")){
                        //menu statement to be inserted
                        mainMenu();
                        break;
                    }



                    break;


                case "3":
                    //create a customer
                    System.out.println();
                    System.out.println("Enter the customer name:");
                    String customerName = scanner.next();
                    customerName += scanner.nextLine();
                    dao.createCustomer(customerName);
                    mainMenu();
                    break;
            }
        }


        //dao.addCompany("FORth company");
    }
    public static void mainMenu(){
        System.out.println();
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");

    }
    public static void managerLogIn() {
        System.out.println();
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");

    }

    public static ArrayList<String> showCompanyList(ArrayList<String> list){
        System.out.println();
        if (list.isEmpty()){
            System.out.println("The company list is empty!");
            return list;
        }
        System.out.println("Choose a company:");
        //System.out.println(list);
        int index = 1;
        for (String s:list) {

            System.out.println(index + ". " + s);
            index++;
        }
        System.out.println("0. Back");
        return list;

    }
    public static void showCompanyMenu(String companyName) {
        System.out.println();
        System.out.println("'" + companyName + "' company:");
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");

    }
    public static void showCustomerMenu() {
        System.out.println();
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");
    }
    public static void showComapanyCars(ArrayList<String>list) {
        System.out.println();
        if (list.isEmpty()){
            System.out.println("The car list is empty!");
            return;
        }
        System.out.println("Car list:");
        //System.out.println(list);
        int index = 1;
        for (String s:list) {
            System.out.println(index + ". " + s);
            index++;
        }
    }
    public static ArrayList<String> showComapanyCarsRent(ArrayList<String>list) {
        System.out.println();
        if (list.isEmpty()){
            System.out.println("The car list is empty!");
            return list;
        }
        System.out.println("Choose a car:");
        //System.out.println(list);
        int index = 1;
        for (String s:list) {
            System.out.println(index + ". " + s);
            index++;
        }
        System.out.println("0. Back");
        return list;
    }
    public static ArrayList<String> showCustomerList(ArrayList<String> list){
        System.out.println();
        if (list.isEmpty()){
            System.out.println("The customer list is empty!");
            return list;
        }
        System.out.println("Customer list:");
        //System.out.println(list);
        int index = 1;
        for (String s:list) {
            System.out.println(index + ". " + s);
            index++;
        }
        System.out.println("0. Back");
        return list;
    }

}