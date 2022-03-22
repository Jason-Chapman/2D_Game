package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest3 extends SuperObject{

    public OBJ_Chest3(){

        name = "Chest3";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/ChestShut.png"));

        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
