package com.catinthedark.ld31;

import com.catinthedark.ld31.lib.run.Launcher;
import org.junit.Test;

public class ResearchTest {
    @Test
    public void test() throws InterruptedException {
        System1Def sys1 = new System1Def();
        System2Def sys2 = new System2Def();

        sys2.messagePipe.connect(sys1.messagePoll);

        Launcher.inThread(sys2);
        Launcher.inThread(sys1).join();
    }
}