

import controllers.MenuManageController;

public class App {

    public static void main(String[] args) throws Exception {

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