package com.catinthedark.ld31;

import com.catinthedark.ld31.lib.AbstractSystemDef;
import com.catinthedark.ld31.lib.io.Port;

/**
 * Created by over on 08.11.14.
 */
public class System1Def extends AbstractSystemDef {
    @Override
    public void onStart() {
        System.out.println("Sys1: Wait for message...");
    }

    public final Port<String> messagePoll = asyncPort(new Sys()::onMessage);

    private class Sys {
        private void onMessage(String msg) throws InterruptedException {
            System.out.println("Sys1: Got message: " + msg);
            System.out.println("Sys1: Stopping sys thread!");
            throw new InterruptedException("well done!");
        }
    }
}
