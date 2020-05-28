package com.company;
// 4 утра))))))
// потом сделаю. я не поняла как парсить (в плане формата, тип сразу в дерево
// совать или надо сделать массивы сначала, а потом уже строить R-tree). need your advice

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileParser {
    File file;
    FileParser(String path){
        file = new File(path);
    }

    public Location parseLocation(String strFromFile){
        String[] data = strFromFile.split(";");
        if(data.length <= 2){
            Location defaultLocation = new Location(0, 0,"","","","");
            return defaultLocation;
        }

        double latitude = Double.parseDouble(data[0].replace(",", "."));
        double longitude = Double.parseDouble(data[1].replace(",", "."));
        String type = data[2];
        //city.getName() == null ? city.getName() : "N/A"
        String subType = data.length>=4 ? data[3] : "";
        String name = data.length>=5 ? data[4] : "";
        String address = data.length>=6 ? data[5] : "";
        Location location = new Location(latitude, longitude, type, subType, name, address);

        return location;
    }

    public RTree createTheTree() throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        RTree tree = new RTree();
        while (sc.hasNextLine()){
            Location location = parseLocation(sc.nextLine());
            tree.insertLocation(location);
//            System.out.println(location);
        }
        return tree;
    }

}