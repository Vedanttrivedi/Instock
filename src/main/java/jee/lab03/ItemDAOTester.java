package jee.lab03;

import jee.lab03.model.ItemTo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOTester
{
    private static void printItemData(ArrayList<ItemTo> items)
    {
        for(ItemTo item : items)
        {
            System.out.println(item);
        }
    }

    public static void main(String[] args)
    {
       try
       {
           ItemDAOSQLImp itemDAO = new ItemDAOSQLImp();


           itemDAO.add(new ItemTo(11,"Cricket Helmets",20,5,500,1));
           itemDAO.add(new ItemTo(12,"Iron",15,7,7000,2));
           itemDAO.add(new ItemTo(13,"jackets",30,20,800,3));
           itemDAO.add(new ItemTo(14,"tennis racket",8,2,600,1));
           itemDAO.add(new ItemTo(15,"Toaster",5,2,4000,2));
           System.out.println("New Items are added in the table");

           itemDAO.update(new ItemTo(15,"Toaster",25,2,4000,2));
           System.out.println("Item updated");

           itemDAO.update(new ItemTo(13,"jackets",25,2,5000,2));
           System.out.println("Item updated");

           itemDAO.delete(14);
           System.out.println("Item Deleted");

           ItemTo myItem = itemDAO.findItem(12);
           System.out.println("My Item is ");
           System.out.println(myItem);


           System.out.println("Getting all the data from item table");
           ArrayList<ItemTo> items = itemDAO.getAll();
           printItemData(items);


           System.out.println("Getting data of 2nd page(Per page rows:7)");
           ArrayList<ItemTo> pagePerItems = itemDAO.getAllPaginated(2);
           printItemData(pagePerItems);
       }
       catch (DAOException de)
       {
          de.printStackTrace();
       }
       catch (ItemNotFound ie)
       {
           ie.printStackTrace();
       }
       catch (SQLException se)
       {
           se.printStackTrace();
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
    }
}
