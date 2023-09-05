// #Math Ceil Function

package views;

import java.util.Scanner;
import java.util.List;

import helpers.helper;
import services.BookService;
import models.Book;

public class ViewBook implements View {

    private static BookService BookService = new BookService();

    @Override
    public void showAll(int currentPage) {

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

    public void show(int isbn) {

        if (!BookService.checkIfBookExist(isbn)) {
            helper.clearConsole();
            System.out.println("Book with ISBN " + isbn + " does not exist.");
            helper.stopProgramUntilButtonIsCliqued();
            return;
        }

        Book book = BookService.findBookWithIsbn(isbn);

        System.out.println("ID: " + book.getId());
        System.out.println("TITLE: " + book.getTitle());
        System.out.println("AUTHOR: " + book.getAuthor());
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("QUANTITE: " + book.getQuantite());

    }

    public void add() {
        Scanner input = new Scanner(System.in);

        // Scann The Data
        System.out.println("Add Title Of Book");
        String title = input.nextLine();

        System.out.println("Add Author Of Book");
        String author = input.nextLine();

        System.out.println("Add Isbn Of Book");
        Integer isbn = input.nextInt();

        System.out.println("Add Quantite Of Book");
        Integer quantite = input.nextInt();

        Book book = new Book(null, title, author, isbn, quantite);

        int status = BookService.add(book);

        helper.clearConsole();

        if (status == 0) {
            System.out.println("Something Went Wrong !!");
        } else {
            System.out.println("Book Has Been Created Successfuly");
        }

        helper.stopProgramUntilButtonIsCliqued();

    }

    public void delete() {
        Scanner input = new Scanner(System.in);

        System.out.println("===================================================");
        System.out.println("Write The Isbn Of The Books That You Want to Delete");
        System.out.println("===================================================");

        Integer book_isbn = input.nextInt();

        helper.clearConsole();

        if (!helper.wannaContinue()) {
            return;
        }

        helper.clearConsole();

        int status = BookService.destroyByIsbn(book_isbn);

        if (status == 0) {
            System.out.println("Book with ISBN " + book_isbn + " does not exist.");
        } else {
            System.out.println("Book with ISBN " + book_isbn + " has been deleted.");
        }

        helper.stopProgramUntilButtonIsCliqued();

    }

    public void update() {

        Scanner input = new Scanner(System.in);

        // Scann The Data
        System.out.println("Write the isbn of the book that you want to update");
        Integer isbn = input.nextInt();

        if (!BookService.checkIfBookExist(isbn)) {
            System.out.println("Book with ISBN " + isbn + " does not exist.");
            helper.stopProgramUntilButtonIsCliqued();
            return;
        }

        helper.clearConsole();

        System.out.println("==========================================");
        System.out.println("Is This The Book That You Want To Update ?");
        System.out.println("==========================================");

        Book book = BookService.findBookWithIsbn(isbn);

        show(isbn);

        if (!helper.wannaContinue()) {
            return;
        }

        helper.clearConsole();

        System.out.println("Select the attributes you want to update:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. ISBN");
        System.out.println("4. Quantity");
        System.out.println("5. All of the above");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        input.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter the new title: ");
                String newTitle = input.nextLine();
                book.setTitle(newTitle);
                break;
            case 2:
                System.out.print("Enter the new author: ");
                String newAuthor = input.nextLine();
                book.setAuthor(newAuthor);
                break;
            case 3:
                System.out.print("Enter the new ISBN: ");
                int newIsbn = input.nextInt();
                book.setIsbn(newIsbn);
                break;
            case 4:
                System.out.print("Enter the new quantity: ");
                int newQuantite = input.nextInt();
                book.setQuantite(newQuantite);
                break;
            case 5:
                System.out.print("Enter the new title: ");
                String updatedTitle = input.nextLine();
                book.setTitle(updatedTitle);

                System.out.print("Enter the new author: ");
                String updatedAuthor = input.nextLine();
                book.setAuthor(updatedAuthor);

                System.out.print("Enter the new ISBN: ");
                Integer updatedIsbn = input.nextInt();
                book.setIsbn(updatedIsbn);

                System.out.print("Enter the new quantity: ");
                int updatedQuantite = input.nextInt();
                book.setQuantite(updatedQuantite);
                break;
            default:
                System.out.println("Invalid choice.");
        }

        int status = BookService.updateByIsbn(book, isbn);

        helper.clearConsole();

        if (status == 1) {
            System.out.println("Book with ISBN " + isbn + " has been updated.");
        } else {
            System.out.println("Something Went Wrong");
        }

        helper.stopProgramUntilButtonIsCliqued();

    }

}
