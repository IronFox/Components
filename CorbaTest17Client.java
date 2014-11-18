/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package corbatest17client;

import TestModule.*;
import TestModule.TestPackage.DivResult;
import org.omg.CORBA.FloatHolder;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/**
 *
 * @author IronFox
 */
public class CorbaTest17Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            // create and initialize the ORB
            java.util.Properties props = new java.util.Properties();
            props.put("org.omg.CORBA.ORBInitialPort","1050");
            ORB orb = ORB.init(args, props);

            // get the root naming context
            org.omg.CORBA.Object objRef = 
               orb.resolve_initial_references("NameService");
            // Use NamingContextExt instead of NamingContext. This is 
            // part of the Interoperable naming Service.  
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // resolve the Object Reference in Naming
            String name = "Test";
            Test testImpl = TestHelper.narrow(ncRef.resolve_str(name));

            System.out.println("Obtained a handle on server object: " + testImpl.toString());
           
            System.out.println(testImpl.invSqrt(25));
            
            FloatHolder rs = new FloatHolder();
            for (int i = 0; i <= 4; i++)
            {
                if (testImpl.div(12,i,rs))
                    System.out.println(rs.value);
                else
                    System.out.println("<no result>");
            }
            try
            {
                DivResult result = testImpl.intDiv(3, 0);
                System.out.println(result.result+"  remainder="+result.remainder);
            }
            catch (Exception e)
            {
                System.out.println("<no result>");
            }

        } catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
        }
    }
    
}
