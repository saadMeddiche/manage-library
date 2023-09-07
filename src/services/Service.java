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
import database.Db;

public abstract class Service {

    private static Connection connection = Db.makeConnection();

    public List<Object> fetchAll(Class<?> c, String table) {

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

            while (rs.next()) {

                Object[] fieldValues = new Object[fields.length];

                for (int i = 0; i < fields.length; i++) {
                    String propertyName = fields[i].getName();
                    Object value = rs.getObject(propertyName);

                    fieldValues[i] = value;
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

    public Boolean add(Object object, String table) {

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

    public Boolean update(Object object, String table, String whereColumn, Object value) {

        try {
            StringBuilder query = new StringBuilder("UPDATE `" + table + "` SET ");
            String columns = "";

            Class<?> c = object.getClass();

            Field[] fields = c.getDeclaredFields();

            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    String ColumnName = field.getName();
                    columns += "`" + ColumnName + "`=?,";
                }
            }

            query.append(columns);
            query.setLength(query.length() - 1);
            query.append(" WHERE `" + whereColumn + "`=?");

            PreparedStatement ps = connection.prepareStatement(query.toString());

            System.out.println(query.toString());

            int index = 1;

            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    field.setAccessible(true);
                    Object fieldValue = field.get(object);
                    ps.setObject(index, fieldValue);
                    index++;
                    System.out.println(fieldValue);
                }
            }

            ps.setObject(index, value);

            int count = ps.executeUpdate();

            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean destroy(String table, String whereColumn, Object value) {

        try {

            String query = "DELETE FROM `" + table + "` WHERE `" + whereColumn + "` = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setObject(1, value);

            int count = ps.executeUpdate();

            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Object find(Class<?> c, String table, String whereColumn, Object value) {

        Object obj = null;
        try {

            String query = "SELECT * FROM " + table + " WHERE " + whereColumn + "=?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setObject(1, value);

            ResultSet resultSet = ps.executeQuery();

            // Not found
            if (!resultSet.next()) {
                return null;
            }

            Field[] fields = c.getDeclaredFields();

            // Source :
            // https://www.geeksforgeeks.org/how-to-create-array-of-objects-in-java/
            Class<?>[] fieldTypes = new Class<?>[fields.length];

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                fieldTypes[i] = fields[i].getType();
            }

            Object[] fieldValues = new Object[fields.length];

            for (int i = 0; i < fields.length; i++) {
                String propertyName = fields[i].getName();
                Object v = resultSet.getObject(propertyName);
                fieldValues[i] = v;
            }

            Constructor<?> constructor = c.getConstructor(fieldTypes);
            obj = constructor.newInstance(fieldValues);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;

    }

    public List<Object> search(Class<?> c, String table, String whereColumn, Object value) {

        List<Object> itemList = new ArrayList<>();

        try {

            String query = "SELECT * FROM " + table + " WHERE " + whereColumn + " LIKE ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setObject(1, value + "%");
            ResultSet rs = ps.executeQuery();

            Field[] fields = c.getDeclaredFields();

            // Source :
            // https://www.geeksforgeeks.org/how-to-create-array-of-objects-in-java/
            Class<?>[] fieldTypes = new Class<?>[fields.length];

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                fieldTypes[i] = fields[i].getType();
            }

            while (rs.next()) {

                Object[] fieldValues = new Object[fields.length];

                for (int i = 0; i < fields.length; i++) {
                    String propertyName = fields[i].getName();
                    Object v = rs.getObject(propertyName);

                    fieldValues[i] = v;
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

    public boolean checkIfExist(String table, String column, Object value) {

        Boolean record_exist = false;

        try {

            String query = "SELECT COUNT(*) FROM " + table + " WHERE " + column + "=?";

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setObject(1, value);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                record_exist = count > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return record_exist;
    }
}