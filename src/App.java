import controllers.MenuManageController;
import models.Book;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class App {

    public static void main(String[] args) throws Exception {
        MenuManageController menuManageController = new MenuManageController();
        menuManageController.start();
        // Class c = Book.class;
        // Field[] fields = c.getDeclaredFields();

        // for (Field field : fields) {
        //     String Name = field.getName();
        //     String Type = field.getType().getName();
        //     Integer i = field.getModifiers();
        //     String modifier = Modifier.toString(i);
        //     System.out.println("==============");
        //     System.out.println("Name :" + Name);
        //     System.out.println("Type :" + Type);
        //     System.out.println("Modifier :" + modifier);
        //     System.out.println("==============");

        // }
    }
}
