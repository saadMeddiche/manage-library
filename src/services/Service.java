// #New way Of Making new instance
// #how to change the lenght of a string in java
package services;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import database.Db;
import models.Book;

public abstract class Service {

    private Connection connection = Db.makeConnection();

    protected List<Object> fetchAll(Class<Object> c, String table) {

        List<Object> itemList = new ArrayList<Object>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + table);

            while (rs.next()) {

                Constructor<Object> constructor = c.getConstructor();

                Object obj = constructor.newInstance();

                Field[] fields = c.getDeclaredFields();

                for (Field field : fields) {

                    String propertyName = field.getName();

                    Object value = rs.getObject(propertyName);

                    field.set(obj, value);
                }

                itemList.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemList;
    }

    protected Boolean add(Object object, String table) {

        try {

            StringBuilder query = new StringBuilder("INSERT INTO" + table);
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

            for (int i = 0; i < query.length(); i++) {
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

    public int updateByIsbn(Book book, int isbn_searched_with) {

        int status = 0;

        try {

            String query = "UPDATE `books` SET `title`=?,`author`=?,`isbn`=?,`quantite`=? WHERE `isbn`=?";
            PreparedStatement ps = connection.prepareStatement(query);

            // set parameters of query
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getIsbn());
            ps.setInt(4, book.getQuantite());
            ps.setInt(5, isbn_searched_with);

            status = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}