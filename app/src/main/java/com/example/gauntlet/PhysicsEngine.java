package com.example.gauntlet;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

class PhysicsEngine {

    // This signature and much more will
    //change later in the project
    boolean update(long fps, ArrayList<GameObject> objects,
                   GameState gs, SoundEngine se){

        // Update all the GameObjects
        for (GameObject object : objects) {
            if (object.checkActive()) {
                object.update(fps, objects.get(Level.PLAYER_INDEX)
                        .getTransform());
            }
        }

        return detectCollisions(gs, objects, se);
    }


    // Collision detection method will go here
    private boolean detectCollisions(
            GameState mGameState,
            ArrayList<GameObject> objects,
            SoundEngine se){

        boolean playerHit = false;

        for (GameObject go1 : objects) {
            if (go1.checkActive()) {
                for (GameObject go2 : objects) {
                    if (go2.checkActive()) {
                        if (RectF.intersects(go1.getTransform().getCollider(), go2.getTransform().getCollider())) {
                            switch (go1.getTag() + " with " + go2.getTag()) {
                                case "Player with Alien":

                                case "Player with Troll":

                                case "Player with Goblin":
                                    //playerHit = true;
                                    mGameState.loseLife(se);

                                    break;

                                case "Player Arrow with Alien":

                                case "Player Arrow with Goblin":

                                case "Player Arrow with Troll":
                                    mGameState.increaseScore();

                                    go2.setInactive();
                                    go2.spawn(objects.get(Level
                                            .PLAYER_INDEX).getTransform());

                                    go1.setInactive();

                                    break;
                                case "Player with PassKey":
                                    Level.isLevelFinished = true;
                                    break;

                                case "Player with PowerUp":
                                    go2.setInactive();
                                    //increase score by 100
                                    for(int i = 0; i < 100; i++){
                                        mGameState.increaseScore();
                                    }
                                    break;

                                default:
                                    break;
                            }
                        }
                    }
                }
            }
        }
        return playerHit;
    }


}

