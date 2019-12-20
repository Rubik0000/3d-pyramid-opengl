package ru.vsu;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.util.Random;

public class Pyramid implements GLEventListener {

    private final GLU glu = new GLU();
    private int countSurfaces;
    private int radius;
    private int height;
    private float opacity;
    private int angle = 0;
    private boolean isRotateX = false;
    private boolean isRotateY = false;
    private boolean isRotateZ = false;


    public Pyramid(int countSurfaces, int radius, int height, float opacity) {
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

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        // set camera parameters
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        float ratio = (float) glAutoDrawable.getWidth() / glAutoDrawable.getHeight();
        glu.gluPerspective(50, ratio, 1, 1024);
        glu.gluLookAt(0, 0, 100, 0, 0, 0, 0.0, 1.0, 0.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        // enable opacity
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_SRC_ALPHA);

        float[] light_position = {0f, 0f, 1f, 0};

        // enable lighting
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light_position, 0);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) { }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );

        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, new float[]{ 0.8f, 0.6f, 0.0f, opacity }, 0);

        gl.glRotatef(angle, isRotateX ? 1 : 0, isRotateY ? 1 : 0, isRotateZ ? 1 : 0);
        angle = 0;
        isRotateX = isRotateY = isRotateZ = false;

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

            // get normal vector to surface
            float[] normal = getNormal(vector1, vector2, vector3);

            // define triangle surface
            gl.glBegin(GL2.GL_TRIANGLES);
            gl.glNormal3f(normal[0], normal[1], normal[2]);
            gl.glVertex3f(x1, y1, 0);
            gl.glVertex3f(x2, y2, 0);
            gl.glVertex3f(0, 0, height);
            gl.glEnd();
        }

        // define bottom of the pyramid
        gl.glBegin(GL2.GL_POLYGON);
        gl.glNormal3f(0 , 0 ,-1);
        for (float[] floats : v) {
            gl.glVertex3f(floats[0], floats[1], 0);
        }
        gl.glEnd();
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) { }

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
