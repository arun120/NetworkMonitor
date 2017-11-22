package util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fluffy
 */
public class Route {
    
    private Integer hop;
    private String ip;

    public Route(Integer hop,String ip){
    this.hop=hop;
    this.ip=ip;
    }
    public Integer getHop() {
        return hop;
    }

    public void setHop(Integer hop) {
        this.hop = hop;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    
    
}
