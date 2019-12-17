package ru.vsu;

import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {

    public static void main(String[] args) {
        final GLProfile profile = GLProfile.get( GLProfile.GL2 );
        GLCapabilities capabilities = new GLCapabilities( profile );

        // The canvas
        final GLCanvas glcanvas = new GLCanvas( capabilities );
        Piramid piramid = new Piramid(5, 20, 40, 0.5f);

        glcanvas.addGLEventListener( piramid );
        glcanvas.setSize( 800, 400 );
        glcanvas.addKeyListener(new PiramidKeyAdapter(piramid, glcanvas));

        final JFrame frame = new JFrame ();

        frame.getContentPane().add(glcanvas);
        frame.setResizable(false);
        frame.setSize( frame.getContentPane().getPreferredSize() );
        frame.setVisible( true );

        //final FPSAnimator animator = new FPSAnimator(glcanvas,10,false);
        //animator.start();
    }
}
