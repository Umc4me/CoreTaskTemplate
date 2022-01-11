package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
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
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("create table user\n" +
                    "(\n" +
                    "\tid bigint auto_increment,\n" +
                    "\tname VARCHAR(20) not null,\n" +
                    "\tlastName VARCHAR(20) not null,\n" +
                    "\tage int(3) not null,\n" +
                    "\tconstraint table_name_pk\n" +
                    "\t\tprimary key (id)\n" +
                    ");");
            connection.close();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Table already exists");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("DROP table user");
            connection.close();
        } catch (SQLSyntaxErrorException sqlSyntaxErrorException) {
            System.out.println("There's no such table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Connection connection = Util.getConnection();
            PreparedStatement pst = connection.prepareStatement("insert into user(name, lastname, age) values (?, ?, ?)");

            pst.setString(1, name);
            pst.setString(2, lastName);
            pst.setInt(3, age);
            pst.executeUpdate();

            System.out.println("User с именем " + name + " был добавлен");

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            Connection connection = Util.getConnection();
            PreparedStatement pst = connection.prepareStatement("DELETE FROM USER where id=?");

            pst.setLong(1, id);
            pst.executeUpdate();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();

        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from USER ");
            while (resultSet.next()) {
                listOfUsers.add(new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                ));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfUsers;
    }

    public void cleanUsersTable() {
        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("delete from user;");
            connection.close();
        } catch (SQLSyntaxErrorException throwables) {
            System.out.println("There is no such table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
