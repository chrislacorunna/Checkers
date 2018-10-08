
import java.awt.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.BoxLayout;


public class CheckersGUI extends javax.swing.JFrame {

    private Board board;
    private CheckerType team1;
    private CheckerType team2;
    public static boolean sound = true;
    private static Clip clip;

    private static final int SCREENX = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int SCREENY = Toolkit.getDefaultToolkit().getScreenSize().height - 84;

    public CheckersGUI(CheckerType t1, CheckerType t2) {
        super("Checkers");
        setDefaultCloseOperation(EXIT_ON_CLOSE);        
        if (sound) {
            playSound("start.wav");
        }
        team1 = t1;
        team2 = t2;
        initComponents();
        getContentPane().setLayout(new GridBagLayout());
        setLocation((SCREENX - Board.BOARDSIZE) / 2, (SCREENY - Board.BOARDSIZE) / 4);
        board = new Board(this, new Game(t1, t2));
        getContentPane().add(board);
        pack();
        setResizable(false);
        setVisible(true);
    }

    public static void playSound(final String name) {
        try {
            if (clip != null) {
                clip.stop();
            }
            clip = null;
            clip = AudioSystem.getClip(null);
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    new File(name));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem3;

    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem3 = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("New Game");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem1.setText("Restart");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem2.setText("Options");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Settings");

        jCheckBoxMenuItem1.setSelected(sound);
        jCheckBoxMenuItem1.setText("Sound");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jCheckBoxMenuItem1);

        jMenu3.setText("Size");

        jRadioButtonMenuItem1.setSelected(Board.FIELDSIZE == 50);
        jRadioButtonMenuItem1.setText("Small");
        jRadioButtonMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jRadioButtonMenuItem1);

        jRadioButtonMenuItem2.setSelected(Board.FIELDSIZE == 67);
        jRadioButtonMenuItem2.setText("Medium");
        jRadioButtonMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jRadioButtonMenuItem2);

        jRadioButtonMenuItem3.setSelected(Board.FIELDSIZE == 84);
        jRadioButtonMenuItem3.setText("Big");
        jRadioButtonMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jRadioButtonMenuItem3);

        jMenu2.add(jMenu3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

    }

    private void jRadioButtonMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (!jRadioButtonMenuItem1.isSelected()) {
            jRadioButtonMenuItem1.setSelected(true);
        }
        jRadioButtonMenuItem2.setSelected(false);// TODO add your handling code here:
        jRadioButtonMenuItem3.setSelected(false);
        setResizable(true);
        setVisible(false);
        board.resize(50);
        setLocation((SCREENX - Board.BOARDSIZE) / 2, (SCREENY - Board.BOARDSIZE) / 4);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void jRadioButtonMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (!jRadioButtonMenuItem2.isSelected()) {
            jRadioButtonMenuItem2.setSelected(true);
        }
        jRadioButtonMenuItem1.setSelected(false);// TODO add your handling code here:
        jRadioButtonMenuItem3.setSelected(false);
        setResizable(true);
        setVisible(false);
        board.resize(67);
        setLocation((SCREENX - Board.BOARDSIZE) / 2, (SCREENY - Board.BOARDSIZE) / 4);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void jRadioButtonMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
        if (!jRadioButtonMenuItem3.isSelected()) {
            jRadioButtonMenuItem3.setSelected(true);
        }
        jRadioButtonMenuItem1.setSelected(false);// TODO add your handling code here:
        jRadioButtonMenuItem2.setSelected(false);
        setResizable(true);
        setVisible(false);
        board.resize(84);
        setLocation((SCREENX - Board.BOARDSIZE) / 2, (SCREENY - Board.BOARDSIZE) / 4);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        new CheckersGUI(team1, team2);
        this.setVisible(false);
    }

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        new WindowFrame(this).setVisible(true); // TODO add your handling code here:
    }

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        sound = jCheckBoxMenuItem1.isSelected(); // TODO add your handling code here:
    }

}
