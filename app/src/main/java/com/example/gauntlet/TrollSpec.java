package com.example.gauntlet;

import android.graphics.PointF;

public class TrollSpec extends ObjectSpec{
    // This is all the unique specifications
    // for an Troll that chases the player
    private static final String tag = "Troll";
    private static final String bitmapName = "troll";
    private static final float speed = 14f;
    private static final PointF relativeScale =
            new PointF(10f, 8f);

    private static final String[] components = new String [] {
            "NPCGraphicsComponent",
            "TrollMovementComponent",
            "TrollSpawnComponent"};

    TrollSpec(){
        super(tag, bitmapName,
                speed, relativeScale,
                components);
    }

}
