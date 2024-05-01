import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;

import java.awt.*;
import java.util.Map;

public class ScriptingTemplate {
    private static boolean run = true;

    public static void main(String[] args) {

        /* Included in this file are all the relevant method calls for scripting using the mouse and keyboard,
         * they have been grouped together for ease of use. The goal of this code is to make the existing framework
         * easy to use and simple to modify.
         *
         *   *** IMPORTANT NOTE ***
         *  When the variable 'run' defined at the top is set to false, the program will terminate.
         */


    }

    public static void requiredMethodForAllPeripheralEvents() {

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // This section instantiates both the GlobalKeyboardHook and GlobalMouseHook
        // For ease of use all methods for both mouse and keyboard are included in the file in their respective sections

        GlobalMouseHook mouseHook = new GlobalMouseHook(); // Add true to the constructor, to switch to raw input mode
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true);

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("| Press [middle] mouse wheel to begin. Press [middle] mouse wheel again to terminate |");
        System.out.println("--------------------------------------------------------------------------------------\n\n");
        System.out.println("Global mouse hook successfully started. Connected mice:");

        for (Map.Entry<Long, String> keyboard : GlobalKeyboardHook.listKeyboards().entrySet()) {
            System.out.format("%d: %s\n", keyboard.getKey(), keyboard.getValue());
            // Keyboard Connections
        }

        for (Map.Entry<Long, String> mouse : GlobalMouseHook.listMice().entrySet()) {
            System.out.format("%d: %s\n", mouse.getKey(), mouse.getValue());
            // Mouse Connections
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // This section handles specifically mouse interactions
        mouseHook.addMouseListener(new GlobalMouseAdapter() {

            @Override
            public void mousePressed(GlobalMouseEvent event) {
                System.out.println(event);
                if ((event.getButtons() & GlobalMouseEvent.BUTTON_LEFT) != GlobalMouseEvent.BUTTON_NO
                        && (event.getButtons() & GlobalMouseEvent.BUTTON_RIGHT) != GlobalMouseEvent.BUTTON_NO) {
                    System.out.println("Both mouse buttons are currently pressed!");
                }
                if (event.getButton() == GlobalMouseEvent.BUTTON_MIDDLE) {
                    run = false;
                }
            }

            @Override
            public void mouseReleased(GlobalMouseEvent event) {
                System.out.println("Mouse released here: " + event);
            }

            @Override
            public void mouseMoved(GlobalMouseEvent event) {
                System.out.println("Mouse moved here: " + event);
            }

            @Override
            public void mouseWheel(GlobalMouseEvent event) {
                System.out.println("Mouse wheel moved!");
            }

        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // This section handles specifically keyboard interactions
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            @Override
            public void keyPressed(GlobalKeyEvent event) {
                System.out.println(event.getKeyChar() + " pressed!");
            }

            @Override
            public void keyReleased(GlobalKeyEvent event) {
                System.out.println(event.getKeyChar() + " released!");
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Required exception handling that must be included
        try {
            while (run) {
                Thread.sleep(128);
            }
        } catch (InterruptedException e) {
            //Do nothing
        } finally {
            mouseHook.shutdownHook();
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    public static void BasicScriptExample() throws AWTException, InterruptedException {
//      The Robot class is what actually performs the tasks required to script,
//      this is where the sequence of inputs are entered.
//      To best utilize it, enter the button sequence desired for the script in order.
        Robot robot = new Robot();

//      keyPress() & keyReleased() take in an integer that relates to a key (aka. a keycode), to see all keycodes
//      call the RobotKeyCodes() function provided at the bottom of this file.
//      Currently, these functions only presses the CAPS LOCK once then releases, as seen below.
        robot.keyPress(20);
        robot.keyRelease(20);

//      Thread.sleep() will pause the program for x amount of milliseconds and can be placed
//      in between robot.KeyPressed() & robot.keyReleased() calls if time is needed in between button presses.
        Thread.sleep(1000);
    }

    public static void RobotKeyCodes() {
        for (int i = 0; i < 1000000; ++i) {
            String text = java.awt.event.KeyEvent.getKeyText(i);
            if (!text.contains("Unknown keyCode: ")) {
                System.out.println("" + i + " -- " + text);
            }
        }
    }

    public static void Driver() throws AWTException, InterruptedException{
//     The best way of leveraging this code is to call Driver() from inside one of the keyboard or mouse events in the first method.
//     From here you create your button sequence as shown in BasicScriptExample(). Once you are satisfied with your code,
//     simply call the requiredMethodForAllPeripheralEvents() inside of main() and you're good to go.
    }

}
