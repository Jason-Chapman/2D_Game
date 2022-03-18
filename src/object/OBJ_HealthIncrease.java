package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_HealthIncrease extends SuperObject {


    public OBJ_HealthIncrease(){

        name = "HealthIncrease";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/heart.png"));

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
