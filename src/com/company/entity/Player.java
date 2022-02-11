package com.company.entity;


import com.company.GamePanel;
import com.company.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler KeyH;

    public Player(GamePanel gp, KeyHandler KeyH) {

        this.gp = gp;
        this.KeyH= KeyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
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
                y -= speed;
            }
            else if (KeyH.downPressed == true) {
                direction = "down";
                y += speed;
            }
            else if (KeyH.leftPressed == true) {
                direction = "left";
                x -= speed;
            }
            else if (KeyH.rightPressed == true) {
                direction = "right";
                x += speed;
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
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}