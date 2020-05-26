package com.company;

import static com.company.RectangleArea.MAX_NUMBER;

public class RTree {
    RectangleArea root;

    void printTree(){
        root.printRect();
    }

    void insertLocation(Location location) {
        if (root == null) {
            RectangleArea rect = new RectangleArea(location, location, location, location);
            rect.addLocation(location);
            root = rect;
            return;
        }

        root.addLocation(location);
        if (root.locations.size() >= MAX_NUMBER || root.children.size() >= MAX_NUMBER) {

            RectangleArea[] newRects = root.splitRectangle(root);
            Location[] bounds = root.getBounds();
            root = new RectangleArea(bounds[0], bounds[1], bounds[2], bounds[3]);
            root.children.add(newRects[0]);
            root.children.add(newRects[1]);
        }

    }
}
