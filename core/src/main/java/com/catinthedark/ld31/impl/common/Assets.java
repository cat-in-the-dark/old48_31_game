package com.catinthedark.ld31.impl.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Assets {
    private interface Initable {
        public void init();
    }

    public static class Audios implements Initable {

        public Music noise_background;
        public Sound hit_tv;
        public Sound ouch_enemy;
        public Sound ouch_noise;
        public Sound noise_sfx;

        @Override
        public void init() {
            noise_background = Gdx.audio.newMusic(Gdx.files.internal("sound/noise_background.mp3"));
            hit_tv = Gdx.audio.newSound(Gdx.files.internal("sound/hit_tv.mp3"));
            ouch_enemy = Gdx.audio.newSound(Gdx.files.internal("sound/ouch_enemy.mp3"));
            ouch_noise = Gdx.audio.newSound(Gdx.files.internal("sound/ouch_noise.mp3"));
            noise_sfx = Gdx.audio.newSound(Gdx.files.internal("sound/noise_sfx.mp3"));
        }

    }

    public static class Textures implements Initable {

        public Texture fistLeftTex;
        public Texture fistTopTex;
        public Texture runningStringTex;
        public Texture childTexture;
        public Texture pedofil;
        public Texture lady;
        public Texture gop;
        public Texture bottle;
        public TextureRegion[][] runningStringTR;
        public Texture roomTex;
        public Texture logo;
        public Texture t1;
        public Texture t2;
        public Texture t3;
        public Texture menu;
        public Texture gameWin;
        public Texture gameOver;
        public Texture coolDownIndicator;

        @Override
        public void init() {
            fistLeftTex = new Texture(Gdx.files.internal("texture/fist_left.png"));
            fistTopTex = new Texture(Gdx.files.internal("texture/fist_top.png"));
            childTexture = new Texture(Gdx.files.internal("texture/child.png"));
            pedofil = new Texture(Gdx.files.internal("texture/pedofil.png"));
            lady = new Texture(Gdx.files.internal("texture/lady.png"));
            gop = new Texture(Gdx.files.internal("texture/gopstop.png"));
            bottle = new Texture(Gdx.files.internal("texture/bottle.png"));
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
            coolDownIndicator = new Texture(Gdx.files.internal("texture/punch_ready.png"));
        }
    }

    public static class Fonts implements Initable {

        public BitmapFont hudFont;

        @Override
        public void init() {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                    Gdx.files.internal("font/impact.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
            params.size = 20;

            hudFont = generator.generateFont(params);
            hudFont.setColor(Color.WHITE);
            generator.dispose(); // don't forget to dispose to avoid memory
            // leaks!

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
