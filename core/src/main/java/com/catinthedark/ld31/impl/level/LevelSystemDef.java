package com.catinthedark.ld31.impl.level;

import com.catinthedark.ld31.impl.common.DirectionX;
import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.common.Nothing;
import com.catinthedark.ld31.lib.io.Port;

/**
 * Created by over on 06.12.14.
 */
public class LevelSystemDef extends AbstractSystemDef{

    public LevelSystemDef(){
        sys = new Sys();
        masterDelay = 16;
        updater(sys::update);
    }

    final Sys sys;

    private class Sys {
        void update(float delay){

        }

    }
}
