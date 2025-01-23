import java.util.ArrayList;
import java.util.Scanner;
public class FDS {
    public static void main(String[]args){
        try{
            DeliverySystem ds1 = new DeliverySystem();
            ds1.start();
        }catch(Exception e){
            System.out.println("An Error Occured!\n"+e);
        }
    }
}
class DeliverySystem{
    Scanner inputString = new Scanner(System.in);
    Scanner inputInt = new Scanner(System.in);
    public ArrayList<Users> userList;
    public ArrayList<Stores> storeList;
    public ArrayList<MenuItems> itemList;
    public ArrayList<Orders> orderList;
    public ArrayList<Customer>customerList;
    public DeliverySystem(){
        this.userList = new ArrayList<>();
        this.storeList = new ArrayList<>();
        this.itemList = new ArrayList<>();
        this.orderList = new ArrayList<>();
        this.customerList = new ArrayList<>();
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
        System.out.println("\nAvailable Stores: ");
        for(Stores store:storeList){
            System.out.println((storeList.indexOf(store)+1)+". "+store.getStoreName());
        }
        System.out.print("From which store do you want to buy?: ");
        int choice = inputInt.nextInt();
        if(choice == 1){
            System.out.println("\nAvailable Products in Lolo Claros' Store: ");
            for(MenuItems item:itemList){
                if(item.getStoreName().equals("Lolo Claros")){
                    System.out.println(item.getfoodName()+"\t\t\t"+item.getfoodPrice()+"\t\t\t"+item.getfoodStock());
                }
            }
        }else if(choice == 2){
            System.out.println("\nAvailable Products in Chef John's Garden Cafe: ");
            for(MenuItems item:itemList){
                if(item.getStoreName().equals("Chef John's Garden Cafe")){
                    System.out.println(item.getfoodName()+"\t\t\t"+item.getfoodPrice()+"\t\t\t"+item.getfoodStock());
                }
            }
        }else{
            System.out.println("Please choose 1 or 2 only!");
        }
    }
    public void addCustomer(Customer customer){
        customerList.add(customer);
    }
    public void removeCustomer(Customer customer){
        customerList.remove(customer);
    }
    public void addStore(Stores store){
        storeList.add(store);
    }
    public void removeStore(Stores store){
        storeList.remove(store);
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
                while (true) { 
                    System.out.print("Enter your Email: ");
                    String email = inputString.nextLine();
                    if(!email.contains("@")||!email.contains(".com")){
                        System.out.println("Please enter a valid email address!");
                    }else{
                        break;
                    }
                }
                System.out.print("Enter your First Name and Last Name (User Name): ");
                String userName = inputString.nextLine();
                while(true){
                    System.out.print("Please Provide a Password: ");
                    String password = inputString.nextLine();
                    for(Users user: userList){
                        if(user.getUserName().equals(userName) && user.getPassword().equals(password)){
                            isAccExists = true;
                        }else{
                            break;
                        }
                    }
                    System.out.print("Please Confirm Password: ");
                    String confirm = inputString.nextLine();
                    if(isAccExists){
                        System.out.println("Another account with username "+userName+" exists! Use another userName and password!");
                        start();
                    }else{
                        if(!confirm.equals(password)){
                            System.out.println("Passwords and Confirm Password does not match!");
                            start();
                        }
                        Users u1 = new Users(userName, password);
                        userList.add(u1);
                        start();
                    }
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
        String sn1 = "Lolo Claros";
        String sn2 = "Chef John's Garden Cafe";
        MenuItems m1 = new MenuItems(sn2,"Beef Brocolli",460.00,25);
        MenuItems m2 = new MenuItems(sn2,"Chicken Wings",330.00,35);
        MenuItems m3 = new MenuItems(sn2,"SnS Fillet",390.00,15);
        MenuItems m4 = new MenuItems(sn2,"Beef Caldereta",450.00,15);
        MenuItems m5 = new MenuItems(sn2,"Fry Eggplant",380.00,15);
        MenuItems m6 = new MenuItems(sn2,"Prawn Tempura",490.00,25);
        MenuItems m7 = new MenuItems(sn2,"Chopsuey",390.00,35);
        MenuItems m8 = new MenuItems(sn2,"Roasted Beef",420.00,25);
        MenuItems m9 = new MenuItems(sn2,"Grilled Salmon",670.00,10);
        MenuItems m10 = new MenuItems(sn2,"Ribs n Fish",680.00,10);
        MenuItems m11 = new MenuItems(sn2,"Chocolate Cake",350.00,35);
        MenuItems m12 = new MenuItems(sn1,"Selecta Meal",250.00,100);
        MenuItems m13 = new MenuItems(sn1,"Whole Chicken",490.00,25);
        MenuItems m14 = new MenuItems(sn1,"Bihon Regular",350.00,25);
        MenuItems m15 = new MenuItems(sn1,"Pancit Canton",220.00,35);
        MenuItems m16 = new MenuItems(sn1,"Lumpia Shanghai",220.00,25);
        MenuItems m17 = new MenuItems(sn1,"Lumpia Ubod",490.00,25);
        MenuItems m18 = new MenuItems(sn1,"Calamares",250.00,25);
        ds1.addMenuItem(m1);
        ds1.addMenuItem(m2);
        ds1.addMenuItem(m3);
        ds1.addMenuItem(m4);
        ds1.addMenuItem(m5);
        ds1.addMenuItem(m6);
        ds1.addMenuItem(m7);
        ds1.addMenuItem(m8);
        ds1.addMenuItem(m9);
        ds1.addMenuItem(m10);
        ds1.addMenuItem(m11);
        ds1.addMenuItem(m12);
        ds1.addMenuItem(m13);
        ds1.addMenuItem(m14);
        ds1.addMenuItem(m15);
        ds1.addMenuItem(m16);
        ds1.addMenuItem(m17);
        ds1.addMenuItem(m18);
        Stores lc = new Stores("Lolo Claros");
        Stores cj = new Stores("Chef John's Garden Cafe");
        ds1.addStore(lc);
        ds1.addStore(cj);

        OUTER:
        while (true) {
            System.out.println("\nWelcome to Delivery System! What do you want to do?");
            System.out.println("1. View Available Stores\n2. Buy Food\n3. Check Orders\n4. Proceed to Checkout\n5. Track my Delivery\n6. Switch User");
            int choice = inputInt.nextInt();
            switch (choice) {
                case 1:
                    ds1.viewMenuItems();
                    break;
                case 2:
                    ds1.buyFood();
                    break;
                case 3:
                    ds1.checkOrder();
                    break;
                case 4:
                    ds1.proceedCheckOut();
                    break;
                case 5:
                    ds1.trackDelivery();
                    break;
                case 6:
                    System.out.println("Thanks for using our Food Delivery System!");
                    start();
                    break OUTER;
                default:
                    System.out.println("Please input only 1-6!");
                    start();
                    break;
            }
        }
    }
}
class MenuItems{
    private String foodName;
    private double foodPrice;
    private int foodStock;
    private String storeName;
    public MenuItems(String storeName, String foodName, double foodPrice, int foodStock){
        this.storeName = storeName;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodStock = foodStock;
    }
    public String getfoodName(){
        return foodName;
    }
    public String getStoreName(){
        return storeName;
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
class Stores{
    private String storeName;
    private ArrayList<MenuItems> storeItems;
    public Stores(String storeName){
        this.storeName = storeName;
    }
    public String getStoreName(){
        return storeName;
    }
    public void addStoreItem(MenuItems item){
        storeItems.add(item);
    }
    public void removeStoreItem(MenuItems item){
        storeItems.remove(item);
    }
    public void viewStoreMenu(){
        System.out.println(storeName + "\nAvailable Items:\t\tPrice: \t\t\tStock:");
        for(MenuItems item:storeItems){
            System.out.println(item.getfoodName()+"\t\t\t"+item.getfoodPrice()+"\t\t\t"+item.getfoodStock());
        }
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
    private final String paymentMethod;
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