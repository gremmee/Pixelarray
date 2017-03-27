package nl.gremmee.pixelarray;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
    LinkedList<Pixel> object = new LinkedList<>();

    public void addObject(Pixel aObject) {
        this.object.add(aObject);
    }

    public Pixel getGameObject(Pixel aObject) {
        int index = this.object.indexOf(aObject);
        if (index != -1) {
            return this.object.get(index);
        }
        return null;
    }

    public int getStars() {
        int result = 0;
        for (Pixel fireObject : object) {
            if (fireObject instanceof Pixel) {
                result++;
            }
        }
        return result;
    }

    public void removeObject(Pixel aObject) {
        this.object.remove(aObject);
    }

    public void render(Graphics aGraphics) {
        for (int i = 0; i < object.size(); i++) {
            Pixel tempObject = object.get(i);
            tempObject.render(aGraphics);
        }
    }

    public void update() {
        for (int i = 0; i < object.size(); i++) {
            Pixel tempObject = object.get(i);
            tempObject.update();
        }
    }
}
