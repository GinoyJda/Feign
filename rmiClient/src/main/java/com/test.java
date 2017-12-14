package com;

import com.inter.RmiTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class test {

    /**
     * @param args
     */
    public static void test(String[] args) {
        try {
            System.setSecurityManager(new java.rmi.RMISecurityManager());
            Object o = Naming.lookup("rmi://127.0.0.1:9999/testRmiService");
            RmiTest r = (RmiTest)Naming.lookup("rmi://127.0.0.1:9999/testRmiService");
            r.test("hello world");
            System.out.println(r.test("hello world"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }  

    }
    public static void main(String[] args) {
            ApplicationContext ac = new ClassPathXmlApplicationContext("springRmiClient.xml");
            TestImpl ti = (TestImpl) ac.getBean("TestImpl");
//            System.out.println(ti.getMessage("hello world"));
//            ti.addMonitor();
        ti.getAllMonitor();
//        for(int i = 0 ;i < 100;i++){
//            ti.getAliveMonitor();
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        ti.stopMonitor();





    }

}
