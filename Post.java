import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Post implements Serializable{
    private static final long serialVersionUID = 6969696969L;
    
    public static final int IMAGE_WIDTH = 480;
    public static final int IMAGE_HEIGHT = 720;
    
    private static int rollingId = 0;
    private int individualId;
    private String poster;

    private String category;
    private int[][] heatmap;
    private Image picture;
    private int likes;
    // Add timestamp here

    public Post(String category, String poster, Image picture)
    {
        individualId = rollingId;
        rollingId++;
        
        this.category = category;
        this.poster = poster;
        
        heatmap = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
        this.picture = picture;
        likes = 0;
    }
    
    public int getId(){
        return individualId;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public String getPoster()
    {
        return poster;
    }
    
    public int getLikes()
    {
        return likes;
    }
    
    public void addLike()
    {
        likes++;
    }
    
    // highlight picture to modify heatmap

}
