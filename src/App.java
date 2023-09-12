
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

import controllers.MainMenu;
import models.Book;
import services.Service;
import helpers.helper;

public class App {

    public static Class<?> c = Book.class;

    public static String nameClass = c.getSimpleName();

    public static String nameTable = nameClass + "s";

    public static Service service = new Service();

    // public static String whereColumn = "isbn";

    public static void main(String[] args) throws Exception {

        // MainMenu m = new MainMenu();
        // m.start();

        Class<?> c = Book.class;

        // System.out.println(c.getTypeName());

        Method method = c.getMethod("special");
        Object result = method.invoke(null).toString();
        System.out.println(result);

        // String x = service.get_referenced_table_name("borrowedbooks", "book_id");
        // System.out.println(x);
    }

}

// Object obj = c.newInstance();

// for (Field field : fields) {

// field.setAccessible(true);

// String propertyName = field.getName();

// Object value = rs.getObject(propertyName);

// field.set(obj, value);

// }