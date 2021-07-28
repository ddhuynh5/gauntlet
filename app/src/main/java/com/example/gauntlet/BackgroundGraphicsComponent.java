package com.example.gauntlet;

import android.content.Context;
import android.graphics.*;


// Overall, same idea as BackgroundGraphics component within ScrollingShooter.

public class BackgroundGraphicsComponent implements GraphicsComponent{
    private Bitmap mBitmap;
    private Bitmap wallBitmap;
    private GameMap gameMap;
    public static Context context;

    public static ObjectSpec s1;
    public static PointF objectSize1;

    private Point mScreenRes = new Point(
            GameData.IMAGE_RESOLUTION_X,
            GameData.IMAGE_RESOLUTION_Y
    );

    private Point lowResFactor = new Point(
            GameData.LOWRES_CONV_FACTOR_X,
            GameData.LOWRES_CONV_FACTOR_Y
    );

    @Override
    public void setBitmap(Bitmap bitmap) { }

    // Draw on top of original bitmap..
    // Use low res map and draw original wall/stationary structure upon initialization.
    // Tracking relative position of player..
    @Override
    public void initialize(Context c, ObjectSpec s, PointF objectSize) {

        int resID = c.getResources().getIdentifier(s.getBitmapName(), "drawable", c.getPackageName());
        mBitmap = BitmapFactory.decodeResource(c.getResources(), resID);
        wallBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.redwall);
        context = c;
        s1 = s;
        objectSize1 = objectSize;


        // Check documentation for filtering...
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mScreenRes.x, mScreenRes.y, false);
        // TO DO: Confirm scaling ratio from text map to  full drawn map..
        wallBitmap = Bitmap.createScaledBitmap(wallBitmap, lowResFactor.x, lowResFactor.y, false);
//        bitmapNew = Bitmap.createScaledBitmap(bitmapNew, (int)objectSize.x, (int)objectSize.y, false);
        GameData.mainBitmap = Bitmap.createBitmap(mScreenRes.x, mScreenRes.y, mBitmap.getConfig());
        GameData.initialBitmap = Bitmap.createBitmap(mScreenRes.x, mScreenRes.y, mBitmap.getConfig());


        gameMap = new GameMap(c);

        Canvas canvas = new Canvas(GameData.mainBitmap);
        canvas.drawBitmap(mBitmap, 0f, 0f, null);

        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 255, 255, 255));


        for (int i = 0; i < gameMap.obstacleContainer.size(); i++) {
            canvas.drawBitmap(wallBitmap,
                    gameMap.obstacleContainer.get(i).location.left,
                    gameMap.obstacleContainer.get(i).location.top,
                    null);
        }

        canvas.setBitmap(GameData.initialBitmap);
        canvas.drawBitmap(GameData.mainBitmap, 0, 0, null);

    /*
     for (int i = 0; i < gameMap.obstacleContainer.size(); i++) {
            canvas.drawBitmap(
                    gameMap.obstacleContainer.get(i).location.left, gameMap.obstacleContainer.get(i).location.top,
                    gameMap.obstacleContainer.get(i).location.right, gameMap.obstacleContainer.get(i).location.bottom,
                    paint);
        }
     */




    }


    @Override
    public void draw(Canvas canvas, Paint paint, Transform m) {

        RectF backgroundPortionToDraw = m.getCollider();
        int width = (int)m.getmScreenSize().x;
        int height = (int)m.getmScreenSize().y;


        // Canvas = new Canvas(GameData.initialBitmap);

        // Conditions below handle drawing full background upon starting game (based off current player location and no spawn)..
        if (backgroundPortionToDraw.top == 0 && backgroundPortionToDraw.bottom == 0) {

            Rect fromRect1 = new Rect(0, 0, mScreenRes.x, mScreenRes.y);

            Rect toRect1 = new Rect(0, 0, width, height);

            canvas.drawBitmap(GameData.mainBitmap, fromRect1, toRect1, paint);
        }

        else {
            // Portion to draw..



            Rect fromRect1 = new Rect((int)backgroundPortionToDraw.left, (int)backgroundPortionToDraw.top,
                    (int)backgroundPortionToDraw.right, (int)backgroundPortionToDraw.bottom);

            // To keep the aspect ratio same, width is passed twice
            GameData.fullMapRect = new Rect(0, 0, width, height);

            // Note: Choose your own size for the map..
            canvas.drawBitmap(GameData.mainBitmap,fromRect1, GameData.fullMapRect, paint);


        }


    }
}

