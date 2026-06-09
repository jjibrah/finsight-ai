// This code acts as a database table called Holding
package com.finsight.finsight_ai.model;

//JPA tool to map classes to a database Table
import jakarta.persistence.*;

//create the database table
@Entity
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    //create the rows in the table
    // | id | symbol | quantity | buyPrice
    private Long id;
    private String symbol;
    private double quantity;
    private double buyPrice;

    //create object automatically when reading the database
    public Holding () {}

    //Constructor with values
    public Holding(String symbol, double quantity, double buyPrice){
        this.symbol=symbol;
        this.quantity=quantity;
        this.buyPrice=buyPrice;
    }

    //write the getter functions to read data
    public Long getId(){
        return id;
    }
    public String getsymbol(){
        return symbol;
    }

    public double getQuantity(){
        return quantity;
    }

    public double getBuyPrice(){
        return buyPrice;
    }

    //Setter functions to allow updating the values
    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public void setQuantity(double quantity){
        this.quantity=quantity;
    }

    public void setBuyPrice(double buyingPrice){
        this.buyPrice=buyingPrice;
    }
}
