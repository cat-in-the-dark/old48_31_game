package com.catinthedark.ld31.impl.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
        public TextureRegion[][] runningStringTR;
        public Texture roomTex;

        @Override
        public void init() {
            fistLeftTex = new Texture(Gdx.files.internal("texture/fist_left.png"));
            fistTopTex = new Texture(Gdx.files.internal("texture/fist_top.png"));
            Texture runningStringTex = new Texture(Gdx.files.internal("texture/lenta.png"));
            runningStringTR = TextureRegion.split(runningStringTex, 32, 32); //156 x 4 tiles (normal + glithed versions)
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
