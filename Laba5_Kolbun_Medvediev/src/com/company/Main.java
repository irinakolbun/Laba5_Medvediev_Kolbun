package com.company;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        RTree tree = new RTree();
        FileParser data = new FileParser("ukraine_poi.csv");
        tree = data.createTheTree();

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
 }
}
//1.9446002281554866