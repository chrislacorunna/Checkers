
import java.awt.Toolkit;



public class WindowFrame extends javax.swing.JFrame {
    
    public WindowFrame(CheckersGUI cgui) {
        super("Options");
        if (cgui == null)
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        Window w = new Window(this, cgui);
        setContentPane(w);
        pack();
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width -getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2 );                               
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WindowFrame(null).setVisible(true);
            }
        });
    }
}
