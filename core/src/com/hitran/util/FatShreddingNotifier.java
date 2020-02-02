package com.hitran.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;

public class FatShreddingNotifier {

    private Preferences prefs;
    private Exercise currentExercise;
    private Sound pushUp, squat, legRaise;

    private float timeOfLastExercise;

    public FatShreddingNotifier() {
        prefs = Gdx.app.getPreferences("BodyFatShredder");

        currentExercise = Exercise.PUSH_UP;

        pushUp = Gdx.audio.newSound(Gdx.files.internal("pushups.wav"));
        squat = Gdx.audio.newSound(Gdx.files.internal("squats.wav"));
        legRaise = Gdx.audio.newSound(Gdx.files.internal("legraises.wav"));
    }

    public void update(float delta) {

        timeOfLastExercise += delta;

        if (timeOfLastExercise > 1800f / prefs.getInteger("mode")) {
            timeOfLastExercise = 0f;

            switch (currentExercise) {
                case PUSH_UP:
                    pushUp.play();
                    break;
                case SQUAT:
                    squat.play();
                    break;
                case LEG_RAISE:
                    legRaise.play();
                    break;
            }
        }
    }

    public void complete() {
        switch (currentExercise) {
            case PUSH_UP:
                int pushUpsCount = prefs.getInteger("currentPushUpsCount");
                prefs.putInteger("currentPushUpsCount", pushUpsCount + 10);
                currentExercise = Exercise.SQUAT;
                break;
            case SQUAT:
                int squatCount = prefs.getInteger("currentSquatsCount");
                prefs.putInteger("currentSquatsCount", squatCount + 10);
                currentExercise = Exercise.LEG_RAISE;
                break;
            case LEG_RAISE:
                int legRaisesCount = prefs.getInteger("currentLegRaisesCount");
                prefs.putInteger("currentLegRaisesCount", legRaisesCount + 10);
                currentExercise = Exercise.SQUAT;
                break;
        }

        int kcalCount = prefs.getInteger("currentKcalCount");
        prefs.putInteger("currentKcalCount" , kcalCount + 10);
        prefs.flush();
    }

    public void postpone() {
        timeOfLastExercise = 0f;
    }

    public void dispose() {
        pushUp.dispose();
        squat.dispose();
        legRaise.dispose();
    }
}
