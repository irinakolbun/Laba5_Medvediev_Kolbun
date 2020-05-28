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

    double getDistanceBetweenRectAndLocation(Location location) {
        if (this.isInside(location)) {
//            System.out.println("IS INSIDE");
            return 0;
        }
        double left = leftUpper.getLatidude(),
                right = rightUpper.getLatidude(),
                up = leftUpper.getLongtitude(),
                low = leftLower.getLongtitude();

//        System.out.println(left + " " + right + " " + up + " " + low);

        if (location.getLatidude() > left && location.getLatidude() < right) {
            Location location1 = new Location(location.getLatidude(), (location.getLongtitude() > low) ? (low) : (up));
            return location.sphericalDistance(location1);
        } else if (location.getLongtitude() < low && location.getLongtitude() > up) {
            Location location1 = new Location((location.getLatidude() < left) ? left : right, location.getLongtitude());
            return location.sphericalDistance(location1);
        }

        if(location.getLatidude()>right) {
//            System.out.println(((location.getLongtitude() < up) ? rightUpper : rightLower).getBounds());
            return location.sphericalDistance((location.getLongtitude() < up) ? rightUpper : rightLower);
        }
        else
            return location.sphericalDistance((location.getLongtitude()<up)?leftUpper:leftLower);
    }

    Location findLocation(Location location){
        if(this.isInside(location)) {
//            System.out.println(this.leftUpper.getBounds());
//            System.out.println(this.rightLower.getBounds());
//            System.out.println();
            if (this.children.size() != 0) {
                for(RectangleArea rect: this.children){
                    if(rect.isInside(location))
                        return rect.findLocation(location);
                }
            }
            if(locations.size()!=0) {
                for(Location current: locations){
                    if(location==current)
                        return current;
                }
            }
        }
        return null;
    }


    ArrayList <Location> getClosestLocations(Location location, double radius, ArrayList<Location> locations){

        if(this.children.size()!=0){
            for(int i=0;i<children.size();i++){
                RectangleArea child = this.children.get(i);
                if(child.getDistanceBetweenRectAndLocation(location) <= radius ) {
//                    System.out.println(child.leftUpper.getBounds() + child.rightLower.getBounds());
//                    System.out.println(child.getDistanceBetweenRectAndLocation(location));
//                    System.out.println();
                    ArrayList<Location> input = child.getClosestLocations(location,radius,new ArrayList<>());
                    //System.out.println();
                    locations.addAll(input);
                }
            }
        }
        else {
//            System.out.println("KU");
//            System.out.println(this.children.size());
            for(int i=0;i<this.locations.size();i++){
                Location currentLocation = this.locations.get(i);
//                System.out.println( currentLocation.getBounds()+ " " + currentLocation.sphericalDistance(location));
                if(currentLocation.sphericalDistance(location)<=radius)
                    locations.add(currentLocation);
            }

        }


        return locations;
    }


    void printRect() {
        System.out.println("LeftUpper: " + leftUpper.getBounds() + ", rightUpper: " + rightUpper.getBounds() + ", leftLower: " + leftLower.getBounds() + ", rightLower: " + rightLower.getBounds());
//
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


    void addChildren(RectangleArea rect) {
        this.children.add(rect);
    }
    private double[] getArrayOfLimits(RectangleArea rect, Location location) {
        double left = Math.min(location.getLatidude(), rect.leftUpper.getLatidude());
        double right = Math.max(location.getLatidude(), rect.rightUpper.getLatidude());
        double up = Math.min(location.getLongtitude(), rect.leftUpper.getLongtitude());
        double low = Math.max(location.getLongtitude(), rect.leftLower.getLongtitude());

        return new double[]{left, right, up, low};
    }


    void addLocation(Location location) {
        if(!this.isInside(location)){
            double[] newLimits = getArrayOfLimits(this, location);
            double left = newLimits[0],
                    right = newLimits[1],
                    up = newLimits[2],
                    low = newLimits[3];
            this.leftUpper = new Location(left, up);
            this.leftLower = new Location(left, low);
            this.rightUpper = new Location(right, up);
            this.rightLower = new Location(right, low);
        }
        this.locations.add(location);
    }




    boolean isInside(Location location) {

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
                first.locations.add(location);
            } else {
                second.locations.add(location);
            }
        }

        rect.locations = new ArrayList<>();

    }

    void restructure() {
        if (this.locations.size() >= MAX_NUMBER) {
//            System.out.println(this.leftUpper.getBounds() + " " + this.rightLower.getBounds());
            RectangleArea[] rects = this.splitRectangle(this);
            this.children.add(rects[0]);
            this.children.add(rects[1]);
            this.children.get(0).restructure();
            this.children.get(1).restructure();
        }
    }

    RectangleArea[] splitRectangle(RectangleArea rect) {
        double width = rect.leftUpper.sphericalDistance(rect.rightUpper);
        double height = rect.leftUpper.sphericalDistance(rect.leftLower);

            if (width >= height) {
                Location middleUpper = new Location((rect.leftUpper.getLatidude() + rect.rightUpper.getLatidude()) / 2, rect.leftUpper.getLongtitude());
                Location middleLower = new Location((rect.leftUpper.getLatidude() + rect.rightUpper.getLatidude()) / 2, rect.leftLower.getLongtitude());
                RectangleArea left = new RectangleArea(rect.leftUpper, middleUpper, rect.leftLower, middleLower);
                RectangleArea right = new RectangleArea(middleUpper, rect.rightUpper, middleLower, rect.rightLower);
//                System.out.println(middleLower.getBounds());
                rectructureRects(rect, left, right);

//                System.out.println(left.locations.size());
//                System.out.println(right.locations.size());

                return new RectangleArea[]{left, right};
//            rect.children.add(left);
////            rect.children.add(right);
//
            } else {
                Location middleLeft = new Location(rect.leftUpper.getLatidude(), (rect.leftUpper.getLongtitude() + rect.leftLower.getLongtitude()) / 2);
                Location middleRight = new Location(rect.rightUpper.getLatidude(), (rect.rightUpper.getLongtitude() + rect.rightLower.getLongtitude()) / 2);
                RectangleArea up = new RectangleArea(rect.leftUpper, rect.rightUpper, middleLeft, middleRight);
                RectangleArea low = new RectangleArea(middleLeft, middleRight, rect.leftLower, rect.rightLower);

//                rect.printRect();
                rectructureRects(rect, up, low);


                return new RectangleArea[]{up, low};
            }
        }


}
