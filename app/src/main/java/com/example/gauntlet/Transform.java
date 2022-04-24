package com.example.gauntlet;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

class Transform {

    // These two members are for scrolling background
    private int mXClip;
    private boolean mReversedFirst = false;

    private RectF mCollider;
    private PointF mPastLocation;
    private PointF mLocation;
    private boolean mFacingRight = true;
    private boolean mHeadingUp = false;
    private boolean mHeadingDown = false;
    private boolean mHeadingLeft = false;
    private boolean mHeadingRight = false;
    private PointF mSpeed;
    private float mObjectHeight;
    private float mObjectWidth;
    private static PointF mScreenSize;
    private boolean availableToMove = false;
    private double mAngle;
    private PointF mVectorComponents;
    public static Point relativePlayerLocation = new Point(0, 0);
    public static PointF lowResConversionFactor = new PointF(0, 0);
    public static PointF screenResConversionFactor = new PointF(0, 0);
    public static int numObjects = 0;
    public static Point bitmapRes;
    public PointF overallLocation;
    public PointF drawableLocation;
    private boolean mMoving = false;

    public static ArrayList<PointF> spawnableLocations = new ArrayList<>();




    Transform(float speed, float objectWidth,
              float objectHeight,
              PointF startingLocation,
              PointF screenSize){

        mCollider = new RectF();
        mObjectHeight = objectHeight;
        mObjectWidth = objectWidth;
        mLocation = startingLocation;
        mPastLocation = startingLocation;
        mScreenSize = screenSize;

        bitmapRes = new Point(GameData.IMAGE_RESOLUTION_X, GameData.IMAGE_RESOLUTION_Y);

        lowResConversionFactor.x = GameData.LOWRES_CONV_FACTOR_X;
        lowResConversionFactor.y = GameData.LOWRES_CONV_FACTOR_Y;



        screenResConversionFactor.x = GameData.IMAGE_RESOLUTION_X / mScreenSize.x;
        screenResConversionFactor.y = GameData.IMAGE_RESOLUTION_Y / mScreenSize.y;

        if (numObjects == 1) {
            //relativePlayerLocation.x = ((int)(mScreenSize.x / lowResConversionFactor.x) / 2) - 1;
            //relativePlayerLocation.y = ((int)(mScreenSize.y / lowResConversionFactor.y) / 2) - 1;
            Transform.relativePlayerLocation.x = (int)((bitmapRes.x / lowResConversionFactor.x) / 2) - 1;
            Transform.relativePlayerLocation.y =(int)((bitmapRes.y / lowResConversionFactor.y) / 2) - 1;
            //lowResConversionFactor.x = BackgroundGraphicsComponent.bitmapNew.getWidth() / 32;
            //lowResConversionFactor.y = BackgroundGraphicsComponent.bitmapNew.getWidth() / 32;

            System.out.println("STARTING: " + Transform.relativePlayerLocation.x + "," + Transform.relativePlayerLocation.y);
        }


        // Adjusting speeds for horizontal/vertical movement to be the same considering non-square resolution..
        // Below will be changed.. Quick fix for code review.
        numObjects++;
        drawableLocation = new PointF(mScreenSize.x / 2, mScreenSize.y / 2);

        mSpeed = new PointF(bitmapRes.x / speed, bitmapRes.y / speed);
        mVectorComponents = new PointF(0, 0);
    }

    void setMovementAvailability() {
        availableToMove = true;
    }

    void resetMovementAvailability() {
        availableToMove = false;
    }

    static void resetRelativeLocation() {
        //relativePlayerLocation.x = ((int)(mScreenSize.x / lowResConversionFactor.x) / 2) -1;
        //relativePlayerLocation.y =
        relativePlayerLocation.x = (int)((bitmapRes.x / lowResConversionFactor.x) / 2) - 1;
        relativePlayerLocation.y = (int)((bitmapRes.y / lowResConversionFactor.y) / 2) - 1;
        //System.out.println("STARTING: " + Transform.relativePlayerLocation.x + "," + Transform.relativePlayerLocation.y);

        PlayerMovementComponent.XYTracker.x = 0;
        PlayerMovementComponent.XYTracker.y = 0;
    }

