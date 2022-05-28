import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import java.util.*;

import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Client implements ActionListener {
    private Socket socket = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private boolean isConnected = false;

    private User consumer;
    private ArrayList<Post> feed;

    private JFrame frame = new JFrame();
    private static JTextField userText;

    private String gotStr;

    public Client() {

        /*
         * JPanel panel = new JPanel();
         * panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
         * panel.setLayout(new GridLayout(0, 1));
         * 
         * userText = new JTextField(20);
         * userText.setBounds(100, 20, 165, 25);
         * panel.add(userText);
         * 
         * // the clickable button
         * JButton button2 = new JButton("Enter Your Name");
         * button2.setBounds(50, 200, 100, 30);
         * panel.add(button2);
         * button2.addActionListener(this);
         * 
         * // set up the frame and display it
         * frame.add(panel, BorderLayout.CENTER);
         * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         * frame.setTitle("GUI");
         * frame.pack();
         * frame.setVisible(true);
         * 
         * String name = gotStr;
         */

        /*
         * panel.remove(button2);
         * JButton button3 = new JButton("What subject are you interested in?");
         * button2.setBounds(50, 200, 100, 30);
         * panel.add(button3);
         * button3.addActionListener(this);
         */

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scan.nextLine();

        ArrayList<String> classes = new ArrayList<String>();
        System.out.print("Enter what class subject you're interested in: ");
        classes.add(scan.nextLine());
        consumer = new User(name, classes);
    }

    // process the button clicks
    public void actionPerformed(ActionEvent e) {
        gotStr = userText.getText();
        System.out.println(gotStr);
    }

    /*
     * public Client(String name, ArrayList<String> classes)
     * {
     * super(name, classes);
     * // feed = new ArrayList<Post>();
     * }
     */

    /*
     * public void communicate() {
     * 
     * while (!isConnected) {
     * try {
     * socket = new Socket("localHost", 6969);
     * System.out.println("Connected");
     * isConnected = true;
     * outputStream = new ObjectOutputStream(socket.getOutputStream());
     * User dude = new User(name, classes);
     * 
     * System.out.println("Object to be written = " + dude.getName());
     * outputStream.writeObject(dude);
     * 
     * socket.close();
     * } catch (SocketException se) {
     * se.printStackTrace();
     * // System.exit(0);
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     * }
     */
    public void initConnections() {
        while (!isConnected) {
            try {
                socket = new Socket("localHost", 6969);
                System.out.println("Connected");
                isConnected = true;
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
                // send user to the server
                outputStream.writeObject(consumer);

            } catch (SocketException se) {
                se.printStackTrace();
                // System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getPosts() {
        System.out.println("Your feed: ");
        try {
            outputStream.writeObject(consumer); // writing again to trigger a refresh
            Object response = inputStream.readObject();
            if (response instanceof ArrayList) {
                feed = (ArrayList<Post>) response;
                // filter out posts that don't correspond to what you want to see
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }
    }

    public void displayPosts() {
        // System.out.println("Your cat is: " + consumer.getClasses().get(0));

        for (Post p : feed) {
            if (p.getCategory().equals(consumer.getClasses().get(0))) {
                System.out.print(p.getCategory() + " --- ");
                System.out.println(p.getPoster());
            }
        }
    }

    public Post makePost() {

        Post poof = new Post(consumer.getClasses().get(0), consumer.getName(), null);
        return poof;
    }

    public static void main(String[] args) {

        /*
         * String name = "smth";
         * ArrayList<String> classes = new ArrayList<String>();
         * classes.add("English");
         */

        Client client = new Client();
        client.initConnections();
        client.getPosts();
        client.displayPosts();

        // like a post

        // Refresh again
        client.getPosts();
        client.displayPosts();

        // upload your own

        // Refresh again
        client.getPosts();
        client.displayPosts();

        // Real product will have an event loop
        /*
         * while (true)
         * {
         * 
         * }
         */
    }
}
