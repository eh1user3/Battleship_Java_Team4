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

public class Main {
    private static List<Ship> myFleet;
    private static List<Ship> enemyFleet;
    private static ColoredPrinter console;

    public static void main(String[] args) {
        console = new ColoredPrinter.Builder(1, false).build();

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
            Position position = parsePosition(scanner.next());
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
                console.println("Yeah ! Nice hit !",  Ansi.Attribute.BOLD, Ansi.FColor.YELLOW, Ansi.BColor.RED);

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
        } catch (Exception e) {
            console.println("Invalid Input. Please try again",  Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLUE);
            Scanner scanner = new Scanner(System.in);
            input = validate(scanner.next());
        }
        return input;
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
