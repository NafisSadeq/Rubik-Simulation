package com.example.Photo_Cube;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Cube {
    private FloatBuffer vertexBuffer;  // Buffer for vertex-array
    private int numFaces = 6;

    private float[][] colors = {  // Colors of the 6 faces
            {1.0f, 1.0f, 1.0f, 1.0f},
            {1.0f, 1.0f, 0.0f, 1.0f},
            {1.0f, 0.0f, 0.0f, 1.0f},
            {1.0f, 0.5f, 0.0f, 1.0f},  // 0. orange
            //{1.0f, 0.0f, 1.0f, 1.0f},  // 1. violet
            {0.0f, 1.0f, 0.0f, 0.5f},  // 2. green
            {0.0f, 0.0f, 1.0f, 1.0f},  // 3. blue
            {0.3f, 0.3f, 0.3f, 1.0f}
            //{1.0f, 0.0f, 0.0f, 1.0f},  // 4. red
            //{1.0f, 1.0f, 0.0f, 1.0f}   // 5. yellow
    };

    public  int[] func={
            6,6,6,6,6,6

    };

    public Cube(Cube c){

        for(int i=0;i<6;i++){
            func[i]=c.func[i];
        }

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices2.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices2);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind
    }

    public void set_color(int front,int back,int left,int right,int up,int down){
        func[0]=front;
        func[1]=back;
        func[2]=left;
        func[3]=right;
        func[4]=up;
        func[5]=down;


    }

    /*private float[] vertices = {  // Vertices of the 6 faces
            // FRONT
            -1.0f, -1.0f,  1.0f,  // 0. left-bottom-front
            1.0f, -1.0f,  1.0f,  // 1. right-bottom-front
            -1.0f,  1.0f,  1.0f,  // 2. left-top-front
            1.0f,  1.0f,  1.0f,  // 3. right-top-front
            // BACK
            1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
            -1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
            1.0f,  1.0f, -1.0f,  // 7. right-top-back
            -1.0f,  1.0f, -1.0f,  // 5. left-top-back
            // LEFT
            -1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
            -1.0f, -1.0f,  1.0f,  // 0. left-bottom-front
            -1.0f,  1.0f, -1.0f,  // 5. left-top-back
            -1.0f,  1.0f,  1.0f,  // 2. left-top-front
            // RIGHT
            1.0f, -1.0f,  1.0f,  // 1. right-bottom-front
            1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
            1.0f,  1.0f,  1.0f,  // 3. right-top-front
            1.0f,  1.0f, -1.0f,  // 7. right-top-back
            // TOP
            -1.0f,  1.0f,  1.0f,  // 2. left-top-front
            1.0f,  1.0f,  1.0f,  // 3. right-top-front
            -1.0f,  1.0f, -1.0f,  // 5. left-top-back
            1.0f,  1.0f, -1.0f,  // 7. right-top-back
            // BOTTOM
            -1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
            1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
            -1.0f, -1.0f,  1.0f,  // 0. left-bottom-front
            1.0f, -1.0f,  1.0f   // 1. right-bottom-front
    };*/

    private float[] vertices2 = {  // Vertices of the 6 faces
            // FRONT
            -0.55f, -0.55f,  0.55f,  // 0. left-bottom-front
            0.55f, -0.55f,  0.55f,  // 1. right-bottom-front
            -0.55f,  0.55f,  0.55f,  // 2. left-top-front
            0.55f,  0.55f,  0.55f,  // 3. right-top-front
            // BACK
            0.55f, -0.55f, -0.55f,  // 6. right-bottom-back
            -0.55f, -0.55f, -0.55f,  // 4. left-bottom-back
            0.55f,  0.55f, -0.55f,  // 7. right-top-back
            -0.55f,  0.55f, -0.55f,  // 5. left-top-back
            // LEFT
            -0.55f, -0.55f, -0.55f,  // 4. left-bottom-back
            -0.55f, -0.55f,  0.55f,  // 0. left-bottom-front
            -0.55f,  0.55f, -0.55f,  // 5. left-top-back
            -0.55f,  0.55f,  0.55f,  // 2. left-top-front
            // RIGHT
            0.55f, -0.55f,  0.55f,  // 1. right-bottom-front
            0.55f, -0.55f, -0.55f,  // 6. right-bottom-back
            0.55f,  0.55f,  0.55f,  // 3. right-top-front
            0.55f,  0.55f, -0.55f,  // 7. right-top-back
            // TOP
            -0.55f,  0.55f,  0.55f,  // 2. left-top-front
            0.55f,  0.55f,  0.55f,  // 3. right-top-front
            -0.55f,  0.55f, -0.55f,  // 5. left-top-back
            0.55f,  0.55f, -0.55f,  // 7. right-top-back
            // BOTTOM
            -0.55f, -0.55f, -0.55f,  // 4. left-bottom-back
            0.55f, -0.55f, -0.55f,  // 6. right-bottom-back
            -0.55f, -0.55f,  0.55f,  // 0. left-bottom-front
            0.55f, -0.55f,  0.55f   // 1. right-bottom-front
    };

    // Constructor - Set up the buffers
    public Cube() {
        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices2.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices2);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind
    }

    // Draw the shape
    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CCW);    // Front face in counter-clockwise orientation
        gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
        gl.glCullFace(GL10.GL_BACK);    // Cull the back face (don't display)

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        // Render all the faces
        for (int face = 0; face < numFaces; face++) {
            // Set the color for each of the faces
            gl.glColor4f(colors[func[face]][0], colors[func[face]][1], colors[func[face]][2], colors[func[face]][3]);
            // Draw the primitive from the vertex-array directly
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face*4, 4);
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }
}