package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Shield extends SuperObject{

    public OBJ_Shield(){

        name = "Shield";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/shield.png"));

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
