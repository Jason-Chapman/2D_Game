package com.company.entity;

import com.company.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        maxLife = 4;
        life = maxLife;
        speed = 1;
        getImage();
        setDialogue();

    }
    public void getImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieBow1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieBow2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieBowAttack1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieBowAttack2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieClub 1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieClub2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieClubAttack1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/npc/ZombieClubAttack2.png"));
        }

        catch(IOException e){
            e.printStackTrace();
        }


    }
    public void setDialogue(){

        dialogues[0]="Hello, lad.";
        dialogues[1]="So you've come to this dungeon to \nfind the treasure?";
        dialogues[2]="I used to be a great wizard but now... \nI think I've got lost.";
        dialogues[3]="Well, good luck on you.";
    }
    public void  speak(){

        super.speak();


    }
}
