package org.scrum.psd.battleship.ascii;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Collection;

public class Main {
    private static List<Ship> myFleet;
    private static List<Ship> enemyFleet;
    private static ColoredPrinter console;
    private static String[][] chess = new String[8][8];
    private static String[] isHitChess = new String[64];
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        console = new ColoredPrinter.Builder(1, false).build();

        for (int r=0; r<chess.length; r++) {
            for (int c=0; c<chess[r].length; c++) {
                chess[r][c]="-";//your value
            }
        }

        console.setForegroundColor(Ansi.FColor.MAGENTA);
        console.println("                                     |__");
        console.println("                                     |\\/");
        console.println("                                     ---");
        console.println("                                     / | [");
        console.println("                              !      | |||");
        console.println("                            _/|     _/|-++'");
        console.println("                        +  +--|    |--|--|_ |-");
        console.println("                     { /|__|  |/\\__|  |--- |||__/");
        console.println("                    +---------------___[}-_===_.'____                 /\\");
        console.println("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _");
        console.println(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7");
        console.println("|                        Welcome to Battleship                         BB-61/");
        console.println(" \\_________________________________________________________________________|");
        console.println("");
        console.clear();

        InitializeGame();

        StartGame();
    }

    private static void StartGame() {

        Scanner scanner = new Scanner(System.in);


        console.print("\033[2J\033[;H");
        console.println("                  __");
        console.println("                 /  \\");
        console.println("           .-.  |    |");
        console.println("   *    _.-'  \\  \\__/");
        console.println("    \\.-'       \\");
        console.println("   /          _/");
        console.println("  |      _  /\" \"");
        console.println("  |     /_\'");
        console.println("   \\    \\_/");
        console.println("    \" \"\" \"\" \"\" \"");

        do {
            console.println("");
            console.println("Player, it's your turn");
            console.println("Enter coordinates for your shot :");
            String positionInputValue = scanner.next();
            Position position = parsePosition(positionInputValue);
            boolean isHit = GameController.checkIsHit(enemyFleet, position);
            if (isHit) {
                beep();

                console.println("                \\         .  ./",  Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLACK);
                console.println("              \\      .:\" \";'.:..\" \"   /",  Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLACK);
                console.println("                  (M^^.^~~:.'\" \").",  Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLACK);
                console.println("            -   (/  .    . . \\ \\)  -",  Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLACK);
                console.println("               ((| :. ~ ^  :. .|))",  Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLACK);
                console.println("            -   (\\- |  \\ /  |  /)  -",  Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLACK);
                console.println("                 -\\  \\     /  /-",  Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLACK);
                console.println("                   \\  \\   /  /",  Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLACK);
            }


            if (isHit) {
                for(int i=0; i<isHitChess.length; i++){
                    if(positionInputValue.equals(isHitChess[i])){
                        console.println("You hit before",  Ansi.Attribute.BOLD, Ansi.FColor.YELLOW, Ansi.BColor.RED);
                    }
                    else{
                        console.println("Yeah ! Nice hit !",  Ansi.Attribute.BOLD, Ansi.FColor.YELLOW, Ansi.BColor.RED);
                        isHitChess[i+1] = positionInputValue;
                        break;
                    }
                }

            } else {
                console.println("Miss :(",  Ansi.Attribute.BOLD, Ansi.FColor.BLUE, Ansi.BColor.BLACK);
            }

            position = getRandomPosition();
            isHit = GameController.checkIsHit(myFleet, position);

            console.println("");
            console.println(String.format("Computer shoot in %s%s and %s", position.getColumn(), position.getRow(), isHit ? "hit your ship !" : "miss"));

            if (isHit) {
                beep();

                console.println("                \\         .  ./");
                console.println("              \\      .:\" \";'.:..\" \"   /");
                console.println("                  (M^^.^~~:.'\" \").");
                console.println("            -   (/  .    . . \\ \\)  -");
                console.println("               ((| :. ~ ^  :. .|))");
                console.println("            -   (\\- |  \\ /  |  /)  -");
                console.println("                 -\\  \\     /  /-");
                console.println("                   \\  \\   /  /");

            }
        } while (true);
    }

    private static void beep() {
        console.print("\007");
    }


    protected static String validate(String input) {
        try {
            Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
            int number = Integer.parseInt(input.substring(1));

            if(number > 8) {
                throw new Exception();
            }

            Position inputPosition = parsePosition(input);

            if(checkIsAssigned(myFleet, inputPosition)) {
                console.println("You have assigned a ship in this position. ",  Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLUE);
                throw new Exception();
            }

        } catch (Exception e) {
            console.println("Invalid input. Please try again",  Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLUE);
            Scanner scanner = new Scanner(System.in);
            input = validate(scanner.next());
        }
        return input;
    }

    public static boolean checkIsAssigned(Collection<Ship> ships, Position input) {
        for (Ship ship : ships) {
            for (Position position : ship.getPositions()) {
                if (position.equals(input)) {
                    return true;
                }
            }
        }

        return false;
    }

