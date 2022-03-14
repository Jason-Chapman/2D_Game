package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_LockedChest extends SuperObject{

    public OBJ_LockedChest(){

        name = "LockedChest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/LockedChest.png"));

        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
