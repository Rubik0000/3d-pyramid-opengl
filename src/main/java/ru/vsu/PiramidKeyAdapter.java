package ru.vsu;

import javax.media.opengl.awt.GLCanvas;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PiramidKeyAdapter extends KeyAdapter {

    private static final int COUNT_SURFACES_COMMAND_CODE = 67;
    private static final int HEIGHT_COMMAND_CODE = 72;
    private static final int RADIUS_COMMAND_CODE = 82;

    private static final int ENTER_COMMAND_CODE = 10;
    private static final int CLEAR_COMMAND_CODE = 27;

    private final Piramid piramid;
    private final GLCanvas glcanvas;
    private final StringBuffer commandBuffer;


    public PiramidKeyAdapter(Piramid piramid, GLCanvas glcanvas) {
        this.piramid = piramid;
        this.glcanvas = glcanvas;
        commandBuffer = new StringBuffer();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        // 48
        if (e.getKeyCode() == CLEAR_COMMAND_CODE) {
            commandBuffer.setLength(0);
            return;
        }
        switch (e.getKeyCode()) {
            case 38:
                piramid.rotateX(-2);
                break;

            case 40:
                piramid.rotateX(2);
                break;

            case 39:
                piramid.rotateY(2);
                break;

            case 37:
                piramid.rotateY(-2);
                break;

            case 88:
                piramid.rotateZ(-2);
                break;

            case 90:
                piramid.rotateZ(2);
                break;

            case COUNT_SURFACES_COMMAND_CODE:
                if (commandBuffer.length() == 0) {
                    commandBuffer.append(COUNT_SURFACES_COMMAND_CODE);
                } else {
                    commandBuffer.setLength(0);
                }
                break;

            case HEIGHT_COMMAND_CODE:
                if (commandBuffer.length() == 0) {
                    commandBuffer.append(HEIGHT_COMMAND_CODE);
                } else {
                    commandBuffer.setLength(0);
                }
                break;

            case RADIUS_COMMAND_CODE:
                if (commandBuffer.length() == 0) {
                    commandBuffer.append(RADIUS_COMMAND_CODE);
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
                    piramid.setCountSurfaces(num);
                    break;

                case HEIGHT_COMMAND_CODE:
                    piramid.setHeight(num);
                    break;

                case RADIUS_COMMAND_CODE:
                    piramid.setRadius(num);
                    break;
            }
            commandBuffer.setLength(0);
        }
        glcanvas.display();
    }
}