    protected static Position parsePosition(String input) {
        Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1));
        return new Position(letter, number);
    }

    private static Position getRandomPosition() {
        int rows = 8;
        int lines = 8;
        Random random = new Random();
        Letter letter = Letter.values()[random.nextInt(lines)];
        int number = random.nextInt(rows);
        Position position = new Position(letter, number);
        return position;
    }

    private static void InitializeGame() {
        InitializeMyFleet();

        InitializeEnemyFleet();
    }

    private static void InitializeMyFleet() {
//        myFleet = GameController.initializeShips();
//
//        myFleet.get(0).getPositions().add(new Position(Letter.B, 4));
//        myFleet.get(0).getPositions().add(new Position(Letter.B, 5));
//        myFleet.get(0).getPositions().add(new Position(Letter.B, 6));
//        myFleet.get(0).getPositions().add(new Position(Letter.B, 7));
//        myFleet.get(0).getPositions().add(new Position(Letter.B, 8));
//
//        myFleet.get(1).getPositions().add(new Position(Letter.E, 6));
//        myFleet.get(1).getPositions().add(new Position(Letter.E, 7));
//        myFleet.get(1).getPositions().add(new Position(Letter.E, 8));
//        myFleet.get(1).getPositions().add(new Position(Letter.E, 9));
//
//        myFleet.get(2).getPositions().add(new Position(Letter.A, 3));
//        myFleet.get(2).getPositions().add(new Position(Letter.B, 3));
//        myFleet.get(2).getPositions().add(new Position(Letter.C, 3));
//
//        myFleet.get(3).getPositions().add(new Position(Letter.F, 8));
//        myFleet.get(3).getPositions().add(new Position(Letter.G, 8));
//        myFleet.get(3).getPositions().add(new Position(Letter.H, 8));
//
//        myFleet.get(4).getPositions().add(new Position(Letter.C, 5));
//        myFleet.get(4).getPositions().add(new Position(Letter.C, 6));

        Scanner scanner = new Scanner(System.in);
        myFleet = GameController.initializeShips();

        console.println("Please position your fleet (Game board has size from A to H and 1 to 8) :");

        int k = 0;

        for (Ship ship : myFleet) {
            if(k == 0) {
                console = new ColoredPrinter.Builder(1, false).background(Ansi.BColor.BLUE).build();
            } else if(k == 1) {
                console = new ColoredPrinter.Builder(1, false).background(Ansi.BColor.YELLOW).build();
            } else if(k == 2) {
                console = new ColoredPrinter.Builder(1, false).background(Ansi.BColor.RED).build();
            } else if(k == 3) {
                console = new ColoredPrinter.Builder(1, false).background(Ansi.BColor.GREEN).build();
            } else if(k == 4) {
                console = new ColoredPrinter.Builder(1, false).background(Ansi.BColor.CYAN).build();
            }

            console.println("");
            console.println(String.format("Please enter the positions for the %s (size: %s)", ship.getName(), ship.getSize()));


            for (int i = 1; i <= ship.getSize(); i++) {
                console.println(String.format("Enter position %s of %s (i.e A3):", i, ship.getSize()));
                String positionInput = validate(scanner.next());
                ship.addPosition(positionInput);

                for (Position position : ship.getPositions()) {
                    switch (position.getColumn()) {
                        case A:
                            chess[0][position.getRow()-1]="X";
                            break;
                        case B:
                            chess[1][position.getRow()-1]="X";
                            break;
                        case C:
                            chess[2][position.getRow()-1]="X";
                            break;
                        case D:
                            chess[3][position.getRow()-1]="X";
                            break;
                        case E:
                            chess[4][position.getRow()-1]="X";
                            break;
                        case F:
                            chess[5][position.getRow()-1]="X";
                            break;
                        case G:
                            chess[6][position.getRow()-1]="X";
                            break;
                        case H:
                            chess[7][position.getRow()-1]="X";
                            break;
                        default:
                            break;
                    }
                }

                console.print(ANSI_GREEN+"   1 2 3 4 5 6 7 8");console.println("");
                for (int r=0; r<chess.length; r++) {
                    switch (r) {
                        case 0:
                            console.print(ANSI_GREEN+"A| ");
                            break;
                        case 1:
                            console.print(ANSI_GREEN+"B| ");
                            break;
                        case 2:
                            console.print(ANSI_GREEN+"C| ");
                            break;
                        case 3:
                            console.print(ANSI_GREEN+"D| ");
                            break;
                        case 4:
                            console.print(ANSI_GREEN+"E| ");
                            break;
                        case 5:
                            console.print(ANSI_GREEN+"F| ");
                            break;
                        case 6:
                            console.print(ANSI_GREEN+"G| ");
                            break;
                        case 7:
                            console.print(ANSI_GREEN+"H| ");
                            break;
                        default:
                            break;
                    }
                    for (int c=0; c<chess[r].length; c++) {
                        if(c==7)
                            console.println(ANSI_GREEN+chess[r][c]);
                        else{
                            console.print(ANSI_GREEN+chess[r][c]+" ");
                        }
                    }
                }
            }

            k++;
        }
        console.clear();
    }

    private static void InitializeEnemyFleet() {
        enemyFleet = GameController.initializeShips();

        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 4));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 5));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 6));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 7));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 8));

        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 6));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 7));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 8));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 9));

        enemyFleet.get(2).getPositions().add(new Position(Letter.A, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.B, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.C, 3));

        enemyFleet.get(3).getPositions().add(new Position(Letter.F, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.G, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.H, 8));

        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 5));
        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 6));
    }
}
