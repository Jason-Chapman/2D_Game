package com.company.monster;

import com.company.GamePanel;
import com.company.entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp){
        super(gp);


        speed = 1;
        maxLife = 4;
        life = maxLife;
        direction = "down";
        getImage();



    }
    public void getImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieDagger1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieDagger2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieDaggerAttack1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieDaggerAttack2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieClub 1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieClub2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieClubattack1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieDaggerAttack2.png"));
        }

        catch(IOException e){
            e.printStackTrace();
        }


    }
}
