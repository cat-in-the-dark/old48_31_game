package com.catinthedark.ld31;

import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.io.Pipe;

/**
 * Created by over on 08.11.14.
 */
public class System2Def extends AbstractSystemDef {
    private final Sys sys = new Sys();
    public final Pipe<String> messagePipe = new Pipe<>();

    {
        updater(sys::updaterMain);
        masterDelay = 1000;
    }

    private class Sys {
        private void updaterMain(float delay) throws InterruptedException {
            System.out.println("Sys2: Send message to pipe!");
            messagePipe.write("Hi, Over!");
        }
    }
}
