package com.example.gauntlet;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class SpriteGraphicsComponent implements GraphicsComponent{


    private Bitmap mBitmap;

    // Dimensions for each sprite
    final int FRAME_WIDTH = 16;
    final int FRAME_HEIGHT = 16;

    // Sprite edge padding
    final int FRAME_PADDING = 1;

    // Position of first sprite on sheet
    final int SHEET_START_X = 3;
    final int SHEET_START_Y = 5;

    final int MAX_SPRITESHEET_X = 43;


    public Bitmap generateSprite(Context c, ObjectSpec s, int spriteIndex, Bitmap spriteSheet){

        // Find where sprite is relative to the spritesheet
        PointF spritePosition = findSpriteLocation(s.getBitmapName());

        Bitmap spriteBitmap;

        // If the desired position would be greater than furthest x position in sprite sheet
        // move to next line
        if (spritePosition.x + spriteIndex > MAX_SPRITESHEET_X){
            spritePosition.x = 0;
            spritePosition.y = spritePosition.y + 1;
        } else {
            spritePosition.x = spritePosition.x + spriteIndex;
        }

        // Create bitmap based on position in spritesheet
        spriteBitmap = Bitmap.createBitmap(spriteSheet,
                SHEET_START_X + (FRAME_WIDTH * (int) spritePosition.x) + (FRAME_PADDING * (int) spritePosition.x * 2),
                SHEET_START_Y + (FRAME_HEIGHT * (int) spritePosition.y) + (FRAME_PADDING * (int) spritePosition.y * 2),
                FRAME_WIDTH, FRAME_HEIGHT);

        // Scale the bitmap to object size
        spriteBitmap = Bitmap.createScaledBitmap(
                spriteBitmap,
                (int)s.getScale().x, (int)s.getScale().y,
                false);

        return spriteBitmap;

    }

    public void setBitmap(Bitmap bitmap){ mBitmap = bitmap; }

    @Override
    public void initialize(Context c, ObjectSpec s, PointF objectSize) {

        try{
            // Try to create initial bitmap
            AssetManager assetManager = c.getAssets();
            InputStream inputStream;
            inputStream = assetManager.open("sprite_sheet16x16.png");
            mBitmap = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e){
            // Spend rest of day debugging
            Log.d("Error", "Failed to load sprite sheet");
        }

        // Find where on sprite sheet a sprite is
        PointF spritePosition = findSpriteLocation(s.getBitmapName());

        // Create sprite based on location in sprite sheet
        mBitmap = Bitmap.createBitmap(mBitmap,
                SHEET_START_X + (FRAME_WIDTH * (int) spritePosition.x) + (FRAME_PADDING * (int) spritePosition.x * 2),
                SHEET_START_Y + (FRAME_HEIGHT * (int) spritePosition.y) + (FRAME_PADDING * (int) spritePosition.y * 2),
                FRAME_WIDTH, FRAME_HEIGHT);

        // Scale bitmap based on relative size
        mBitmap = Bitmap.createScaledBitmap(
                mBitmap,
                (int)objectSize.x, (int)objectSize.y,
                false);

    }


    @Override
    public void draw(Canvas canvas, Paint paint, Transform t) {

        // Use the magic of Div / JT / Matthew / Lans to draw the sprite
        // in the proper place
        if (BackgroundMovementComponent.atEdge) {
            canvas.drawBitmap(mBitmap,
                    PlayerMovementComponent.screenLocation.x,
                    PlayerMovementComponent.screenLocation.y,
                    paint);
        } else {
            canvas.drawBitmap(mBitmap,
                    t.getmScreenSize().x / 2,
                    t.getmScreenSize().y / 2,
                    paint);

        }
    }

    private PointF findSpriteLocation(String spriteName){
        // Initialize sprite point
        PointF position = new PointF();
        position.x = 0;
        position.y = 0;

        // Set sprite position based on sprite name
        switch (spriteName){
            case "player":
                position.x = 23;
                position.y = 11;
                return position;
            case "ghost":
                position.x = 0;
                position.y = 0;
                return position;
            case "key":
                position.x = 19;
                position.y = 6;
                break;
            case "wall":
                position.x = 6;
                position.y = 13;
                break;
            case "troll":
                position.x = 0;
                position.y = 1;
                break;
            default:
                // cry
                Log.d("Error", "Failed to find sprite");
                break;
        }

        return position;
    }
}
