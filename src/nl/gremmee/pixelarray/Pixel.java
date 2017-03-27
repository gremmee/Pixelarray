package nl.gremmee.pixelarray;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Pixel {
    private ID id;
    private int color;
    private int w;

    private int x;
    private int y;

    private Random random = new Random();

    public Pixel(int aW, int aX, int aY, ID aId) {
        x = aX;
        y = aY;
        w = aW;
    }

    public void doRender(Graphics g) {
        float h = Utils.map((w % 256), 0, 255, 0, 1);
        float s = Utils.map(255, 0, 255, 0, 1);
        float b = Utils.map(255, 0, 255, 0, 1);
        g.setColor(Color.getHSBColor(h, s, b));
        g.drawLine(this.x, this.y, this.x, this.y);
    }

    public void doUpdate() {
    }

    public ID getID() {
        return this.id;
    }

    public Random getRandom() {
        return this.random;
    }

    public void render(Graphics aGraphics) {
        doRender(aGraphics);
    }

    public void setId(ID aId) {
        this.id = aId;
    }

    public void update() {
        doUpdate();
    }
}
