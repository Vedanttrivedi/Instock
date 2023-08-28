package jee.lab03;

import jee.lab03.model.ItemTo;
import java.sql.*;
import java.util.ArrayList;

public class InventoryService {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/inventorymanager";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "root";
    private Connection connection;

    public InventoryService() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException ce) {
            throw new ClassCastException("Driver not found!");
        } catch (SQLException e) {

            throw new SQLException("Error establishing database connection");
        }
    }


    public ItemTo getItem(int itemno) throws ItemNotFound {
        try {
            String query = "SELECT * FROM Item WHERE code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, itemno);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ItemTo item = new ItemTo();
                item.setCode(resultSet.getInt("code"));
                item.setDescription(resultSet.getString("description"));
                item.setStock(resultSet.getInt("stock"));
                item.setMinStock(resultSet.getInt("min_stock"));
                item.setCost(resultSet.getDouble("cost"));
                item.setCatId(resultSet.getInt("cat_id"));
                return item;
            } else {
                throw new ItemNotFound("Item not found with code: " + itemno);
            }
        } catch (SQLException e) {

            throw new ItemNotFound("Error finding item: " + e.getMessage());
        }
    }


    public void addItem(ItemTo item) throws ItemAlreadyExists {
        try {
            String query = "INSERT INTO Item (code, description, stock, min_stock, cost, cat_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, item.getCode());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setInt(3, item.getStock());
            preparedStatement.setInt(4, item.getMinStock());
            preparedStatement.setDouble(5, item.getCost());
            preparedStatement.setInt(6, item.getCatId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            throw new ItemAlreadyExists("Error adding item: " + e.getMessage());
        }
    }


    public void updateItem(ItemTo item) throws ItemNotFound {
        try {
            String query = "UPDATE Item SET description = ?, stock = ?, min_stock = ?, cost = ?, cat_id = ? WHERE code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, item.getDescription());
            preparedStatement.setInt(2, item.getStock());
            preparedStatement.setInt(3, item.getMinStock());
            preparedStatement.setDouble(4, item.getCost());
            preparedStatement.setInt(5, item.getCatId());
            preparedStatement.setInt(6, item.getCode());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new ItemNotFound("Item not found with code: " + item.getCode());
            }
        } catch (SQLException e) {

            throw new ItemNotFound("Error updating item: " + e.getMessage());
        }
    }


    public void addStock(int item_code, int qty) throws ItemNotFound {
        try {
            String query = "UPDATE Item SET stock = stock + ? WHERE code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, qty);
            preparedStatement.setInt(2, item_code);
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new ItemNotFound("Item not found with code: " + item_code);
            }
        } catch (SQLException e) {
            throw new ItemNotFound("Error adding stock: " + e.getMessage());
        }
    }


    public void withdrawStock(int item_code, int qty) throws ItemNotFound, InSufficientStock {
        try {
            String query = "SELECT stock FROM Item WHERE code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, item_code);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int currentStock = resultSet.getInt("stock");
                if (currentStock >= qty) {
                    String updateQuery = "UPDATE Item SET stock = stock - ? WHERE code = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setInt(1, qty);
                    updateStatement.setInt(2, item_code);
                    int rows = updateStatement.executeUpdate();
                    if (rows == 0) {
                        throw new ItemNotFound("Item not found with code: " + item_code);
                    }
                } else {
                    throw new InSufficientStock("Insufficient stock for item: " + item_code);
                }
            } else {
                throw new ItemNotFound("Item not found with code: " + item_code);
            }
        } catch (SQLException e) {

            throw new ItemNotFound("Error withdrawing stock: " + e.getMessage());
        }
    }
    public void deleteItem(int item_code) throws ItemNotFound {
        try {
            String query = "DELETE FROM Item WHERE code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, item_code);
            int rows = preparedStatement.executeUpdate();
            if (rows == 0)
            {
                throw new ItemNotFound("Item not found with code: " + item_code);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new ItemNotFound("Error deleting item: " + e.getMessage());
        }
    }
    public ArrayList<ItemTo> getAllItems() throws SQLException{
        try {
            String query = "SELECT * FROM Item";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<ItemTo> items = new ArrayList<>();
            while (resultSet.next()) {
                ItemTo item = new ItemTo();
                item.setCode(resultSet.getInt("code"));
                item.setDescription(resultSet.getString("description"));
                item.setStock(resultSet.getInt("stock"));
                item.setMinStock(resultSet.getInt("min_stock"));
                item.setCost(resultSet.getDouble("cost"));
                item.setCatId(resultSet.getInt("cat_id"));
                items.add(item);
            }
            return items;
        } catch (SQLException e) {

            throw  new SQLException("sql error!"); // Return an empty list on error
        }
    }
    public ArrayList<ItemTo> getAllCatItems(int cat_id) throws SQLException{
        try {
            String query = "SELECT * FROM Item WHERE cat_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, cat_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<ItemTo> items = new ArrayList<>();
            while (resultSet.next()) {
                ItemTo item = new ItemTo();
                item.setCode(resultSet.getInt("code"));
                item.setDescription(resultSet.getString("description"));
                item.setStock(resultSet.getInt("stock"));
                item.setMinStock(resultSet.getInt("min_stock"));
                item.setCost(resultSet.getDouble("cost"));
                item.setCatId(resultSet.getInt("cat_id"));
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
             throw new SQLException("sql  error");
        }
    }
    public ArrayList<ItemTo> getItemsUnderStock() throws SQLException {
        try {
            String query = "SELECT * FROM Item WHERE stock < min_stock";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<ItemTo> items = new ArrayList<>();
            while (resultSet.next()) {
                ItemTo item = new ItemTo();
                item.setCode(resultSet.getInt("code"));
                item.setDescription(resultSet.getString("description"));
                item.setStock(resultSet.getInt("stock"));
                item.setMinStock(resultSet.getInt("min_stock"));
                item.setCost(resultSet.getDouble("cost"));
                item.setCatId(resultSet.getInt("cat_id"));
                items.add(item);
            }
            return items;
        } catch (SQLException e) {

            throw new SQLException("sql error");
        }
    }
    public double totalInventoryCost() throws  SQLException{
        try {
            String query = "SELECT SUM(cost) AS total_cost FROM Item";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("total_cost");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new SQLException("sql error");
        }
    }
}

