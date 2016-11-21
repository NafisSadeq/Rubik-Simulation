package com.example.Photo_Cube;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;

/**
 * Our OpenGL program's main activity
 */
public class MyGLActivity extends Activity implements  GestureDetector.OnGestureListener{

    private GLSurfaceView glView;   // Use GLSurfaceView
    int flag_touch=0;
    GestureDetector detector;
    // Call back when the activity is started, to initialize the view



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*glView = new GLSurfaceView(this);           // Allocate a GLSurfaceView
        glView.setRenderer(new com.example.Photo_Cube.MyGLRenderer(this)); // Use a custom renderer
        this.setContentView(glView);                // This activity sets to GLSurfaceView
        Button b = new Button(this);
        b.setText("Right1");
       this.addContentView(b,
                new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MyGLRenderer.move="R1";
                MyGLRenderer.count=60;
            }
        });*/
        detector = new GestureDetector(this);
        setContentView(R.layout.main);
        glView=(GLSurfaceView)findViewById(R.id.glsurfaceview);
        glView.setRenderer(new com.example.Photo_Cube.MyGLRenderer(this));

    }

    // Call back when the activity is going into the background
    @Override
    protected void onPause() {
        super.onPause();
        glView.onPause();
    }

    // Call back after onPause()
    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
    }

    public  void R1(View view){
        MyGLRenderer.count=30;
        MyGLRenderer.move="R1";
        MyGLRenderer.angleCube=0;
    }
    public  void R2(View view){
        MyGLRenderer.count=60;
        MyGLRenderer.move="R2";
        MyGLRenderer.angleCube=0;
    }
    public  void R3(View view){
        MyGLRenderer.count=30;
        MyGLRenderer.move="R3";
        MyGLRenderer.angleCube=0;
    }

    public  void L1(View view){
        MyGLRenderer.count=30;
        MyGLRenderer.move="L1";
        MyGLRenderer.angleCube=0;
    }
    public  void L2(View view){
        MyGLRenderer.count=60;
        MyGLRenderer.move="L2";
        MyGLRenderer.angleCube=0;
    }
    public  void L3(View view){
        MyGLRenderer.count=30;
        MyGLRenderer.move="L3";
        MyGLRenderer.angleCube=0;
    }
    public  void F1(View view){
        MyGLRenderer.count=30;
        MyGLRenderer.move="F1";
        MyGLRenderer.angleCube=0;
    }
    public  void F2(View view){
        MyGLRenderer.count=60;
        MyGLRenderer.move="F2";
        MyGLRenderer.angleCube=0;
    }
    public  void F3(View view){
        MyGLRenderer.count=30;
        MyGLRenderer.move="F3";
        MyGLRenderer.angleCube=0;
    }
    public  void U1(View view){
        MyGLRenderer.count=30;
        MyGLRenderer.move="U1";
        MyGLRenderer.angleCube=0;
    }
    public  void U2(View view){
        MyGLRenderer.count=60;
        MyGLRenderer.move="U2";
        MyGLRenderer.angleCube=0;
    }
    public  void U3(View view){
        MyGLRenderer.count=30;
        MyGLRenderer.move="U3";
        MyGLRenderer.angleCube=0;
    }
    public  void D1(View view){
        MyGLRenderer.count=30;
        MyGLRenderer.move="D1";
        MyGLRenderer.angleCube=0;
    }
    public  void D2(View view){
        MyGLRenderer.count=60;
        MyGLRenderer.move="D2";
        MyGLRenderer.angleCube=0;
    }
    public  void D3(View view){
        MyGLRenderer.count=30;
        MyGLRenderer.move="D3";
        MyGLRenderer.angleCube=0;
    }
    public  void B1(View view){
        MyGLRenderer.count=30;
        MyGLRenderer.move="B1";
        MyGLRenderer.angleCube=0;
    }
    public  void B2(View view){
        MyGLRenderer.count=60;
        MyGLRenderer.move="B2";
        MyGLRenderer.angleCube=0;
    }
    public  void B3(View view){
        MyGLRenderer.count=30;
        MyGLRenderer.move="B3";
        MyGLRenderer.angleCube=0;
    }

    public void up(){

            MyGLRenderer.angleCube=0;
            MyGLRenderer.count=30;
            MyGLRenderer.move="cube_up";


    }

    public void down(){
        MyGLRenderer.angleCube=0;
        MyGLRenderer.count=30;
        MyGLRenderer.move="cube_down";
    }

    public void right(){
        MyGLRenderer.angleCube=0;
        MyGLRenderer.count=30;
        MyGLRenderer.move="cube_right";
    }

    public void left(){
        MyGLRenderer.angleCube=0;
        MyGLRenderer.count=30;
        MyGLRenderer.move="cube_left";
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return detector.onTouchEvent(event);


    }

    @Override
    public boolean onDown(MotionEvent e) {

        flag_touch = 1;
        return true;
        //return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        System.out.println("fling up");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {


        float dx = Math.abs(e2.getX() - e1.getX());
        float dy = Math.abs(e2.getY() - e1.getY());

        if (flag_touch == 1) {
            if (e1.getY() + 50 < e2.getY() && dy >= dx) {
                //set();

                down();

            } else if (e1.getY() - 50 > e2.getY() && dy >= dx) {
                //set();
                up();
                System.out.println("fling up");

            } else if (e1.getX() + 50 < e2.getX() && dy < dx) {
                //set();
                right();

            } else if (e1.getX() - 50 > e2.getX() && dy < dx) {
                //set();
                left();

            }


        }

        flag_touch = 0;

        return false;
    }
}