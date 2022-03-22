package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Bolt extends SuperObject{

    public OBJ_Bolt(){

        name = "Bolt";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/bolt.png"));

        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
