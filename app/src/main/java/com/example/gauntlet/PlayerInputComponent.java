package com.example.gauntlet;

import android.graphics.Rect;
import android.util.Pair;
import android.view.MotionEvent;

import java.util.ArrayList;

class PlayerInputComponent implements InputComponent,
        InputObserver {

    private Transform mTransform;
    private PlayerArrowSpawner mPAS;

    PlayerInputComponent(GameEngine ger) {

        ger.addObserver(this);
        mPAS = ger;

    }

    @Override
    public void setTransform(Transform transform) {
        mTransform = transform;

    }

    // Required method of InputObserver
    // interface called from the onTouchEvent method
    @Override
    public void handleInput(MotionEvent event,
                            GameState gameState, Pair<ArrayList<Rect>, Joystick> buttons) {

        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);
        Joystick localJS = buttons.second;
        ArrayList<Rect> localButtons = buttons.first;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_UP:
                if (localJS.isJoystickTouched(x, y)) {
                    mTransform.resetMovementAvailability();
                }
                break;

            case MotionEvent.ACTION_DOWN:

                if (localButtons.get(HUD.SHOOT).contains(x, y)) {
                    System.out.println("TRYING TO SHOOT.");
                    mPAS.spawnPlayerArrow(mTransform);
                }

                else if (localJS.isJoystickTouched(x, y)) {
                    mTransform.setAngle(localJS.calcAngle(x, y));
                    mTransform.setMovementAvailability();
                }

                break;

            case MotionEvent.ACTION_POINTER_UP:
                if (localJS.isJoystickTouched(x, y)) {
                    mTransform.resetMovementAvailability();
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                if (localJS.isJoystickTouched(x, y)) {
                    mTransform.setAngle(localJS.calcAngle(x, y));
                    mTransform.setMovementAvailability();
                }
                else if (localButtons.get(HUD.SHOOT).contains(x,y)){
                    mPAS.spawnPlayerArrow(mTransform);
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if (localJS.isJoystickTouched(x, y)) {
                    mTransform.setAngle(localJS.calcAngle(x, y));
                    mTransform.setMovementAvailability();
                }
        }


    }
}
