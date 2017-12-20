package ua.khpi.kozhanov.servies.dao.impl;

import ua.khpi.kozhanov.servies.dao.ProductDao;

/**
 * Created by PC on 10.12.2017.
 */
public class ProductDaoImpl implements ProductDao {
    private int id;
    private String name;
    private String description;
    private int popular;
    private int price;
    private int discount;
    private String imagePath;

    public ProductDaoImpl(int id, String name, String description, int popular, int price, int discount, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.popular = popular;
        this.price = price;
        this.imagePath = imagePath;
        this.discount = discount;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getPopular() {
        return popular;
    }

    public int getId() {
        return id;
    }

    public int getDiscount() {
        return discount;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }
}
