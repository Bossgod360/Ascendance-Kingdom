package Game_Menu.Story_Mode.MK;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean pressedJ, pressedK, pressedL;
    /**
     * Invoked when a key is typed. This method does nothing in this implementation.
     *
     * @param e The KeyEvent object describing the key event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Method intentionally left blank
    }

    /**
     * Invoked when a key is pressed. Updates the corresponding boolean flags based on the key code.
     *
     * @param e The KeyEvent object describing the key event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_J) {
            pressedJ = true;
        }
        if (code == KeyEvent.VK_K) {
            pressedK = true;
        }
        if (code == KeyEvent.VK_L) {
            pressedL = true;
        }
    }


    /**
     * Invoked when a key is released. Resets the corresponding boolean flags based on the key code.
     *
     * @param e The KeyEvent object describing the key event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_J) {
            pressedJ = false;
        }
        if (code == KeyEvent.VK_K) {
            pressedK = false;
        }
        if (code == KeyEvent.VK_L) {
            pressedL = false;
        }
    }

}
