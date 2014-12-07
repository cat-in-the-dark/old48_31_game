package com.catinthedark.ld31.impl.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;
import java.util.List;

public class Assets {
    private interface Initable {
        public void init();
    }

    public static class Audios implements Initable {

        public Music noise_background;
        public Music bgm;
        public Sound hit_tv;
        public Sound ouch_enemy;
        public Sound ouch_noise;
        public Sound noise_sfx;

        @Override
        public void init() {
            noise_background = Gdx.audio.newMusic(Gdx.files.internal("sound/noise_background.mp3"));
            bgm = Gdx.audio.newMusic(Gdx.files.internal("sound/bgm.mp3"));
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
        public Texture coolDownIndicatorCol;
        public Texture coolDownIndicatorRow;
        public TextureRegion gopFrames[][];
        public Texture gopAnimFrame;
        public Texture bgTex;

        @Override
        public void init() {
            fistLeftTex = new Texture(Gdx.files.internal("texture/fist_left.png"));
            fistTopTex = new Texture(Gdx.files.internal("texture/fist_top.png"));
            childTexture = new Texture(Gdx.files.internal("texture/child.png"));
            pedofil = new Texture(Gdx.files.internal("texture/pedofil.png"));
            lady = new Texture(Gdx.files.internal("texture/lady.png"));
            gop = new Texture(Gdx.files.internal("texture/gopstop.png"));
            gopAnimFrame = new Texture(Gdx.files.internal("texture/gopstop_animation.png"));
            bottle = new Texture(Gdx.files.internal("texture/bottle.png"));
            runningStringTex = new Texture(Gdx.files.internal("texture/running_string.png"));
            Texture runningStringTex = new Texture(Gdx.files.internal("texture/lenta.png"));
            runningStringTR = TextureRegion.split(runningStringTex, 32, 32); //156 x 4 tiles (normal + glithed versions)
            roomTex = new Texture(Gdx.files.internal("texture/room.png"));
            logo = new Texture(Gdx.files.internal("texture/logo.png"));
            t1 = new Texture(Gdx.files.internal("texture/menu.png"));
            t2 = new Texture(Gdx.files.internal("texture/t1.png"));
            t3 = new Texture(Gdx.files.internal("texture/t2.png"));
            menu = new Texture(Gdx.files.internal("texture/t3.png"));
            gameOver = new Texture(Gdx.files.internal("texture/gameover.png"));
            gameWin = new Texture(Gdx.files.internal("texture/gamewin.png"));
            coolDownIndicatorCol = new Texture(Gdx.files.internal("texture/punch_ready_col.png"));
            coolDownIndicatorRow = new Texture(Gdx.files.internal("texture/punch_ready_row.png"));
            gopFrames = TextureRegion.split(gopAnimFrame, 120, 128);
            bgTex = new Texture(Gdx.files.internal("texture/bg.png"));
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

        private TextureRegion[] selectRegions(TextureRegion[][] frames,
                                              int[] framesNumbers) {
            List<TextureRegion> regions = new ArrayList<TextureRegion>(
                    framesNumbers.length);
            for (int index : framesNumbers) {
                regions.add(frames[0][index]);
            }

            return regions.toArray(new TextureRegion[regions.size()]);
        }

        public Animation gop_anim;

        @Override
        public void init() {
            gop_anim = new Animation(0.05f, selectRegions(textures.gopFrames, new int[] { 0, 1, 2,
                    3, 4, 5, 6, 7, 8, 9 }));
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
