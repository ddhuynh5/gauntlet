package com.example.gauntlet;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.*;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class NPCGraphicsComponent implements GraphicsComponent {
    private Bitmap mBitmap;
    private Bitmap mBitmapReversed;
    private SpriteGraphicsComponent spriteGraphicsComponent;

    Canvas canvas2;
    @Override
    public void initialize(Context context, ObjectSpec spec, PointF objectSize) {

        int resID = context.getResources().getIdentifier(spec.getBitmapName(), "drawable", context.getPackageName());
        spriteGraphicsComponent = new SpriteGraphicsComponent();
        try{
            // Try to create initial bitmap
            AssetManager assetManager = context.getAssets();
            InputStream inputStream;
            inputStream = assetManager.open("sprite_sheet16x16.png");
            mBitmap = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e){
            // Spend rest of day debugging
            Log.d("Error", "Failed to load sprite sheet");
        }

        mBitmap = spriteGraphicsComponent.generateSprite(context, spec, 0, mBitmap);

        // Scaling the bitmap object to the correct size for the game object.
        mBitmap = Bitmap.createScaledBitmap(mBitmap, (int)objectSize.x, (int)objectSize.y, false);

        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);

        // Now we can show any game object instance in a left or right-facing state.
        mBitmapReversed = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);


    }

    @Override
    public void setBitmap(Bitmap bitmap) {

    }

    @Override
    public void draw(Canvas canvas, Paint paint, Transform t) {
        // Below is best for current movement..
        GameData.resetBitmap();

        Canvas canvas1 = new Canvas(GameData.mainBitmap);

        canvas1.setBitmap(GameData.mainBitmap);


        if (!t.getFacingRight()) {
            canvas1.drawBitmap(mBitmap, t.getLocation().x, t.getLocation().y, paint);
        }

        else {
            canvas1.drawBitmap(mBitmapReversed, t.getLocation().x, t.getLocation().y, paint);
        }

    }


}
