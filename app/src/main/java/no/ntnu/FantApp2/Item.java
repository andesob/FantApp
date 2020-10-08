package no.ntnu.FantApp2;

public class Item {
    long id;
    int price;
    String title;
    String description;
    User seller;
    boolean bought;

    public Item(long id, int price, String title, String description, User seller, boolean bought) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.description = description;
        this.seller = seller;
        this.bought = bought;
    }

    @Override
    public String toString() {
        return "Id=" + id +
                ", Price=" + price +
                ", Title='" + title + '\'' +
                ", Description='" + description + '\'' +
                ", Sellerid=" + seller +
                ", Bought=" + bought;
    }

    public long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getSeller() {
        return seller;
    }

    public boolean isBought() {
        return bought;
    }
}
