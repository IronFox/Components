/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;


@Retention(RetentionPolicy.RUNTIME)
@interface Visibility
{
    public int value();
    
}
@Retention(RetentionPolicy.RUNTIME)
@interface Hint
{
    public String value();
    
}

@Retention(RetentionPolicy.RUNTIME)
@interface PrintValue {}

class TestClass
{
    @Visibility(1)
    public int a = -1;
    
    @Visibility(2)
    public int b = -2;
    
    @Visibility(3)
    public int c = -3;
    
    @Visibility(4)
    @PrintValue
    public int d = -4;
    
    @Visibility(5)
    @Hint("No Value")
    public int e = -5;
    
    @Visibility(6)
    @PrintValue
    public int f = -6;
}


/**
 *
 * @author IronFox
 */
public class Annotations {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        TestClass cl = new TestClass();
        try {
            Field[] fields = TestClass.class.getFields();
            for (Field f: fields)
            {
                if (f.isAnnotationPresent(Visibility.class) && f.getAnnotation(Visibility.class).value() > 3)
                {
                
                    System.out.print(f.getName());
                    if (f.isAnnotationPresent(PrintValue.class))
                    {
                        System.out.print(" ="+f.get(cl));
                    
                    }
                    if (f.isAnnotationPresent(Hint.class))
                    {
                        System.out.print(" ("+f.getAnnotation(Hint.class).value()+")");
                    
                    }
                    System.out.println();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Annotations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
