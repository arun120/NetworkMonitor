package util;


import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fluffy
 */
public class HostDetails {

    private  Map<Integer,String> ports=new HashMap<>();
    private  List<Route> traceroutes=new ArrayList<>();
    private Integer uptime;
    private String bootTime;
    private String OS;
    private String originaliyFactor;
    
}
