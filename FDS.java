import java.util.ArrayList;
import java.util.Scanner;
public class FDS {
    public static void main(String[]args){
        DeliverySystem ds1 = new DeliverySystem();
        ds1.start();
    }
}
class DeliverySystem{
    Scanner inputString = new Scanner(System.in);
    Scanner inputInt = new Scanner(System.in);
    public ArrayList<Users> userList;
    public ArrayList<MenuItems> itemList;
    public ArrayList<Orders> orderList;
    public ArrayList<Customer>customerList;
    public DeliverySystem(){
        this.userList = new ArrayList<Users>();
        this.itemList = new ArrayList<MenuItems>();
        this.orderList = new ArrayList<Orders>();
        this.customerList = new ArrayList<Customer>();
    }
    public void getUserList(){
        for(Users user: userList){
            System.out.println(user.getUserName());
        }
    }
    public void addMenuItem(MenuItems item){
        itemList.add(item);
    }
    public void removeMenuItem(MenuItems item){
        itemList.remove(item);
    }
    public void viewMenuItems(){
        System.out.println("Available Items:\t\tPrice: \t\t\tStock:");
        for(MenuItems item:itemList){
            System.out.println(item.getfoodName()+"\t\t\t"+item.getfoodPrice()+"\t\t\t"+item.getfoodStock());
        }
    }
    public void addCustomer(Customer customer){
        customerList.add(customer);
    }
    public void removeCustomer(Customer customer){
        customerList.remove(customer);
    }
    public void trackDelivery(){
        if(customerList.isEmpty()){
            System.out.println("No Pending Deliveries! Purchase a product first!");
        }else{
            System.out.println("Pending Deliveries: ");
            for(Customer customer:customerList){
                if(customer.getPaymentMethod().equals("Cash on Delivery")){
                    checkOrder();
                    System.out.println("Payment Method: "+customer.getPaymentMethod()+"\nLocation: "+customer.getCustomerHouseNo()+" "+customer.getCustomerAddress()+"\nPhone Number: "+customer.getCustomerPhoneNo());
                }else if(customer.getPaymentMethod().equals("Online via Bank Account")){
                    checkOrder();
                    System.out.println("Payment Method: "+customer.getPaymentMethod()+"\nBank Account Number"+customer.getCustomerBankAccNo()+" ("+customer.getCustomerBankCVV()+")\nName on Card: "+customer.getCustomerNameOnCard());
                }
            }
        }
    }
    public void proceedCheckOut(){
        if(orderList.isEmpty()){
            System.out.println("No existing order! Buy a product first!");
        }else if(!customerList.isEmpty()){
            System.out.println("There is an existing payment method, do you wish to change it? (y/n)");
            String choice = inputString.nextLine().toLowerCase();
            if(choice.equals("y")){
                customerList.clear();
                proceedCheckOut();
            }
        }
        else{
            System.out.println("Choose a payment method: \n1. Cash on Delivery\n2. Online Payment Method");
            int choice = inputInt.nextInt();
            if(choice == 1){
                int houseNo;
                String address;
                String phoneNo;
                while(true){
                    System.out.print("Enter your House Number: ");
                    houseNo = inputInt.nextInt();
                    if(houseNo <= 0){
                        System.out.println("Please enter a valid house number!");
                    }else{
                        break;
                    }
                }
                while(true){
                    System.out.print("Enter your 11-Digit Phone Number (+63): ");
                    phoneNo = inputString.nextLine();
                    if(phoneNo.length() != 11){
                        System.out.println("Invalid Phone Number! Enter 11-Digit phone number");
                    }
                    else{
                        break;
                    }
                }
                System.out.print("Enter your Address: ");
                address = inputString.nextLine();
                Customer c1 = new Customer(houseNo,address,phoneNo);
                addCustomer(c1);
                System.out.println("Successfully Added Payment Option!");
            }
            else if(choice == 2){
                String bankAccNo;
                String bankCVV;
                String nameOnCard;
                while(true){
                    System.out.print("Enter your 12 Digit Bank Account Number: ");
                    bankAccNo = inputString.nextLine();
                    if(bankAccNo.length() != 12){
                        System.out.println("Please enter a valid bank account number!");
                    }
                    else{
                        break;
                    }
                }
                while(true){
                    System.out.print("Enter your Card's 3-digit CVV: ");
                    bankCVV = inputString.nextLine();
                    if(bankCVV.length() != 3){
                        System.out.println("Please enter a valid 3-digit CVV number!");
                    }else{
                        break;
                    }
                }
                System.out.print("Enter your Name on Card: ");
                nameOnCard = inputString.nextLine();
                Customer c1 = new Customer(bankAccNo,bankCVV,nameOnCard);
                addCustomer(c1);
                System.out.println("Successfully Added Payment Option!");
            }
            else{
                System.out.println("Please choose 1 or 2 only!");
            }
        }
    }
    public void checkOrder(){
        if(orderList.isEmpty()){
            System.out.println("No existing order! Buy a product first!");
        }else{
            double totalAmount = 0.00;
            System.out.println("Item Name:\t\t\tQuantity:\t\tPrice:\t\t\tTotal:");
            for(Orders order:orderList){
                for(MenuItems menu: itemList){
                    if(order.getOrderName().equals(menu.getfoodName())){
                        System.out.println(order.getOrderName()+"\t\t\t"+order.getOrderQty()+"\t\t\t"+menu.getfoodPrice()+"\t\t\t"+(order.getOrderQty()*menu.getfoodPrice()));
                        totalAmount += order.getOrderQty()*menu.getfoodPrice();
                    }else{
                        continue;
                    }
                }
                
            }
            System.out.println("\t\t\t\t\t\t\t\t\t\tTotal Amount:"+totalAmount);
        }
    }
    public void buyFood(){
        boolean isItemFound = false;
        boolean isOutOfStock = false;
        boolean isQty0 = false;
        System.out.print("Enter the product you want to buy: ");
        String productBuy = inputString.nextLine();
        System.out.print("Enter the quantity of the product you want to buy: ");
        int buyQty = inputInt.nextInt();
        Orders o1 = new Orders(productBuy,buyQty);
        for(MenuItems item: itemList){
            if(item.getfoodName().equals(o1.getOrderName()) && o1.getOrderQty() <=0){
                isQty0 = true;
                break;
            }else if(item.getfoodName().equals(o1.getOrderName()) && (o1.getOrderQty() > 0 && item.getfoodStock() < o1.getOrderQty())){
                isOutOfStock = true;
                break;
            }else if(item.getfoodName().equals(o1.getOrderName()) && (o1.getOrderQty() > 0 && item.getfoodStock() >= o1.getOrderQty())){
                orderList.add(o1);
                isItemFound = true;
                System.out.println("Successfully Added Item");
                int newStock = item.getfoodStock() - o1.getOrderQty();
                item.setFoodStock(newStock);
                break;
            }else{
                continue;
            }
        }
        if(isItemFound){
            buyAnother();
        }else if(isOutOfStock){
            System.out.println("The product/s you attempt to buy is out of stock!");
            buyAnother();
        }else if(isQty0){
            System.out.println("Impossible to input zero or less than zero product quantity!");
            buyAnother();
        }else{
            System.out.println("The product you want to buy does not exist!");
            buyAnother();
        }
    }
    public void buyAnother(){
        System.out.println("Do you want to buy another product? (y/n)");
        String choice = inputString.nextLine().toLowerCase();
        if(choice.equals("y")){
            buyFood();
        }else{
            System.out.println("Thank you for purchasing!");
        }
    }
    public void start(){
        while (true) { 
            System.out.println("\nRegister / Log-in");
            System.out.println("1. Register an Account\n2. Login Account\n3. Exit");
            int choice = inputInt.nextInt();
            if(choice == 1){
                boolean isAccExists = false;
                System.out.print("Enter your Username: ");
                String userName = inputString.nextLine();
                System.out.print("Please Provide a Password: ");
                String password = inputString.nextLine();
                for(Users user: userList){
                    if(user.getUserName().equals(userName) && user.getPassword().equals(password)){
                        isAccExists = true;
                    }else{
                        break;
                    }
                }
                if(isAccExists){
                    System.out.println("Another account with username "+userName+" exists! Use another userName and password!");
                    start();
                }else{
                    Users u1 = new Users(userName, password);
                    userList.add(u1);
                    start();
                }
            }else if(choice == 2){
                boolean isAccountFound = false;
                boolean isPasswordMismatch = false;
                System.out.print("Username: ");
                String logName = inputString.nextLine();
                System.out.print("Password: ");
                String logPassword = inputString.nextLine();
                for(Users user: userList){
                    if(user.getUserName().equals(logName) && user.getPassword().equals(logPassword)){
                        System.out.println("Account Found!");
                        isAccountFound = true;
                        break;
                    }else if(user.getUserName().equals(logName) && !user.getPassword().equals(logPassword)){
                        System.out.println("Passwords Mismatch");
                        isPasswordMismatch = true;
                        break;
                    }
                    else{
                        continue;
                    }
                }
                if(isAccountFound){
                    welcome();
                }
                else if(isPasswordMismatch){
                    System.out.println("Check your credentials then try again!");
                    start();
                }
                else{
                    System.out.println("Account Not Found! Register First or Check your credentials then try again!");
                    start();
                }
                
            }
            else if(choice == 3){
                break;
            }
            else{
                System.out.println("Please input only 1-3!");
                start();
            }
            break;
            
        }
    }
    public void welcome(){
        DeliverySystem ds1 = new DeliverySystem();
        MenuItems m1 = new MenuItems("Indang's Tocino",199.00,25);
        MenuItems m2 = new MenuItems("Kalamay Buna",75.00,35);
        MenuItems m3 = new MenuItems("Kapeng Barako",150.00,10);
        ds1.addMenuItem(m1);
        ds1.addMenuItem(m2);
        ds1.addMenuItem(m3);
        while(true){
            System.out.println("\nWelcome to Delivery System! What do you want to do?");
            System.out.println("1. View Available Products\n2. Buy Food\n3. Check Orders\n4. Proceed to Checkout\n5. Track my Delivery\n6. Exit");
            int choice = inputInt.nextInt();
            if(choice == 1){
                ds1.viewMenuItems();
            }else if(choice == 2){
                ds1.buyFood();
            }else if(choice == 3){
                ds1.checkOrder();
            }else if(choice == 4){
                ds1.proceedCheckOut();
            }else if(choice == 5){
                ds1.trackDelivery();
            }else if(choice == 6){
                System.out.println("Thanks for using our Food Delivery System!");
                break;
            }else{
                System.out.println("Please input only 1-6!");
                start();
            }
        }
    }
}
class MenuItems{
    private String foodName;
    private double foodPrice;
    private int foodStock;
    public MenuItems(String foodName, double foodPrice, int foodStock){
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodStock = foodStock;
    }
    public String getfoodName(){
        return foodName;
    }
    public double getfoodPrice(){
        return foodPrice;
    }
    public int getfoodStock(){
        return foodStock;
    }
    public void setFoodStock(int newStock){
        this.foodStock = newStock;
    }
}
class Users {
    private String userName;
    private String password;
    public Users(String userName, String password){
        this.userName = userName;
        this.password = password;
        System.out.println("Successfully Added User!");
    }
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
}
class Orders{
    private String orderName;
    private int orderQty;
    public Orders(String orderName, int orderQty){
        this.orderName = orderName;
        this.orderQty = orderQty;
    }
    public String getOrderName(){
        return orderName;
    }
    public int getOrderQty(){
        return orderQty;
    }
}

