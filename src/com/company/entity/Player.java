package com.company.entity;


import com.company.GamePanel;
import com.company.KeyHandler;
import object.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Player extends Entity {


    KeyHandler KeyH;
    Random random = new Random();
    int floorCount = 1;
    int highRoomCount = 5;
    int lowRoomCount = 1;
    int floorRoomCount = 1;
    int maxRoomCount = 3;
    boolean open = false;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler KeyH) {
        super(gp);


        this.KeyH= KeyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        //PLAYER HITBOX
        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        // PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/walk 1 up.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/walk 2 up.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/walk 1 down.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/walk 2 down.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/walk 1 left.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/walk 2 left.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/walk 1 right.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/walk 2 right.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update() {

        if (KeyH.upPressed == true || KeyH.downPressed == true || KeyH.leftPressed == true || KeyH.rightPressed == true) {
            if (KeyH.upPressed == true) {
                direction = "up";
            }
            else if (KeyH.downPressed == true) {
                direction = "down";
            }
            else if (KeyH.leftPressed == true) {
                direction = "left";
            }
            else if (KeyH.rightPressed == true) {
                direction = "right";
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this,true);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false) {
                switch(direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject (int i){

        if (i != 999){ //id not 999 then have touched an object
            String objectName = gp.obj[i].name;

            switch (objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i]= null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if (hasKey >0){
                        gp.playSE(3);

                        if (floorRoomCount == 0) {
                            floorRoomCount--;
                            gp.ui.floorRoomCount = floorRoomCount;
                            worldX = 1100;
                            worldY = 1000;

                            for (int j = 0; j < 8; j++) {
                                gp.obj[j]= null; // REMOVES ALL OLD ROOM OBJECTS
                            }

                            gp.obj[0]= new OBJ_Ladder();
                            gp.obj[0].worldX = 35* gp.tileSize;
                            gp.obj[0].worldY = 21*gp.tileSize;

                            gp.tileM.loadMap("/maps/EndRoom.txt");
                            hasKey--;
                            gp.ui.showMessage("You opened the door!");
                        }
                        else {
                            int nextRoom = random.nextInt(7); // CHANGE 3 -> (APPROPRIATE NUMBER), WHEN ADDING NEW DUNGEON ROOMS

                            if (nextRoom <= 3) { //COMBAT ROOM CHOSEN
                                floorRoomCount--;
                                gp.ui.floorRoomCount = floorRoomCount;
                                worldX = 1100;
                                worldY = 1000;
                                for (int j = 0; j < 8; j++) {
                                    gp.obj[j]= null; // REMOVES ALL OLD ROOM OBJECTS
                                }

                                gp.obj[1]= new OBJ_Key();
                                gp.obj[1].worldX = 30* gp.tileSize;
                                gp.obj[1].worldY = 21*gp.tileSize;

                                gp.obj[2]  = new OBJ_Door();
                                gp.obj[2].worldX = 38 * gp.tileSize;
                                gp.obj[2].worldY = 21*gp.tileSize;

                                gp.tileM.loadMap("/maps/CombatRoom.txt");
                            }
                            else if (nextRoom == 4) { //TRAP ROOM CHOSEN
                                floorRoomCount--;
                                gp.ui.floorRoomCount = floorRoomCount;
                                worldX = 1100;
                                worldY = 1000;
                                for (int j = 0; j < 8; j++) {
                                    gp.obj[j]= null; // REMOVES ALL OLD ROOM OBJECTS
                                }

                                gp.obj[1]= new OBJ_Key();
                                gp.obj[1].worldX = 23* gp.tileSize;
                                gp.obj[1].worldY = 23*gp.tileSize;

                                gp.obj[2]  = new OBJ_Door();
                                gp.obj[2].worldX = 42 * gp.tileSize;
                                gp.obj[2].worldY = 8*gp.tileSize;

                                gp.tileM.loadMap("/maps/TrapRoom1.txt");
                            }
                            else if (nextRoom == 5) { //TRAP ROOM CHOSEN
                                floorRoomCount--;
                                gp.ui.floorRoomCount = floorRoomCount;
                                worldX = 1100;
                                worldY = 1000;
                                for (int j = 0; j < 8; j++) {
                                    gp.obj[j]= null; // REMOVES ALL OLD ROOM OBJECTS
                                }

                                gp.obj[1]= new OBJ_Key();
                                gp.obj[1].worldX = 23* gp.tileSize;
                                gp.obj[1].worldY = 23*gp.tileSize;

                                gp.obj[2]  = new OBJ_Door();
                                gp.obj[2].worldX = 42 * gp.tileSize;
                                gp.obj[2].worldY = 34*gp.tileSize;

                                gp.tileM.loadMap("/maps/TrapRoom2.txt");
                            }
                            else if (nextRoom == 6) { //TREASURE ROOM CHOSEN
                                hasKey = 1;
                                floorRoomCount--;
                                gp.ui.floorRoomCount = floorRoomCount;
                                worldX = 1100;
                                worldY = 1000;
                                for (int j = 0; j < 8; j++) {
                                    gp.obj[j]= null; // REMOVES ALL OLD ROOM OBJECTS
                                }

                                gp.obj[1]= new OBJ_Key();
                                gp.obj[1].worldX = 23* gp.tileSize;
                                gp.obj[1].worldY = 23*gp.tileSize;

                                gp.obj[2]  = new OBJ_Door();
                                gp.obj[2].worldX = 38 * gp.tileSize;
                                gp.obj[2].worldY = 21*gp.tileSize;

                                gp.obj[3]  = new OBJ_Chest();
                                gp.obj[3].worldX = 30 * gp.tileSize;
                                gp.obj[3].worldY = 23 *gp.tileSize;

                                gp.obj[4]  = new OBJ_Chest();
                                gp.obj[4].worldX = 32 * gp.tileSize;
                                gp.obj[4].worldY = 23 *gp.tileSize;

                                gp.obj[5]  = new OBJ_Chest();
                                gp.obj[5].worldX = 28 * gp.tileSize;
                                gp.obj[5].worldY = 23 *gp.tileSize;

                                gp.obj[6] = new OBJ_LockedChest();
                                gp.obj[6].worldX = 30 * gp.tileSize;
                                gp.obj[6].worldY = 19 *gp.tileSize;

                                gp.tileM.loadMap("/maps/TreasureRoom.txt");
                            }
                            hasKey--;
                            gp.ui.showMessage("You opened the Door!");
                        }

                    }
                    else{
                        gp.ui.showMessage("You need a Key.");
                    }
                    break;
                case "Ladder":
                    highRoomCount++;
                    lowRoomCount++;

                    worldX = 1100;
                    worldY = 1000;

                    for (int j = 0; j < 8; j++) {
                        gp.obj[j]= null; // REMOVES ALL OLD ROOM OBJECTS
                    }

                    gp.obj[1]= new OBJ_Key();
                    gp.obj[1].worldX = 23* gp.tileSize;
                    gp.obj[1].worldY = 17*gp.tileSize;

                    gp.obj[2]  = new OBJ_Door();
                    gp.obj[2].worldX = 29 * gp.tileSize;
                    gp.obj[2].worldY = 21*gp.tileSize;

                    gp.npc[3]=new NPC_OldMan(gp);
                    gp.npc[3].worldX = gp.tileSize* 19;
                    gp.npc[3].worldY = gp.tileSize*21;

                    gp.obj[4]=new OBJ_HealthPotion();
                    gp.obj[4].worldX = gp.tileSize* 23;
                    gp.obj[4].worldY = gp.tileSize*25;

                    floorCount++;
                    gp.ui.floor = floorCount;
                    maxRoomCount = random.nextInt(highRoomCount - lowRoomCount) + lowRoomCount;
                    gp.ui.maxFloorRoomCount = maxRoomCount;
                    gp.ui.floorRoomCount = maxRoomCount;
                    floorRoomCount = maxRoomCount;

                    gp.tileM.loadMap("/maps/StartRoom.txt");
                    break;

                case "LockedChest":
                    if (open == true) {
                        gp.ui.showMessage("You have opened this Chest.");
                    }
                    else if (hasKey >0 && open == false){
                        try {
                            gp.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/objects/LockedChestOpen.png"));
                            gp.ui.showMessage("You have opened this Chest.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //DROP LOOT
                        hasKey--;
                        open = true;
                    }
                    else if (hasKey == 0 && open == false){
                        gp.ui.showMessage("You need a Key.");
                    }
                    break;

                case "Chest":
                    try {
                        gp.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/objects/ChestOpen.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    gp.ui.showMessage("You have opened this Chest.");
                    break;
                    //DROP LOOT
                case "HealthPotion":
                    life = maxLife;
                    gp.obj[i]= null;
                    System.out.println("max: "+maxLife);
                    System.out.println("current: "+life);
                    break;
                case "HealthIncrease":
                    maxLife++;
                    gp.obj[i]= null;
                    System.out.println("max: "+maxLife);
                    System.out.println("current: "+life);
                case "Spikes":
                    System.out.println(gp.getFPS());
//                    gp.setFPS(60);
                    System.out.println(gp.getFPS());
                    life--;
                    System.out.println("max: "+maxLife);
                    System.out.println("current: "+life);
                    }
                }
            }
    public void interactNPC(int i){
        if(i != 999){
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }

        }
        gp.keyH.enterPressed = false;
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}