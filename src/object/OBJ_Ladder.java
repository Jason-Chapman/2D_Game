package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Ladder extends SuperObject {

    public OBJ_Ladder(){

        name = "Ladder";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Ladder.png"));

        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
