package application;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import entities.InterfaceGrafica;

public class Main {

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeAndWait(InterfaceGrafica::new);
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
