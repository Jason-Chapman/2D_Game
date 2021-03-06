package com.company;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {


    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    BufferedImage heart_full,heart_half,heart_blank;
    BufferedImage keyImage;
    private static boolean messageOn = false;
    private static String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int floor = 1;
    public int floorRoomCount = 1;
    public int maxFloorRoomCount = 1;
    public int commandNum = 0;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;

        //CREATE HUD OBJECT
        SuperObject heart = new OBJ_Heart(gp) ;
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

    }

    public static void showMessage(String text) {

        message = text;
        messageOn = true;

    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;
        if (gameFinished == true) {

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You found the treasure!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            text = "Your Time is :" + dFormat.format(playTime) + "!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 4);
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "Congratulations!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);

            gp.gameThread = null;


        } else {


            g2.setFont(arial_40);
            g2.setColor(Color.white);
            // PLAY STATE


            if (gp.gameState == gp.playState) {
                g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2 *3 , gp.tileSize, gp.tileSize, null);
                g2.drawString("x  " + gp.player.hasKey, 74, 110);


                drawPlayerLife();

                //TIME
                playTime += (double) 1 / 60;
                g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11, 135);

                //FLOOR
                g2.drawString("Floor "+floor, gp.tileSize * 11, 65);

                //ROOM
                g2.drawString("Room "+(maxFloorRoomCount - floorRoomCount)+"/"+(maxFloorRoomCount + 1), gp.tileSize * 11, 100);

                //MESSAGE
                if (messageOn) {

                    g2.setFont(g2.getFont().deriveFont(30F));
                    g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                    messageCounter++;


                    if (messageCounter > 120) {
                        messageCounter = 0;
                        messageOn = false;

                    }
                }
            }
            // TITLE STATE
            if(gp.gameState == gp.titleState){
                drawTitleScreen();
            }
            //PAUSE STATE
            if (gp.gameState == gp.pauseState) {
                drawPlayerLife();
                drawPauseScreen();

            }
            //DIALOGUE STATE
            if(gp.gameState == gp.dialogueState){
                drawPlayerLife();
                drawDialogueScreen();
            }
            // FIGHT STATE
            if(gp.gameState == gp.fightState){
                drawFightScreen();
            }
            // POST FIGHT STATE
            if(gp.gameState == gp.fightEndState){
                drawEndBattleScrren();
            }
            if(gp.player.life==0){
                gp.gameState= gp.deathState;

            }
            if (gp.gameState==gp.deathState){
                drawDeathScreen();
                gp.player.life=gp.player.maxLife;
            }


        }
    }
    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        while (i < gp.player.maxLife/2) {

            //DRAW BLANK HEART

            g2.drawImage(heart_blank, x , y,gp.tileSize,gp.tileSize, null);
            i++;
            x+=gp.tileSize;
        }
        //RESET
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //DRAW CURRENT LIFE
        while(i<gp.player.life){
            g2.drawImage(heart_half,x,y,gp.tileSize,gp.tileSize,null);
            i++;
            if(i<gp.player.life){
                g2.drawImage(heart_full,x,y,gp.tileSize,gp.tileSize,null);
            }
            i++;
            x+= gp.tileSize;
        }
    }
    public void drawTitleScreen(){
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "Lute Quest";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;
        // SHADOW COLOUR
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);

        // MAIN COLOUR
        g2.setColor(Color.white);
        g2.drawString(text, x,y);

        // CHARACTER IMAGE
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x,y,gp.tileSize*2,gp.tileSize*2,null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize*3.5;
        g2.drawString(text,x,y);
        if(commandNum == 0) {
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 1) {
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 2) {
            g2.drawString(">",x-gp.tileSize,y);
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);


        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }
    public void drawFightScreen(){
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "FIGHT!";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;
        // SHADOW COLOUR
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);

        // MAIN COLOUR
        g2.setColor(Color.white);
        g2.drawString(text, x,y);

        // CHARACTER IMAGE
        x = gp.screenWidth/2 - (gp.tileSize*2)*2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x,y,gp.tileSize*2,gp.tileSize*2,null);

        // PLAYER HEALTH
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,16F));
        text = "player life : "+gp.player.life;
        y-=10;
        g2.drawString(text,x,y);

        // ENEMY IMAGE
        x =+ gp.tileSize*10;
        g2.drawImage(gp.npc[1].left1, x,y,gp.tileSize*2,gp.tileSize*2,null);
        //ENEMY LIFE
        text = "enemy life : "+gp.npc[0].life;

        g2.drawString(text,x,y);



        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));




        text = "ATTACK";
        x = getXforCenteredText(text);
        y += gp.tileSize*3.5;
        g2.drawString(text,x,y);
        if(commandNum == 0) {
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "GUARD";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 1) {
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "RUN";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 2) {
            g2.drawString(">",x-gp.tileSize,y);
        }
    }
    public void drawEndBattleScrren(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40F));
        String text = "YOU WIN AND OBTAINED A KEY!";
        int x = getXforCenteredText(text);


        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
        gp.player.hasKey=1;

    }
    public void drawDeathScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "YOU DIED!";
        int x = getXforCenteredText(text);


        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }
    public void drawDialogueScreen(){

        //WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x,y,width,height);

        g2.setFont((g2.getFont().deriveFont(Font.PLAIN,30F)));

        x += gp.tileSize;
        y+= gp.tileSize;
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line,x,y);
            y+= 40;
        }

    }
    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);

    }
    public int getXforCenteredText(String text){
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}



