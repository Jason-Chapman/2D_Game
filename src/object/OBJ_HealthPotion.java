package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_HealthPotion extends SuperObject {


    public OBJ_HealthPotion() {

        name = "HealthPotion";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/health_potion.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
