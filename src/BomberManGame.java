import java.util.*;

public class BomberManGame {
    static int n;
    static char[][] grid;
    static int[] playerPos = new int[2];
    static int[] keyPos = new int[2];
    static Queue<int[]> bombList = new ArrayDeque<>();
    static Set<String > set = new HashSet<>();
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the size of Matrix : ");
        n = input.nextInt();
        grid = new char[n][n];
        for(int i = 0;i<n;i++){
            grid[i][0] = '*';
            grid[0][i] = '*';
            grid[i][n-1] = '*';
            grid[n-1][i] = '*';
        }

        for(int i = 2;i<n-2;i+=2){
            for(int j = 2;j<n-2;j+=2){
                grid[i][j] = '*';
            }
        }
        level1();
        printGrid();
        move();
        printGrid();
    }

    public static void printGrid(){
        System.out.print("\n  ");
        for(int i = 0;i<n;i++) System.out.print((char)(i+'A')+" ");
        System.out.println();
        for(int i = 0;i<n;i++){
            System.out.print((char)(i+'A')+" ");
            for(int j = 0;j<n;j++){
                System.out.print(grid[i][j] != 0?grid[i][j]+" ":"  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void level1(){
        Scanner input = new Scanner(System.in);
        addValue("Player",1);
        addValue("Key",1);
        System.out.print("Enter Number of Villains : ");
        int number = input.nextInt();
        addValue("Villain",number);
        System.out.print("Enter Number of Bricks : ");
        number = input.nextInt();
        addValue("Bricks",number);
    }
    public static void addValue(String string,int number){
        Scanner input = new Scanner(System.in);
        int row,col;
        int i = 0;
        while (i<number) {
            System.out.print("Enter " + string + " position : ");
            String player = input.next();
            row = player.charAt(0) - 65;
            col = player.charAt(1) - 65;
            if(row <0 || col <0 || row>=n || col >= n || grid[row][col] != 0) {
                System.out.println("Invalid position !!");
                continue;
            }
            if(string.equals("Villain")){
                set.add(row+","+col);
            }
            grid[row][col] = string.charAt(0);
            if (string.equals("Player")) {
                playerPos[0] = row;
                playerPos[1] = col;
            } else if (string.equals("Key")) {
                keyPos[0] = row;
                keyPos[1] = col;
            }
            i++;
        }
    }

    public static void move(){
        Scanner input = new Scanner(System.in);
        int row = playerPos[0];
        int col = playerPos[1];
        while (true) {
            row = playerPos[0];
            col = playerPos[1];
            System.out.print("1.Plant bomb \n2.detonates\n3.CONTINUE\n CHOICE : ");
            int choice = input.nextInt();
            if(choice == 1) {
                if(bombList.size()!= 0){
                    System.out.println("You must detonates the bomb");
                    System.out.print("Are you want to blast\n1.yes\n2.no");
                    choice = input.nextInt();
                    if(choice ==1) blast();;
                }else {
                    grid[row][col] = 'X';
                    bombList.add(new int[]{row, col});
                }
            }
            else if(choice == 2){
                blast();
            }
            System.out.print("Enter the direction : ");
            char direction = input.next().charAt(0);
            switch (direction) {
                case 'W' -> {
                    if (direction(row - 1, col)) {
                        if(grid[row][col]!='X') grid[row][col] = ' ';
                        grid[row - 1][col] = 'P';
                        playerPos[0] = row - 1;
                    }
                    break;
                }
                case 'S' -> {
                    if (direction(row + 1, col)) {
                        if(grid[row][col]!='X') grid[row][col] = ' ';
                        grid[row + 1][col] = 'P';
                        playerPos[0] = row + 1;
                    }
                    break;
                }
                case 'A' -> {
                    if (direction(row, col - 1)) {
                        if(grid[row][col]!='X') grid[row][col] = ' ';
                        grid[row][col - 1] = 'P';
                        playerPos[1] = col - 1;
                    }
                    break;
                }
                case 'D' -> {
                    if (direction(row, col + 1)) {
                        if(grid[row][col]!='X') grid[row][col] = ' ';
                        grid[row][col + 1] = 'P';
                        playerPos[1] = col + 1;
                    }
                    break;
                }
                case 'Q' -> {
                    if (direction(row-1, col-1) && (direction(row-1,col) || direction(row,col-1))) {
                        if(grid[row][col]!='X') grid[row][col] = ' ';
                        grid[row-1][col-1] = 'P';
                        playerPos[0] = row-1;
                        playerPos[1] = col - 1;
                    }
                    break;
                }

                case 'E' -> {
                    if (direction(row-1, col+1) && (direction(row,col+1) || direction(row-1,col))) {
                        if(grid[row][col]!='X') grid[row][col] = ' ';
                        grid[row-1][col+1] = 'P';
                        playerPos[0] = row-1;
                        playerPos[1] = col+1;
                    }
                    break;
                }
                case 'Z' -> {
                    if (direction(row+1, col-1) && (direction(row,col-1) || direction(row+1,col))) {
                        if(grid[row][col]!='X') grid[row][col] = ' ';
                        grid[row+1][col-1] = 'P';
                        playerPos[0] = row+1;
                        playerPos[1] = col - 1;
                    }
                    break;
                }
                case 'C' -> {
                    if (direction(row+1, col+1) && (direction(row+1,col) || direction(row,col+1))) {
                        if(grid[row][col]!='X') grid[row][col] = ' ';
                        grid[row+1][col+1] = 'P';
                        playerPos[0] = row+1;
                        playerPos[1] = col+1;
                    }
                    break;
                }
            }
            if(set.contains(playerPos[0]+","+playerPos[1])){
                System.out.println("You lose !!!");
                return;
            }
            else if(grid[playerPos[0]][playerPos[1]] == 'K'){
                System.out.println("You Won !!!");
                return;
            }
            printGrid();
        }
    }

    public static boolean direction(int row,int col){
        if(grid[row][col] == '*' || grid[row][col] == 'B'){
            System.out.println("Move is not possible");
            return false;
        }
        return true;
    }

    public static void blast(){
        boolean playerBlast = false;
        if(bombList.size()==0){
            System.out.println("No bomb to detonates");
        }
        for(int[] arr:bombList){
            if(grid[arr[0]-1][arr[1]] == 'P' || grid[arr[0]+1][arr[1]] == 'P' ||
                    grid[arr[0]][arr[1]-1] == 'P' || grid[arr[0]][arr[1]+1] == 'P') playerBlast = true;
            grid[arr[0]][arr[1]] = ' ';
            if(grid[arr[0]-1][arr[1]] != '*' && grid[arr[0]-1][arr[1]] != 'K') grid[arr[0]-1][arr[1]] = ' ';
            if(grid[arr[0]+1][arr[1]] != '*' && grid[arr[0]+1][arr[1]] != 'K') grid[arr[0]+1][arr[1]] = ' ';
            if(grid[arr[0]][arr[1]-1] != '*' && grid[arr[0]][arr[1]-1] != 'K') grid[arr[0]][arr[1]-1] = ' ';
            if(grid[arr[0]][arr[1]+1] != '*' && grid[arr[0]][arr[1]+1] != 'K') grid[arr[0]][arr[1]+1] = ' ';
        }
        if(playerBlast) {
            printGrid();
            System.out.println("Match lost Player Died !!!");
            System.exit(0);
        }
    }
}
