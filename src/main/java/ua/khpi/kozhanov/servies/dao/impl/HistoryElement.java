package ua.khpi.kozhanov.servies.dao.impl;

import java.util.Date;

/**
 * Created by PC on 17.12.2017.
 */
public class HistoryElement {
    public int id;
    public int idUser;
    public int price;
    public Date time;
    public int idProduct;
    public String name;
    public String description;
    public String image;
    public boolean  assessment;

    public HistoryElement(int id, int idUser, int price, Date time, int idProduct, String name, String description, String image, boolean assessment) {
        this.id = id;
        this.idUser = idUser;
        this.price = price;
        this.time = time;
        this.idProduct = idProduct;
        this.name = name;
        this.description = description;
        this.image = image;
        this.assessment = assessment;
    }
}
