/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Workers;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.HostDetails;
import util.Network;

/**
 *
 * @author Fluffy
 */

//scan port update host details
public class PortScanner extends Thread implements Runnable {

    private String ip;
    public PortScanner(String ip) {
        this.ip=ip;
    }
    
    @Override
    public void run() {
        System.out.println("Port scan startted" );
          final ExecutorService es = Executors.newFixedThreadPool(20);
 
  final int timeout = 200;
  final List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
  for (int port = 1; port <= 1000; port++) {
    futures.add(portIsOpen(es, ip, port, timeout));
  }
  es.shutdown();
  int openPorts = 0;
  for (final Future<Boolean> f : futures) {
              try {
                  if (f.get()) {
                      openPorts++;
                  }         
              } catch (InterruptedException ex) {
                  Logger.getLogger(PortScanner.class.getName()).log(Level.SEVERE, null, ex);
              } catch (ExecutionException ex) {
                  Logger.getLogger(PortScanner.class.getName()).log(Level.SEVERE, null, ex);
              }
  }
  System.out.println("There are " + openPorts + " open ports on host " + ip );
  
    }
    
public static Future<Boolean> portIsOpen(final ExecutorService es, final String ip, final int port, final int timeout) {
  return es.submit(new Callable<Boolean>() {
      @Override public Boolean call() {
          HostDetails hd=Network.getHost(ip);
          System.out.println("used ip:"+ip+":");
        try {
          Socket socket = new Socket();
          socket.connect(new InetSocketAddress(ip, port), timeout);
          socket.close();
          
            hd.portHandler(port, HostDetails.PORT_OPEN);
            
          return true;
        } catch (Exception ex) {
            
            hd.portHandler(port, HostDetails.PORT_CLOSED);
          return false;
        }
      }
   });
}    
    
}
