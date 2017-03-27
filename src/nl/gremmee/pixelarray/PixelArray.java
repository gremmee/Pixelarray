package nl.gremmee.pixelarray;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class PixelArray extends Canvas implements Runnable {
    public static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static final int NUM_STARS = WIDTH;

    public static int speed = 1;
    public BufferedImage bi;

    public int x = 0;
    public int y = 0;

    private boolean running = false;
    private Handler handler;
    private int frames = 0;

    private Thread thread;

    public PixelArray() {
        handler = new Handler();
        bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        new Window(WIDTH, HEIGHT, "PixelArray", this);
    }

    public static void main(String[] aArgs) {
        new PixelArray();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if ((System.currentTimeMillis() - timer) > 1000) {
                timer += 1000;
                int stars = handler.getStars();
                System.out.println("W x H : " + WIDTH + " x " + HEIGHT + " FPS: " + frames + " : Pixels " + stars);
                frames = 0;
            }
        }
        stop();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(new Color(0, 0, 0, 15));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                float cr = Utils.map(x, 0, WIDTH, 0, 1);
                float cg = Utils.map(Window.getMouseX() + Window.getMouseY(), 0, WIDTH + HEIGHT, 0, 1);
                float cb = Utils.map(y, 0, WIDTH, 0, 1);
                float ca = Utils.map(Window.getMouseY(), 0, HEIGHT, 0, 1);
                bi.setRGB(x, y, new Color(cr, cg, cb).getRGB());
            }
        }
        if (bi != null) {
            g.drawImage(bi, 0, 0, null);
        }

        g.dispose();
        bs.show();
    }

    private void update() {
        handler.update();
    }
}
