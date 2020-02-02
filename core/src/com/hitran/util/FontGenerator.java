package com.hitran.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontGenerator {
    public static final String ENGLISH_FONT_REGULAR_NAME = "font/CHLORINR.TTF";
    public static final String ENGLISH_FONT_CARBON_NAME = "font/theboldfont.ttf";
    public static final String ENGLISH_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}";

    private static FreeTypeFontGenerator generatorRegular;
    private static FreeTypeFontGenerator generatorCarbon;

    public static void init() {
        generatorRegular = new FreeTypeFontGenerator(Gdx.files.internal(ENGLISH_FONT_REGULAR_NAME));
        generatorCarbon = new FreeTypeFontGenerator(Gdx.files.internal(ENGLISH_FONT_CARBON_NAME));
    }

    public static BitmapFont generateFont(String characters, int size) {
        // Configure font parameters
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = characters;
        parameter.size = size;

        return generatorRegular.generateFont(parameter);
    }

    public static BitmapFont generateCarbonFont(String characters, int size) {
        // Configure font parameters
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = characters;
        parameter.size = size;

        return generatorCarbon.generateFont(parameter);
    }

    public static void dispose() {
        generatorRegular.dispose();
        generatorCarbon.dispose();
    }

}
