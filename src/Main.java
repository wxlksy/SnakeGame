import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Main extends JPanel implements ActionListener
{

    private final int SCREEN_WIDTH = 600;
    private final int SCREEN_HEIGHT = 600;
    private final int UNIT_SIZE = 25;
    private final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private final int DELAY = 75;
    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];
    private int b = 6;
    private int app;
    private int appX;
    private int appY;
    private char dir = 'R';
    private boolean run = false;
    private Timer time;
    private Random rand;

    public Main()
    {
        rand = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        start();
    }

    public void start() {
        newApp();
        run = true;
        time = new Timer(DELAY, this);
        time.start();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g)
    {
        if (run)
        {
            g.setColor(Color.red);
            g.fillOval(appX, appY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < b; i++)
            {
                if (i == 0)
                {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }

                else
                {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + app, (SCREEN_WIDTH - metrics.stringWidth("Score: " + app)) / 2, g.getFont().getSize());
        }

        else
        {
            over(g);
        }
    }

    public void newApp()
    {
        appX = rand.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appY = rand.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move()
    {
        for (int i = b; i > 0; i--)
        {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (dir)
        {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApp()
    {
        if ((x[0] == appX) && (y[0] == appY))
        {
            b++;
            app++;
            newApp();
        }
    }

    public void checkCol()
    {
        for (int i = b; i > 0; i--)
        {
            if ((x[0] == x[i]) && (y[0] == y[i]))
            {
                run = false;
            }
        }

        if (x[0] < 0)
        {
            run = false;
        }

        if (x[0] >= SCREEN_WIDTH)
        {
            run = false;
        }

        if (y[0] < 0)
        {
            run = false;
        }

        if (y[0] >= SCREEN_HEIGHT)
        {
            run = false;
        }

        if (!run)
        {
            time.stop();
        }
    }
    public void over(Graphics g)
    {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game over", (SCREEN_WIDTH - metrics.stringWidth("Game over")) / 2, SCREEN_HEIGHT / 2);

        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        g.drawString("Point: " + app, (SCREEN_WIDTH - metrics.stringWidth("Point: " + app)) / 2, g.getFont().getSize() * 2);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (run)
        {
            move();
            checkApp();
            checkCol();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:

                    if (dir != 'R')
                    {
                        dir = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:

                    if (dir != 'L')
                    {
                        dir = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:

                    if (dir != 'D')
                    {
                        dir = 'U';
                    }
                    break;

                case KeyEvent.VK_DOWN:

                    if (dir != 'U')
                    {
                        dir = 'D';
                    }
                    break;
            }
        }
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        Main gamePanel = new Main();
        frame.add(gamePanel);
        frame.setTitle("Змейка");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}