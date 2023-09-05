package views;

import java.util.List;
import java.util.Scanner;

import models.Book;
import models.Client;
import services.ClientService;
import helpers.helper;

public class ViewClient implements View {

    private static ClientService ClientService = new ClientService();

    @Override
    public void showAll(int currentPage) {

        helper.clearConsole();

        int pageSize = 5;

        List<Client> clientList = ClientService.getAllClients();

        int startRow = currentPage * pageSize;
        int endRow = Math.min(startRow + pageSize, clientList.size());

        int pages = (int) Math.ceil((double) clientList.size() / pageSize);

        System.out.println("\u001B[32m" + "=======List Clients=======" + "\u001B[0m");

        if (startRow >= clientList.size()) {
            System.out.println("No more Clients to display.");
            return;
        }

        for (int i = startRow; i < endRow; i++) {
            Client book = clientList.get(i);
            System.out.println("ID: " + book.getId());
            System.out.println("NAME: " + book.getName());
            System.out.println("CIN: " + book.getCin());
            System.out.println("PHONE: " + book.getPhone());
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

    public void show(int id) {

        if (!ClientService.checkIfClientExist(id)) {
            helper.clearConsole();
            System.out.println("Client with id " + id + " does not exist.");
            helper.stopProgramUntilButtonIsCliqued();
            return;
        }

        Client client = ClientService.find(id);

        System.out.println("ID: " + client.getId());
        System.out.println("NAME: " + client.getName());
        System.out.println("CIN: " + client.getCin());
        System.out.println("PHONE: " + client.getPhone());

    }

    public void add() {
        Scanner input = new Scanner(System.in);

        // Scann The Data
        System.out.println("Add Name Of Client");
        String name = input.nextLine();

        System.out.println("Add Cin Of Client");
        String cin = input.nextLine();

        System.out.println("Add Phone Of Client");
        String phone = input.nextLine();

        Client client = new Client(null, name, cin, phone);

        int status = ClientService.add(client);

        helper.clearConsole();

        if (status == 0) {
            System.out.println("Something Went Wrong !!");
        } else {
            System.out.println("Client Has Been Added Successfuly");
        }

        helper.stopProgramUntilButtonIsCliqued();

    }

    public void delete() {
        Scanner input = new Scanner(System.in);

        System.out.println("===================================================");
        System.out.println("Write The Id Of The Client That You Want to Remove");
        System.out.println("===================================================");

        Integer id = input.nextInt();

        helper.clearConsole();

        if (!helper.wannaContinue()) {
            return;
        }

        helper.clearConsole();

        int status = ClientService.destroy(id);

        if (status == 0) {
            System.out.println("Client with ISBN " + id + " does not exist.");
        } else {
            System.out.println("Client with ISBN " + id + " has been removed.");
        }

        helper.stopProgramUntilButtonIsCliqued();

    }

    public void update() {

        Scanner input = new Scanner(System.in);

        // Scann The Data
        System.out.println("Write the id of the client that you want to update");
        Integer id = input.nextInt();

        if (!ClientService.checkIfClientExist(id)) {
            System.out.println("Client with id " + id + " does not exist.");
            helper.stopProgramUntilButtonIsCliqued();
            return;
        }

        helper.clearConsole();

        System.out.println("==========================================");
        System.out.println("Is This The Client That You Want To Update ?");
        System.out.println("==========================================");

        Client client = ClientService.find(id);

        show(id);

        if (!helper.wannaContinue()) {
            return;
        }

        helper.clearConsole();

        System.out.println("Select the attributes you want to update:");
        System.out.println("1. Name");
        System.out.println("2. Cin");
        System.out.println("3. Phone");
        System.out.println("4. All of the above");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        input.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter the new name: ");
                String newName = input.nextLine();
                client.setName(newName);
                break;
            case 2:
                System.out.print("Enter the new cin: ");
                String newCin = input.nextLine();
                client.setCin(newCin);
                break;
            case 3:
                System.out.print("Enter the new phone: ");
                String newPhone = input.nextLine();
                client.setPhone(newPhone);
                break;
            case 4:
                System.out.print("Enter the new name: ");
                newName = input.nextLine();
                client.setName(newName);

                System.out.print("Enter the new cin: ");
                newCin = input.nextLine();
                client.setCin(newCin);

                System.out.print("Enter the new phone: ");
                newPhone = input.nextLine();
                client.setPhone(newPhone);

                break;
            default:
                System.out.println("Invalid choice.");
        }

        int status = ClientService.update(client);

        helper.clearConsole();

        if (status == 1) {
            System.out.println("Client with id " + id + " has been updated.");
        } else {
            System.out.println("Something Went Wrong");
        }

        helper.stopProgramUntilButtonIsCliqued();

    }

}
