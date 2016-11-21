package com.example.Photo_Cube;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *  OpenGL Custom renderer used with GLSurfaceView
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    Context context;   // Application's context
    //Triangle triangle;     // ( NEW )
    //Square quad;
    // Rotational angle and speed (NEW)
    private float angleTriangle = 0.0f; // (NEW)
    private float angleQuad = 0.0f;     // (NEW)
    private float speedTriangle = 1.5f; // (NEW)
    private float speedQuad = -1.4f;    // (NEW)
    public static String move="start";

    //private Pyramid pyramid;    // (NEW)
    //private Cube cube2;          // (NEW)
    private Cube[][][] cube=new Cube[3][3][3];

    GestureDetector detector;

    //private TextureCube cube;
    //private TextureCube2 tcube;
   // private  Sphere sphere;

    private static float anglePyramid = 0; // Rotational angle in degree for pyramid (NEW)
    public static float angleCube = 0;    // Rotational angle in degree for cube (NEW)
    private static float speedPyramid = 2.0f; // Rotational speed for pyramid (NEW)
    private static float speedCube = -3.0f;   // Rotational speed for cube (NEW)
    public static int count=0;

    float scale=.9f;

    // Constructor with global application context
    public MyGLRenderer(Context context) {
        this.context = context;

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                for(int k=0;k<3;k++){
                    cube[i][j][k]=new Cube();
                }
            }
        }



        cube[0][0][0].set_color(0, 6, 2, 6, 4, 6);
        cube[0][0][1].set_color(0,6,6,6,4,6);
        cube[0][0][2].set_color(0,6,6,3,4,6);

        cube[0][1][0].set_color(0,6,2,6,6,6);
        cube[0][1][1].set_color(0,6,6,6,6,6);
        cube[0][1][2].set_color(0,6,6,3,6,6);

        cube[0][2][0].set_color(0,6,2,6,6,5);
        cube[0][2][1].set_color(0,6,6,6,6,5);
        cube[0][2][2].set_color(0,6,6,3,6,5);

        cube[1][0][0].set_color(6,6,2,6,4,6);
        cube[1][0][1].set_color(6,6,6,6,4,6);
        cube[1][0][2].set_color(6,6,6,3,4,6);

        cube[1][1][0].set_color(6,6,2,6,6,6);
        cube[1][1][1].set_color(6,6,6,6,6,6);
        cube[1][1][2].set_color(6,6,6,3,6,6);

        cube[1][2][0].set_color(6,6,2,6,6,5);
        cube[1][2][1].set_color(6,6,6,6,6,5);
        cube[1][2][2].set_color(6,6,6,3,6,5);

        cube[2][0][0].set_color(6,1,2,6,4,6);
        cube[2][0][1].set_color(6,1,6,6,4,6);
        cube[2][0][2].set_color(6,1,6,3,4,6);

        cube[2][1][0].set_color(6,1,2,6,6,6);
        cube[2][1][1].set_color(6,1,6,6,6,6);
        cube[2][1][2].set_color(6,1,6,3,6,6);

        cube[2][2][0].set_color(6,1,2,6,6,5);
        cube[2][2][1].set_color(6,1,6,6,6,5);
        cube[2][2][2].set_color(6,1,6,3,6,5);




    }

    // Call back when the surface is first created or re-created
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // Set color's clear-value to black
        gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
        gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
        gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
        gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
        gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance


    }

    // Call back after onSurfaceCreated() or whenever the window's size changes
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0) height = 1;   // To prevent divide by zero
        float aspect = (float)width / height;

        // Set the viewport (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
        gl.glLoadIdentity();                 // Reset projection matrix
        // Use perspective projection
        GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);  // Select model-view matrix
        gl.glLoadIdentity();                 // Reset


    }

    public void initialize(GL10 gl){
        gl.glLoadIdentity();

        gl.glTranslatef(-0.5f, 1.0f, -10.0f);
        gl.glScalef(scale, scale, scale);
        gl.glRotatef(45, 1.0f, 1.0f, 0.0f);
    }

    // Call back to draw the current frame.
    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear color and depth buffers using clear-value set earlier
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);


        if(move.equalsIgnoreCase("start")){
            /*gl.glLoadIdentity();

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);
            gl.glScalef(scale, scale, scale);
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);


            //gl.glTranslatef(1.8f, -1.2f, -1.2f);
            //gl.glRotatef(angleCube, 1.0f, 0.0f, 0.0f);
            //gl.glTranslatef(-1.8f, 1.2f, 1.2f);




            gl.glTranslatef(2.4f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][2].draw(gl);
                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);     // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][0].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale,scale,scale);      // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            gl.glTranslatef(1.2f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][1].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }





        }

        else if(move.equalsIgnoreCase("R1")){
            /*gl.glLoadIdentity();

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);
            gl.glScalef(scale, scale, scale);
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);


            gl.glTranslatef(1.8f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 1.0f, 0.0f, 0.0f);
            gl.glTranslatef(-1.8f, 1.2f, 1.2f);




            gl.glTranslatef(2.4f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][2].draw(gl);
                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);     // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][0].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale,scale,scale);      // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            gl.glTranslatef(1.2f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][1].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }


                if(count>0){
                    angleCube += speedCube;
                    count--;

                }

                else{
                    move="start";
                    right1(gl);
                }



        }

        else if(move.equalsIgnoreCase("U1")){

            initialize(gl);







            //gl.glTranslatef(2.4f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, -2.4f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][2][j].draw(gl);
                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            initialize(gl);
            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][0][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            initialize(gl);

            //gl.glTranslatef(1.2f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, -1.2f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][1][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move = "start";
                Cube_down();
                Cube_right();
                right1(gl);
                Cube_left();
                Cube_up();


            }



        }

        else if(move.equalsIgnoreCase("U2")){

            initialize(gl);







            //gl.glTranslatef(2.4f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, -2.4f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][2][j].draw(gl);
                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            initialize(gl);
            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][0][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            initialize(gl);

            //gl.glTranslatef(1.2f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, -1.2f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][1][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move = "start";
                Cube_down();
                Cube_right();
                right1(gl);
                right1(gl);
                Cube_left();
                Cube_up();


            }



        }

        else if(move.equalsIgnoreCase("U3")){

            initialize(gl);







            //gl.glTranslatef(2.4f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, -2.4f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][2][j].draw(gl);
                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            initialize(gl);
            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(-angleCube, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][0][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            initialize(gl);

            //gl.glTranslatef(1.2f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, -1.2f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][1][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move = "start";
                Cube_down();
                Cube_right();
                right1(gl);
                right1(gl);
                right1(gl);
                Cube_left();
                Cube_up();


            }



        }

        else if(move.equalsIgnoreCase("F1")){

            initialize(gl);







            //gl.glTranslatef(2.4f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, 0.0f, -2.4f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[2][i][j].draw(gl);
                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            initialize(gl);
            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 0.0f, 0.0f, 1.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[0][i][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            initialize(gl);

            //gl.glTranslatef(1.2f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, 0.0f, -1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[1][i][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move = "start";
                //Cube_down();
                Cube_right();
                right1(gl);
                Cube_left();
                //Cube_up();


            }



        }

        else if(move.equalsIgnoreCase("F2")){

            initialize(gl);







            //gl.glTranslatef(2.4f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, 0.0f, -2.4f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[2][i][j].draw(gl);
                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            initialize(gl);
            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 0.0f, 0.0f, 1.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[0][i][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            initialize(gl);

            //gl.glTranslatef(1.2f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, 0.0f, -1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[1][i][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move = "start";
                //Cube_down();
                Cube_right();
                right1(gl);
                right1(gl);
                Cube_left();
                //Cube_up();


            }



        }

        else if(move.equalsIgnoreCase("F3")){

            initialize(gl);







            //gl.glTranslatef(2.4f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, 0.0f, -2.4f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[2][i][j].draw(gl);
                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            initialize(gl);
            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(-angleCube, 0.0f, 0.0f, 1.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[0][i][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            initialize(gl);

            //gl.glTranslatef(1.2f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, 0.0f, -1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[1][i][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move = "start";
                //Cube_down();
                Cube_right();
                right1(gl);
                right1(gl);
                right1(gl);
                Cube_left();
                //Cube_up();


            }



        }

        else if(move.equalsIgnoreCase("B1")){

            initialize(gl);



            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(-angleCube, 0.0f, 0.0f, 1.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);



            //gl.glTranslatef(2.4f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, 0.0f, -2.4f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[2][i][j].draw(gl);
                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            initialize(gl);


            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[0][i][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            initialize(gl);

            //gl.glTranslatef(1.2f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, 0.0f, -1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[1][i][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move = "start";
                //Cube_down();
                Cube_left();

                right1(gl);
                Cube_right();

                //Cube_up();


            }



        }

        else if(move.equalsIgnoreCase("B2")){

            initialize(gl);



            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(-angleCube, 0.0f, 0.0f, 1.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);



            //gl.glTranslatef(2.4f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, 0.0f, -2.4f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[2][i][j].draw(gl);
                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            initialize(gl);


            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[0][i][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            initialize(gl);

            //gl.glTranslatef(1.2f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, 0.0f, -1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[1][i][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move = "start";
                //Cube_down();
                Cube_left();
                right1(gl);
                right1(gl);
                Cube_right();

                //Cube_up();


            }



        }

        else if(move.equalsIgnoreCase("B3")){

            initialize(gl);



            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 0.0f, 0.0f, 1.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);



            //gl.glTranslatef(2.4f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, 0.0f, -2.4f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[2][i][j].draw(gl);
                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            initialize(gl);


            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[0][i][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            initialize(gl);

            //gl.glTranslatef(1.2f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, 0.0f, -1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[1][i][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, -1.2f, 0.0f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move = "start";
                //Cube_down();
                Cube_left();
                right1(gl);
                right1(gl);
                right1(gl);
                Cube_right();

                //Cube_up();


            }



        }

        else if(move.equalsIgnoreCase("D1")){

            initialize(gl);


            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(-angleCube, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);




            //gl.glTranslatef(2.4f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, -2.4f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][2][j].draw(gl);
                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            initialize(gl);


            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][0][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            initialize(gl);

            //gl.glTranslatef(1.2f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, -1.2f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][1][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move = "start";
                Cube_up();

                Cube_right();
                right1(gl);
                //right1(gl);
                Cube_left();
                Cube_down();


            }



        }

        else if(move.equalsIgnoreCase("D2")){

            initialize(gl);


            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(-angleCube, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);




            //gl.glTranslatef(2.4f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, -2.4f, 0.0f);

            for (int i = 0; i < 3; i++) {
                for(int j=0;j<3;j++){

                    cube[i][2][j].draw(gl);
                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            initialize(gl);


            for(int i = 0;i<3;i++) {
                for(int j=0;j<3;j++){

                    cube[i][0][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            initialize(gl);

            //gl.glTranslatef(1.2f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, -1.2f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][1][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move = "start";
                Cube_up();

                Cube_right();
                right1(gl);
                right1(gl);
                Cube_left();
                Cube_down();


            }


        } else if (move.equalsIgnoreCase("D3")){

            initialize(gl);


            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);




            //gl.glTranslatef(2.4f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, -2.4f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][2][j].draw(gl);
                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            initialize(gl);


            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][0][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            initialize(gl);

            //gl.glTranslatef(1.2f, 0.0f, 0.0f);
            gl.glTranslatef(0.0f, -1.2f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][1][j].draw(gl);


                    //gl.glTranslatef(0.0f, -1.2f, 0.0f);
                    gl.glTranslatef(1.2f, 0.0f, 0.0f);

                }

                //gl.glTranslatef(0.0f, 3.6f, -1.2f);
                gl.glTranslatef(-3.6f, 0.0f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move = "start";
                Cube_up();

                Cube_right();
                right1(gl);
                right1(gl);
                right1(gl);
                Cube_left();
                Cube_down();


            }



        }



        else if(move.equalsIgnoreCase("L1")){
            /*gl.glLoadIdentity();

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);
            gl.glScalef(scale, scale, scale);
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);


            gl.glTranslatef(2.4f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][2].draw(gl);
                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);     // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            gl.glTranslatef(1.8f, -1.2f, -1.2f);
            gl.glRotatef(-angleCube, 1.0f, 0.0f, 0.0f);
            gl.glTranslatef(-1.8f, 1.2f, 1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][0].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale,scale,scale);      // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            gl.glTranslatef(1.2f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][1].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }


            if(count >0){
                angleCube += speedCube;
                count--;

            }

            else{
                move="start";
                Cube_right();
                Cube_right();
                right1(gl);
                Cube_right();
                Cube_right();
            }



        }

        else if(move.equalsIgnoreCase("L2")){
            /*gl.glLoadIdentity();

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);
            gl.glScalef(scale, scale, scale);
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);







            gl.glTranslatef(2.4f, 0.0f, 0.0f);

            for(int i= 0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][2].draw(gl);
                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);     // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            gl.glTranslatef(1.8f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 1.0f, 0.0f, 0.0f);
            gl.glTranslatef(-1.8f, 1.2f, 1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][0].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale,scale,scale);      // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            gl.glTranslatef(1.2f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][1].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move="start";
                Cube_right();
                Cube_right();
                right1(gl);
                right1(gl);

                Cube_right();
                Cube_right();
            }



        } else if(move.equalsIgnoreCase("L3")){
            /*gl.glLoadIdentity();

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);
            gl.glScalef(scale, scale, scale);
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);







            gl.glTranslatef(2.4f, 0.0f, 0.0f);

            for (int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][2].draw(gl);
                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);     // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            gl.glTranslatef(1.8f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 1.0f, 0.0f, 0.0f);
            gl.glTranslatef(-1.8f, 1.2f, 1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][0].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale,scale,scale);      // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            gl.glTranslatef(1.2f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][1].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move="start";
                Cube_right();
                Cube_right();
                right1(gl);
                right1(gl);
                right1(gl);
                Cube_right();
                Cube_right();
            }



        }



        else if(move.equalsIgnoreCase("R2")){
            /*gl.glLoadIdentity();

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);
            gl.glScalef(scale, scale, scale);
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);


            gl.glTranslatef(1.8f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 1.0f, 0.0f, 0.0f);
            gl.glTranslatef(-1.8f, 1.2f, 1.2f);




            gl.glTranslatef(2.4f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][2].draw(gl);
                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);     // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][0].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale,scale,scale);      // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            gl.glTranslatef(1.2f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][1].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;


            }

            else{
                move="start";
                right1(gl);
                right1(gl);
            }



        } else if(move.equalsIgnoreCase("R3")){
           /* gl.glLoadIdentity();

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);
            gl.glScalef(scale, scale, scale);
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);


            gl.glTranslatef(1.8f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, -1.0f, 0.0f, 0.0f);
            gl.glTranslatef(-1.8f, 1.2f, 1.2f);




            gl.glTranslatef(2.4f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][2].draw(gl);
                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);     // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][0].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

           /* gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale,scale,scale);      // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            gl.glTranslatef(1.2f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][1].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move="start";
                right1(gl);
                right1(gl);
                right1(gl);

            }



        }

        else if(move.equalsIgnoreCase("cube_up")){
            /*gl.glLoadIdentity();

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);
            gl.glScalef(scale, scale, scale);
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);


            gl.glTranslatef(1.8f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 1.0f, 0.0f, 0.0f);
            gl.glTranslatef(-1.8f, 1.2f, 1.2f);




            gl.glTranslatef(2.4f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][2].draw(gl);
                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

           /* gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);     // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            //added
            gl.glTranslatef(1.8f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 1.0f, 0.0f, 0.0f);
            gl.glTranslatef(-1.8f, 1.2f, 1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][0].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);      // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            //added
            gl.glTranslatef(1.8f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 1.0f, 0.0f, 0.0f);
            gl.glTranslatef(-1.8f, 1.2f, 1.2f);

            gl.glTranslatef(1.2f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][1].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move="start";
                Cube_up();
            }

        }

        else if(move.equalsIgnoreCase("cube_down")){
            /*gl.glLoadIdentity();

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);
            gl.glScalef(scale, scale, scale);
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);


            gl.glTranslatef(1.8f, -1.2f, -1.2f);
            gl.glRotatef(-angleCube, 1.0f, 0.0f, 0.0f);
            gl.glTranslatef(-1.8f, 1.2f, 1.2f);




            gl.glTranslatef(2.4f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][2].draw(gl);
                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);     // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            //added
            gl.glTranslatef(1.8f, -1.2f, -1.2f);
            gl.glRotatef(-angleCube, 1.0f, 0.0f, 0.0f);
            gl.glTranslatef(-1.8f, 1.2f, 1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][0].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);      // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            //added
            gl.glTranslatef(1.8f, -1.2f, -1.2f);
            gl.glRotatef(-angleCube, 1.0f, 0.0f, 0.0f);
            gl.glTranslatef(-1.8f, 1.2f, 1.2f);

            gl.glTranslatef(1.2f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][1].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move="start";
                Cube_up();
                Cube_up();
                Cube_up();
            }

        }

        else if(move.equalsIgnoreCase("cube_right")){
            /*gl.glLoadIdentity();

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);
            gl.glScalef(scale, scale, scale);
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);


            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(-angleCube, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);




            gl.glTranslatef(2.4f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][2].draw(gl);
                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);     // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            //added
            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(-angleCube, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][0].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);      // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            //added
            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(-angleCube, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);

            gl.glTranslatef(1.2f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][1].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move="start";
                Cube_right();
            }

        }

        else if(move.equalsIgnoreCase("cube_left")){
            /*gl.glLoadIdentity();

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);
            gl.glScalef(scale, scale, scale);
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);


            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);




            gl.glTranslatef(2.4f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][2].draw(gl);
                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

            /*gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);     // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            //added
            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][0].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }

           /* gl.glLoadIdentity();                 // Reset the mode-view matrix (NEW)

            gl.glTranslatef(-0.5f, 1.0f, -10.0f);  // Translate right and into the screen (NEW)

            gl.glScalef(scale, scale, scale);      // Scale down (NEW)
            gl.glRotatef(45, 1.0f, 1.0f, 0.0f);*/
            initialize(gl);

            //added
            gl.glTranslatef(1.2f, -1.2f, -1.2f);
            gl.glRotatef(angleCube, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(-1.2f, 1.2f, 1.2f);

            gl.glTranslatef(1.2f, 0.0f, 0.0f);

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){

                    cube[i][j][1].draw(gl);


                    gl.glTranslatef(0.0f, -1.2f, 0.0f);

                }

                gl.glTranslatef(0.0f, 3.6f, -1.2f);

            }


            if(count>0){
                angleCube += speedCube;
                count--;

            }

            else{
                move="start";
                Cube_right();
                Cube_right();
                Cube_right();
            }

        }


    }



    void right1(GL10 gl){
        Cube[][][] temp=new Cube[3][3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                for(int k=0;k<3;k++){
                    temp[i][j][k]=new Cube(cube[i][j][k]);


                }
            }
        }

        temp[0][0][2].func[0]=cube[0][2][2].func[5];
        temp[0][0][2].func[4]=cube[0][2][2].func[0];
        temp[0][0][2].func[3]=cube[0][2][2].func[3];

        temp[1][0][2].func[4]=cube[0][1][2].func[0];
        temp[1][0][2].func[3]=cube[0][1][2].func[3];

        temp[2][0][2].func[4]=cube[0][0][2].func[0];
        temp[2][0][2].func[1]=cube[0][0][2].func[4];
        temp[2][0][2].func[3]=cube[0][0][2].func[3];

        temp[2][1][2].func[1]=cube[1][0][2].func[4];
        temp[2][1][2].func[3]=cube[1][0][2].func[3];

        temp[2][2][2].func[1]=cube[2][0][2].func[4];
        temp[2][2][2].func[5]=cube[2][0][2].func[1];
        temp[2][2][2].func[3]=cube[2][0][2].func[3];

        temp[1][2][2].func[5]=cube[2][1][2].func[1];
        temp[1][2][2].func[3]=cube[2][1][2].func[3];

        temp[0][2][2].func[0]=cube[2][2][2].func[5];
        temp[0][2][2].func[5]=cube[2][2][2].func[1];
        temp[0][2][2].func[3]=cube[2][2][2].func[3];

        temp[0][1][2].func[0]=cube[1][2][2].func[5];
        temp[0][1][2].func[3]=cube[1][2][2].func[3];

        cube=temp;




    }

    void Cube_up(){
        Cube[][][] temp=new Cube[3][3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                for(int k=0;k<3;k++){
                    temp[i][j][k]=new Cube(cube[i][j][k]);


                }
            }
        }

        temp[0][0][0].func[0]=cube[0][2][0].func[5];
        temp[0][0][0].func[4]=cube[0][2][0].func[0];
        temp[0][0][0].func[2]=cube[0][2][0].func[2];

        temp[0][0][1].func[0]=cube[0][2][1].func[5];
        temp[0][0][1].func[4]=cube[0][2][1].func[0];

        temp[0][0][2].func[0]=cube[0][2][2].func[5];
        temp[0][0][2].func[4]=cube[0][2][2].func[0];
        temp[0][0][2].func[3]=cube[0][2][2].func[3];

        temp[0][1][0].func[0]=cube[1][2][0].func[5];
        temp[0][1][0].func[2]=cube[1][2][0].func[2];
        temp[0][1][1].func[0]=cube[1][2][1].func[5];
        temp[0][1][2].func[0]=cube[1][2][2].func[5];
        temp[0][1][2].func[3]=cube[1][2][2].func[3];

        temp[0][2][0].func[0]=cube[2][2][0].func[5];
        temp[0][2][0].func[5]=cube[2][2][0].func[1];
        temp[0][2][0].func[2]=cube[2][2][0].func[2];

        temp[0][2][1].func[0]=cube[2][2][1].func[5];
        temp[0][2][1].func[5]=cube[2][2][1].func[1];

        temp[0][2][2].func[0]=cube[2][2][2].func[5];
        temp[0][2][2].func[5]=cube[2][2][2].func[1];
        temp[0][2][2].func[3]=cube[2][2][2].func[3];

        temp[1][2][0].func[5]=cube[2][1][0].func[1];
        temp[1][2][0].func[2]=cube[2][1][0].func[2];
        temp[1][2][1].func[5]=cube[2][1][1].func[1];
        temp[1][2][2].func[5]=cube[2][1][2].func[1];
        temp[1][2][2].func[3]=cube[2][1][2].func[3];

        temp[2][2][0].func[5]=cube[2][0][0].func[1];
        temp[2][2][0].func[1]=cube[2][0][0].func[4];
        temp[2][2][0].func[2]=cube[2][0][0].func[2];

        temp[2][2][1].func[5]=cube[2][0][1].func[1];
        temp[2][2][1].func[1]=cube[2][0][1].func[4];

        temp[2][2][2].func[5]=cube[2][0][2].func[1];
        temp[2][2][2].func[1]=cube[2][0][2].func[4];
        temp[2][2][2].func[3]=cube[2][0][2].func[3];

        temp[2][1][0].func[1]=cube[1][0][0].func[4];
        temp[2][1][0].func[2]=cube[1][0][0].func[2];
        temp[2][1][1].func[1]=cube[1][0][1].func[4];
        temp[2][1][2].func[1]=cube[1][0][2].func[4];
        temp[2][1][2].func[3]=cube[1][0][2].func[3];

        temp[2][0][0].func[1]=cube[0][0][0].func[4];
        temp[2][0][0].func[4]=cube[0][0][0].func[0];
        temp[2][0][0].func[2]=cube[0][0][0].func[2];

        temp[2][0][1].func[1]=cube[0][0][1].func[4];
        temp[2][0][1].func[4]=cube[0][0][1].func[0];

        temp[2][0][2].func[1]=cube[0][0][2].func[4];
        temp[2][0][2].func[4]=cube[0][0][2].func[0];
        temp[2][0][2].func[3]=cube[0][0][2].func[3];

        temp[1][0][0].func[4]=cube[0][1][0].func[0];
        temp[1][0][0].func[2]=cube[0][1][0].func[2];
        temp[1][0][1].func[4]=cube[0][1][1].func[0];
        temp[1][0][2].func[4]=cube[0][1][2].func[0];
        temp[1][0][2].func[3]=cube[0][1][2].func[3];

        cube=temp;




    }

    void Cube_left(){
        Cube_right();
        Cube_right();
        Cube_right();

    }

    void Cube_down(){
        Cube_up();
        Cube_up();
        Cube_up();
    }

    void Cube_right(){
        Cube[][][] temp=new Cube[3][3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                for(int k=0;k<3;k++){
                    temp[i][j][k]=new Cube(cube[i][j][k]);


                }
            }
        }

        temp[0][0][0].func[0]=cube[2][0][0].func[2];
        temp[0][0][0].func[4]=cube[2][0][0].func[4];
        temp[0][0][0].func[2]=cube[2][0][0].func[1];

        temp[0][0][1].func[0]=cube[1][0][0].func[2];
        temp[0][0][1].func[4]=cube[1][0][0].func[4];

        temp[0][0][2].func[0]=cube[0][0][0].func[2];
        temp[0][0][2].func[4]=cube[0][0][0].func[4];
        temp[0][0][2].func[3]=cube[0][0][0].func[0];

        temp[0][1][0].func[0]=cube[2][1][0].func[2];
        temp[0][1][0].func[2]=cube[2][1][0].func[1];

        temp[0][1][1].func[0]=cube[1][1][0].func[2];

        temp[0][1][2].func[0]=cube[0][1][0].func[2];
        temp[0][1][2].func[3]=cube[0][1][0].func[0];

        temp[0][2][0].func[0]=cube[2][2][0].func[2];
        temp[0][2][0].func[2]=cube[2][2][0].func[1];
        temp[0][2][0].func[5]=cube[2][2][0].func[5];

        temp[0][2][1].func[0]=cube[1][2][0].func[2];
        temp[0][2][1].func[5]=cube[1][2][0].func[5];

        temp[0][2][2].func[0]=cube[0][2][0].func[2];
        temp[0][2][2].func[3]=cube[0][2][0].func[0];
        temp[0][2][2].func[5]=cube[0][2][0].func[5];

        temp[1][0][0].func[4]=cube[2][0][1].func[4];
        temp[1][0][0].func[2]=cube[2][0][1].func[1];

        temp[1][0][2].func[4]=cube[0][0][1].func[4];
        temp[1][0][2].func[3]=cube[0][0][1].func[0];

        temp[1][1][0].func[2]=cube[2][1][1].func[1];

        temp[1][1][2].func[3]=cube[0][1][1].func[0];

        temp[1][2][0].func[2]=cube[2][2][1].func[1];
        temp[1][2][0].func[5]=cube[2][2][1].func[5];

        temp[1][2][2].func[3]=cube[0][2][1].func[0];
        temp[1][2][2].func[5]=cube[0][2][1].func[5];

        temp[2][0][0].func[4]=cube[2][0][2].func[4];
        temp[2][0][0].func[2]=cube[2][0][2].func[1];
        temp[2][0][0].func[1]=cube[2][0][2].func[3];

        temp[2][0][1].func[4]=cube[1][0][2].func[4];
        temp[2][0][1].func[1]=cube[1][0][2].func[3];

        temp[2][0][2].func[4]=cube[0][0][2].func[4];
        temp[2][0][2].func[1]=cube[0][0][2].func[3];
        temp[2][0][2].func[3]=cube[0][0][2].func[0];

        temp[2][1][0].func[2]=cube[2][1][2].func[1];
        temp[2][1][0].func[1]=cube[2][1][2].func[3];

        temp[2][1][1].func[1]=cube[1][1][2].func[3];

        temp[2][1][2].func[1]=cube[0][1][2].func[3];
        temp[2][1][2].func[3]=cube[0][1][2].func[0];

        temp[2][2][0].func[2]=cube[2][2][2].func[1];
        temp[2][2][0].func[5]=cube[2][2][2].func[5];
        temp[2][2][0].func[1]=cube[2][2][2].func[3];

        temp[2][2][1].func[1]=cube[1][2][2].func[3];
        temp[2][2][1].func[5]=cube[1][2][2].func[5];

        temp[2][2][2].func[1]=cube[0][2][2].func[3];
        temp[2][2][2].func[3]=cube[0][2][2].func[0];
        temp[2][2][2].func[5]=cube[0][2][2].func[5];


        cube=temp;


    }


}