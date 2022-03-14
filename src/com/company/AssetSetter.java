package com.company;

import com.company.entity.NPC_OldMan;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;

    }
    public void setObject(){

        gp.obj[0]= new OBJ_Key();
        gp.obj[0].worldX = 23* gp.tileSize;
        gp.obj[0].worldY = 17*gp.tileSize;

        gp.obj[4]  = new OBJ_Door();
        gp.obj[4].worldX = 29 * gp.tileSize;
        gp.obj[4].worldY = 21*gp.tileSize;
    }
    public void setNPC(){
        gp.npc[0]=new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize* 19;
        gp.npc[0].worldY = gp.tileSize*21;
    }
}
