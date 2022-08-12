package model.product;

import java.util.Objects;

public class Telephone extends Product{
    private String model;

    public Telephone(String series, ScreenType screenType, double price, String model) {
        super(series, screenType, price);
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Telephone telephone)) return false;
        return getModel().equals(telephone.getModel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModel());
    }

    @Override
    public String toString() {
        return "Telephone{" +
                "model='" + model + '\'' +
                ", series='" + series + '\'' +
                ", screenType=" + screenType +
                ", price=" + price +
                '}';
    }
}
