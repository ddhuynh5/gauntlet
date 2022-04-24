package com.example.gauntlet;

import android.graphics.PointF;

class ArrowMovementComponent implements MovementComponent {

    @Override
    public boolean move(long fps,
                        Transform t,
                        Transform playerTransform) {

        // Arrow can only travel two screen widths
        //float rightRange = playerTransform.getLocation().x + (GameData.IMAGE_RESOLUTION_X / 10);
        //float leftRange = playerTransform.getLocation().x - (GameData.IMAGE_RESOLUTION_X / 10);

        float range = 5120;

        // Where is the arrow
        PointF location = t.getLocation();

        // How fast is it going
        float speed = 5120 / 3;
        float drawSpeed = t.getmScreenSize().x / 3;


        if(t.headingRight()){
            location.x += speed / fps;
            t.drawableLocation.x += drawSpeed / fps;
        }
        else if(t.headingLeft()){
            location.x -= speed / fps;
            t.drawableLocation.x -= drawSpeed / fps;
        } else if (t.headingUp()) {
            location.y -= speed / fps;
            t.drawableLocation.y -= drawSpeed / fps;
        } else if (t.headingDown()){
            location.y += speed / fps;
            t.drawableLocation.y += drawSpeed / fps;
        }

        // Has the arrow gone out of range
        if(location.x < -range|| location.x > range){
            // disable the arrow
            return false;
        }

        t.updateCollider();


        return true;
    }
}
