
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Checker {

    public static int FIELDSIZE = Board.FIELDSIZE;
    public static int CHECKERSIZE = (int) (0.8 * Board.FIELDSIZE);
    public int i;           //rząd
    public int j;           //kolumna
    public int x;           //współrzędne x
    public int y;           //współrzędne y
    private final CheckerType checkertype;
    public boolean isKing;  //czy damka?
    
    public CheckerType getCheckertype() {
        return checkertype;
    }
    
    public Checker(int i, int j, CheckerType ctype) {
        this.i = i;
        this.j = j;
        x = (j - 1) * FIELDSIZE + FIELDSIZE / 2;
        y = (i - 1) * FIELDSIZE + FIELDSIZE / 2;
        checkertype = ctype;
    }

    public void draw(Graphics g) {
        if (checkertype == CheckerType.BLACK) {
            g.setColor(Color.BLACK);
        }
        if (checkertype == CheckerType.RED) {
            g.setColor(Color.RED);
        }
        if (checkertype == CheckerType.WHITE) {
            g.setColor(Color.WHITE);
        }

        g.fillOval(x - CHECKERSIZE / 2, y - CHECKERSIZE / 2, CHECKERSIZE, CHECKERSIZE);
        if (isKing == true) {
            try {
                File pathToFile = null;
                if (checkertype == CheckerType.BLACK)
                    pathToFile = new File("black.jpeg");
                else if (checkertype == CheckerType.RED)    
                    pathToFile = new File("red.jpeg");
                else if (checkertype == CheckerType.WHITE)
                    pathToFile = new File ("white.jpg");
                Image image = ImageIO.read(pathToFile);
                g.drawImage(image, x - CHECKERSIZE / 4, y - CHECKERSIZE / 4, CHECKERSIZE / 2, CHECKERSIZE / 2, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    boolean contains(int x, int y) {        //sprawdza czy punkt (x, y) należy do piona
        int distance = (this.x - x) * (this.x - x) + (this.y - y) * (this.y - y);        
        return distance < CHECKERSIZE * CHECKERSIZE / 4;                
    }
    
    public void adjust(){
        x = (j - 1) * FIELDSIZE + FIELDSIZE / 2;
        y = (i - 1) * FIELDSIZE + FIELDSIZE / 2;
    }

}
