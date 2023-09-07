import database.Db;
import services.BookService;
import java.sql.Connection;

import controllers.MenuManageController;

public class App {

    public static Connection connection = Db.makeConnection();
    public static BookService bookService = new BookService();

    public static void main(String[] args) throws Exception {

        // List<Object> objects = search(Book.class, "books", "author", "test");

        // for (Object object : objects) {

        // Field[] fields = object.getClass().getDeclaredFields();

        // for (Field field : fields) {
        // field.setAccessible(true);
        // String fieldName = field.getName();
        // Object value = field.get(object);
        // System.out.println(fieldName + ": " + value);
        // }

        // }
        MenuManageController m = new MenuManageController();
        m.start();

    }

}

// Object obj = c.newInstance();

// for (Field field : fields) {

// field.setAccessible(true);

// String propertyName = field.getName();

// Object value = rs.getObject(propertyName);

// field.set(obj, value);

// }