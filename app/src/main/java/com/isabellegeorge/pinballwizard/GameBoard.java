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
        import android.util.DisplayMetrics;
        import android.util.Log;
        import android.view.Display;
        import android.view.View;
public class GameBoard extends View{
    int width;
    int height;
    private Paint p;

    public void setData(int w, int h){
        width = w;
        height = h;
    }
//    synchronized public void resetBoard() {
//        pinballGame = null;
//    }

    public GameBoard(Context context, AttributeSet aSet) {
        super(context, aSet);
        p = new Paint();

    }

    @Override
    synchronized public void onDraw(Canvas canvas) {

        String height = String.valueOf(getHeight());
        Log.v("height", height);

        RectF arc = new RectF(300, 300, 400, 400);

        p.setColor(Color.BLACK);
        p.setAlpha(255);
        p.setStrokeWidth(1);
        canvas.drawRect(0, 0, 576, 1024, p);
        p.setColor(Color.LTGRAY);
        canvas.drawArc(arc, 180,180, true, p);



//        canvas.drawRect(getWidth()/widthModifier+2, (((getHeight()/heightModifier)*bottomHeightMod)/2), getWidth()/widthModifier*(widthModifier-1)-2, getHeight(), p);
//        canvas.drawCircle(getWidth()/2,(getHeight()/4)+100, 500, p);

    }
}







