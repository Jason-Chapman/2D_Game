package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest2 extends SuperObject{

    public OBJ_Chest2(){

        name = "Chest2";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/ChestShut.png"));

        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
