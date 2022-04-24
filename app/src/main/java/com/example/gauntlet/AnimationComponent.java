package com.example.gauntlet;

import android.content.Context;

public interface AnimationComponent {
    void initialize(Context c, ObjectSpec s);
    void determineAnimation(Transform t, GameObject object);
    boolean canAnimate();
    void changeFrame(int facing, GameObject object);
    void objectAction(int action, GameObject object);
}
