package com.nest5.businessClient;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;




import com.google.gson.JsonObject;
public class SaleObject {

    private Long id;
    private long syncId;
    private Integer isDelivery;
    private Integer isTogo;
    private Integer tip;
    private Integer number;
    private String method;
    private Double received;
    private Double discount;
    private java.util.Date date;
    private ReceivedObject[] ingredients;
    private ReceivedObject[] products;
    private ReceivedObject[] combos;



    public SaleObject() {
    }

    public SaleObject(Long id) {
        this.id = id;
    }

    public SaleObject(Long id, long syncId, Integer isDelivery, Integer isTogo, Integer tip, Integer number, String method, Double received, Double discount, String date,ReceivedObject[] ingredients,ReceivedObject[] products,ReceivedObject[] combos) {
        this.id = id;
        this.syncId = syncId;
        this.isDelivery = isDelivery;
        this.isTogo = isTogo;
        this.tip = tip;
        this.number = number;
        this.method = method;
        this.received = received;
        DateTimeFormatter parser = ISODateTimeFormat.dateTime();
        DateTime dt = parser.parseDateTime(date);
        this.date = dt.toDate();
        this.discount = discount;
        this.ingredients = ingredients;
        this.products = products;
        this.combos = combos;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSyncId() {
        return syncId;
    }

    public void setSyncId(long syncId) {
        this.syncId = syncId;
    }

    public Integer getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Integer isDelivery) {
        this.isDelivery = isDelivery;
    }

    public Integer getIsTogo() {
        return isTogo;
    }

    public void setIsTogo(Integer isTogo) {
        this.isTogo = isTogo;
    }

    public Integer getTip() {
        return tip;
    }

    public void setTip(Integer tip) {
        this.tip = tip;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Double getReceived() {
        return received;
    }

    public void setReceived(Double received) {
        this.received = received;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(String date) {
    	DateTimeFormatter parser = ISODateTimeFormat.dateTime();
        DateTime dt = parser.parseDateTime(date);
        this.date = dt.toDate();
    }
    
    public ReceivedObject[] getIngredients(){
    	return this.ingredients;
    }
    
    public void setIngredients(ReceivedObject[] ingredients){
    	this.ingredients = ingredients;
    }
    
    public ReceivedObject[] getProducts(){
    	return this.products;
    }
    
    public void setProducts(ReceivedObject[] products){
    	this.products = products;
    }
    
    public ReceivedObject[] getCombos(){
    	return this.combos;
    }
    
    public void setCombos(ReceivedObject[] combos){
    	this.combos = combos;
    }

}
