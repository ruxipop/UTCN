package businessLayer;

import java.util.Objects;

public class BaseProduct extends MenuItem {


    private float rating;
    private float calories;
    private float protein;
    private float fat;
    private float sodium;
    private float price;

    public BaseProduct(String name, float rating, float calories, float protein, float fat, float sodium, float price) {
        super(name);
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    public String getName() {
        return super.getName();
    }

    public float getPrice() {
        return price;
    }

    @Override
    public float getRating() {
        return rating;
    }

    @Override
    public float getCalories() {
        return calories;
    }

    @Override
    public float getProtein() {
        return protein;
    }

    @Override
    public float getFat() {
        return fat;
    }

    @Override
    public float getSodium() {
        return sodium;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public float computePrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o && this.hashCode() == o.hashCode()) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseProduct that = (BaseProduct) o;
        return ((BaseProduct) o).getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), price, rating, calories, protein, fat, sodium);

    }


}
