package com.nest5.businessClient;



public class ReceivedObject {


    private long sync_id;
    private int quantity;



    public ReceivedObject() {
    }

    public ReceivedObject(Long id) {
        this.sync_id = id;
    }

    public ReceivedObject(Long sync_id, int qunatity) {
        this.sync_id = sync_id;
        this.quantity = qunatity;
        
    }



    public Long getSyncId() {
        return sync_id;
    }

    public void setSyncId(Long id) {
        this.sync_id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int qunatity) {
        this.quantity = qunatity;
    }

}
