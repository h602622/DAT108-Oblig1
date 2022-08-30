package oppg2;

public class Main {
    // made by truls
    public static void main(String[] args) {
        final String [] Chef = {"Anne", "Erik", "Knut"};
        final String [] Server = {"Mia","Per"};
        final int CAPACITY = 4;


    }

}

class Burger {

}

class Tray {

    private int capacity;
    private int count;
    private Burger[] burgers;

    public Tray(int capacity) {
        this.burgers = new Burger[capacity];
        this.count = 0;
        this.capacity = capacity;
    }

    public void put(Burger burger) {
        this.burgers[this.count] = burger;
        this.count++;
    }

    public Burger take() {
        Burger burger = this.burgers[0];
        System.arraycopy(this.burgers, 0, this.burgers, 1, this.capacity - 1);
        this.burgers[this.count - 1] = null;
        this.count--;
        return burger;
    }

    public boolean isFull() {
        return this.count == this.capacity;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

}

class Chef extends Thread {

    private Tray tray;

    public Chef(Tray tray) {
        this.tray = tray;
    }

    @Override
    public void run() {
        synchronized (tray) {
            while (tray.isFull()) {
                try {
                    tray.wait();
                } catch (InterruptedException e) {
                    System.out.println("Tray is full. Chef is waiting for server to empty the tray");
                }
            }
        }
    }

    private void makeBurger() {
        Burger burger = new Burger();
        synchronized (tray) {
            while (tray.isFull()) {
                try {
                    tray.wait();
                } catch (InterruptedException e) {
                }
            }
            tray.put(burger);
            tray.notifyAll();
        }
    }
}

class Server extends Thread {

    private Tray tray;

    public Server(Tray tray) {
        this.tray = tray;
    }

    @Override
    public void run() {
        synchronized (tray) {
            while (tray.isEmpty()) {
                try {
                    tray.wait();
                } catch (InterruptedException e) {
                    System.out.println("Tray is empty. Server is waiting for a chef to make a burger");
                }
            }
        }
    }

    private void serveBurger() {
        if (this.tray.isEmpty()) {
            try {
                tray.wait();
            } catch (InterruptedException e) {
            }
            tray.take();
            tray.notifyAll();
        }
    }

}
