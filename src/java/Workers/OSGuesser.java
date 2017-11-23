 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Workers;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.nmap4j.Nmap4j;
import org.nmap4j.core.flags.Flag;
import org.nmap4j.parser.NMapRunHandlerImpl;
import util.HostDetails;
import util.Network;

/**
 *
 * @author Fluffy
 */
public class OSGuesser extends Thread implements Runnable{
    
    private String ip;

    public OSGuesser(String ip) {
        this.ip = ip;
    }

    
    
    @Override
    public void run() {
         try{
             super.run(); //To change body of generated methods, choose Tools | Templates.
             Nmap4j nmap4j = new Nmap4j( "C:/Program Files (x86)/Nmap" ) ;
             NMapRunHandlerImpl h=new NMapRunHandlerImpl();
             
             //nmap4j.includeHosts(" -T4 -A -Pn -oX - 192.168.1.7");
             nmap4j.includeHosts(ip) ;
             nmap4j.addFlags(Flag.INSANE_TIMING.toString()+" "+Flag.A_FLAG.toString()+" "+Flag.TREAT_HOSTS_AS_ONLINE.toString());
             
             System.out.println("Start Execution");
             try{
                 nmap4j.execute() ;
             }catch(Exception e){
                 System.out.println("error execute");
                 
             }
             System.out.println("Execution Completed ");
             String jsonstr=null;
             try {
                 JSONObject xmlJSONObj = XML.toJSONObject(nmap4j.getOutput());
                 jsonstr = xmlJSONObj.toString(4);
                 System.out.println(jsonstr);
             } catch (JSONException je) {
                 System.out.println(je.toString());
             }
             
             
             //use json to get data
             JSONParser parser=new JSONParser();
             
             JSONObject json=(JSONObject)parser.parse(jsonstr);
             JSONObject os=((JSONObject) ((JSONArray) ( (JSONObject) ((JSONObject) ((JSONObject)json.get("nmaprun")).get("host")).get("os") ).get("osmatch") ).get(0) );
             JSONObject uptime= (JSONObject) ((JSONObject) ((JSONObject)json.get("nmaprun")).get("host")).get("uptime");
             System.out.println( uptime.toString() );
             System.out.println( os.toString());
             HostDetails hd=Network.getHost(ip);
             hd.setTimeDetails(uptime.getInt("seconds"), uptime.getString("lastboot"));
             hd.setOSDetails(os.getString("name"),os.getInt("accuracy"));
             
             
         }catch(ParseException ex){
            Logger.getLogger(OSGuesser.class.getName()).log(Level.SEVERE, null, ex);

        } catch (JSONException ex) {
            Logger.getLogger(OSGuesser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}
