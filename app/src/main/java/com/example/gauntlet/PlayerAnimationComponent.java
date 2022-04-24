package com.example.gauntlet;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PlayerAnimationComponent extends SpriteGraphicsComponent implements AnimationComponent {

    // Start frames of each animation
    private final int FACING_NORTH = 0;
    private final int FACING_NORTHEAST = 1;
    private final int FACING_EAST = 2;
    private final int FACING_SOUTHEAST = 3;
    private final int FACING_SOUTH = 4;
    private final int FACING_SOUTHWEST = 5;
    private final int FACING_WEST = 6;
    private final int FACING_NORTHWEST= 7;

    // Number of frames in between each animation frame
    private final int ANIMATION_OFFSET = 8;

    // Current frame of animation
    private static int currentFrame = 0;

    // Current Animation
    private static int currentAnimation = 0;

    // Time of last frame
    private static long lastFrameTime;

    // Array of all sprite bitmaps
    private ArrayList<Bitmap> mSpriteArray;

    @Override
    public void initialize(Context c, ObjectSpec s){

        // Initialize starter variables
        ArrayList<Bitmap> animationList = new ArrayList<>();
        Bitmap tempBitmap = null;
        AssetManager assetManager = c.getAssets();
        InputStream inputStream = null;

        // Try to open sprite sheet
        try{
            inputStream = assetManager.open("sprite_sheet16x16.png");
            tempBitmap = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e){
            // Panic
            Log.d("Error", "Failed to find Sprite Sheet");
        }

        // Add all 24 sprites to array
        for (int i = 0; i < 24; i++){
            animationList.add(generateSprite(c, s, i, tempBitmap));
        }

        // Initialize class variables
        mSpriteArray = animationList;
        lastFrameTime = System.currentTimeMillis();

    }

    @Override
    public void determineAnimation(Transform t, GameObject player){

        // No magic numbers here
        final double PI = 3.14159;
        final int DEGREES_CIRCLE = 360;

        // Get current angle of joystick
        double currentAngle = t.getAngle();

        // Joystick angle varies from -pi -> pi
        // Calculate current angle to degrees out of 360
        currentAngle = currentAngle * (DEGREES_CIRCLE / 2) / PI;
        if (currentAngle < 0) { currentAngle = Math.abs(currentAngle); }
        else { currentAngle = -currentAngle + DEGREES_CIRCLE; }


        // heading east
        if (currentAngle > 340d && currentAngle < 40d){
            changeFrame(FACING_EAST, player);
        }

        // heading north east
        else if (currentAngle < 85d){
            changeFrame(FACING_NORTHEAST, player);
        }

        // heading north
        else if (currentAngle < 110d){
            changeFrame(FACING_NORTH, player);
        }

        // heading north west
        else if (currentAngle < 160d){
            changeFrame(FACING_NORTHWEST, player);
        }

        // heading west
        else if (currentAngle < 200d ){
            changeFrame(FACING_WEST, player);
        }

        // heading south west
        else if (currentAngle < 255d){
            changeFrame(FACING_SOUTHWEST, player);
        }

        // heading south
        else if (currentAngle < 290){
            changeFrame(FACING_SOUTH, player);
        }

        // heading south east
        else {
            changeFrame(FACING_SOUTHEAST, player);
        }

        //Log.d("D", "Last time: " + lastFrameTime);
    }
    @Override
    public boolean canAnimate(){

        // Log current system time
        long currentTime = System.currentTimeMillis();
        final long ANIMATION_TIME = 75;
        // Log.d("D", "Time " + currentTime);

        // If a sufficient amount of time has past from the last tick to current tick,
        // animate the player, otherwise keep at current animation frame
        if (currentTime > ANIMATION_TIME + lastFrameTime ) {
            Log.d("D", "Passed");
            lastFrameTime = currentTime;
            return true;
        }

        return false;
    }

    @Override
    public void changeFrame(int facing, GameObject player) {

        // If the current animation is not equal to desired animation,
        // change current frame to desired animation
        if (currentAnimation != facing) {
            currentAnimation = facing;
            currentFrame = currentAnimation;
        } else {
            // If the desired amount of time between ticks has passed, animate player
            if (canAnimate()) {
                // If the current frame is on second frame of movement, switch to first
                if (currentFrame == currentAnimation) {
                    currentFrame = currentFrame + ANIMATION_OFFSET;
                }
                // else, switch to second frame
                else {
                    currentFrame = currentAnimation;
                }

            }
        }

        // If the player is moving, animate
        if (player.getTransform().isAvailableToMove()) {
            player.getGraphicsComponent().setBitmap(mSpriteArray.get(currentFrame));
        }
        // else, stay on current frame
        else {
            player.getGraphicsComponent().setBitmap(mSpriteArray.get(currentAnimation));
        }

    }


    @Override
    public void objectAction(int action, GameObject player){
        final int SHOT_ARROW = 1;
        long currentTime = System.currentTimeMillis();
        if (action == SHOT_ARROW){
            Log.d("D", "Arrow Animation");
            currentFrame = currentAnimation + 8;
            player.getGraphicsComponent().setBitmap(mSpriteArray.get(currentFrame));
            Log.d("D", "Current frame: " + currentFrame + " Current Animation: " + currentAnimation);
            lastFrameTime = currentTime;
        }

    }
}
