package com.company;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

//        RTree tree = new RTree();
        FileParser data = new FileParser("ukraine_poi.csv");
        RTree tree = data.createTheTree();
        tree.restructure();
        tree.printTree();

//        System.out.println(tree.root.findLocation(new Location(51.23445, 31.23326)));
//        System.out.println("Enter the data in the following format: latitude, longitude, radius, type/subtype");
//        System.out.println("For example: 49.37649, 40.14664, 500, shop");
//        Scanner input = new Scanner(System.in);
//
//        String[] params = input.nextLine().split(", ");
//
//        double latitude = Double.parseDouble(params[0]);
//        double longitude = Double.parseDouble(params[1]);
//        double radius = Double.parseDouble(params[2]);
//        String type = params[3];
//
//        Location location = new Location(latitude, longitude);
//        ArrayList<Location> closestLocations = searchByParameters(location, radius, tree);
////
//        System.out.println("Location in this radius by your params: ");
//
//
//        for (Location currentLocation : closestLocations) {
//            if (currentLocation.type.equals(type) || currentLocation.subType.equals(type))
//                System.out.println(currentLocation.getBounds());
//        }
//
////
////        System.out.println(new RectangleArea(new Location(49.39262000000001, 31.28775),new Location(51.49777, 31.28775),new Location(49.39262000000001, 32.7378075), new Location(51.49777, 32.7378075))
////                .getDistanceBetweenRectAndLocation(new Location(51.23445, 31.23326)));

//        51.23445, 31.23326, 1, shop
        //49.06183, 22.68685, 1, tourism
    }

    //
    private static ArrayList<Location> searchByParameters(Location location, double radius, RTree tree) {
        ArrayList<Location> locations = tree.getClosetsLocations(location, radius);
        return locations;
    }

}
//1.9446002281554866

//        Location l1 = new Location(0, 5);
//        Location l2 = new Location(0, 0);
//        Location l3 = new Location(5, 5);
//        Location l4 = new Location(5, 0);
//        Location l5 = new Location(3, 0);
//        Location l6 = new Location(1, 1);
//        Location l7 = new Location(1, 2);
//        Location l8 = new Location(2, 2);
//        Location l9 = new Location(0, 2);
//        Location l10 = new Location(0, 1);
//        tree.insertLocation(l1);
//        tree.insertLocation(l2);
//        tree.insertLocation(l3);
//        tree.insertLocation(l4);
//        tree.insertLocation(l5);
//        tree.insertLocation(l6);
//        tree.insertLocation(l7);
//        tree.insertLocation(l8);
//        tree.insertLocation(l9);
//        tree.insertLocation(l10);
//          tree.printTree();

//        Location current = new Location(0,7);
//        double radius = 6;
//        ArrayList<Location> list = tree.getClosetsLocations(current,radius);
//        for(int i=0;i<list.size();i++){
//            System.out.println(list.get(i).getBounds());
//        }
////        System.out.println(l1.sphericalDistance(l2));