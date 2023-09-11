// # Interface In Java
package views;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

import helpers.helper;
import services.Service;
import controllers.MainMenu;
import controllers.Menu;

public class View extends Menu {

    protected Class<?> c;

    protected String nameTable;

    protected String nameClass;

    protected Service service = new Service();

    protected String whereColumn;

    public View(Class<?> c) {
        this.c = c;
        this.nameClass = c.getSimpleName();
        this.nameTable = c.getSimpleName() + "s";

        this.whereColumn = getSpecial(c);
    }

    public String getSpecial(Class<?> clazz) {

        try {
            Object obj = clazz.newInstance();

            Method[] methods = clazz.getDeclaredMethods();

            for (Method method : methods) {
                if (method.getName().equals("special")) {
                    return method.invoke(obj).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void showAll() {

        List<Object> listObject = service.fetchAll(c, nameTable);

        pagination(0, listObject);
    }

    public void search() {

        try {
            helper.clearConsole();

            Scanner input = new Scanner(System.in);

            Field[] fields = c.getDeclaredFields();

            System.out.print("Wich Column You Looking For (");
            for (Field field : fields) {
                System.out.print(field.getName());
                System.out.print(",");
            }
            System.out.print(")");
            System.out.println();

            String whereColumn = input.next();

            helper.clearConsole();

            Boolean columnExist = false;
            for (Field field : fields) {
                if (field.getName().equals(whereColumn)) {
                    columnExist = true;
                    break;
                }
            }

            if (!columnExist) {
                System.out.println("This Column do not Exist");
                helper.stopProgramUntilButtonIsCliqued();
                return;
            }

            Field field = c.getField(whereColumn);

            System.out.println("Write the " + whereColumn + " of the " + nameClass + "that you want to search");

            Object value = null;
            if (field.getType() == String.class) {
                value = input.next();
            }

            if (field.getType() == Integer.class) {
                value = input.nextInt();
            }

            helper.clearConsole();

            if (!checkIfExist(nameTable, whereColumn, value)) {
                return;
            }

            helper.clearConsole();

            List<Object> objects = service.search(c, nameTable, whereColumn, value);

            pagination(0, objects);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void show(String whereColumn, Object value) {

        if (!checkIfExist(nameTable, whereColumn, value)) {
            return;
        }

        Object object = service.find(c, nameTable, whereColumn, value);

        Field[] fields = object.getClass().getDeclaredFields();

        try {

            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object v = field.get(object);
                System.out.println(fieldName + ": " + v);
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

                    String referenced_table_name = service.get_referenced_table_name(nameTable, field.getName());

                    if (referenced_table_name == null) {
                        System.out.println("Add " + field.getName() + " Of " + nameClass);

                        if (field.getType() == String.class) {
                            String value = input.next();
                            Values[index] = value;
                        }

                        if (field.getType() == Integer.class) {
                            Integer value = Integer.parseInt(input.next());
                            Values[index] = value;
                        }

                    } else {

                        StringBuilder sb = new StringBuilder(referenced_table_name);
                        sb.setLength(sb.length() - 1);
                        String referenced_class_name = sb.toString();

                        Class<?> claxx = getClass(referenced_class_name);

                        String whereColumn = getSpecial(claxx);

                        System.out.println("Add " + whereColumn + " Of " + claxx.getSimpleName());

                        String v = input.next().toString();

                        if (!checkIfExist(referenced_table_name, whereColumn, v)) {
                            return;
                        }

                        Object object = service.find(claxx, referenced_table_name, whereColumn, v);

                        Method getIdMethod = object.getClass().getMethod("getId");

                        Object id = getIdMethod.invoke(object);

                        Values[index] = id;

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

            Field field = c.getField(whereColumn);

            Object value = null;

            System.out.println("Write the " + whereColumn + " of the " + nameClass + " that you want to update");

            if (field.getType() == String.class) {
                value = input.nextLine();
            }

            if (field.getType() == Integer.class) {
                value = input.nextInt();
            }

            helper.clearConsole();

            if (!checkIfExist(nameTable, whereColumn, value)) {
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
            int numeration = 1;

            for (Field f : fields) {

                if (f.getName() == "id") {
                    continue;
                }

                System.out.println(numeration + ". " + f.getName());
                numeration++;

            }
            System.out.println(numeration + ". All of the above");

            System.out.print("Enter your choice: ");
            int choice = input.nextInt();

            // If Choice Is Out Of Range
            if (choice < 1 && choice > fields.length + 1) {
                System.out.println("Invalid choice.");
                return;
            }

            if (choice == numeration) {

                for (Field f : fields) {

                    Object nv = null;

                    if (f.getName() == "id") {
                        f.set(object, null);
                        continue;
                    }

                    System.out.print("Enter the new value for " + f.getName() + ": ");

                    if (f.getType() == String.class) {
                        nv = input.next();
                    }

                    if (f.getType() == Integer.class) {
                        nv = input.nextInt();
                    }

                    f.set(object, nv);
                }

            } else {

                Field selectedField = fields[choice];
                System.out.print("Enter the new value for " + selectedField.getName() + ": ");
                Object newValue = null;
                Scanner scann = new Scanner(System.in);

                if (selectedField.getType() == String.class) {
                    newValue = scann.nextLine();
                }

                if (selectedField.getType() == Integer.class) {
                    newValue = scann.nextInt();
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

            if (!checkIfExist(nameTable, whereColumn, value)) {
                return;
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

    public void pagination(int currentPage, List<Object> listObject) {

        helper.clearConsole();

        int pageSize = 5;

        int startRow = currentPage * pageSize;
        int endRow = Math.min(startRow + pageSize, listObject.size());

        int pages = (int) Math.ceil((double) listObject.size() / pageSize);

        System.out.println("\u001B[32m" + "=======List " + nameTable + "=======" + "\u001B[0m");

        if (listObject.isEmpty()) {
            System.out.println("There is no " + nameTable + " to display.");
            helper.stopProgramUntilButtonIsCliqued();
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
                    pagination(currentPage + 1, listObject);
                    break;
                }

                helper.clearConsole();
                System.out.println("You have reached the last page");
                helper.stopProgramUntilButtonIsCliqued();
                pagination(currentPage, listObject);

                break;
            case 2:
                if (currentPage > 0) {
                    pagination(currentPage - 1, listObject);
                    break;
                }

                helper.clearConsole();
                System.out.println("You are already on the first page.");
                helper.stopProgramUntilButtonIsCliqued();
                pagination(currentPage, listObject);

                break;
            case 3:
                return;
            default:
                helper.clearConsole();
                System.out.println("wa haaaad choice makaynx :/");
                helper.stopProgramUntilButtonIsCliqued();
                pagination(currentPage, listObject);
        }
    }

    @Override
    public String[] options() {

        return new String[] {
                "Add " + nameClass,
                "Search " + nameClass,
                "Show " + nameClass,
                "Update " + nameClass,
                "Delete " + nameClass,
                "Return"
        };
    }

    @Override
    public void excuteChoice(int choice) {
        switch (choice) {
            case 0:
                add();
                break;
            case 1:
                search();
                break;
            case 2:
                showAll();
                break;
            case 3:
                update(whereColumn);
                break;
            case 4:
                delete(whereColumn);
                break;
            case 5:
                // System.exit(0);
                MainMenu m = new MainMenu();
                m.start();
                break;
            default:
                System.out.println("wa haaaad choice makaynx :/");
        }
    }

    public Boolean checkIfExist(String nameTable, String whereColumn, Object value) {

        if (!service.checkIfExist(nameTable, whereColumn, value)) {

            System.out.println(nameTable + " with " + whereColumn + " " + value + " does not exist.");
            helper.stopProgramUntilButtonIsCliqued();

            return false;
        }

        return true;
    }

}
