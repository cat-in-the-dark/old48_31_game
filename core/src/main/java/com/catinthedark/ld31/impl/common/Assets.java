package com.catinthedark.ld31.impl.common;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
    private interface Initable {
        public void init();
    }

    public static class Audios implements Initable {

        @Override
        public void init() {

        }

    }

    public static class Textures implements Initable {


        @Override
        public void init() {

        }
    }

    public static class Fonts implements Initable {

        public BitmapFont hudFont;

        @Override
        public void init() {


        }

    }

    public static class Animations implements Initable {




        @Override
        public void init() {


        }

    }


    public static void init() {

    }
}
