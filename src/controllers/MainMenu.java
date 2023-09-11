package controllers;

import views.View;

import java.lang.reflect.Method;

import models.*;

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
    protected void excuteChoice(int choice) {

        if (choice >= 0 && choice <= options().length - 1) {

            if (options().length - 1 == choice) {
                System.exit(0);
            } else {
                Class<?>[] classes = findModels();
                Class<?> c = classes[choice];

                View view = new View(c);
                view.start();

            }
        } else {
            System.out.println("Invalid choice.");

        }

    }

}
