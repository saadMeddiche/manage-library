import controllers.MenuManageController;
import database.Db;
import models.Book;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;

import models.Book;

public class App {

    public static Connection connection = Db.makeConnection();

    public static void main(String[] args) throws Exception {

        // List<Book> books = (List) fetchAll(Book.class, "books");

        // for (Book book : books) {
        // System.out.println("ID: " + book.getId());
        // System.out.println("TITLE: " + book.getTitle());
        // System.out.println("AUTHOR: " + book.getAuthor());
        // System.out.println("ISBN: " + book.getIsbn());
        // System.out.println("QUANTITE: " + book.getQuantite());

        // }

        Book book = new Book(null, "Tress", "Saad", 2345, 22);

        add(book, "books");
    }

    public static List<Object> fetchAll(Class<?> c, String table) {

        List<Object> itemList = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + table);

            Field[] fields = c.getDeclaredFields();

            // Source :
            // https://www.geeksforgeeks.org/how-to-create-array-of-objects-in-java/
            Class<?>[] fieldTypes = new Class<?>[fields.length];

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                fieldTypes[i] = fields[i].getType();
            }

            for (Class<?> field : fieldTypes) {
                System.out.println(field);
            }

            System.out.println("=================");

            while (rs.next()) {

                Object[] fieldValues = new Object[fields.length];

                for (int i = 0; i < fields.length; i++) {
                    String propertyName = fields[i].getName();
                    Object value = rs.getObject(propertyName);

                    fieldValues[i] = value;
                    System.out.println(value.getClass());
                }

                Constructor<?> constructor = c.getConstructor(fieldTypes);
                Object obj = constructor.newInstance(fieldValues);

                itemList.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemList;
    }

    public static Boolean add(Object object, String table) {

        try {

            StringBuilder query = new StringBuilder("INSERT INTO " + table);
            String columns = " (";
            String values = ") VALUES(";

            // Solution For <Object>
            // https://stackoverflow.com/questions/4460580/java-generics-why-someobject-getclass-doesnt-return-class-extends-t
            Class<?> c = object.getClass();

            Field[] fields = c.getDeclaredFields();

            for (Field field : fields) {
                String ColumnName = field.getName();
                columns += ColumnName + ",";
            }

            query.append(columns);
            query.setLength(query.length() - 1);

            for (Field field : fields) {
                values += "?,";
            }

            // for (int i = 0; i < fields.length(); i++) {
            // values += "?,";
            // }

            query.append(values);
            query.setLength(query.length() - 1);
            query.append(")");

            PreparedStatement ps = connection.prepareStatement(query.toString());

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Object value = fields[i].get(object);
                ps.setObject(i + 1, value);
            }

            int count = ps.executeUpdate();

            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}

// Object obj = c.newInstance();

// for (Field field : fields) {

// field.setAccessible(true);

// String propertyName = field.getName();

// Object value = rs.getObject(propertyName);

// field.set(obj, value);

// }