// # Interface In Java
package views;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

import helpers.helper;

import services.Service;

public class View {

    private Class<?> c;

    private String nameTable;

    private String nameClass;

    private Service service = new Service();

    public View(Class<?> c) {
        this.c = c;
        this.nameClass = c.getSimpleName();
        this.nameTable = c.getSimpleName() + "s";
    }

    public void showAll(int currentPage) {

        helper.clearConsole();

        int pageSize = 5;

        List<Object> listObject = service.fetchAll(c, nameTable);

        int startRow = currentPage * pageSize;
        int endRow = Math.min(startRow + pageSize, listObject.size());

        int pages = (int) Math.ceil((double) listObject.size() / pageSize);

        System.out.println("\u001B[32m" + "=======List " + nameTable + "=======" + "\u001B[0m");

        if (startRow >= listObject.size()) {
            System.out.println("No more " + nameTable + " to display.");
            return;
        }

        for (int i = startRow; i < endRow; i++) {

            Field[] fields = listObject.get(i).getClass().getDeclaredFields();

            try {
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object value = field.get(listObject.get(i));
                    System.out.println(fieldName + ": " + value);
                }

                System.out.println("\u001B[32m" + "======================" + "\u001B[0m");

            } catch (Exception e) {
                e.printStackTrace();
            }

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

    public void show(String whereColumn, Object vlaue) {

        if (!service.checkIfExist(nameTable, whereColumn, vlaue)) {
            helper.clearConsole();
            System.out.println(nameClass + " with " + whereColumn + " " + vlaue + " does not exist.");
            helper.stopProgramUntilButtonIsCliqued();
            return;
        }

        Object object = service.find(c, nameTable, whereColumn, vlaue);

        Field[] fields = object.getClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(object);
                System.out.println(fieldName + ": " + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void add() {
        try {
            Scanner input = new Scanner(System.in);

            Field[] fields = c.getDeclaredFields();

            // Source :
            // https://www.geeksforgeeks.org/how-to-create-array-of-objects-in-java/
            Class<?>[] fieldTypes = new Class<?>[fields.length];

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                fieldTypes[i] = fields[i].getType();
            }

            Object[] Values = new Object[fields.length];

            int index = 0;
            for (Field field : fields) {

                if (field.getName().equals("id")) {

                    Values[index] = null;

                } else {
                    System.out.println("Add " + field.getName() + " Of " + nameClass);

                    if (field.getType() == String.class) {
                        String value = input.nextLine();
                        Values[index] = value;
                    }

                    if (field.getType() == Integer.class) {
                        Integer value = input.nextInt();
                        Values[index] = value;
                    }
                    // index++;

                }

                index++;

            }

            Constructor<?> constructor = c.getConstructor(fieldTypes);
            Object obj = constructor.newInstance(Values);

            Boolean addedSuccessfuly = service.add(obj, nameTable);
            helper.clearConsole();

            if (addedSuccessfuly) {
                System.out.println(nameClass + " Has Been Created Successfuly");
            } else {
                System.out.println("Something Went Wrong !!");
            }

            helper.stopProgramUntilButtonIsCliqued();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void update(String whereColumn) {

        try {
            Scanner input = new Scanner(System.in);
            System.out.println(whereColumn);
            helper.stopProgramUntilButtonIsCliqued();
            Field field = c.getField(whereColumn);

            Object value = null;

            System.out.println("Write the " + whereColumn + " of the " + nameClass + " that you want to update");
            if (field.getType() == String.class) {
                value = input.nextLine();
            }

            if (field.getType() == Integer.class) {
                value = input.nextInt();
            }

            if (!service.checkIfExist(nameTable, whereColumn, value)) {
                System.out.println(nameTable + " with " + whereColumn + " " + value + " does not exist.");
                helper.stopProgramUntilButtonIsCliqued();
                return;
            }

            helper.clearConsole();

            System.out.println("==========================================");
            System.out.println("Is This The " + nameClass + " That You Want To Update ?");
            System.out.println("==========================================");

            Object object = service.find(c, nameTable, whereColumn, value);

            show(whereColumn, value);

            if (!helper.wannaContinue()) {
                return;
            }

            helper.clearConsole();

            System.out.println("Select what you want to Update:");
            Field[] fields = c.getDeclaredFields();
            int attributeNumber = 1;

            for (Field f : fields) {
                System.out.println(attributeNumber + ". " + f.getName());
                attributeNumber++;
            }
            System.out.println(attributeNumber + ". All of the above");

            System.out.print("Enter your choice: ");
            int choice = input.nextInt();

            if (choice >= 1 && choice <= fields.length + 1) {
                if (choice == attributeNumber) {

                    for (Field f : fields) {
                        System.out.print("Enter the new value for " + f.getName() + ": ");
                        Object newValue = null;

                        if (f.getType() == String.class) {
                            newValue = input.nextLine();
                        }

                        if (f.getType() == Integer.class) {
                            newValue = input.nextInt();
                        }

                        f.set(object, newValue);
                    }
                } else {

                    Field selectedField = fields[choice - 1];
                    System.out.print("Enter the new value for " + selectedField.getName() + ": ");
                    Object newValue = null;

                    if (selectedField.getType() == String.class) {
                        newValue = input.nextLine();
                    }

                    if (selectedField.getType() == Integer.class) {
                        newValue = input.nextInt();
                    }

                    selectedField.set(object, newValue);
                }

                Boolean updatedSuccessfully = service.update(object, nameTable, whereColumn, value);

                helper.clearConsole();

                if (updatedSuccessfully) {
                    System.out.println(nameClass + " with " + whereColumn + " " + value + " has been updated.");
                } else {
                    System.out.println("Something Went Wrong");
                }

            } else {
                System.out.println("Invalid choice.");
            }

            helper.stopProgramUntilButtonIsCliqued();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete(String whereColumn) {

        try {
            Field field = c.getField(whereColumn);

            Scanner input = new Scanner(System.in);

            Object value = null;

            System.out.println("===================================================");
            System.out.println("Write The " + whereColumn + " Of The " + nameClass + " That You Want to Delete");
            System.out.println("===================================================");

            if (field.getType() == String.class) {
                value = input.nextLine();
            }

            if (field.getType() == Integer.class) {
                value = input.nextInt();
            }

            helper.clearConsole();

            if (!helper.wannaContinue()) {
                return;
            }

            helper.clearConsole();

            Boolean deletedSuccessfuly = service.destroy(nameTable, whereColumn, value);

            if (deletedSuccessfuly) {
                System.out.println(nameClass + " with " + whereColumn + " " + value + " has been deleted.");
            } else {
                System.out.println(nameClass + " with " + whereColumn + " " + value + " does not exist.");
            }

            helper.stopProgramUntilButtonIsCliqued();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
