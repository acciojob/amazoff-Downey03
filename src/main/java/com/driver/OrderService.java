package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
   // @Autowired
    OrderRepository repository = new OrderRepository()z;
    public void addOrder(Order order){
        repository.addOrder(order);
    }
    public void addPartner(String partner){
        repository.addPartner(partner);
    }
    public void addOrderPartnerPair(String orderId,String partnerId){
        repository.addOrderPartnerPair(orderId, partnerId);
    }
    public Order getOrderById(String orderId){
        return repository.getOrderById(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
        return repository.getPartnerById(partnerId);
    }
    public Integer getOrderCountByPartnerId(String Id){
        return repository.getOrderCountByPartnerId(Id);
    }
    public List<String> getOrdersByPartnerId(String Id){
        return repository.getOrdersByPartnerId(Id);
    }
    public List<String> getAllOrders(){
        return repository.getAllOrders();
    }
    public Integer getCountOfUnassignedOrder(){
        return repository.getCountOfUnassignedOrder();
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time , String parthnerId){
        return repository.getOrdersLeftAfterGivenTimeByPartnerId(time,parthnerId);
    }
    public String getLastDeliveryTimeByPartnerId(String parthnerId){
        return repository.getLastDeliveryTimeByPartnerId(parthnerId);
    }
    public void deletePartnerById(String partnerId){
        repository.deletePartnerById(partnerId);
    }
    public void deleteOrderById(String orderId){
        repository.deleteOrderById(orderId);
    }
}
