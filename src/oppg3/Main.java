package oppg3;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    // made by truls
    public static void main(String[] args) {
        final String[] CHEFS = { "Anne", "Erik", "Knut" };
        final String[] SERVERS = { "Mia", "Per" };

        Tray t = new Tray(4);

        for (String name : CHEFS) {
            new Chef(name, t).start();
        }

        for (String name : SERVERS) {
            new Server(name, t).start();
        }
    }

}

class Burger {

    private int id;

    public Burger(int id) {
        this.id = id;
    }

    public String toString() {
        return String.format("◖%d◗", this.id);
    }

}

class Tray {

    private BlockingQueue<Burger> burgers;
    private int total;

    public Tray(int capacity) {
        this.burgers = new LinkedBlockingQueue<Burger>(4);
        this.total = 0;
    }

    public void add(String name) {
        try {
            this.burgers.put(new Burger(this.total++));
        } catch (InterruptedException e) {
        }
        System.out.println(name + " added a burger | Tray: " + this.toString());
    }

    public void take(String name) {
        try {
            this.burgers.take();
        } catch (InterruptedException e) {
        }
        System.out.println(name + " took a burger | Tray: " + this.toString());
    }

    public String toString() {
        return this.burgers.toString();
    }

}

class Chef extends Thread {

    private Tray tray;
    private Random randy;

    public Chef(String name, Tray tray) {
        super(name);
        this.tray = tray;
        this.randy = new Random(LocalDateTime.now().getNano());
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            try {
                sleep((randy.nextInt(5) + 2) * 1000);
            } catch (InterruptedException e) {
            }
            tray.add(this.getName());
        }
    }

}

class Server extends Thread {

    private Tray tray;
    private Random randy;

    public Server(String name, Tray tray) {
        super(name);
        this.tray = tray;
        this.randy = new Random(LocalDateTime.now().getNano());
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            try {
                sleep((randy.nextInt(5) + 2) * 1000);
            } catch (InterruptedException e) {
            }
            tray.take(this.getName());
        }
    }

}
