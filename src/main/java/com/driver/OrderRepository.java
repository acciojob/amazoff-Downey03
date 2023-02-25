package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    Map<String,Order> orders ;
    Map<String,DeliveryPartner> partners;
    Map<String,List<Order>> pairs;
    OrderRepository(){
        this.orders = new HashMap<>();
        this.pairs = new HashMap<>();
        this.partners = new HashMap<>();
    }
    public void addOrder(Order order){
        orders.put(order.getId(),order);
    }
    public void addPartner(String partner){

        partners.put(partner,new DeliveryPartner(partner));
    }
    public void addOrderPartnerPair(String orderId,String partnerId){
        if(pairs.containsKey(partnerId)){
            pairs.get(partnerId).add(orders.get(orderId));
        }
        else{
            List<Order> newOrder = new ArrayList<>();
            newOrder.add(orders.get(orderId));
            pairs.put(partnerId,newOrder);
        }
        partners.get(partnerId).setNumberOfOrders(pairs.get(partnerId).size());
    }
    public Order getOrderById(String orderId){
        if(!orders.containsKey(orderId)) return null;
        return orders.get(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
        if(!partners.containsKey(partnerId)) return null;
        return partners.get(partnerId);
    }
    public Integer getOrderCountByPartnerId(String Id){

       //return partners.get(Id).getNumberOfOrders();
        return pairs.get(Id).size();
    }
    public List<String> getOrdersByPartnerId(String Id){
        List<String> orderss = new ArrayList<>();
        for(Order order : pairs.get(Id)){
            orderss.add(order.getId());
        }
        return orderss;
    }
    public List<String> getAllOrders(){
        List<String> orderss = new ArrayList<>();
        for(String id : orders.keySet()){
            orderss.add(id);
        }
        return orderss;
    }
    public Integer getCountOfUnassignedOrder(){
        int cnt=0;
        for (String id: pairs.keySet()) {
            cnt+= pairs.get(id).size();
        }
        return orders.size()-cnt;
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time , String parthnerId){
        int cnt=0;
        String[] timeSTR=time.split(":");
        int Time = (Integer.valueOf(timeSTR[0])*60) * Integer.valueOf(timeSTR[1]);
        for(Order order: pairs.get(parthnerId)){
            if(order.getDeliveryTime()>Time) cnt++;
        }
        return cnt;
    }
    public String getLastDeliveryTimeByPartnerId(String parthnerId){
        return String.valueOf(pairs.get(parthnerId).get(pairs.get(parthnerId).size()-1).getDeliveryTime());
    }
    public void deletePartnerById(String partnerId){
        pairs.remove(partnerId);
        partners.remove(partnerId);
    }
    public void deleteOrderById(String orderId){
        for(String id : pairs.keySet()){
            if(pairs.get(id).contains(orderId)){
                pairs.get(id).remove(orderId);
            }
        }
        orders.remove(orderId);
    }
}