    void setAngle(double angle) {
        mAngle = angle;
    }

    // Here are some helper methods that the background will use
    boolean getReversedFirst(){
        return mReversedFirst;
    }

    void flipReversedFirst(){
        mReversedFirst = !mReversedFirst;
    }

    int getXClip(){
        return mXClip;
    }

    void setXClip(int newXClip){
        mXClip = newXClip;
    }

    PointF getmScreenSize(){
        return mScreenSize;
    }


    void headUp(){
        mHeadingUp = true;
        mHeadingDown = false;

    }

    void setDrawableLocation() {
        drawableLocation.x = mScreenSize.x / 2;
        drawableLocation.y = mScreenSize.y / 2;
    }

    void headDown(){
        mHeadingDown = true;
        mHeadingUp = false;
    }

    void headRight(){
        mHeadingRight = true;
        mHeadingLeft = false;
        mFacingRight = true;
    }

    void headLeft(){
        mHeadingLeft = true;
        mHeadingRight = false;
        mFacingRight = false;
    }


    boolean headingUp(){
        return mHeadingUp;
    }

    boolean headingDown(){
        return mHeadingDown;
    }

    boolean headingRight(){
        return mHeadingRight;
    }

    boolean headingLeft(){
        return mHeadingLeft;
    }
    void setMoving(boolean action) { mMoving = action; }
    boolean isMoving() {
        // TODO: FIX PLS
//        boolean check =  mPastLocation != mLocation;
//        Log.d("D", "Past location " + mPastLocation);
//        Log.d("D", "Current Location: " + mLocation);
//        mPastLocation = mLocation;
        Log.d("D", "Is Moving: " + mMoving);

        return mMoving;
    }

    boolean isAvailableToMove() {
        return availableToMove;
    }
    void updateCollider(){
        // Pull the borders in a bit (10%)
        mCollider.top = mLocation.y + (mObjectHeight / 10);

        mCollider.left = mLocation.x + (mObjectWidth /10);
       // mCollider.bottom = (mCollider.top + mObjectHeight)
            //    - mObjectHeight/10;

        mCollider.bottom = (mCollider.top + (float)(mObjectHeight * .90));
        /*
        mCollider.right = (mCollider.left + mObjectWidth)
                -  mObjectWidth/10;
         */

        mCollider.right = (mCollider.left + (mObjectWidth / 3));

    }

    float getObjectHeight(){
        return mObjectHeight;
    }

    void stopVertical(){
        mHeadingDown = false;
        mHeadingUp = false;
    }

    PointF getSpeed(){
        return mSpeed;
    }

    void setLocation(float horizontal, float vertical){
        mLocation = new PointF(horizontal, vertical);
        updateCollider();
    }

    double getAngle() { return mAngle; }


    PointF getLocation() {

        return mLocation;
    }

    PointF getSize(){
        return new PointF(mObjectWidth,
                mObjectHeight);
    }

    void flip(){
        mFacingRight = !mFacingRight;
    }

    boolean getFacingRight(){
        return mFacingRight;
    }

    RectF getCollider(){
        return mCollider;
    }

    PointF getFiringLocation(float arrowLength){
        PointF mFiringLocation = new PointF();

        if(mFacingRight) {
            mFiringLocation.x = mScreenSize.x
                    + (mObjectWidth);
        }else
        {
            mFiringLocation.x = mLocation.x
                    + (mObjectWidth) - (arrowLength);
        }
        // Move the height down a bit of ship height from origin
        mFiringLocation.y = mLocation.y + (mObjectHeight / 1.28f);
        return mFiringLocation;
    }

    void setMovementComponents() {
        mVectorComponents.x = (float)Math.cos(mAngle) * mSpeed.x;
        mVectorComponents.y = (float)Math.sin(mAngle) * mSpeed.y;

    }

    PointF getMovementComponents() {
        return mVectorComponents;
    }

    void resetMovement() {
        mFacingRight = false;
    }

    public static ArrayList<PointF> getSpawnableLocations() { return spawnableLocations; }




}

