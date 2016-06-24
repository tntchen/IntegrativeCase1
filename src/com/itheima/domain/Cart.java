package com.itheima.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * 购物车实体
 */
public class Cart {
    // 存放购物车项的集合,key为商品id,使用map便于删除单个购物项
    private Map<String, CartItem> map = new LinkedHashMap<>();
    // 总金额
    private Double total = 0.0;
    
    //获取所有的购物车项
    public Collection<CartItem> getItems() {
        return map.values();
    }
    
    // 添加到购物车
    public void add2Cart(CartItem item) {
        // 先判断购物车中有无该商品
        // 获取商品id
        String pid = item.getProduct().getPid();
        if (map.containsKey(pid)) {
            // 有,设置购买数量,(商品之前购买数量+现在购买数量)
            CartItem oItem = map.get(pid);
            oItem.setCount(oItem.getCount() + item.getCount());
        } else {
            // 没有,将现在购买数量添加进去
            map.put(pid, item);
        }
        // 计算总计
        total += item.getSubtotal();
    }

    // 从购物车删除
    public void removeFromCart(String pid) {
        // 从集合中删除
        CartItem item = map.remove(pid);
        // 修改金额
        total -= item.getSubtotal();
    }

    // 清空购物车
    public void clearCart() {
        // map清空
        map.clear();
        // 金额归零
        total = 0.0;
    }

    public Map<String, CartItem> getMap() {
        return map;
    }

    public void setMap(Map<String, CartItem> map) {
        this.map = map;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}
