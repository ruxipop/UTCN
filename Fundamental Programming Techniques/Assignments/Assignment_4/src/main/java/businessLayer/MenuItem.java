package businessLayer;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    private String name;

    public MenuItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract float getRating();

    public abstract float getCalories();

    public abstract float getProtein();

    public abstract float getFat();

    public abstract float getSodium();

    public void setName(String name) {
        this.name = name;
    }

    public abstract float computePrice();


}