class Customer{
    private int customerHouseNo;
    private String customerAddress;
    private String customerPhoneNumber;
    private String customerBankAccountNumber;
    private String customerBankCVV;
    private String customerNameOnCard;
    private String paymentMethod;
    public Customer(int customerHouseNo, String customerAddress, String customerPhoneNumber){
        this.customerHouseNo = customerHouseNo;
        this.customerAddress = customerAddress;
        this.customerPhoneNumber = customerPhoneNumber;
        paymentMethod = "Cash on Delivery";
    }
    public Customer(String customerBankAccountNumber, String customerBankCVV, String customerNameOnCard){
        this.customerBankAccountNumber = customerBankAccountNumber;
        this.customerBankCVV = customerBankCVV;
        this.customerNameOnCard = customerNameOnCard;
        paymentMethod = "Online via Bank Account";
    }
    public int getCustomerHouseNo(){
        return customerHouseNo;
    }
    public String getCustomerAddress(){
        return customerAddress;
    }
    public String getCustomerPhoneNo(){
        return customerPhoneNumber;
    }
    public String getCustomerBankAccNo(){
        return customerBankAccountNumber;
    }
    public String getCustomerBankCVV(){
        return customerBankCVV;
    }
    public String getCustomerNameOnCard(){
        return customerNameOnCard;
    }
    public String getPaymentMethod(){
        return paymentMethod;
    }
}