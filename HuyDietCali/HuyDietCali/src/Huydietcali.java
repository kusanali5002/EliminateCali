import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Huydietcali extends JPanel {
    private BufferedImage image;
    private boolean[][] revealed;
    private static final int REVEAL_RADIUS = 30;

    public Huydietcali() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("2_9.jpg"));
            revealed = new boolean[image.getWidth()][image.getHeight()];
        } catch (IOException e) {
            e.printStackTrace();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                revealArea(e.getX(), e.getY());
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    if (revealed[x][y]) {
                        int pixelColor = image.getRGB(x, y);
                        g.setColor(new Color(pixelColor, true));
                        g.fillRect(x, y, 1, 1);
                    } else {
                        g.setColor(Color.WHITE);
                        g.fillRect(x, y, 1, 1);
                    }
                }
            }
        }
    }

    private void revealArea(int centerX, int centerY) {
        for (int y = Math.max(0, centerY - REVEAL_RADIUS); y < Math.min(image.getHeight(), centerY + REVEAL_RADIUS); y++) {
            for (int x = Math.max(0, centerX - REVEAL_RADIUS); x < Math.min(image.getWidth(), centerX + REVEAL_RADIUS); x++) {
                double distance = Math.hypot(x - centerX, y - centerY);
                if (distance <= REVEAL_RADIUS) {
                    revealed[x][y] = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Drawer");
        Huydietcali huydietcali = new Huydietcali();
        frame.add(huydietcali);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}