package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Spikes extends SuperObject {

    public OBJ_Spikes(){

        name = "Spikes";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Spikes.png"));

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
