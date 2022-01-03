package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private String name;
    private String lastName;
    private byte age;
    private long id;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Util.getStatement().execute("create table user\n" +
                    "(\n" +
                    "\tid bigint auto_increment,\n" +
                    "\tname VARCHAR(20) not null,\n" +
                    "\tlastName VARCHAR(20) not null,\n" +
                    "\tage int(3) not null,\n" +
                    "\tconstraint table_name_pk\n" +
                    "\t\tprimary key (id)\n" +
                    ");");
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Table already exists");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            Util.getStatement().execute("DROP table user");
        } catch (SQLSyntaxErrorException sqlSyntaxErrorException) {
            System.out.println("There's no such table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Util.getStatement().execute("insert into user(name, lastName, age) " +
                    "values ('" + name + "', '" + lastName + "', " + age + ");");
            System.out.println("User с именем " + name + " был добавлен");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            Util.getStatement().execute("delete from user where id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = Util.getStatement().executeQuery("select * from USER");
            while (resultSet.next()) {
                listOfUsers.add(new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfUsers;
    }

    public void cleanUsersTable() {
        try {
            Util.getStatement().execute("delete from user;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
