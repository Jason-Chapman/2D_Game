package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class KeyHandler implements KeyListener {
  GamePanel gp;


    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    public KeyHandler (GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // NOT USED
    }

    public static Stats stats = new Stats(3, 1, 0);

    @Override
    public void keyPressed(KeyEvent e) {
        Random random = new Random();

        int code = e.getKeyCode();
        //TITLE STATE
        if(gp.gameState == gp.titleState){
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum<0){
                    gp.ui.commandNum=2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;

                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                }
                if(gp.ui.commandNum == 1){

                }
                if (gp.ui.commandNum==2){
                    System.exit(0);
                }
            }


        }
        //PLAY STATE
        if (gp.gameState == gp.playState) {

            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }


        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;

            }
        }
        //DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }
        // FIGHT END STATE
        else if(gp.gameState == gp.fightEndState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }
        // DEATH STATE
        else if(gp.gameState == gp.deathState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.titleState;
            }
        }

        // FIGHT STATE
        else if(gp.gameState == gp.fightState){
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum<0){
                    gp.ui.commandNum=2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;

                }
            }
            if (code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){ //ATTACK
                    int i = random.nextInt(stats.getDmgUpper() - stats.getDmgLower()) + stats.getDmgLower(); //DAMAGE DEALT
                    gp.npc[0].life -= i;
                    gp.gameState = gp.pauseState;
                    gp.gameState = gp.fightState;

                    if (gp.npc[0].life <= 0){
                        gp.npc[0].life = gp.npc[0].maxLife;
                        gp.gameState = gp.fightEndState;
                    }
                    else {
                        i = random.nextInt(2) - stats.getDefense();
                        if (i < 0) {
                            i = 1;
                        }
                        gp.player.life -= i;
                    }
                }
                if (gp.ui.commandNum == 1){ //GUARD
                    int i = random.nextInt(1) - stats.getDefense();
                    if (i < 0) {
                        i = 1;
                    }
                    gp.player.life -= i;
                }
                if (gp.ui.commandNum==2){ //RUN
                    gp.gameState =gp.playState;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
