interface Beverage {
    String getDescription();
    double cost();
}

class Espresso implements Beverage {
    public String getDescription() { return "Espresso"; }
    public double cost() { return 500; }
}

class Tea implements Beverage {
    public String getDescription() { return "Tea"; }
    public double cost() { return 300; }
}

abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;
    public BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }
    public String getDescription() { return beverage.getDescription(); }
    public double cost() { return beverage.cost(); }
}

class Milk extends BeverageDecorator {
    public Milk(Beverage beverage) { super(beverage); }
    public String getDescription() { return beverage.getDescription() + ", Milk"; }
    public double cost() { return beverage.cost() + 100; }
}

class Sugar extends BeverageDecorator {
    public Sugar(Beverage beverage) { super(beverage); }
    public String getDescription() { return beverage.getDescription() + ", Sugar"; }
    public double cost() { return beverage.cost() + 50; }
}

class WhippedCream extends BeverageDecorator {
    public WhippedCream(Beverage beverage) { super(beverage); }
    public String getDescription() { return beverage.getDescription() + ", Whipped Cream"; }
    public double cost() { return beverage.cost() + 150; }
}

interface IPaymentProcessor {
    void processPayment(double amount);
}

class PayPalPaymentProcessor implements IPaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment of " + amount + " ₸");
    }
}

class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Stripe transaction complete: " + totalAmount + " ₸");
    }
}

class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripeService;

    public StripePaymentAdapter(StripePaymentService stripeService) {
        this.stripeService = stripeService;
    }

    public void processPayment(double amount) {
        stripeService.makeTransaction(amount);
    }
}

class QiwiPaymentService {
    public void pay(double sum) {
        System.out.println("Qiwi paid: " + sum + " ₸");
    }
}

class QiwiPaymentAdapter implements IPaymentProcessor {
    private QiwiPaymentService qiwiService;

    public QiwiPaymentAdapter(QiwiPaymentService qiwiService) {
        this.qiwiService = qiwiService;
    }

    public void processPayment(double amount) {
        qiwiService.pay(amount);
    }
}


public class Main {
    public static void main(String[] args) {

        System.out.println("=== ДЕКОРАТОР ПАТТЕРНІ ===");
        Beverage drink = new Espresso();
        drink = new Milk(drink);
        drink = new Sugar(drink);
        drink = new WhippedCream(drink);

        System.out.println("Тапсырыс: " + drink.getDescription());
        System.out.println("Жалпы баға: " + drink.cost() + " ₸");

        System.out.println("\n=== АДАПТЕР ПАТТЕРНІ ===");
        IPaymentProcessor paypal = new PayPalPaymentProcessor();
        paypal.processPayment(2000);

        IPaymentProcessor stripe = new StripePaymentAdapter(new StripePaymentService());
        stripe.processPayment(3500);

        IPaymentProcessor qiwi = new QiwiPaymentAdapter(new QiwiPaymentService());
        qiwi.processPayment(1500);

        System.out.println("\nБарлық төлемдер сәтті орындалды ✅");
    }
}
