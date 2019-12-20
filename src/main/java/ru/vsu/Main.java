package ru.vsu;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {
        final GLProfile profile = GLProfile.get( GLProfile.GL2 );
        GLCapabilities capabilities = new GLCapabilities(profile);
        final GLCanvas glcanvas = new GLCanvas( capabilities );
        Pyramid pyramid = new Pyramid(5, 20, 40, 1);

        glcanvas.addGLEventListener(pyramid);
        glcanvas.setSize( 800, 800 );
        glcanvas.addKeyListener(new PyramidKeyAdapter(pyramid, glcanvas));

        final JFrame frame = new JFrame ();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                glcanvas.destroy();
                frame.setVisible(false);
                frame.dispose();
            }
        });

        frame.getContentPane().add(glcanvas);
        frame.setResizable(false);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible( true );

        //final FPSAnimator animator = new FPSAnimator(glcanvas,10,false);
        //animator.start();
    }
}
