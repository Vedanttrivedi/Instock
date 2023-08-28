package jee.lab03;


import java.sql.*;

import java.sql.*;
//update the password System.getenv("PS_SQL_PASSWORD");
public class Dbcreator {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/inventorymanager";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "root";

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            //createTables(connection);
            /*add the categories

            insertCategory(connection,"Sports");
            insertCategory(connection,"Electronics");
            insertCategory(connection,"Apparel");*/
            //insert the items

            insertItem(connection,"Cricket Bat",10,5,5000,1);
            insertItem(connection,"Badminton Racket",20,3,1000,1);
            insertItem(connection,"Cricket Ball",30,5,700,1);
            insertItem(connection,"Football",36,4,3000,1);

            insertItem(connection,"Air Conditioner",5,2,50000,2);
            insertItem(connection,"Washing machine",10,6,20000,2);
            insertItem(connection,"Laptop",40,20,70000,2);
            insertItem(connection,"Shirts",20,10,1500,3);
            insertItem(connection,"Pants",30,7,700,3);
            insertItem(connection,"TShirts",10,8,1000,3);


            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        String createCategoryTable = "CREATE TABLE Category (" +
                "cat_id serial PRIMARY KEY," +
                "cat_name varchar(50) NOT NULL" +
                ")";

        String createItemTable = "CREATE TABLE Item (" +
                "code serial PRIMARY KEY," +
                "description varchar(50) NOT NULL," +
                "stock int," +
                "min_stock int," +
                "cost decimal(8, 2)," +
                "cat_id int REFERENCES Category(cat_id)" +
                ")";

        Statement statement = connection.createStatement();
        statement.execute(createCategoryTable);
        statement.execute(createItemTable);
        statement.close();
    }

    private static void insertCategory(Connection connection, String categoryName) {
        String insertQuery = "INSERT INTO Category (cat_name) VALUES (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, categoryName);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Category inserted successfully: " + categoryName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertItem(Connection connection, String description, int stock, int minStock, double cost, int categoryId) {
        String insertQuery = "INSERT INTO Item (description, stock, min_stock, cost, cat_id) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, description);
            preparedStatement.setInt(2, stock);
            preparedStatement.setInt(3, minStock);
            preparedStatement.setDouble(4, cost);
            preparedStatement.setInt(5, categoryId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateItemDescription(Connection connection, int itemCode, String newDescription) {
        String updateQuery = "UPDATE Item SET description = ? WHERE code = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, newDescription);
            preparedStatement.setInt(2, itemCode);
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (rowsAffected > 0) {
                System.out.println("Item description updated successfully for code " + itemCode);
            } else {
                System.out.println("No item found with code " + itemCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteItem(Connection connection, int itemCode) {
        String deleteQuery = "DELETE FROM Item WHERE code = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, itemCode);
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (rowsAffected > 0) {
                System.out.println("Item deleted successfully with code " + itemCode);
            } else {
                System.out.println("No item found with code " + itemCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
