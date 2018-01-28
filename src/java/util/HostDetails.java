package util;


import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlElementDecl;

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

    public static final int PORT_CLOSED=1;
    public static final int PORT_OPEN=2;
    
    public static int getMisMatchvalue(int a){
    return a*200;
    }
    private  Map<Integer,Integer> ports=new HashMap<>();
    private  Map<Integer,String> traceroutes=new HashMap<>();
    private Integer uptime;
    private ThresholdFactor myThreshold=new ThresholdFactor();
    private String bootTime;
    private String OS;
    private Integer originaliyFactor;

    public Map<Integer, String> getTraceroutes() {
        return traceroutes;
    }

    public String getOS() {
        return OS;
    }
    
    public synchronized void   portHandler(Integer port,Integer type){
        if(type==PORT_OPEN)
            System.out.println(port +" is open");
        
        if(ports.get(port)==null){
             ports.put(port, type);
            System.out.println("New Port Added "+ports.size());
             
        return;
        }
        
        if(ports.get(port).equals(type)){
            System.out.println("New Already Exist");
            return;
        }else if(ports.get(port).equals( getMisMatchvalue(type) )){
        myThreshold.decPort();
        }
        else{
        myThreshold.setPort(myThreshold.getPort()*2);
        ports.put(port, getMisMatchvalue(type));
        }
    }

    public Map<Integer, Integer> getPorts() {
        return ports;
    }

    public void setPorts(Map<Integer, Integer> ports) {
        this.ports = ports;
    }

   public void handleRoute(Integer hc,String host) {
    
        
        if(traceroutes.get(hc)==null){
             traceroutes.put(hc, host);
            System.out.println("New Route Added "+traceroutes.size());
             
        return;
        }
        
        if(traceroutes.get(hc).equals(host)){
            System.out.println("New Already Exist");
            //reduce threshold
        myThreshold.decRoute();
        
            return;
        }
        else{
        //TO-DO calculate threshold
        myThreshold.incRoute();
        traceroutes.put(hc, host);
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
            
            myThreshold.decUptime();
            System.out.println("Already Exist Updated uptime");
        }else{
        this.uptime = uptime;
        myThreshold.incUptime();
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
            myThreshold.decOS();
            System.out.println("Already Exist OS");
        }else{
        this.OS=OS;
        //TO-DO calculate threshold
        myThreshold.incOS();
        }
        
        if(originaliyFactor==null){
            originaliyFactor=accuracy;
            System.out.println("New Entry Accurracy");
        }
        else if(originaliyFactor==accuracy){
        originaliyFactor = accuracy;
        //reduce
        myThreshold.incOS();
        System.out.println("Already Exist accuracy");
        }else{
        originaliyFactor=accuracy;
            //TO-DO calculate threshold
            myThreshold.decOS();
        }
    }

   
    public double getTotalThreshold(){
    
    double d=myThreshold.getPort()*Network.GLOBAL_THRESHOLD.getPort();
    d+=myThreshold.getOS()*Network.GLOBAL_THRESHOLD.getOS();
    d+=myThreshold.getRoute()*Network.GLOBAL_THRESHOLD.getRoute();
    d+=myThreshold.getUptime()*Network.GLOBAL_THRESHOLD.getUptime();
    
    return d;
    }
    
    
}
