package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.*;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String SELECT_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS_QUERY = "SELECT * FROM users";

    // create
    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // read
    public User read(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(SELECT_USER_QUERY);
            statement.setString(1, String.valueOf(userId));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                User user = new User();
                user.setId(id);
                user.setEmail(email);
                user.setUserName(userName);
                user.setPassword(password);
                return user;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    // update
    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // delete
    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(DELETE_USER_QUERY);
            statement.setString(1, String.valueOf(userId));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // find all
    public User[] findAll() {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(SELECT_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            User[] users = new User[0];
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                User user = new User();
                user.setId(id);
                user.setEmail(email);
                user.setUserName(userName);
                user.setPassword(password);
                User[] usersArray = new User[users.length + 1];
                System.arraycopy(users, 0, usersArray, 0, users.length);
                usersArray[users.length] = user;
                users = usersArray;
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
