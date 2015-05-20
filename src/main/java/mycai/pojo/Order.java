package mycai.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tangld on 2015/5/20.
 */
@Entity
@Table(name = "bill_order")
public class Order {
    @Id
    private int id;
    @Column(name = "userid")
    private String userId;
    @Column(name = "bill")
    private String bill;
    @Column(name = "delivery_ts")
    private String deliveryTs;
    @Column(name = "shop_info")
    private String shopInfo;
    @Column(name = "consignee")
    private String consignee;
    @Column(name = "consignee_contact")
    private String consigneeContact;
    @Column(name = "status")
    private String status;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", bill='" + bill + '\'' +
                ", deliveryTs=" + deliveryTs +
                ", shopInfo='" + shopInfo + '\'' +
                ", consignee='" + consignee + '\'' +
                ", consigneeContact='" + consigneeContact + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getDeliveryTs() {
        return deliveryTs;
    }

    public void setDeliveryTs(String deliveryTs) {
        this.deliveryTs = deliveryTs;
    }

    public String getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(String shopInfo) {
        this.shopInfo = shopInfo;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeContact() {
        return consigneeContact;
    }

    public void setConsigneeContact(String consigneeContact) {
        this.consigneeContact = consigneeContact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
