import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 420420420L;

    //private static int rollingId = 0;
    //private int individualId;

    private String name;
    private ArrayList<String> classes;
    private double balance; // for crypto

    // add upload times here
    private ArrayList<Post> posts;

    public User(String name, ArrayList<String> classes) {
        //individualId = rollingId;
        //rollingId++;

        this.name = name;
        this.classes = classes;

        balance = 0.0;
        posts = new ArrayList<Post>();
    }
/*
    public int getId()
    {
        return individualId;
    }
 */   
    public String getName()
    {
        return name;
    }
    
    public double getBalance()
    {
        return balance;
    }
    
    /*
    public void addPost(Post p)
    {
        posts.add(p);
    }
    */

    public void updatePost(Post updated)
    {
        for (int i = 0; i < posts.size(); i++)
        {
            if (posts.get(i).getId() == updated.getId())
            {
                posts.set(i, updated);
                return;
            }
        }
        
        posts.add(updated);
    }
    
    public ArrayList<String> getClasses()
    {
        return classes;
    }
}
