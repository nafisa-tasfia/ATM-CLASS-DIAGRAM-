// Abstract base class for Account
abstract class AccountATM {
    protected String accountNumber;
    protected double balance;

    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    
    public double getBalance() {
        return balance;
    }
}

// Savings Account
class SavingsAccount extends AccountATM {
    public SavingsAccount(String accNum, double initialBalance) {
        this.accountNumber = accNum;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited " + amount + " to Savings Account");
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn " + amount + " from Savings Account");
        } else {
            System.out.println("Insufficient balance in Savings Account");
        }
    }
}

// Current Account
class CurrentAccount extends AccountATM {
    public CurrentAccount(String accNum, double initialBalance) {
        this.accountNumber = accNum;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited " + amount + " to Current Account");
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn " + amount + " from Current Account");
        } else {
            System.out.println("Insufficient balance in Current Account");
        }
    }
}

// Bank class
class BankATM {
    private String bankName;
    private String branch;

    public BankATM(String bankName, String branch) {
        this.bankName = bankName;
        this.branch = branch;
    }

    public String getBankName() {
        return bankName;
    }
}

// ATM class
class ATM {
    private String location;
    private String atmId;

    public ATM(String location, String atmId) {
        this.location = location;
        this.atmId = atmId;
    }

    public void authenticateUser() {
        System.out.println("User authenticated at ATM " + atmId);
    }

    public void performTransaction(ATMTransaction transaction) {
        transaction.processTransaction();
    }
}

// Customer class
class Customer {
    private String name;
    private String address;
    private DebitCard card;
    private AccountATM account;

    public Customer(String name, String address, DebitCard card, AccountATM account) {
        this.name = name;
        this.address = address;
        this.card = card;
        this.account = account;
    }

    public boolean verifyPin(int pin) {
        return card.validatePin(pin);
    }

    public AccountATM getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }
}

// DebitCard class
class DebitCard {
    private String cardNumber;
    private String expiryDate;
    private int pin;

    public DebitCard(String cardNumber, String expiryDate, int pin) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.pin = pin;
    }

    public boolean validatePin(int inputPin) {
        return inputPin == pin;
    }

    public void setPin(int newPin) {
        this.pin = newPin;
    }
}

// Abstract ATMTransaction class
abstract class ATMTransaction {
    protected double amount;

    public ATMTransaction(double amount) {
        this.amount = amount;
    }

    public abstract void processTransaction();
}

// Withdrawal class
class Withdrawal extends ATMTransaction {
    private AccountATM account;

    public Withdrawal(AccountATM account, double amount) {
        super(amount);
        this.account = account;
    }

    public void processTransaction() {
        account.withdraw(amount);
    }
}

// Transfer class
class Transfer extends ATMTransaction {
    private AccountATM sender;
    private AccountATM receiver;

    public Transfer(AccountATM sender, AccountATM receiver, double amount) {
        super(amount);
        this.sender = sender;
        this.receiver = receiver;
    }

    public void processTransaction() {
        if (sender.getBalance() >= amount) {
            sender.withdraw(amount);
            receiver.deposit(amount);
            System.out.println("Transferred " + amount + " from sender to receiver");
        } else {
            System.out.println("Transfer failed: Insufficient balance");
        }
    }
}

// PIN Generation class
class PinGeneration {
    public void generatePin(DebitCard card, int newPin) {
        card.setPin(newPin);
        System.out.println("PIN successfully updated.");
    }
}

// Main class to test
public class ATMSystemMain {
    public static void main(String[] args) {
        // Setup
        BankATM bank = new BankATM("Bank Asia", "Dhanmondi");
        ATM atm = new ATM("Dhanmondi Branch", "ATM001");

        DebitCard card = new DebitCard("1234-5678-9012", "12/28", 1234);
        AccountATM account = new SavingsAccount("SA123", 10000);
        Customer customer = new Customer("Nafisa", "Dhaka", card, account);

        // Simulate ATM transaction
        atm.authenticateUser();

        if (customer.verifyPin(1234)) {
            ATMTransaction withdrawal = new Withdrawal(customer.getAccount(), 2000);
            atm.performTransaction(withdrawal);

            System.out.println("Remaining balance: " + customer.getAccount().getBalance());
        } else {
            System.out.println("Incorrect PIN!");
        }

        // PIN Generation
        PinGeneration pinGen = new PinGeneration();
        pinGen.generatePin(card, 5678);
    }
}
