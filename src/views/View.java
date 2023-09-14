// # Interface In Java
package views;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

            Method method = clazz.getMethod("special");
            return method.invoke(null).toString();

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

            Field field = chooseColumn(c, input);

            helper.clearConsole();

            Object value = null;
            while (true) {
                System.out.println("Write the " + field.getName() + " of the " + nameClass + "that you want to search");

                value = getInput(input, field);

                helper.clearConsole();

                if (!checkIfExist(nameTable, field.getName(), value)) {
                    helper.clearConsole();
                    continue;
                }
                break;
            }

            helper.clearConsole();

            List<Object> objects = service.search(c, nameTable, field.getName(), value);

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

            Class<?>[] fieldTypes = getFieldTypes(fields);

            Object[] Values = new Object[fields.length];

            int index = 0;

            for (Field field : fields) {

                // If The Field Is The Id
                if (field.getName().equals("id")) {
                    Values[index] = null;
                    index++;
                    continue;
                }

                String referenced_table_name = service.get_referenced_table_name(nameTable, field.getName());

                // Check If The field have a referenced_table_name
                if (referenced_table_name != null) {

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

                    continue;

                }

                System.out.println("Add " + field.getName() + " Of " + nameClass);
                Values[index] = getInput(input, field);

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

    public void update() {

        try {
            helper.clearConsole();

            Scanner input = new Scanner(System.in);

            Field field = chooseColumn(c, input);

            Object value = null;

            Map<String, Object> results = referencedFields(nameTable, field);

            String special_column = results.get("special_column").toString();
            String class_name = results.get("class_name").toString();
            String table_name = results.get("table_name").toString();
            Boolean referenced = Boolean.parseBoolean(results.get("referenced").toString());

            while (true) {

                System.out.println("Write the " + special_column + " of the " + class_name);

                value = getInput(input, field);

                if (referenced) {

                    Class<?> claxx = getClass(class_name);

                    if (!checkIfExist(table_name, special_column, value)) {
                        continue;
                    }

                    Object object = service.find(claxx, table_name, special_column, value);

                    Method getIdMethod = object.getClass().getMethod("getId");

                    Object id = getIdMethod.invoke(object);

                    value = id;
                }

                helper.clearConsole();

                if (!checkIfExist(nameTable, field.getName(), value)) {
                    continue;
                }

                break;
            }

            helper.clearConsole();

            System.out.println("==========================================");
            System.out.println("Is This The " + nameClass + " That You Want To Update ?");
            System.out.println("==========================================");

            Object object = service.find(c, nameTable, field.getName(), value);

            show(field.getName(), value);

            if (!helper.wannaContinue()) {
                return;
            }

            helper.clearConsole();

            System.out.println("Select what you want to Update:");
            Field[] fields = c.getDeclaredFields();
            int numeration = 1;

            for (Field f : fields) {

                if (!f.getName().equals("id")) {

                    results = referencedFields(nameTable, f);

                    special_column = results.get("special_column").toString();

                    System.out.println(numeration + ". " + special_column);
                    numeration++;
                }
            }

            System.out.println(numeration + ". All of the above");

            int choice = 0;
            while (true) {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(input.nextLine());

                // If Choice Is Out Of Range)
                if (choice < 1 || choice >= fields.length + 1) {
                    System.out.println("Invalid choice");
                    helper.stopProgramUntilButtonIsCliqued();
                    continue;
                }

                break;
            }

            if (choice == numeration) {

                for (Field f : fields) {

                    if (!f.getName().equals("id")) {

                        results = referencedFields(nameTable, f);

                        special_column = results.get("special_column").toString();
                        class_name = results.get("class_name").toString();
                        table_name = results.get("table_name").toString();
                        referenced = Boolean.parseBoolean(results.get("referenced").toString());

                        Class<?> claxx = getClass(class_name);

                        Object nv = null;

                        while (true) {
                            System.out.print("Enter the new value for " + special_column + ": ");
                            nv = getInput(input, f);

                            if (referenced) {

                                if (!checkIfExist(table_name, special_column, nv)) {
                                    helper.clearConsole();
                                    continue;
                                }

                                Object obj = service.find(claxx, table_name, special_column, nv);

                                Method getIdMethod = obj.getClass().getMethod("getId");

                                Object id = getIdMethod.invoke(obj);

                                nv = id;

                            }

                            break;
                        }

                        f.set(object, nv);
                    }
                }

            } else {

                Field selectedField = fields[choice];

                results = referencedFields(nameTable, selectedField);

                special_column = results.get("special_column").toString();
                class_name = results.get("class_name").toString();
                table_name = results.get("table_name").toString();
                referenced = Boolean.parseBoolean(results.get("referenced").toString());

                Class<?> claxx = getClass(class_name);

                Object nv = null;

                while (true) {
                    System.out.print("Enter the new value for " + special_column + ": ");
                    nv = getInput(input, selectedField);

                    if (referenced) {

                        if (!checkIfExist(table_name, special_column, nv)) {
                            helper.clearConsole();
                            continue;
                        }

                        Object obj = service.find(claxx, table_name, special_column, nv);

                        Method getIdMethod = obj.getClass().getMethod("getId");

                        Object id = getIdMethod.invoke(obj);

                        nv = id;

                    }

                    break;
                }

                selectedField.set(object, nv);

            }

            Boolean updatedSuccessfully = service.update(object, nameTable, field.getName(), value);

            helper.clearConsole();

            if (updatedSuccessfully) {
                System.out.println(nameClass + " has been updated.");
            } else {
                System.out.println("Something Went Wrong");
            }

            helper.stopProgramUntilButtonIsCliqued();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete() {

        try {

            Scanner input = new Scanner(System.in);

            Field field = chooseColumn(c, input);

            helper.clearConsole();

            Object value = 0;

            while (true) {

                System.out
                        .println("Write The " + field.getName() + " Of The " + nameClass + " That You Want to Delete");

                value = getInput(input, field);

                helper.clearConsole();

                if (!checkIfExist(nameTable, field.getName(), value)) {
                    helper.clearConsole();
                    continue;
                }

                break;
            }

            helper.clearConsole();

            System.out.println("==========================================");
            System.out.println("Is This The " + nameClass + " That You Want To Delete ?");
            System.out.println("==========================================");

            show(field.getName(), value);

            if (!helper.wannaContinue()) {
                return;
            }

            helper.clearConsole();

            Boolean deletedSuccessfuly = service.destroy(nameTable, field.getName(), value);

            if (deletedSuccessfuly) {
                System.out.println(nameClass + " with " + field.getName() + " " + value + " has been deleted.");
            } else {
                System.out.println(nameClass + " with " + field.getName() + " " + value + " does not exist.");
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
                update();
                break;
            case 4:
                delete();
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

    public Field chooseColumn(Class<?> c, Scanner input) {

        Field[] fields = c.getDeclaredFields();

        Map<Object, Field> fakeAndRealFileds = new HashMap<>();

        while (true) {

            System.out.print("Wich Column You Looking For (");
            for (Field field : fields) {

                Map<String, Object> results = referencedFields(nameTable, field);

                fakeAndRealFileds.put(results.get("special_column"), field);

                System.out.print(results.get("special_column"));
                System.out.print(",");
            }

            System.out.print(")");
            System.out.println();

            String whereColumn = input.nextLine();

            helper.clearConsole();

            Field field = fakeAndRealFileds.get(whereColumn);

            if (field != null) {
                return field;
            }

            helper.clearConsole();
            System.out.println("This Column do not Exist");
            helper.stopProgramUntilButtonIsCliqued();
            helper.clearConsole();
        }

    }

    public Boolean checkIfExist(String nameTable, String whereColumn, Object value) {

        if (!service.checkIfExist(nameTable, whereColumn, value)) {

            System.out.println(nameTable + " with " + whereColumn + " " + value + " does not exist.");
            helper.stopProgramUntilButtonIsCliqued();
            helper.clearConsole();

            return false;
        }

        return true;
    }

    public Object getInput(Scanner input, Field field) {

        String referenced_table_name = service.get_referenced_table_name(nameTable, field.getName());

        // Check If The field have a referenced_table_name
        if (referenced_table_name != null) {

            StringBuilder sb = new StringBuilder(referenced_table_name);
            sb.setLength(sb.length() - 1);
            String referenced_class_name = sb.toString();

            Class<?> claxx = getClass(referenced_class_name);
            String Column = getSpecial(claxx);
            try {
                field = claxx.getField(Column);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        while (true) {
            switch (field.getType().getSimpleName()) {
                case "String":
                    String stringValue = input.nextLine();
                    return stringValue;
                case "Integer":
                    try {
                        Integer integerValue = Integer.parseInt(input.nextLine());
                        return integerValue;
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid Integer value for " + field.getName());
                    }
                    break;
                case "Long":
                    try {
                        Long longValue = Long.parseLong(input.nextLine());
                        return longValue;
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid Long value for " + field.getName());
                    }
                    break;
                default:
                    System.out.println("Had Type ga3 makayn");
                    break;
            }
        }
    }

    public Class<?>[] getFieldTypes(Field[] fields) {

        // Source :
        // https://www.geeksforgeeks.org/how-to-create-array-of-objects-in-java/
        Class<?>[] fieldTypes = new Class<?>[fields.length];

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            fieldTypes[i] = fields[i].getType();
        }

        return fieldTypes;
    }

    public Map<String, Object> referencedFields(String nameOfTable, Field field) {

        Map<String, Object> results = new HashMap<>();

        String referenced_table_name = service.get_referenced_table_name(nameOfTable, field.getName());

        // Check If The field have a referenced_table_name
        if (referenced_table_name != null) {

            StringBuilder sb = new StringBuilder(referenced_table_name);
            sb.setLength(sb.length() - 1);
            String referenced_class_name = sb.toString();

            Class<?> claxx = getClass(referenced_class_name);
            String Column = getSpecial(claxx);

            results.put("table_name", referenced_table_name);
            results.put("class_name", referenced_class_name);
            results.put("special_column", Column);
            results.put("referenced", true);

            return results;
        }

        results.put("table_name", field.getDeclaringClass().getSimpleName() + "s");
        results.put("class_name", field.getDeclaringClass().getSimpleName());
        results.put("special_column", field.getName());
        results.put("referenced", false);

        return results;

    }
}
