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

    public static final int PORT_CLOSED=0;
    public static final int PORT_OPEN=1;
    
    private  Map<Integer,Integer> ports=new HashMap<>();
    private  Map<Integer,String> traceroutes=new HashMap<>();
    private Integer uptime;
    private String bootTime;
    private String OS;
    private Integer originaliyFactor;
    
    public synchronized void   portHandler(Integer port,Integer type){
        if(type==PORT_OPEN)
            System.out.println(port +" is open");
        
        if(ports.get(port)==null){
             ports.put(port, type);
            System.out.println("New Port Added "+ports.size());
             
        return;
        }
        
        if(ports.get(port)==type){
            System.out.println("New Already Exist");
            return;
        }
        else{
        //TO-DO calculate threshold
        ports.put(port, type);
        }
    }

    public Map<Integer, Integer> getPorts() {
        return ports;
    }

    public void setPorts(Map<Integer, Integer> ports) {
        this.ports = ports;
    }

   public void handleRoute(Integer ip,String host) {
    
        
        if(traceroutes.get(ip)==null){
             traceroutes.put(ip, host);
            System.out.println("New Route Added "+traceroutes.size());
             
        return;
        }
        
        if(traceroutes.get(ip)==host){
            System.out.println("New Already Exist");
            return;
        }
        else{
        //TO-DO calculate threshold
        traceroutes.put(ip, host);
        }
    }

    public Integer getUptime() {
        return uptime;
    }

    public void setTimeDetails(Integer uptime,String bootTime) {
        
        if(this.uptime==null){
        this.uptime = uptime;
        System.out.println("New Entry uptime");
        }
        else if(this.uptime < uptime){
            this.uptime = uptime;
            System.out.println("Already Exist Updated uptime");
        }else{
        this.uptime = uptime;
        
        //TO-DO calculate threshold
        }
        
        
        if(this.bootTime==null){
        this.bootTime = bootTime;
        System.out.println("New Entry boot time");
        }else if(this.bootTime.equals(bootTime)){
            System.out.println("Already Exist Updated boot time");
        }else{
            //Do nothing only for display
            this.bootTime = bootTime;
           
        }
    }

    

   

    public void setOSDetails(String OS,Integer accuracy ) {
        
        if(this.OS==null){
        this.OS = OS;
        System.out.println("New Entry OS");
        }else if(this.OS.equals(OS)){
        System.out.println("Already Exist OS");
        }else{
        this.OS=OS;
        //TO-DO calculate threshold
        }
        
        if(originaliyFactor==null){
            originaliyFactor=accuracy;
            System.out.println("New Entry Accurracy");
        }
        else if(originaliyFactor==originaliyFactor){
        originaliyFactor = accuracy;
        System.out.println("Already Exist accuracy");
        }else{
        originaliyFactor=accuracy;
            //TO-DO calculate threshold
        }
    }

   
    
    
    
}
