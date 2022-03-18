package com.company;

import com.company.entity.NPC_OldMan;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;

    }
    public void setObject(){

        gp.obj[0]= new OBJ_Key();
        gp.obj[0].worldX = 23* gp.tileSize;
        gp.obj[0].worldY = 17*gp.tileSize;

        gp.obj[1]  = new OBJ_Door();
        gp.obj[1].worldX = 29 * gp.tileSize;
        gp.obj[1].worldY = 21*gp.tileSize;

        gp.obj[2]=new OBJ_HealthPotion();
        gp.obj[2].worldX = gp.tileSize* 23;
        gp.obj[2].worldY = gp.tileSize*25;

        gp.obj[4]=new OBJ_HealthPotion();
        gp.obj[4].worldX = gp.tileSize* 24;
        gp.obj[4].worldY = gp.tileSize*25;

        gp.obj[5]=new OBJ_HealthPotion();
        gp.obj[5].worldX = gp.tileSize* 25;
        gp.obj[5].worldY = gp.tileSize*25;

        gp.obj[3]=new OBJ_HealthIncrease();
        gp.obj[3].worldX = gp.tileSize* 19;
        gp.obj[3].worldY = gp.tileSize*25;

        gp.obj[6]=new OBJ_HealthIncrease();
        gp.obj[6].worldX = gp.tileSize* 18;
        gp.obj[6].worldY = gp.tileSize*25;
    }
    public void setNPC(){
        gp.npc[0]=new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize* 19;
        gp.npc[0].worldY = gp.tileSize*21;
    }
}
