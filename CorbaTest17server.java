/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package corbatest17;

import TestModule.*;
import TestModule.TestPackage.DivResult;
import org.omg.CORBA.FloatHolder;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;



class TestImpl extends TestPOA
{

    @Override
    public float minus(float a, float b) {
        return a - b;
    }

    @Override
    public boolean div(float x, float y, FloatHolder result) {
        result.value = 0.0f;
        if (y == 0.0f)
           return false;
        result.value = x / y;
        return true;
    }

    @Override
    public DivResult intDiv(int x, int y) {
        DivResult rs = new DivResult();
        rs.remainder = x % y;
        rs.result = x / y;
        return rs;
    }

    @Override
    public float invSqrt(float x) {
        return 1.0f / (float)Math.sqrt(x);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

/**
 *
 * @author IronFox
 */
public class CorbaTest17 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            java.util.Properties props = new java.util.Properties();
            props.put("org.omg.CORBA.ORBInitialPort","1050");
            ORB orb = ORB.init(args, props);

            // get reference to rootpoa and activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // create servant and register it with the ORB
            TestImpl testImpl = new TestImpl();

            // get object reference from the servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(testImpl);
            Test href = TestHelper.narrow(ref);

            // get the root naming context
            org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");
            // Use NamingContextExt which is part of the Interoperable
            // Naming Service (INS) specification.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // bind the Object Reference in Naming
            String name = "Test";
            NameComponent path[] = ncRef.to_name( name );
            for (NameComponent nc : path)
                System.out.println(nc.id+" ("+nc.kind+")");
            ncRef.rebind(path, href);

            System.out.println("TestServer ready and waiting ...");

            // wait for invocations from clients
            orb.run();
        } 

        catch (Exception e) {
          System.err.println("ERROR: " + e);
          e.printStackTrace(System.out);
        }

        System.out.println("HelloServer Exiting ...");
    }
    
}
