package jee.lab03;


import jee.lab03.model.ItemTo;

import java.sql.*;
import java.util.ArrayList;

public class  ItemDAOSQLImp
{
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/inventorymanager";
    private static final String DB_USER = System.getenv("PS_SQL_USER");
    private static final String DB_PASSWORD = System.getenv("PS_SQL_PASSWORD");
    private Connection connection;

    public ItemDAOSQLImp() throws ItemNotFound,DAOException
    {
        try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e)
        {
            throw new ClassCastException("error in finding driver class");
        }
        try
        {
             connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        catch (SQLException e) {
            throw new DAOException("error in establishing database connection");
        }
    }

    public void add(ItemTo item) throws DAOException
    {
        String query = "INSERT INTO Item (description, stock, min_stock, cost, cat_id) VALUES (?, ?, ?, ?, ?)";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, item.getDescription());
            preparedStatement.setInt(2, item.getStock());
            preparedStatement.setInt(3, item.getMinStock());
            preparedStatement.setDouble(4, item.getCost());
            preparedStatement.setInt(5, item.getCatId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DAOException("Error adding item: " + e.getMessage());
        }
    }
    public void update(ItemTo item) throws ItemNotFound, DAOException
    {
        String query = "UPDATE Item SET description = ?, stock = ?, min_stock = ?, cost = ?, cat_id = ? WHERE code = ?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, item.getDescription());
            preparedStatement.setInt(2, item.getStock());
            preparedStatement.setInt(3, item.getMinStock());
            preparedStatement.setDouble(4, item.getCost());
            preparedStatement.setInt(5, item.getCatId());
            preparedStatement.setInt(6, item.getCode());

            int rows = preparedStatement.executeUpdate();
            if (rows == 0)
            {
                throw new ItemNotFound("Item not found with code: " + item.getCode());
            }
        }
        catch (SQLException e) {
            throw new DAOException("Error updating item: " + e.getMessage());
        }
    }
    public ItemTo findItem(int item_code) throws ItemNotFound, DAOException
    {
        String query = "SELECT * FROM Item WHERE code = ?";

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, item_code);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                ItemTo item = new ItemTo();
                item.setCode(resultSet.getInt("code"));
                item.setDescription(resultSet.getString("description"));
                item.setStock(resultSet.getInt("stock"));
                item.setMinStock(resultSet.getInt("min_stock"));
                item.setCost(resultSet.getDouble("cost"));
                item.setCatId(resultSet.getInt("cat_id"));
                return item;
            }
            else
            {
                throw new ItemNotFound("Item not found with code: " + item_code);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("Error finding item: " + e.getMessage());
        }
    }
    public void delete(int item_code) throws ItemNotFound, DAOException {
        String query = "DELETE FROM Item WHERE code = ?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, item_code);
            int rows = preparedStatement.executeUpdate();

            if (rows == 0)
            {
                throw new ItemNotFound("Item not found with code: " + item_code);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("Error deleting item: " + e.getMessage());
        }
    }

    public ArrayList<ItemTo> getAll() throws DAOException
    {
        String query = "SELECT * FROM Item";
        ArrayList<ItemTo> items = new ArrayList<>();

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                ItemTo item = new ItemTo();
                item.setCode(resultSet.getInt("code"));
                item.setDescription(resultSet.getString("description"));
                item.setStock(resultSet.getInt("stock"));
                item.setMinStock(resultSet.getInt("min_stock"));
                item.setCost(resultSet.getDouble("cost"));
                item.setCatId(resultSet.getInt("cat_id"));
                items.add(item);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("Error fetching items: " + e.getMessage());
        }
        return items;
    }
    public ArrayList<ItemTo> getAllPaginated(int page_no) throws DAOException
    {
        int pageItemCount = 6;
        int offset = (page_no - 1) * pageItemCount;

        String query = "SELECT * FROM Item LIMIT ? OFFSET ?";
        ArrayList<ItemTo> items = new ArrayList<>();

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, pageItemCount);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                ItemTo item = new ItemTo();
                item.setCode(resultSet.getInt("code"));
                item.setDescription(resultSet.getString("description"));
                item.setStock(resultSet.getInt("stock"));
                item.setMinStock(resultSet.getInt("min_stock"));
                item.setCost(resultSet.getDouble("cost"));
                item.setCatId(resultSet.getInt("cat_id"));
                items.add(item);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("Error fetching paginated items: " + e.getMessage());
        }
        return items;
    }
}

