// # Interface In Java
package views;

import java.util.List;
import java.util.Scanner;

import models.Book;
import services.BookService;
import helpers.helper;

public abstract class View {

    private Service service;

    public View(Service service) {
        this.service = service;
    }

    protected void showAll(int currentPage) {

        helper.clearConsole();

        int pageSize = 5;

        List<Book> bookList = BookService.getAllBooks();

        int startRow = currentPage * pageSize;
        int endRow = Math.min(startRow + pageSize, bookList.size());

        int pages = (int) Math.ceil((double) bookList.size() / pageSize);

        System.out.println("\u001B[32m" + "=======List Books=======" + "\u001B[0m");

        if (startRow >= bookList.size()) {
            System.out.println("No more books to display.");
            return;
        }

        for (int i = startRow; i < endRow; i++) {
            Book book = bookList.get(i);

            Class c = Book.class;
            c.getDeclaredFields();

            System.out.println("ID: " + book.getId());
            System.out.println("TITLE: " + book.getTitle());
            System.out.println("AUTHOR: " + book.getAuthor());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("QUANTITY: " + book.getQuantite());
            System.out.println("\u001B[32m" + "======================" + "\u001B[0m");
        }

        System.out.println("Page " + (currentPage + 1) + " of " + pages);

        System.out.println("1. Next Page");
        System.out.println("2. Previous Page");
        System.out.println("3. Exit");
        System.out.println();

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        switch (choice) {
            case 1:
                if (currentPage + 1 < pages) {

                    showAll(currentPage + 1);

                } else {
                    helper.clearConsole();
                    System.out.println("You have reached the last page");
                    helper.stopProgramUntilButtonIsCliqued();

                    showAll(currentPage);
                }

                break;
            case 2:
                if (currentPage > 0) {
                    showAll(currentPage - 1);
                } else {
                    helper.clearConsole();
                    System.out.println("You are already on the first page.");
                    helper.stopProgramUntilButtonIsCliqued();

                    showAll(currentPage);
                }
                break;
            case 3:
                return;
            default:
                helper.clearConsole();

                System.out.println("wa haaaad choice makaynx :/");

                helper.stopProgramUntilButtonIsCliqued();
                showAll(currentPage);
        }
    }

    public abstract void show(int value);

    public abstract void add();

    public abstract void update();

    public abstract void delete();

}
