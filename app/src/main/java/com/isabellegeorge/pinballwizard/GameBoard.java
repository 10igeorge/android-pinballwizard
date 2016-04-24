package com.isabellegeorge.pinballwizard;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;
        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Point;
        import android.graphics.RectF;
        import android.util.AttributeSet;
        import android.util.Log;
        import android.view.View;
public class GameBoard extends View{
    private Paint p;
//    synchronized public void resetBoard() {
//        pinballGame = null;
//    }

    public GameBoard(Context context, AttributeSet aSet) {
        super(context, aSet);
        p = new Paint();
    }

    @Override
    synchronized public void onDraw(Canvas canvas) {
//        float left = getWidth()/9;
//        float top = getHeight()/13;
//        float right = getWidth()/9;
//        float bottom = getHeight()/8;
//        RectF arc = new RectF(left, top, right*8, bottom+1000);
        float widthModifier = 10;
        float heightModifier = 14;
        int bottomHeightMod = 8;
        RectF arc = new RectF(getWidth()/widthModifier, getHeight()/heightModifier, getWidth()/widthModifier*(widthModifier-1), (getHeight()/heightModifier)*bottomHeightMod);

        p.setColor(Color.BLACK);
        p.setAlpha(255);
        p.setStrokeWidth(1);
        canvas.drawRect(0, 0, getWidth(), getHeight(), p);
        p.setColor(Color.LTGRAY);
        canvas.drawArc(arc, 180,180, true, p);
//        canvas.drawRect(getHeight()/heightModifier, (getHeight()/heightModifier)*bottomHeightMod, );

        canvas.drawRect(getWidth()/widthModifier+2, (((getHeight()/heightModifier)*bottomHeightMod)/2), getWidth()/widthModifier*(widthModifier-1)-2, getHeight(), p);
//        canvas.drawCircle(getWidth()/2,(getHeight()/4)+100, 500, p);

        String x = String.valueOf(getWidth()/widthModifier*(widthModifier-1));
        String y = String.valueOf((getHeight()/heightModifier)*bottomHeightMod);
        Log.e("CoordinatesX", x+","+y);
    }
}







