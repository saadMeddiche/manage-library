package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// import java.sql.Statement;
import java.util.Scanner;

import java.io.IOException;
import java.lang.ProcessBuilder;
import database.Db;

public class helper {

    private static Connection connection = Db.makeConnection();

    public static void stopProgramUntilButtonIsCliqued() {

        System.out.println("==============================");
        System.out.println("Click a button to continue ...");
        System.out.println("==============================");

        Scanner x = new Scanner(System.in);
        x.nextLine();
    }

    public static void clearConsole() {
        // 033[H put the cursor in top
        // 033[2J clear all what is after the cursor
        System.out.print("\033[H\033[2J");
    }

    public static Boolean wannaContinue() {

        Scanner input = new Scanner(System.in);

        System.out.println("=================================================================");
        System.out.println("Wanna Continue Or Go Back To Menu ?: yes => continue - no => menu");
        System.out.println("=================================================================");

        String value = input.nextLine();

        if (value.equals("no")) {
            return false;
        }

        return true;
    }
}
