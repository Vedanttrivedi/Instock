package jee.lab03.model;

public class ItemTo {
    private int code;
    private String description;
    private int stock;
    private int minStock;
    private double cost;
    private int catId;

    public ItemTo() {
        // Default constructor
    }

    public ItemTo(int code, String description, int stock, int minStock, double cost, int catId) {
        this.code = code;
        this.description = description;
        this.stock = stock;
        this.minStock = minStock;
        this.cost = cost;
        this.catId = catId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }
    @Override
    public String toString() {
        return "ItemTo{" +
                "code=" + code +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", minStock=" + minStock +
                ", cost=" + cost +
                ", catId=" + catId +
                '}';
    }
}
