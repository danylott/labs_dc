import frame.MainFrame;

import javax.swing.*;


public class MyGui {
    public static void main(String [] args) {
        SwingUtilities.invokeLater(
            () -> {
                MainFrame frame=MainFrame.getInstance();
                frame.setSize(300,300);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        );

    }
}
