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
        public Texture runningStringTex;
        public Texture childTexture;
        public TextureRegion[][] runningStringTR;
        public Texture roomTex;
        public Texture logo;
        public Texture t1;
        public Texture t2;
        public Texture t3;
        public Texture menu;
        public Texture gameWin;
        public Texture gameOver;

        @Override
        public void init() {
            fistLeftTex = new Texture(Gdx.files.internal("texture/fist_left.png"));
            fistTopTex = new Texture(Gdx.files.internal("texture/fist_top.png"));
            childTexture = new Texture(Gdx.files.internal("texture/child.png"));
            runningStringTex = new Texture(Gdx.files.internal("texture/running_string.png"));
            Texture runningStringTex = new Texture(Gdx.files.internal("texture/lenta.png"));
            runningStringTR = TextureRegion.split(runningStringTex, 32, 32); //156 x 4 tiles (normal + glithed versions)
            roomTex = new Texture(Gdx.files.internal("texture/room.png"));
            logo = new Texture(Gdx.files.internal("texture/logo.png"));
            t1 = new Texture(Gdx.files.internal("texture/t1.png"));
            t2 = new Texture(Gdx.files.internal("texture/t2.png"));
            t3 = new Texture(Gdx.files.internal("texture/t3.png"));
            menu = new Texture(Gdx.files.internal("texture/menu.png"));
            gameOver = new Texture(Gdx.files.internal("texture/gameover.png"));
            gameWin = new Texture(Gdx.files.internal("texture/gamewin.png"));
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
