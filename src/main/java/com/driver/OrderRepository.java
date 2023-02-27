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
    Map<String,String> paired;
    Map<String,List<String>> pairs;
    OrderRepository(){
        this.orders = new HashMap<>();
        this.pairs = new HashMap<>();
        this.partners = new HashMap<>();
        this.paired = new HashMap<>();
    }
    public void addOrder(Order order){
        orders.put(order.getId(),order);
    }
    public void addPartner(String partner){

        partners.put(partner,new DeliveryPartner(partner));
    }
    public void addOrderPartnerPair(String orderId,String partnerId){
        if(orders.containsKey(orderId) && partners.containsKey(partnerId)){
            paired.put(orderId,partnerId);
            if(pairs.containsKey(partnerId)){
                pairs.get(partnerId).add(orderId);
            }else{
                List<String> newOrder = new ArrayList<>();
                newOrder.add(orderId);
                pairs.put(partnerId,newOrder);
            }
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
        for(String order: pairs.get(Id)){
            orderss.add(order);
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
        return orders.size()-paired.size();
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time , String parthnerId){
        int cnt=0;
        String[] timeSTR=time.split(":");
        int Time = (Integer.valueOf(timeSTR[0])*60) * Integer.valueOf(timeSTR[1]);
        for(String order: pairs.get(parthnerId)){
            if(orders.get(order).getDeliveryTime()>Time) cnt++;
        }
        return cnt;
    }
    public String getLastDeliveryTimeByPartnerId(String parthnerId){
        int max=0;
        for(String order : pairs.get(parthnerId)){
            max = Math.max(max , orders.get(order).getDeliveryTime());
        }
        String hh = String.valueOf(max/60);
        System.out.println(max+" "+max/60+" "+max%60);
        String mm = String.valueOf(max%60);
        String time = hh+"HH:"+mm+"MM";
        return  time;
    }
    public void deletePartnerById(String partnerId){
        for(String order : pairs.get(partnerId)){
            paired.remove(order);
        }
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
