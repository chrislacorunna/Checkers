import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

public class Board extends javax.swing.JComponent {

    public static int FIELDSIZE = 67;
    public static int BOARDSIZE = 8 * FIELDSIZE;
    private Dimension dimPrefSize;
    private CheckerType whoseTurn;
    private boolean inDrag = false;
    private Checker dragged;        //przenoszony pion
    private Checker prevCapturing;  //pion, którym wykonano bicie i jest konieczność wykonanie kolejnego bicia
    private int prev_x, prev_y;     //współrzędne piona przed przenoszeniem
    private int dx, dy;             //różnica współrzędnych miejsca kliknięcia myszą i środka klikniętego piona
    private CheckersGUI cgui;
    private Game game;

    public Board(CheckersGUI cgui, Game game) {
        this.cgui = cgui;
        this.game = game;
        dimPrefSize = new Dimension(BOARDSIZE, BOARDSIZE);
        whoseTurn = (game.team1 == CheckerType.WHITE || game.team2 == CheckerType.WHITE) ? CheckerType.WHITE : game.team2;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (game.inGame) {
                    int x = me.getX();
                    int y = me.getY();
                    for (int i = 0; i < game.n; i++) {
                        if (game.checkers[i].contains(x, y) && game.checkers[i].getCheckertype() == whoseTurn) {
                            dragged = game.checkers[i];
                            prev_x = dragged.x;
                            prev_y = dragged.y;
                            dx = x - dragged.x;
                            dy = y - dragged.y;
                            inDrag = true;
                            return;
                        }
                    }
                } else {
                    new WindowFrame(cgui).setVisible(true);     //wyświetl okno ustawień
                    removeMouseListener(this);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (inDrag) {
                    inDrag = false;
                } else {
                    return;
                }
                int x = me.getX();
                int y = me.getY();
                dragged.x = (x - dx) / FIELDSIZE * FIELDSIZE //zmiana współrzędnych piona
                        + FIELDSIZE / 2;
                dragged.y = (y - dy) / FIELDSIZE * FIELDSIZE
                        + FIELDSIZE / 2;
                Checker r;
                if (game.isAnyCapturePossible(dragged.getCheckertype()) == false) {                  //jeżeli drużyna nie ma szans na bicie
                    if (game.isMovePossible(dragged, whichRow(dragged.y), whichColumn(dragged.x))) { //rozpatrz czy dany ruch jest możliwy
                        dragged.i = whichRow(dragged.y);                                        //przesunięcie piona
                        dragged.j = whichColumn(dragged.x);
                        if ((dragged.getCheckertype() == game.team1 && dragged.i == 8)
                         || (dragged.getCheckertype() == game.team2 && dragged.i == 1)) {
                            dragged.isKing = true;                                      //jeżeli przesunięto na przeciwległy
                        }                                                               //koniec planszy tzn. że pion staje się damką
                        whoseTurn = (whoseTurn == game.team1) ? game.team2 : game.team1;
                        game.isAnyMovePossible(whoseTurn);  //sprawdź czy drużyna ma możliwość jakiekolwiek ruchu
                    } else {
                        if (CheckersGUI.sound) {
                            Toolkit.getDefaultToolkit().beep();
                        }
                        dragged.x = prev_x;
                        dragged.y = prev_y;
                    }
                } else if ((r = game.isCapturePossible(dragged, whichRow(dragged.y), whichColumn(dragged.x))) != null
                        && (prevCapturing == null || prevCapturing == dragged)) { //   skoro drużyna w danym ruchu ma możliwe bicie
                    dragged.i = whichRow(dragged.y);                              //   to sprawdź czy dany ruch jest biciem
                    dragged.j = whichColumn(dragged.x);                           //   bicie jest możliwe, gdy dane bicie jest pierwszym w turze tej drużyny
                    game.remChecker(r);        //usuń zbitego piona                    bądź jest to kolejne bicie przez piona prevCapturing
                    if (game.isCapturesPossible(dragged) == false) {                   //sprawdź czy jest konieczność dalszego bicia przez piona dragged
                        if ((dragged.getCheckertype() == game.team1 && dragged.i == 8) 
                         || (dragged.getCheckertype() == game.team2 && dragged.i == 1)) {
                            dragged.isKing = true;
                        }
                        whoseTurn = (whoseTurn == game.team1) ? game.team2 : game.team1;
                        prevCapturing = null;
                        game.isAnyMovePossible(whoseTurn);  //sprawdź czy drużyna ma możliwość jakiekolwiek ruchu
                    } else {
                        prevCapturing = dragged;            //ostatnio bicie było wykonywane przez piona dragged
                    }
                } else {
                    if (CheckersGUI.sound) {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    dragged.x = prev_x;             //ani dane bicie ani ruch
                    dragged.y = prev_y;             //jest niemożliwe
                }                                   //pion wraca na poprzednią pozycję
                dragged = null;         
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                if (inDrag) {
                    dragged.x = me.getX() - dx;
                    dragged.y = me.getY() - dy;
                    repaint();
                }
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARDSIZE, BOARDSIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Font f = new Font("serif", Font.BOLD, FIELDSIZE / 2);
        g.setFont(f);
        paintCheckersBoard(g);
        for (int i = 0; i < game.n; i++) {
            if (game.checkers[i] != dragged) {
                game.checkers[i].draw(g);
            }
        }
        if (inDrag) {
            dragged.draw(g);
        }
        g.setColor(Color.ORANGE);
        String str;
        if (game.inGame) {
            str = whoseTurn + "'s turn";
            
        } else {
            str = game.winner + " wins!!!";
        }                                                              
        double l = (double) str.length() / 9;    //umieszczenie napisu na środku XDD
        g.drawString(str, BOARDSIZE / 2 - (int) (l * FIELDSIZE), FIELDSIZE / 2);
    }

    private void paintCheckersBoard(Graphics g) {
        for (int i = 0; i < 8; i++) {
            g.setColor(((i % 2) != 0) ? Color.DARK_GRAY : Color.LIGHT_GRAY);
            for (int j = 0; j < 8; j++) {
                g.fillRect(j * FIELDSIZE, i * FIELDSIZE, FIELDSIZE, FIELDSIZE);
                g.setColor((g.getColor() == Color.LIGHT_GRAY) ? Color.DARK_GRAY : Color.LIGHT_GRAY);
            }
        }
        if (inDrag) {               //jeżeli pion jest unoszony to wskaż pola, na które można go postawić
            if ((game.isAnyCapturePossible(dragged.getCheckertype())) == false) {    //jeżeli drużyna może wykonać bicia
                for (int i = 1; i < 9; i++) {                                   //to nie możę wykonać normalnego ruchu
                    for (int j = 1; j < 9; j++) {
                        if (game.isMovePossible(dragged, i, j)) {                    //bicia nie ma, więc wskaż
                            g.setColor(Color.MAGENTA);                          //pola, na które można wykonać zwykły ruch
                            g.fillRect((j - 1) * FIELDSIZE, (i - 1) * FIELDSIZE, FIELDSIZE, FIELDSIZE);
                            g.setColor(Color.BLACK);
                            g.drawRect((j - 1) * FIELDSIZE, (i - 1) * FIELDSIZE, FIELDSIZE, FIELDSIZE);
                        }
                    }
                }
            } else {
                printCaptures(dragged, g);          //wskaż bicia, któe można wykonać przez piona dragged
            }
        }
    }
    
    private void printCaptures(Checker c, Graphics g) {     //zamaluj pola na których można wykonać bicie przez piona c
        if (prevCapturing == null || prevCapturing == dragged) {
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    if (game.isCapturePossible(c, i, j) != null) {
                        g.setColor(Color.MAGENTA);
                        g.fillRect((j - 1) * FIELDSIZE, (i - 1) * FIELDSIZE, FIELDSIZE, FIELDSIZE);
                        g.setColor(Color.BLACK);
                        g.drawRect((j - 1) * FIELDSIZE, (i - 1) * FIELDSIZE, FIELDSIZE, FIELDSIZE);
                    }
                }
            }
        }
    }
    
    /*public static void playSound(final String name) {
        try {
            if (clip != null) {
                clip.stop();
            }
            clip = null;
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    new File(name));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }*/

    public static int whichRow(int y) {
        return ((int) (1 + y + FIELDSIZE / 2) / FIELDSIZE);  //1 po to, aby uniknąć umiejscowienia piona
    }                                          //w złym miejscu w przypadku
                                              //nieparzystego wymiaru FieldSize
    public static int whichColumn(int x) {
        return ((int) (1 + x + FIELDSIZE / 2) / FIELDSIZE);
    }

    public void resize(int size) {
        Board.FIELDSIZE = size;
        Board.BOARDSIZE = 8 * Board.FIELDSIZE;
        Checker.FIELDSIZE = Board.FIELDSIZE;
        Checker.CHECKERSIZE = (int) (0.8 * Board.FIELDSIZE);
        for (int i = 0; i < game.n; i++) {
            game.checkers[i].adjust();
        }
    }
}


