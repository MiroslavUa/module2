package module.model.product;

public abstract class Product {
    protected String series;
    protected ScreenType screenType;
    protected double price;

    public Product(String series, ScreenType screenType, double price) {
        this.series = series;
        this.screenType = screenType;
        this.price = price;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public ScreenType getScreenType() {
        return screenType;
    }

    public void setScreenType(ScreenType screenType) {
        this.screenType = screenType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
