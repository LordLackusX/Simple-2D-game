package com.example.fear5.a2dplatformer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private RectPlayer enemy;
    private Point enemyPoint;
    Bitmap bmp;




    public GamePanel(Context context){
    super (context );
    getHolder().addCallback(this);

    bmp = BitmapFactory.decodeResource(getResources(),R.mipmap.grass);

    thread = new MainThread(getHolder(),this);

    player = new RectPlayer(new Rect(100,100,200,200), Color.MAGENTA);
    playerPoint = new Point(150,150);

    enemy = new RectPlayer(new Rect(100,100,200,200), Color.RED);
    enemyPoint = new Point(50,100);


    obstacleManager = new ObstacleManager(200, 350, 75,Color.BLACK);



    setFocusable( true);


    }


    @Override

    public void surfaceChanged (SurfaceHolder holder , int format, int width , int height){


    }


    @Override

    public void surfaceCreated (SurfaceHolder holder){

        thread = new MainThread(getHolder(),this);

        thread.setRunning(true);
        thread.start();




    }

    @Override

    public void surfaceDestroyed(SurfaceHolder holder){
    boolean retry = true;
    while(retry) {
        try {
            thread.setRunning(false);
            thread.join();
        } catch (Exception e) { e.printStackTrace();}

        retry = false;
    }

    }



    @Override

    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int) event.getX(),(int) event.getY());
        }
        return true;
       // return super.onTouchEvent(event);

    }

    public void  update() {

    player.update(playerPoint);
  //  obstacleManager.update();
    }

    @Override

    public  void draw (Canvas canvas   ){

        super.draw(canvas);


       // canvas.drawColor(Color.WHITE );
            canvas.drawBitmap(bmp,0,0,null);
        player.draw(canvas);
        obstacleManager.draw(canvas);
        enemy.draw(canvas);





    }
}
