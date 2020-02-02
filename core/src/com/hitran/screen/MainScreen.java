package com.hitran.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hitran.BodyFatShredderGame;
import com.hitran.util.FatShreddingNotifier;
import com.hitran.util.FontGenerator;

public class MainScreen implements Screen {

    private final BodyFatShredderGame game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private BitmapFont headingFont, normalFont, clickToPlayFont;

    private Texture background;

    private Preferences prefs;

    private float viewportX, viewportY, viewposrtWidth, viewportHeight;

    private Rectangle easyButton, mediumButton, hardButton, didItButton, postponeButton;

    private FatShreddingNotifier fatShreddingNotifier;

    public MainScreen(BodyFatShredderGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        Vector2 size = Scaling.fit.apply(BodyFatShredderGame.WIDTH, BodyFatShredderGame.HEIGHT, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewportX = (Gdx.graphics.getHeight() - size.x) / 2;
        viewportY = (Gdx.graphics.getHeight() - size.y) / 2;
        viewposrtWidth = size.x;
        viewportHeight = size.y;

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(BodyFatShredderGame.WIDTH, BodyFatShredderGame.HEIGHT, gameCam);
        gamePort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        gameCam.position.set(gamePort.getWorldWidth()/2 , gamePort.getWorldHeight()/2, 0);

        prefs = Gdx.app.getPreferences("BodyFatShredder");

        background = new Texture(Gdx.files.internal("background.png"));

        headingFont = FontGenerator.generateFont(FontGenerator.ENGLISH_CHARACTERS, 150);
        clickToPlayFont = FontGenerator.generateFont(FontGenerator.ENGLISH_CHARACTERS, 100);
        normalFont = FontGenerator.generateCarbonFont(FontGenerator.ENGLISH_CHARACTERS, 50);

        easyButton = new Rectangle(100f, BodyFatShredderGame.HEIGHT - 900, 300f, 100f);
        mediumButton = new Rectangle(355f, BodyFatShredderGame.HEIGHT - 900, 300f, 100f);
        hardButton = new Rectangle(700f, BodyFatShredderGame.HEIGHT - 900, 300f, 100f);

        didItButton = new Rectangle(365f, BodyFatShredderGame.HEIGHT - 1225, 300f, 100f);
        postponeButton = new Rectangle(335f, BodyFatShredderGame.HEIGHT - 1350, 300f, 100f);

        fatShreddingNotifier = new FatShreddingNotifier();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        game.getBatch().setProjectionMatrix(gameCam.combined);

        game.getBatch().begin();

        game.getBatch().disableBlending();
        game.getBatch().draw(background, 0, 0);
        game.getBatch().enableBlending();

        headingFont.setColor(Color.BLACK);
        headingFont.draw(game.getBatch(), "Fat Shredder", 50, BodyFatShredderGame.HEIGHT - 130);

        normalFont.setColor(Color.BLACK);
        normalFont.draw(game.getBatch(), "Lifetime exercises: ", 20, BodyFatShredderGame.HEIGHT - 290);
        normalFont.setColor(Color.WHITE);
        normalFont.draw(game.getBatch(), "Push ups = " + prefs.getInteger("currentPushUpsCount"), 20, BodyFatShredderGame.HEIGHT - 360);
        normalFont.draw(game.getBatch(), "Squats = " + prefs.getInteger("currentSquatsCount"), 20, BodyFatShredderGame.HEIGHT - 430);
        normalFont.draw(game.getBatch(), "Leg raises = " + prefs.getInteger("currentLegRaisesCount"), 20, BodyFatShredderGame.HEIGHT - 500);
        normalFont.draw(game.getBatch(), "kcal burnt = " + prefs.getInteger("currentKcalCount"), 20, BodyFatShredderGame.HEIGHT - 570);

        clickToPlayFont.setColor(prefs.getInteger("mode") == 1 ? Color.BLACK : Color.LIGHT_GRAY);
        clickToPlayFont.draw(game.getBatch(), "easy", 100, BodyFatShredderGame.HEIGHT - 800);
        clickToPlayFont.setColor(prefs.getInteger("mode") == 2 ? Color.BLACK : Color.LIGHT_GRAY);
        clickToPlayFont.draw(game.getBatch(), "medium", 355, BodyFatShredderGame.HEIGHT - 800);
        clickToPlayFont.setColor(prefs.getInteger("mode") == 3 ? Color.BLACK : Color.LIGHT_GRAY);
        clickToPlayFont.draw(game.getBatch(), "hard", 700, BodyFatShredderGame.HEIGHT - 800);

        clickToPlayFont.setColor(Color.GREEN);
        clickToPlayFont.draw(game.getBatch(), "I did it", 365, BodyFatShredderGame.HEIGHT - 1125);

        clickToPlayFont.setColor(Color.FIREBRICK);
        clickToPlayFont.draw(game.getBatch(), "Postpone", 335, BodyFatShredderGame.HEIGHT - 1250);

        game.getBatch().end();
    }

    private void update(float delta) {
        gameCam.update();

        fatShreddingNotifier.update(delta);
        handleInput(delta);
    }

    private void handleInput(float delta) {
        if (Gdx.input.justTouched()) {
            Vector3 press = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Vector3 camCoordinates = gameCam.unproject(press, viewportX, viewportY, viewposrtWidth, viewportHeight);

            if (easyButton.contains(camCoordinates.x, camCoordinates.y)) {
                prefs.putInteger("mode", 1);
                prefs.flush();
            } else if (mediumButton.contains(camCoordinates.x, camCoordinates.y)) {
                prefs.putInteger("mode", 2);
                prefs.flush();
            } else if (hardButton.contains(camCoordinates.x, camCoordinates.y)) {
                prefs.putInteger("mode", 3);
                prefs.flush();
            }

            if (didItButton.contains(camCoordinates.x, camCoordinates.y)) {
                fatShreddingNotifier.complete();
            } else if (postponeButton.contains(camCoordinates.x, camCoordinates.y)) {
                fatShreddingNotifier.postpone();
            }

        } else if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            this.dispose();
            game.dispose();
            System.exit(0);
        }
    }

    @Override
    public void resize(int width, int height) {
        Vector2 size = Scaling.fit.apply(BodyFatShredderGame.WIDTH, BodyFatShredderGame.HEIGHT, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewportX = (width - size.x) / 2;
        viewportY = (height - size.y) / 2;
        viewposrtWidth = size.x;
        viewportHeight = size.y;
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();

        headingFont.dispose();
        normalFont.dispose();
        clickToPlayFont.dispose();

        fatShreddingNotifier.dispose();

    }
}
