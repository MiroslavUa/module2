package model.product;

import java.util.Objects;

public class Television extends Product{
    private double diagonal;
    private String country;

    public Television(String series, ScreenType screenType, double price, double diagonal, String country) {
        super(series, screenType, price);
        this.diagonal = diagonal;
        this.country = country;
    }

    public double getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(double diagonal) {
        this.diagonal = diagonal;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Television)) return false;
        Television that = (Television) o;
        return Double.compare(that.getDiagonal(), getDiagonal()) == 0 && getCountry().equals(that.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDiagonal(), getCountry());
    }

    @Override
    public String toString() {
        return "Television{" +
                "diagonal=" + diagonal +
                ", country='" + country + '\'' +
                ", series='" + series + '\'' +
                ", screenType=" + screenType +
                ", price=" + price +
                '}';
    }
}
