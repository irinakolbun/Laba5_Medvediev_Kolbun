package com.company;

import java.util.ArrayList;

public class RectangleArea {
    private Location leftUpper, rightUpper, leftLower, rightLower;
    ArrayList<Location> locations;

    ArrayList<RectangleArea> children;

    final static int MAX_NUMBER = 5;

    RectangleArea(Location leftUpper, Location rightUpper, Location leftLower, Location rightLower) {
        this.leftUpper = leftUpper;
        this.leftLower = leftLower;
        this.rightLower = rightLower;
        this.rightUpper = rightUpper;
        locations = new ArrayList<>();
        children = new ArrayList<>();
    }

    void printRect() {
        System.out.println("LeftUpper: " + leftUpper.getBounds() + ", rightUpper: " + rightUpper.getBounds() + ", leftLower: " + leftLower.getBounds() + ", rightLower: " + rightLower.getBounds());

        System.out.println("{");
//        System.out.println(children.size());
        for (RectangleArea child : children)
            child.printRect();

        for (Location location : locations)
            System.out.println("Location: " + location.getBounds());

        System.out.println("}");

    }

    Location[] getBounds() {
        return new Location[]{leftUpper, rightUpper, leftLower, rightLower};
    }

    double getSquare() {
        double width = leftUpper.sphericalDistance(rightUpper);
        double height = leftUpper.sphericalDistance(leftLower);
        return width * height;
    }

    private double[] getArrayOfLimits(RectangleArea rect, Location location) {
        double left = Math.min(location.getLatidude(), rect.leftUpper.getLatidude());
        double right = Math.max(location.getLatidude(), rect.rightUpper.getLatidude());
        double up = Math.min(location.getLongtitude(), rect.leftUpper.getLongtitude());
        double low = Math.max(location.getLongtitude(), rect.leftLower.getLongtitude());

        return new double[]{left, right, up, low};
    }

    private RectangleArea getExtendedArea(RectangleArea rect, Location location) {
        double[] newLimits = getArrayOfLimits(rect, location);
        double left = newLimits[0],
                right = newLimits[1],
                up = newLimits[2],
                low = newLimits[3];

        return new RectangleArea(new Location(left, up), new Location(right, up), new Location(left, low), new Location(right, low));
    }

    void addChildren(RectangleArea rect) {
        this.children.add(rect);
    }


    void addLocation(Location location) {
        if (!this.isInside(location)) {
            double[] newLimits = getArrayOfLimits(this, location);
            double left = newLimits[0],
                    right = newLimits[1],
                    up = newLimits[2],
                    low = newLimits[3];
            this.leftUpper = new Location(left, up);
            this.leftLower = new Location(left, low);
            this.rightUpper = new Location(right, up);
            this.rightLower = new Location(right, low);
//            System.out.println(left + " " + right + " " + up + " " + low);
//            this.addLocation(location);
//            return;
        }

        if (this.children.size() != 0) {
            for (int i = 0; i < this.children.size(); i++) {
                RectangleArea child = this.children.get(i);
                if (child.isInside(location)) {
                    child.addLocation(location);
                    if (child.locations.size() >= MAX_NUMBER || child.children.size() >= MAX_NUMBER) {
                        RectangleArea[] newRects = splitRectangle(child);

                        this.children.remove(i);
                        this.children.add(newRects[0]);
                        this.children.add(newRects[1]);
                    }
                    return;
                }
            }

            double minDeltaSquare = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < children.size(); i++) {
                RectangleArea rect = children.get(i);
                double startSquare = rect.getSquare();

                RectangleArea newRect = getExtendedArea(rect, location);
                double newSquare = newRect.getSquare();
                double delta = newSquare - startSquare;
                if (delta < minDeltaSquare) {
                    minIndex = i;
                    minDeltaSquare = delta;
                }
            }

            if (minIndex != -1) {
                RectangleArea rectToChange = children.remove(minIndex);
                RectangleArea changedRect = getExtendedArea(rectToChange, location);
                changedRect.locations = rectToChange.locations;
                changedRect.children = rectToChange.children;
                children.add(minIndex, changedRect);
            }

        } else {
            locations.add(location);
        }


    }

    boolean isInside(Location location) {
//        System.out.println(location.getLatidude() >= leftUpper.getLatidude());
//        System.out.println(location.getLatidude() <= rightUpper.getLatidude());
//        System.out.println(location.getLongtitude() >= leftUpper.getLongtitude());
//        System.out.println(location.getLongtitude() <= leftLower.getLongtitude());
//        System.out.println(location.getLongtitude());

        return (location.getLatidude() >= leftUpper.getLatidude() && location.getLatidude() <= rightUpper.getLatidude() &&
                location.getLongtitude() >= leftUpper.getLongtitude() && location.getLongtitude() <= leftLower.getLongtitude());
    }

    boolean isInside(RectangleArea area) {
        Location middle = new Location((area.leftUpper.getLatidude() + area.rightLower.getLatidude()) / 2, (area.leftUpper.getLongtitude() + area.rightLower.getLongtitude()) / 2);
        return isInside(middle);
    }

    private void rectructureRects(RectangleArea rect, RectangleArea first, RectangleArea second) {

        for (int i = 0; i < rect.locations.size(); i++) {
            Location location = rect.locations.get(i);

            if (first.isInside(location)) {
                first.addLocation(location);
            }
            else {
                second.addLocation(location);
            }
        }
        rect.locations = new ArrayList<>();

        for (int i = 0; i < rect.children.size(); i++) {
            RectangleArea child = rect.children.remove(0);
            if (first.isInside(child))
                first.addChildren(child);
            else
                second.addChildren(child);
        }

    }

    RectangleArea[] splitRectangle(RectangleArea rect) {
        double width = rect.leftUpper.sphericalDistance(rect.rightUpper);
        double height = rect.leftUpper.sphericalDistance(rect.leftLower);
//        System.out.println("KUKU LeftUpper: " + rect.leftUpper.getBounds() + ", rightUpper: " + rect.rightUpper.getBounds() + ", leftLower: " + rect.leftLower.getBounds() + ", rightLower: " + rect.rightLower.getBounds());
//        System.out.println(width + " " + height);
        if (width >= height) {
            Location middleUpper = new Location((rect.leftUpper.getLatidude() + rect.rightUpper.getLatidude()) / 2, rect.leftUpper.getLongtitude());
            Location middleLower = new Location((rect.leftUpper.getLatidude() + rect.rightUpper.getLatidude()) / 2, rect.leftLower.getLongtitude());
            RectangleArea left = new RectangleArea(rect.leftUpper, middleUpper, rect.leftLower, middleLower);
            RectangleArea right = new RectangleArea(middleUpper, rect.rightUpper, middleLower, rect.rightLower);

            rectructureRects(rect, left, right);
            return new RectangleArea[]{left, right};
//            rect.children.add(left);
////            rect.children.add(right);
//
        } else {
            Location middleLeft = new Location(rect.leftUpper.getLatidude(), (rect.leftUpper.getLongtitude() + rect.leftLower.getLongtitude()) / 2);
            Location middleRight = new Location(rect.rightUpper.getLatidude(), (rect.rightUpper.getLongtitude() + rect.rightLower.getLongtitude()) / 2);
            RectangleArea up = new RectangleArea(rect.leftUpper, rect.rightUpper, middleLeft, middleRight);
            RectangleArea low = new RectangleArea(middleLeft, middleRight, rect.leftLower, rect.rightLower);

            rectructureRects(rect, up, low);
            return new RectangleArea[]{up, low};
        }
    }


}
