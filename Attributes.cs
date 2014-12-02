using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;



//[System.AttributeUsage(System.AttributeTargets.All)]




public class Hint : System.Attribute
{
    private string _value;

    public Hint(string value)
    {
        _value = value;
    }

    public string Get()
    {
        return _value;
    }
}


public class Visibility : System.Attribute
{
    private int _value;

    public Visibility(int value)
    {
        _value = value;
    }

    public int Get()
    {
        return _value;
    }
}

public class PrintValue : System.Attribute
{
    public PrintValue()
    {
    }
}


class Test
{
    [Visibility(1)][PrintValue][Hint("Least Visible")]
    public int a = -1;
    [Visibility(2)]
    public int b = -2;
    [Visibility(3)]
    [PrintValue]
    public int c = -3;
    [Visibility(4)]
    public int d = -4;
    [Visibility(5)]
    [Hint("Something to note")]
    public int e = -5;
    [PrintValue]
    [Visibility(6)]
    public int f = -6;
}


namespace ConsoleApplication1
{
    class Program
    {
        static void Main(string[] args)
        {
            Test t = new Test();
            var fields = t.GetType().GetFields();
            int minVisibility = 4;
            foreach (var f in fields)
            {
                Visibility v = (Visibility)Attribute.GetCustomAttribute(f,typeof(Visibility));
                bool printValue = Attribute.GetCustomAttribute(f, typeof(PrintValue)) != null;
                if (v != null && v.Get() >= minVisibility)
                {
                    Console.Out.Write(f.Name + " (" + v.Get() + ")");
                    if (printValue)
                        Console.Out.Write(" = "+f.GetValue(t));

                    Hint h = (Hint)Attribute.GetCustomAttribute(f, typeof(Hint));
                    if (h != null)
                        Console.Out.Write("('" + h.Get() + "')");
                    Console.Out.WriteLine();
                }
            }
        }
    }
}
