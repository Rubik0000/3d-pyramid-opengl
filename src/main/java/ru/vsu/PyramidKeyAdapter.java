package ru.vsu;

import javax.media.opengl.awt.GLCanvas;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PyramidKeyAdapter extends KeyAdapter {

    private static final int COUNT_SURFACES_COMMAND_CODE = 67;
    private static final int HEIGHT_COMMAND_CODE = 72;
    private static final int RADIUS_COMMAND_CODE = 82;
    private static final int OPACITY_COMMAND_CODE = 79;

    private static final int ENTER_COMMAND_CODE = 10;
    private static final int CLEAR_COMMAND_CODE = 27;

    private final Pyramid pyramid;
    private final GLCanvas glcanvas;
    private final StringBuffer commandBuffer;


    public PyramidKeyAdapter(Pyramid pyramid, GLCanvas glcanvas) {
        this.pyramid = pyramid;
        this.glcanvas = glcanvas;
        commandBuffer = new StringBuffer();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == CLEAR_COMMAND_CODE) {
            commandBuffer.setLength(0);
            return;
        }
        switch (e.getKeyCode()) {
            case 38:
                pyramid.rotateX(-2);
                break;

            case 40:
                pyramid.rotateX(2);
                break;

            case 39:
                pyramid.rotateY(2);
                break;

            case 37:
                pyramid.rotateY(-2);
                break;

            case 88:
                pyramid.rotateZ(-2);
                break;

            case 90:
                pyramid.rotateZ(2);
                break;

            case COUNT_SURFACES_COMMAND_CODE:
            case HEIGHT_COMMAND_CODE:
            case RADIUS_COMMAND_CODE:
            case OPACITY_COMMAND_CODE:
                if (commandBuffer.length() == 0) {
                    commandBuffer.append(e.getKeyCode());
                } else {
                    commandBuffer.setLength(0);
                }
                break;
        }
        if (e.getKeyCode() >= 48 && e.getKeyCode() <= 57) {
            if (commandBuffer.length() != 0) {
                commandBuffer.append(e.getKeyChar());
            }
        } else if (e.getKeyCode() == ENTER_COMMAND_CODE) {
            if (commandBuffer.length() < 2) {
                commandBuffer.setLength(0);
                return;
            }
            int num = Integer.parseInt(commandBuffer.substring(2));
            switch (Integer.parseInt(commandBuffer.substring(0, 2))) {
                case COUNT_SURFACES_COMMAND_CODE:
                    pyramid.setCountSurfaces(num);
                    break;

                case HEIGHT_COMMAND_CODE:
                    pyramid.setHeight(num);
                    break;

                case RADIUS_COMMAND_CODE:
                    pyramid.setRadius(num);
                    break;
                case OPACITY_COMMAND_CODE:
                    pyramid.setOpacity((float) num / 10);
                    break;
            }
            commandBuffer.setLength(0);
        }
        glcanvas.display();
    }
}
