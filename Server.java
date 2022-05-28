import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.util.ArrayList;
import java.lang.*;

public class Server {
    public static final int PORT = 6969;

    private ServerSocket servSock = null;
    private Socket sock = null;
    private ObjectInputStream inStream = null;
    private ObjectOutputStream outStream = null;

    private ArrayList<User> users;

    public Server() {
        users = new ArrayList<User>();
        // make three fake, preexisting posts here for demonstration
        ArrayList<String> aliceSubs = new ArrayList<String>();
        aliceSubs.add("English");
        User alice = new User("Alice", aliceSubs);
        Post p1 = new Post("English", alice.getName(), null);
        alice.updatePost(p1);
        users.add(alice);

        ArrayList<String> bobSubs = new ArrayList<String>();
        bobSubs.add("English");
        bobSubs.add("Math");
        User bob = new User("Bob", bobSubs);
        Post p2 = new Post("English", bob.getName(), null);
        alice.updatePost(p2);
        users.add(bob);

        ArrayList<String> charlesSubs = new ArrayList<String>();
        charlesSubs.add("Math");
        User charles = new User("Charles", charlesSubs);
        Post p3 = new Post("Math", charles.getName(), null);
        charles.updatePost(p3);
        users.add(charles);

    }

    public void communicate() {
        try {
            servSock = new ServerSocket(PORT);
            sock = servSock.accept();
            System.out.println("Connected");

            inStream = new ObjectInputStream(sock.getInputStream());
            // used for returning feed
            outStream = new ObjectOutputStream(sock.getOutputStream());

            while (true) {

                getUsersNames();
                Object thing = inStream.readObject();

                // If user does not exist, make a new one
                // Otherwise, sending a user object is a request for returning feed.
                if (thing instanceof User) {
                    User guy = (User) thing;
                    boolean exists = false;
                    for (User u : users) {
                        if (u.getName().equals(guy.getName())) {
                            ArrayList<Post> freshPosts = new ArrayList<Post>();
                            for (User per : users)
                            {
                                ArrayList<Post> indPosts = per.getPosts();
                                for (Post p : indPosts)
                                {
                                    freshPosts.add(p);
                                }
                            }
                            outStream.writeObject(freshPosts);
                            exists = true;
                            System.out.println("feed sent");
                        }
                    }
                    if (!exists) {
                        users.add(guy);
                        System.out.println("New User Added: " + ((User) thing).getName());
                    }

                } else if (thing instanceof Post) {
                    Post herald = (Post) thing;
                    System.out.println("post recieved");

                    for (User u : users) {
                        if (u.getName().equals(herald.getPoster())) {
                            u.updatePost(herald);
                            break;
                        }
                    }

                    // if it didn't find a user, something went wrong
                }
            }

            //sock.close();
        } catch (SocketException se) {
            System.out.println("socket exception");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        }
    }

    public void getUsersNames() {
        for (User u : users) {
            System.out.println(u.getName());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();

        server.communicate();
        /*
         * while (true)
         * {
         * server.communicate();
         * server.getUsersNames();
         * }
         */
    }
}