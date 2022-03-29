package com.company;

import com.company.entity.NPC_OldMan;
import com.company.monster.MON_GreenSlime;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;

    }

    public void setObject() {

        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 17 * gp.tileSize;

        gp.obj[8] = new OBJ_Sword();
        gp.obj[8].worldX = 26 * gp.tileSize;
        gp.obj[8].worldY = 18 * gp.tileSize;

        gp.obj[11] = new OBJ_Shield();
        gp.obj[11].worldX = 20 * gp.tileSize;
        gp.obj[11].worldY = 18 * gp.tileSize;

        gp.obj[1] = new OBJ_Door();
        gp.obj[1].worldX = 29 * gp.tileSize;
        gp.obj[1].worldY = 21 * gp.tileSize;

        gp.obj[5] = new OBJ_HealthPotion();
        gp.obj[5].worldX = gp.tileSize * 26;
        gp.obj[5].worldY = gp.tileSize * 24;

        gp.obj[6] = new OBJ_HealthIncrease();
        gp.obj[6].worldX = gp.tileSize * 20;
        gp.obj[6].worldY = gp.tileSize * 24;

        gp.obj[7] = new OBJ_Spikes();
        gp.obj[7].worldX = gp.tileSize * 23;
        gp.obj[7].worldY = gp.tileSize * 25;
    }

    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 19;
        gp.npc[0].worldY = gp.tileSize * 21;
    }
    public void setMon(){
        gp.npc[1] = new MON_GreenSlime(gp);
        gp.npc[1].worldX = gp.tileSize * 20;
        gp.npc[0].worldY = gp.tileSize * 21;

    }
}
