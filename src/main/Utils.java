package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Utils {

    public static String fileToString(String path) {
        StringBuilder stringBuilder = new StringBuilder();

        Scanner sc = null;
        try {
            sc = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (Objects.requireNonNull(sc).hasNext()) {
            stringBuilder.append(sc.nextLine()).append("\n");
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String file = fileToString(".\\src\\resource\\map\\level1.txt");
        String[] lines = file.split(Pattern.quote("\n"));
        String[] levelHeightWidth = lines[0].split(Pattern.quote(" "));
        int level = Integer.parseInt(levelHeightWidth[0]);
        int height = Integer.parseInt(levelHeightWidth[1]);
        int width = Integer.parseInt(levelHeightWidth[2]);

        System.out.println(level + " " + height + " " + width);

        char[][] tiles = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = lines[i+1].charAt(j);
            }
        }


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(tiles[i][j]);
            }
            System.out.println();
        }
    }

}
