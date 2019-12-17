package ru.vsu;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.nio.FloatBuffer;
import java.util.Random;

public class Piramid implements GLEventListener {

    private final GLU glu = new GLU();
    private Random random;
    private int countSurfaces;
    private int radius;
    private int height;
    private float opacity;
    private int angle = 0;
    private boolean isRotateX = false;
    private boolean isRotateY = false;
    private boolean isRotateZ = false;


    public Piramid(int countSurfaces, int radius, int height, float opacity) {
        random = new Random();
        this.countSurfaces = countSurfaces;
        this.radius = radius;
        this.height = height;
        this.opacity = opacity;
    }

    public void rotateX(int angle) {
        this.angle = angle;
        isRotateX = true;
    }

    public void rotateY(int angle) {
        this.angle = angle;
        isRotateY = true;
    }

    public void rotateZ(int angle) {
        this.angle = angle;
        isRotateZ = true;
    }

    public void setCountSurfaces(int countSurfaces) {
        this.countSurfaces = countSurfaces;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        /*final GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);*/
        GL2 gl = glAutoDrawable.getGL().getGL2();
        setCamera(gl, glu, 100);

        gl.glColor4f(random.nextFloat(), random.nextFloat(), random.nextFloat(), opacity);

        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_SRC_ALPHA);

        float[] light_diffuse = {1.0f, 0.0f, 0.0f, 1.0f};  /* Red diffuse light. */
        float[] light_position = {0f, 0f, -1f, 0.0f};

        //gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light_diffuse, 0);
        //gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, light_diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light_position, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, FloatBuffer.wrap(new float[]{ 0.6f, 0.0f, 0.0f, 1.0f }));
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_LIGHTING);

        /* Use depth buffering for hidden surface elimination. */
        gl.glEnable(GL2.GL_DEPTH_TEST);

        //gl.glRotatef( -45, 1, 1, 0);

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    private float a = 0;
    @Override
    public void display(GLAutoDrawable glAutoDrawable) {

        GL2 gl = glAutoDrawable.getGL().getGL2();

        //gl.glClearColor(1, 1, 1, 1);
        gl.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        //setCamera(gl, glu, 100);
        //gl.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        //gl.glLoadIdentity(); // Reset The View
        //gl.glTranslatef( -0.5f, 0.0f, -6.0f ); // Move the triangle
        //gl.glRotatef( 30f, 1.0f, 0, 0);
        //gl.glRotatef( 40, 0, 1, 0);

        //gl.glRotatef( 45, 1, 0, 0);
        //gl.glRotatef( 225, 0, 1, 0);
        gl.glRotatef(angle, isRotateX ? 1 : 0, isRotateY ? 1 : 0, isRotateZ ? 1 : 0);
        angle = 0;
        isRotateX = false;
        isRotateY = false;
        isRotateZ = false;

        //gl.glRotatef( a, 0, 1, 0);
        //a += 0.2;

        /*gl.glEnable( GL2.GL_LIGHTING );
        gl.glEnable( GL2.GL_LIGHT0 );
        gl.glEnable( GL2.GL_NORMALIZE );
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL2.GL_LIGHT0, GL2.GL_AMBIENT_AND_DIFFUSE);*/

        /*gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);*/

        float[][] v = new float[countSurfaces][2];


        double angle = 0;
        double angleStep = Math.PI * 2 / countSurfaces;
        for (int i = 0; i < countSurfaces; ++i) {
            float x1 = (float) (radius * Math.cos(angle));
            float y1 = (float) (radius * Math.sin(angle));
            v[i][0] = x1;
            v[i][1] = y1;
            angle += angleStep;
            float x2 = (float) (radius * Math.cos(angle));
            float y2 = (float) (radius * Math.sin(angle));

            float[] vector1 = new float[] {x1, y1, 0};
            float[] vector2 = new float[] {x2, y2, 0};
            float[] vector3 = new float[] {0, 0, height};

            float[] normal = getNormal(vector1, vector2, vector3);

            gl.glBegin( GL2.GL_TRIANGLES );
            //gl.glNormal3f(Math.signum(x1) , Math.signum(y1) ,1);
            gl.glNormal3f(normal[0], normal[1], normal[2]);

            gl.glVertex3f(x1, y1, 0);
            gl.glVertex3f(x2, y2, 0);
            gl.glVertex3f(0, 0, height);

            gl.glEnd();
        }



        gl.glBegin(GL2.GL_POLYGON);

        gl.glNormal3f(0 , 0 ,-1);
        for (float[] floats : v) {
            gl.glVertex3f(floats[0], floats[1], 0);
        }
        gl.glEnd();

        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();

        /*final float h = ( float ) width / ( float ) height;
        gl.glViewport( 0, 0, width, height );
        gl.glMatrixMode( GL2.GL_PROJECTION );
        gl.glLoadIdentity();

        glu.gluPerspective( 45.0f, h, 1.0, 20.0 );
        gl.glMatrixMode( GL2.GL_MODELVIEW );
        gl.glLoadIdentity();*/
    }

    private void setCamera(GL2 gl, GLU glu, float distance) {
        // Change to projection matrix.
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        // Perspective.
        //float widthHeightRatio = (float) getWidth() / (float) getHeight();

        //glu.gluOrtho2D(-100, 100, -100, 100);
        glu.gluPerspective(50, 2, 1, 1024);
        glu.gluLookAt(0, 0, 100, 0, 0, 0, 0.0, 1.0, 0.0);
        //glu.gluPerspective(45, 2, 1, 1000);
        //glu.gluLookAt(0, 0, distance, 0, 0, 0, 0, 1, 0);



        // Change back to model view matrix.
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private float[] getNormal(float[] v1, float[] v2, float[] v3) {
        float x = (v2[1] - v1[1]) * (v3[2] - v1[2]) - (v3[1] - v1[1]) * (v2[2] - v1[2]);
        float y = (v3[0] - v1[0]) * (v2[2] - v1[2]) - (v2[0] - v1[0]) * (v3[2] - v1[2]);
        float z = (v2[0] - v1[0]) * (v3[1] - v1[1]) - (v3[0] - v1[0]) * (v2[1] - v1[1]);
        float len = getLength(new float[]{x, y, z});
        return new float[] {x / len, y / len, z / len};
    }

    private float getLength(float[] v) {
        return (float) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
    }
}
