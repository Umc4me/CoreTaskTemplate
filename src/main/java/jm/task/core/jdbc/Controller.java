package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Controller {
    public void run() {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Mike", "Wazowski", (byte) 19);
        userService.saveUser("Lightning", "Mcqueen",  (byte) 95);
        userService.saveUser("Bob", "Dylan", (byte) 80);
        userService.saveUser("Kurt", "Cobain", (byte) 27);

        List<User> listOfUsers = userService.getAllUsers();
        System.out.println(listOfUsers);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
