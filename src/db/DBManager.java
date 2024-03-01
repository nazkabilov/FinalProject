package db;

import model.Category;
import model.Comment;
import model.News;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.DropMode.ON;

public class DBManager {
    private static Connection connection;


    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/FinalProjectJavaEEApp",
                    "postgres",
                    "postgres");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String email) {
        User user = null;

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ? ");
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String password = resultSet.getString("password");
                String fullName = resultSet.getString("fullName");
                String roleId = resultSet.getString("roleId");
                user = new User();
                user.setFullName(fullName);
                user.setPassword(password);
                user.setId(id);
                user.setEmail(email);
                user.setRoleId(roleId);
            }
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static void registerUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO users (email,password,fullName,roleId) " +
                            "VALUES (?,?,?,?) ");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFullName());
            preparedStatement.setString(4, user.getRoleId());
            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addNews(News news) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO news (title,content,categoryId,postDate) " +
                            "VALUES (?,?,?,now()) ");
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getContent());
            preparedStatement.setString(3, String.valueOf(news.getCategory()));
            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<News> getAllNews() {
        List<News> news = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT news.id, news.title, news.content, news.postDate, users.fullName, newsCategories.name " +
                            "FROM news " +
                            "INNER JOIN newsCategories" +
                            "ON news.categoryId = newsCategories.id" +
                            "ORDER BY postDate DESC ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String text = resultSet.getString("content");
                Timestamp postdate = resultSet.getTimestamp("postdate");
                String userFullName = resultSet.getString("fullName");
                String newsCategoriesName = resultSet.getString("name");

                User user = new User();
                user.setFullName(userFullName);


                News news1 = new News();
                news1.setId(id);
                news1.setTitle(title);
                news1.setContent(text);
                news1.setPostDate(postdate);


                news.add(news1);
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return news;
    }

    public static News getNewsById(Long id) {
        News news = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT news.title, news.content, news.postdate, users.fullName, newsCategories.name " +
                    "FROM news " +
                    "INNER JOIN users" +
                    "ON news.userId = users.id" +
                    "INNER JOIN newsCategories " +
                    "ON news.categoryId = newsCategories.id " +
                    "WHERE news.id=? ");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String text = resultSet.getString("content");
                Timestamp postdate = resultSet.getTimestamp("postdate");
                String userFullName = resultSet.getString("fullName");
                String newsCategoriesName = resultSet.getString("name");

                User user = new User();
                user.setFullName(userFullName);


                news = new News();
                news.setId(id);
                news.setTitle(title);
                news.setContent(text);
                news.setPostDate(postdate);

            }
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return news;
    }

    public static void addComment(Comment comment) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO comments (comment,userId,newsId,postDate) " +
                            "VALUES (?,?,?,now()) ");
            preparedStatement.setString(1, comment.getComment());
            preparedStatement.setString(2, String.valueOf(comment.getUser().getId()));
            preparedStatement.setString(3, String.valueOf(comment.getNews().getCategory()));
            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}