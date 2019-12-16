package ru.vsu;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.glu.GLU;
import java.nio.FloatBuffer;
import java.util.Random;

public class Piramid implements GLEventListener {

    private final GLU glu = new GLU();
    private final Random random;
    private final int countSurfaces;
    private final int radius;
    private final int height;
    private final float opacity;


    public Piramid(int countSurfaces, int radius, int height, float opacity) {
        random = new Random();
        this.countSurfaces = countSurfaces;
        this.radius = radius;
        this.height = height;
        this.opacity = opacity;
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
        float[] light_position = {-1f, 0f, 1.0f, 0.0f};

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light_diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light_position, 0);
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

        gl.glRotatef( 45, 1, 0, 0);
        gl.glRotatef( 225, 0, 1, 0);

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
            gl.glBegin( GL2.GL_TRIANGLES );
            //gl.glColor4f(random.nextFloat(), random.nextFloat(), random.nextFloat(), opacity);
            float x1 = (float) (radius * Math.cos(angle));
            float y1 = (float) (radius * Math.sin(angle));
            v[i][0] = x1;
            v[i][1] = y1;
            gl.glNormal3f(Math.signum(x1) , Math.signum(y1) ,1);

            gl.glVertex3f(x1, y1, 0);
            angle += angleStep;
            float x2 = (float) (radius * Math.cos(angle));
            float y2 = (float) (radius * Math.sin(angle));
            gl.glVertex3f(x2, y2, 0);
            gl.glVertex3f(0, 0, height);

            gl.glEnd();
            //gl.glColor4f(1, 1, 1, opacity);
            /*gl.glVertex3f(x1, y1, 0);
            gl.glVertex3f(x2, y2, 0);
            gl.glVertex3f(0, 0, 0);*/
        }


        // Done Drawing 3d triangle (Pyramid)


        gl.glBegin(GL2.GL_POLYGON);
        gl.glNormal3f(0 , 0 ,-1);
        for (float[] floats : v) {
            gl.glVertex3f(floats[0], floats[1], 0);
        }

        gl.glEnd();


        // multicolor diffuse
        /*float[] diffuseLight = { 60, 0, 100, 0 };
        gl.glLightfv( GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0 );*/


        gl.glFlush();

        //gl.glEnable( GL2.GL_LIGHTING );
        //gl.glEnable( GL2.GL_LIGHT0 );
        //gl.glEnable( GL2.GL_DEPTH_TEST );

        /*float[] ambientLight = {0f, 0f, 1f,0f };
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);

        float[] specularLight = {1f, 0f, 0f,0f };
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularLight, 0);*/


        /*float[] diffuseLight = { 1f,0f,50f,0f };
        gl.glLightfv( GLLightingFunc.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0 );*/

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
}
