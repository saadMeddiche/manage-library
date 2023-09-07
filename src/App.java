import database.Db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import helpers.helper;
import services.Service;
import controllers.MenuManageController;
import models.*;

public class App {

    // public static Connection connection = Db.makeConnection();
    // public static Service service = new BookService();

    // public static Class<?> c = Book.class;

    // public static String nameTable = c.getSimpleName() + "s";
    // public static String nameClass = c.getSimpleName();

    public static void main(String[] args) throws Exception {

        MenuManageController m = new MenuManageController();
        m.start();

        // showAll(0);

    }

    // protected static void showAll(int currentPage) {

    // helper.clearConsole();

    // int pageSize = 5;

    // List<Object> listObject = service.fetchAll(c, nameTable);

    // int startRow = currentPage * pageSize;
    // int endRow = Math.min(startRow + pageSize, listObject.size());

    // int pages = (int) Math.ceil((double) listObject.size() / pageSize);

    // System.out.println("\u001B[32m" + "=======List " + nameTable + "=======" +
    // "\u001B[0m");

    // if (startRow >= listObject.size()) {
    // System.out.println("No more " + nameTable + " to display.");
    // return;
    // }

    // for (int i = startRow; i < endRow; i++) {

    // Field[] fields = listObject.get(i).getClass().getDeclaredFields();

    // try {
    // for (Field field : fields) {
    // field.setAccessible(true);
    // String fieldName = field.getName();
    // Object value = field.get(listObject.get(i));
    // System.out.println(fieldName + ": " + value);
    // }

    // System.out.println("\u001B[32m" + "======================" + "\u001B[0m");

    // } catch (Exception e) {
    // e.printStackTrace();
    // }

    // }

    // System.out.println("Page " + (currentPage + 1) + " of " + pages);

    // System.out.println("1. Next Page");
    // System.out.println("2. Previous Page");
    // System.out.println("3. Exit");
    // System.out.println();

    // Scanner input = new Scanner(System.in);
    // int choice = input.nextInt();

    // switch (choice) {
    // case 1:
    // if (currentPage + 1 < pages) {

    // showAll(currentPage + 1);

    // } else {
    // helper.clearConsole();
    // System.out.println("You have reached the last page");
    // helper.stopProgramUntilButtonIsCliqued();

    // showAll(currentPage);
    // }

    // break;
    // case 2:
    // if (currentPage > 0) {
    // showAll(currentPage - 1);
    // } else {
    // helper.clearConsole();
    // System.out.println("You are already on the first page.");
    // helper.stopProgramUntilButtonIsCliqued();

    // showAll(currentPage);
    // }
    // break;
    // case 3:
    // return;
    // default:
    // helper.clearConsole();

    // System.out.println("wa haaaad choice makaynx :/");

    // helper.stopProgramUntilButtonIsCliqued();
    // showAll(currentPage);
    // }
    // }

}

// Object obj = c.newInstance();

// for (Field field : fields) {

// field.setAccessible(true);

// String propertyName = field.getName();

// Object value = rs.getObject(propertyName);

// field.set(obj, value);

// }