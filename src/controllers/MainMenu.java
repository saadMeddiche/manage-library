package controllers;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import helpers.helper;
import views.View;

public class MainMenu extends Menu {

    @Override
    protected String[] options() {

        Class<?>[] models = findModels();

        String[] options = new String[models.length + 1];

        int index = 0;

        for (Class<?> m : models) {

            options[index] = "Manage " + m.getSimpleName();

            index++;
        }

        options[index] = "Exist";

        return options;
    }

    @Override
    public void excuteChoice(int choice) {
        try {
            // If Choice is Out Of Range
            if (choice < 0 || choice > options().length - 1) {
                helper.clearConsole();
                System.out.println("Invalid choice.");
                helper.stopProgramUntilButtonIsCliqued();
                return;
            }

            // If user choosed Exit
            if (options().length - 1 == choice) {
                System.out.println("Lay3awen");
                System.exit(0);
            }

            Class<?>[] classes = findModels();
            Class<?> c = classes[choice];

            InputStream input = new FileInputStream("src/config/view.properties");
            Properties prop = new Properties();
            prop.load(input);

            String viewClassName = prop.getProperty(c.getSimpleName());

            if (viewClassName != null) {

                Class<?> viewClass = Class.forName(viewClassName);

                View view = (View) viewClass.getDeclaredConstructor().newInstance();

                view.start();

                return;
            }

            View view = new View(c);
            
            view.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
