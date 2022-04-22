package businessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompositeProduct extends MenuItem {

    private List<BaseProduct> items;

    public CompositeProduct(String nume) {
        super(nume);
        items = new ArrayList<>();
    }

    @Override
    public float getRating() {
        return -1;
    }

    @Override
    public float getCalories() {
        return -1;
    }

    @Override
    public float getProtein() {
        return -1;
    }

    @Override
    public float getFat() {
        return -1;
    }

    @Override
    public float getSodium() {
        return -1;
    }

    public float getPrice() {
        return 0;
    }

    public void addItems(BaseProduct item) {
        items.add(item);
    }

    public List<BaseProduct> getItems() {
        return items;
    }

    public boolean contains(MenuItem item) {

        for (MenuItem menuItem : items) {
            if (menuItem.equals(item)) return true;

        }
        return false;
    }

    @Override
    public float computePrice() {
        float price = 0;
        for (MenuItem item : items) {
            price += item.computePrice();
        }
        return price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
