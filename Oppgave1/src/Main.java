import javax.swing.JOptionPane;

class Message {

    private String data;

    public Message(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

}

class Printer implements Runnable {

    private Message message;

    public Printer(Message message) {
        this.message = message;
    }

    public void run() {
        while (!message.getData().equals("Exit")) {
            System.out.println(message.getData());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }
    }

}

class Asker implements Runnable {

    private Message message;

    public Asker(Message message) {
        this.message = message;
    }

    public void run() {
        while (!message.getData().equals("Exit")) {
            this.message.setData(JOptionPane.showInputDialog(null, "Enter message"));
        }
    }

}

public class Main {

    public static void main(String[] args) {
        Message m = new Message("Hello world!");
        Thread p = new Thread(new Printer(m));
        Thread a = new Thread(new Asker(m));

        p.start();
        a.start();
    }
}
