package jee.lab03;


import jee.lab03.model.ItemTo;

import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryTester {
    private static void printItemData(ArrayList<ItemTo> items)
    {
        for(ItemTo item : items)
        {
            System.out.println(item);
        }
    }
    public static void main(String[] args) {
        try {
            InventoryService inventoryService = new InventoryService();


            inventoryService.addItem(new ItemTo(16,"Basketball",30,25,2500,1));
            inventoryService.addItem(new ItemTo(17,"Airpods",20,7,25000,2));
            inventoryService.addItem(new ItemTo(18,"Suits",12,5,5000,3));


            ItemTo updatedItem = new ItemTo(17, "Apple Airpods", 150, 30, 28000, 2);
            inventoryService.updateItem(updatedItem);
            System.out.println("Item Updated");

            inventoryService.addStock(18, 50);
            System.out.println("New stock added");

            inventoryService.withdrawStock(16, 3);
            System.out.println("Withdrew some stock");

            ItemTo retrievedItem = inventoryService.getItem(1);
            System.out.println("Retrieved Item: " + retrievedItem);

            // Get all items
            System.out.println("All Items:");
            ArrayList<ItemTo> allItems  = inventoryService.getAllItems();
            printItemData(allItems);


            System.out.println("Items Under Stock:");
            ArrayList<ItemTo> stockItems  = inventoryService.getItemsUnderStock();
            printItemData(stockItems);


            double totalCost = inventoryService.totalInventoryCost();
            System.out.println("Total Inventory Cost: " + totalCost);
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

