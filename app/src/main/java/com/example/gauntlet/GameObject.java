package com.example.gauntlet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

class GameObject {

    private Transform mTransform;
    private boolean isActive = false;
    private String mTag;

    private GraphicsComponent graphicsComponent;
    private MovementComponent movementComponent;
    private SpawnComponent spawnComponent;
    private AnimationComponent animationComponent = null;

    void setSpawner(SpawnComponent s) {
        spawnComponent = s;
    }

    void setGraphics(GraphicsComponent g, Context c,
                     ObjectSpec spec, PointF objectSize) {

        graphicsComponent = g;
        g.initialize(c, spec, objectSize);
    }

    void setAnimation(AnimationComponent ac, Context c, ObjectSpec spec){
        animationComponent = ac;
        ac.initialize(c, spec);
    }

    AnimationComponent getAnimationComponent(){ return animationComponent; }

    void setMovement(MovementComponent m) {
        movementComponent = m;
    }

    void setInput(InputComponent s) {
        s.setTransform(mTransform);
    }

    void setmTag(String tag) {
        mTag = tag;
    }

    void setTransform(Transform t) {
        mTransform = t;
    }

    void draw(Canvas canvas, Paint paint) {
        // If the object has an animation component, animate
        if (animationComponent != null){
            animationComponent.determineAnimation(mTransform, this);
        }
        graphicsComponent.draw(canvas, paint, mTransform);
    }

    void update(long fps, Transform playerTransform) {
        if (!(movementComponent.move(fps,
                mTransform, playerTransform))) {
            // Component returned false
            isActive = false;
        }
    }

    boolean spawn(Transform playerTransform) {
        // Only spawnComponent if not already active
        if (!isActive) {
            spawnComponent.spawn(playerTransform, mTransform);
            isActive = true;
            return true;
        }
        return false;
    }

    boolean checkActive() {
        return isActive;
    }

    String getTag() {
        return mTag;
    }

    void setInactive() {
        isActive = false;
    }

    Transform getTransform() {
        return mTransform;
    }

    GraphicsComponent getGraphicsComponent() { return graphicsComponent; }



}

