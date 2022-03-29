package com.company.entity;


import com.company.GamePanel;
import com.company.KeyHandler;
import object.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import static com.company.KeyHandler.stats;

public class Player extends Entity {


    KeyHandler KeyH;
    Random random = new Random();
    int floorCount = 1;
    int highRoomCount = 5;
    int lowRoomCount = 1;
    int floorRoomCount = 1;
    int maxRoomCount = 3;
    boolean lockedOpen = false;
    boolean regularOpen = false;
    boolean regularOpen2 = false;
    boolean regularOpen3 = false;
    int cooldown = 0;

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

                            for (int j = 0; j < 1000; j++) {
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
                                for (int j = 0; j < 1000; j++) {
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
                                int temp = 5;
                                floorRoomCount--;
                                gp.ui.floorRoomCount = floorRoomCount;
                                worldX = 1100;
                                worldY = 1000;
                                for (int j = 0; j < 1000; j++) {
                                    gp.obj[j]= null; // REMOVES ALL OLD ROOM OBJECTS
                                }

                                gp.obj[1]= new OBJ_Key();
                                gp.obj[1].worldX = 28* gp.tileSize;
                                gp.obj[1].worldY = 24*gp.tileSize;

                                gp.obj[2]  = new OBJ_Door();
                                gp.obj[2].worldX = 42 * gp.tileSize;
                                gp.obj[2].worldY = 8*gp.tileSize;

                                gp.obj[3]= new OBJ_Key();
                                gp.obj[3].worldX = 36* gp.tileSize;
                                gp.obj[3].worldY = 14*gp.tileSize;

                                gp.obj[4]= new OBJ_Key();
                                gp.obj[4].worldX = 32* gp.tileSize;
                                gp.obj[4].worldY = 12*gp.tileSize;

                                for (int j = 23; j < 38; j++) {
                                    for (int k = 7; k < 27; k++) {
                                        if (((k == 8) && ((j>=28 && j<=31) || (j>=34 && j<=37)))
                                                || ((k == 9) && ((j==28) || (j>=31 && j<=34)))
                                                || ((k == 10) && (j==28))
                                                || ((k == 11) && ((j==28) || (j>=31 && j<=33)))
                                                || ((k == 12) && ((j>=26 && j<=33)))
                                                || ((k == 13) && ((j==26) || (j>=31 && j<=33) || (j>=35 && j<=37)))
                                                || ((k == 14) && ((j==26) || (j>=35 && j<=37)))
                                                || ((k == 15) && ((j==26) || (j>=35 && j<=37)))
                                                || ((k == 16) && ((j>=26 && j<=32) || (j==36)))
                                                || ((k == 17) && ((j==32) || (j>=35 && j<=36)))
                                                || ((k == 18) && ((j==32) || (j==35)))
                                                || ((k == 19) && ((j==32) || (j>=35 && j<=36)))
                                                || ((k == 20) && ((j==36 || j==32)))
                                                || ((k == 21) && ((j>=23 && j<=33) || (j==36)))
                                                || ((k == 22) && (j>=33 && j<=36))
                                                || ((k == 23) && ((j>=27 && j<=29) || (j==33)))
                                                || ((k == 24) && ((j>=27 && j<=33)))
                                                || ((k == 25) && ((j>=27 && j<=29)))) {
                                        }
                                        else {
                                            gp.obj[temp]= new OBJ_Spikes();
                                            gp.obj[temp].worldX = (j)* gp.tileSize;
                                            gp.obj[temp].worldY = (k)*gp.tileSize;
                                        }
                                        temp++;
                                    }
                                }

                                gp.tileM.loadMap("/maps/TrapRoom1.txt");
                            }
                            else if (nextRoom == 5) { //TRAP ROOM CHOSEN
                                floorRoomCount--;
                                gp.ui.floorRoomCount = floorRoomCount;
                                worldX = 1100;
                                worldY = 1000;
                                for (int j = 0; j < 1000; j++) {
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
                                floorRoomCount--;
                                gp.ui.floorRoomCount = floorRoomCount;
                                worldX = 1100;
                                worldY = 1000;
                                for (int j = 0; j < 1000; j++) {
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
                    gp.playSE(5);
                    highRoomCount++;
                    lowRoomCount++;

                    worldX = 1100;
                    worldY = 1000;

                    for (int j = 0; j < 1000; j++) {
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

                    floorCount++;
                    gp.ui.floor = floorCount;
                    maxRoomCount = random.nextInt(highRoomCount - lowRoomCount) + lowRoomCount;
                    gp.ui.maxFloorRoomCount = maxRoomCount;
                    gp.ui.floorRoomCount = maxRoomCount;
                    floorRoomCount = maxRoomCount;

                    gp.tileM.loadMap("/maps/StartRoom.txt");
                    break;

                case "LockedChest":
                    if (lockedOpen == true) {
                        gp.ui.showMessage("You have opened this Chest.");
                    }
                    else if (hasKey >0 && lockedOpen == false){
                        gp.playSE(7);
                        try {
                            int chestDrop = random.nextInt(3);

                            if (chestDrop == 0) {
                                gp.obj[0] = new OBJ_HealthIncrease();
                                gp.obj[0].worldX = 30 * gp.tileSize;
                                gp.obj[0].worldY = 21*gp.tileSize;
                            }
                            else if (chestDrop == 1) {
                                gp.obj[0] = new OBJ_Sword();
                                gp.obj[0].worldX = 30 * gp.tileSize;
                                gp.obj[0].worldY = 21*gp.tileSize;
                            }
                            else if (chestDrop == 2) {
                                gp.obj[0] = new OBJ_Shield();
                                gp.obj[0].worldX = 30 * gp.tileSize;
                                gp.obj[0].worldY = 21*gp.tileSize;
                            }
                            gp.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/objects/LockedChestOpen.png"));
                            gp.ui.showMessage("You have opened this Chest.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        hasKey--;
                        lockedOpen = true;
                    }
                    else if (hasKey == 0 && lockedOpen == false){
                        gp.ui.showMessage("You need a Key.");
                    }
                    break;

                case "Chest":
                    if (gp.obj[i].worldX == 28 * gp.tileSize) {
                        if (regularOpen == true) {
                            try {
                                gp.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/objects/ChestOpen.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            gp.ui.showMessage("You have opened this Chest.");
                        }
                        else if (regularOpen == false){
                            gp.playSE(6);
                            try {
                                gp.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/objects/ChestOpen.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            regularOpen = true;
                            int chestDrop = random.nextInt(9);

                            if (chestDrop == 0) {
                                gp.obj[0] = new OBJ_HealthIncrease();
                                gp.obj[0].worldX = 30 * gp.tileSize;
                                gp.obj[0].worldY = 21*gp.tileSize;
                            }
                            if (chestDrop == 1) {
                                gp.obj[0] = new OBJ_Sword();
                                gp.obj[0].worldX = 30 * gp.tileSize;
                                gp.obj[0].worldY = 21*gp.tileSize;
                            }
                            if (chestDrop == 2) {
                                gp.obj[0] = new OBJ_Shield();
                                gp.obj[0].worldX = 30 * gp.tileSize;
                                gp.obj[0].worldY = 21*gp.tileSize;
                            }
                            else if (chestDrop != 0) {
                                chestDrop = random.nextInt(3);
                                if (chestDrop == 0) {
                                    gp.obj[0] = new OBJ_HealthPotion();
                                    gp.obj[0].worldX = 30 * gp.tileSize;
                                    gp.obj[0].worldY = 21 * gp.tileSize;
                                }
                                if (chestDrop == 1) {
                                    gp.obj[0] = new OBJ_Bolt();
                                    gp.obj[0].worldX = 30 * gp.tileSize;
                                    gp.obj[0].worldY = 21 * gp.tileSize;
                                }
                                if (chestDrop == 2) {
                                    gp.obj[0] = new OBJ_Key();
                                    gp.obj[0].worldX = 30 * gp.tileSize;
                                    gp.obj[0].worldY = 21 * gp.tileSize;
                                }
                            }
                        }
                    }
                    if (gp.obj[i].worldX == 30 * gp.tileSize) {
                        if (regularOpen2 == true) {
                            try {
                                gp.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/objects/ChestOpen.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            gp.ui.showMessage("You have opened this Chest.");
                        }
                        else if (regularOpen2 == false){
                            gp.playSE(6);
                            try {
                                gp.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/objects/ChestOpen.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            regularOpen2 = true;
                            int chestDrop = random.nextInt(9);

                            if (chestDrop == 0) {
                                gp.obj[0] = new OBJ_HealthIncrease();
                                gp.obj[0].worldX = 30 * gp.tileSize;
                                gp.obj[0].worldY = 21*gp.tileSize;
                            }
                            if (chestDrop == 1) {
                                gp.obj[0] = new OBJ_Sword();
                                gp.obj[0].worldX = 30 * gp.tileSize;
                                gp.obj[0].worldY = 21*gp.tileSize;
                            }
                            if (chestDrop == 2) {
                                gp.obj[0] = new OBJ_Shield();
                                gp.obj[0].worldX = 30 * gp.tileSize;
                                gp.obj[0].worldY = 21*gp.tileSize;
                            }
                            else if (chestDrop != 0) {
                                chestDrop = random.nextInt(3);
                                if (chestDrop == 0) {
                                    gp.obj[0] = new OBJ_HealthPotion();
                                    gp.obj[0].worldX = 30 * gp.tileSize;
                                    gp.obj[0].worldY = 21 * gp.tileSize;
                                }
                                if (chestDrop == 1) {
                                    gp.obj[0] = new OBJ_Bolt();
                                    gp.obj[0].worldX = 30 * gp.tileSize;
                                    gp.obj[0].worldY = 21 * gp.tileSize;
                                }
                                if (chestDrop == 2) {
                                    gp.obj[0] = new OBJ_Key();
                                    gp.obj[0].worldX = 30 * gp.tileSize;
                                    gp.obj[0].worldY = 21 * gp.tileSize;
                                }
                            }
                        }
                    }
                    if (gp.obj[i].worldX == 32 * gp.tileSize) {
                        if (regularOpen3 == true) {
                            try {
                                gp.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/objects/ChestOpen.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            gp.ui.showMessage("You have opened this Chest.");
                        }
                        else if (regularOpen3 == false){
                            gp.playSE(6);
                            try {
                                gp.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/objects/ChestOpen.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            regularOpen3 = true;
                            int chestDrop = random.nextInt(9);

                            if (chestDrop == 0) {
                                gp.obj[0] = new OBJ_HealthIncrease();
                                gp.obj[0].worldX = 30 * gp.tileSize;
                                gp.obj[0].worldY = 21*gp.tileSize;
                            }
                            if (chestDrop == 1) {
                                gp.obj[0] = new OBJ_Sword();
                                gp.obj[0].worldX = 30 * gp.tileSize;
                                gp.obj[0].worldY = 21*gp.tileSize;
                            }
                            if (chestDrop == 2) {
                                gp.obj[0] = new OBJ_Shield();
                                gp.obj[0].worldX = 30 * gp.tileSize;
                                gp.obj[0].worldY = 21*gp.tileSize;
                            }
                            else if (chestDrop != 0) {
                                chestDrop = random.nextInt(3);
                                if (chestDrop == 0) {
                                    gp.obj[0] = new OBJ_HealthPotion();
                                    gp.obj[0].worldX = 30 * gp.tileSize;
                                    gp.obj[0].worldY = 21 * gp.tileSize;
                                }
                                if (chestDrop == 1) {
                                    gp.obj[0] = new OBJ_Bolt();
                                    gp.obj[0].worldX = 30 * gp.tileSize;
                                    gp.obj[0].worldY = 21 * gp.tileSize;
                                }
                                if (chestDrop == 2) {
                                    gp.obj[0] = new OBJ_Key();
                                    gp.obj[0].worldX = 30 * gp.tileSize;
                                    gp.obj[0].worldY = 21 * gp.tileSize;
                                }
                            }
                        }
                    }
                    break;
                case "HealthPotion":
                    gp.playSE(1);
                    life = maxLife;
                    gp.obj[i]= null;
                    break;
                case "HealthIncrease":
                    gp.playSE(1);
                    maxLife+=2;
                    life+=2;
                    gp.obj[i]= null;
                    break;
                case "Spikes":
                    if (cooldown == 0){
                        life--;
                        try {
                            gp.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/objects/Bloody Spikes.png"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    cooldown++;
                    System.out.println(cooldown);
                    if ((cooldown % 30) == 0){
                        life--;
                        try {
                            gp.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/objects/Bloody Spikes.png"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "Sword":
                    stats.setDmgLower(stats.getDmgLower()+2);
                    stats.setDmgUpper(stats.getDmgUpper()+2);
                    gp.obj[i] = null;
                    break;
                case "Shield":
                    stats.setDefense(stats.getDefense()+1);
                    gp.obj[i] = null;
                    break;
                    }
                }
            }
    public void interactNPC(int i){
        if(i != 999){
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.fightState;
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