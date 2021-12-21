//package main.entities.creatures;
//
//import edu.princeton.cs.algs4.BreadthFirstPaths;
//import edu.princeton.cs.algs4.Graph;
//import main.Handler;
//import main.worlds.World;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class FindPath {
//
//    private final char[][] grid;
//    private final int N;
//    private final int M;
//    private Graph G;
//    private int playerX, playerY, botX, botY;
//    private Handler handler;
//    private Balloon balloon;
//    private Player player;
//
//    public FindPath(Handler handler, Player player, Balloon balloon) {
//        grid = handler.getWorld().getTiles();
//        N = grid.length;
//        M = grid[0].length;
//
//        this.player = player;
//        this.balloon = balloon;
//    }
//
//    private int index(int i, int j) {
//        return i * M + j;
//    }
//
//    public void tick() {
//
//        playerX = (int) (player.getLeftX() / 36);
//        playerY = (int) (player.getUpY() / 36);
//
//        botX = (int) (balloon.getCurrentTopLeftX() / 36);
//        botY = (int) (balloon.getCurrentTopLeftY() / 36);
//
//        G = new Graph(N * M);
//
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < M; j++) {
//                if (grid[i][j] != ' ') continue;
//                if (i > 0 && grid[i-1][j] == ' ') G.addEdge(index(i, j), index(i-1, j));
//                if (j > 0 && grid[i][j-1] == ' ') G.addEdge(index(i, j), index(i, j-1));
//                if (i < N-1 && grid[i+1][j] == ' ') G.addEdge(index(i, j), index(i+1, j));
//                if (j < M-1 && grid[i][j+1] == ' ') G.addEdge(index(i, j), index(i, j+1));
//                if (i > 0 && j > 0 && grid[i-1][j-1] == ' ') G.addEdge(index(i, j), index(i-1, j-1));
//                if (i > 0 && j < M-1 && grid[i-1][j+1] == ' ') G.addEdge(index(i, j), index(i-1, j+1));
//                if (i < N-1 && j > 0 && grid[i+1][j-1] == ' ') G.addEdge(index(i, j), index(i+1, j-1));
//                if (i < N-1 && j < M-1 && grid[i+1][j+1] == ' ') G.addEdge(index(i, j), index(i+1, j+1));
//            }
//        }
//
//        System.out.println(playerX + " " + playerY + " " + botX + " " + botY);
//    }
//
//    private void render(Graphics g) {
//
//    }
//
//    public List<World.Position> paths() {
//        List<World.Position> list = new ArrayList<>();
//        BreadthFirstPaths bf = new BreadthFirstPaths(G, index(botX, botY));
//        for (int w : bf.pathTo(index(playerX, playerY))) {
//            list.add(positionFromIndex(w));
//        }
//        return list;
//    }
//
//    private World.Position positionFromIndex(int index) {
//        int j = index % M;
//        int i = index / M;
//        return new World.Position(i, j);
//    }
//
//
//}
