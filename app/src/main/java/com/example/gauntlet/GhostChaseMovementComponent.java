package com.example.gauntlet;

import android.graphics.PointF;
import java.util.Random;

class GhostChaseMovementComponent implements MovementComponent {


    GhostChaseMovementComponent(AlienArrowSpawner als){

    }

    @Override
    public boolean move(long fps, Transform t, Transform playerTransform) {

        playerTransform.setMoving(true);
        PointF playerLocation = playerTransform.getLocation();


        PointF location = t.getLocation();

        float speed = (float)(t.getmScreenSize().x / 15);

        // Prevent the ship locking on too accurately
        float verticalSearchBounce = 20f * Transform.screenResConversionFactor.y;

        // move in the direction of the player horizontally
        // but relative to the player's direction of travel


     if ((playerLocation.x - location.x) > (t.getSize().x * Transform.screenResConversionFactor.x)) {
            t.headRight();
        }

     else if ((location.x - playerLocation.x) > (t.getSize().x * Transform.screenResConversionFactor.x)) {
            t.headLeft();
        }
        // move in the direction of the player vertically
        // Use a cast to get rid of unnecessary floats that make ship judder
        if ((int) location.y < playerLocation.y) {
            t.headDown();
        } else if ((int) location.y > playerLocation.y) {
            t.headUp();
        }






        //move vertically
        if(t.headingDown()){
            location.y += (speed) / fps;
        }
        else if(t.headingUp()){
            location.y -= (speed) / fps;
        }

        // Move horizontally
        if(t.headingLeft()){
            location.x -= (speed) / fps;
        }
        if(t.headingRight()){
            location.x += (speed) / fps;
        }

        // Update the collider
        t.updateCollider();

        playerTransform.setMoving(false);

        return true;
    }
}
