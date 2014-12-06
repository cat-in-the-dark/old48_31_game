package com.catinthedark.ld31.impl.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

        public Texture fistLeftTex;
        public Texture fistTopTex;
        public Texture runningStringTex;
        public Texture roomTex;

        @Override
        public void init() {
            fistLeftTex = new Texture(Gdx.files.internal("texture/fist_left.png"));
            fistTopTex = new Texture(Gdx.files.internal("texture/fist_top.png"));
            runningStringTex = new Texture(Gdx.files.internal("texture/running_string.png"));
            roomTex = new Texture(Gdx.files.internal("texture/room.png"));
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

    public static Audios audios = new Audios();
    public static Textures textures = new Textures();
    public static Fonts fonts = new Fonts();
    public static Animations animations = new Animations();


    public static void init() {
        audios.init();
        textures.init();
        fonts.init();
        animations.init();
    }
}
