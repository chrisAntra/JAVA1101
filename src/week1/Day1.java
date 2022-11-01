package week1;

import java.util.Comparator;
import java.util.*;

public class Day1 {
    static void method1(){}
    public static void main(String[] args) {
        Comparator<Integer> comparator = (e1, e2)->{return e1-e2;};
//        Comparator<Integer> cc = new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return 0;
//            }
//        };
        System.out.println(comparator.compare(4,5));
        Day1 da1 = new Day1();
        //Comparator<Integer> c2 = new Comparator<Integer>();
        //object   primitive int i=1
        //reference variable
        //upcasting:
        //            rv
        List<Integer> list = new ArrayList<>();
        list = new LinkedList<>();


        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.add(0,2);
        System.out.println(linkedList);


        P p = new C();
        p= new P();
        p.print();
        linkedList.addLast(1);
//        list.addLast(1);


        //static method can be directly use from the class/interface, no need any instance
        Interface1.method4();
    }
}
class P{

    private int i=1;

    public int getI() {
        return i;
    }

    public void setI(int i) {

        this.i = i;
    }

    P(){}
    P(int i){}
    static void method1(){}
    void print(){
        System.out.println("in the P");
    }
    void methodOfP(){}
}
class C extends P{

    //void print()
    @Override
    void print(){
        //System.out.println(i);
        System.out.println("in the C");
    }
//    C(){
//        //super();
//    }

    int dummyOfC(int i){return 1;};
    void dummyOfC(int i, int j){};

}

/**
 * 1. OOP object oriented programming
 *      class(attribute, method)           instance
 *      blue print
 *
 *      1) polymorphism:
 *          1.upcasting
 *          2.method overloading (compile)
 *              happen in the same class
 *              methods have same name, but with different input argument
 *          3.method overriding (runtime)
 *              in parent has method
 *              in child of p, override method
 *
 *      2) inheritance
 *          reuse code
 *          extends/ implements
 *          java not support multiple inheritance
 *      3) abstraction
 *          hide detail
 *          abstract class/ interface
 *          diff:
 *           can                 cannot have constructor
 *           cannot mul inh         can
 *          same:
 *              cannot instantiate instance
 *      4) encapsulation
 *          access modifier:
 *              public
 *              protected
 *              (default)
 *              private: combine getter setter
 *
 *
 *
 */
abstract class AbstractClass implements Interface1, Interface2{
    abstract void m1();
    void m2(){}
}
interface Interface2{}
interface Interface1{
    void method1(int i);
    default void method2(){
        method3();
    }
    //private cannot be inherited
    private void method3(){}
    static void method4(){
        System.out.println("1");
    }
}
/**
 * 2.static
 *      method/attribute/inner class directly belongs to the class
 *      cannot be inherited
 *
 */
class Test1 {
    {
        System.out.println("in side non static");
    }
    void method2(){}
    void method1(){
        this.method2();
        System.out.println(this);
    }
    static {

        System.out.println("in side static");
    }
}
class Test2{
    public static void main(String[] args) throws Exception{
        Class clz=Class.forName("week1.Test1");
        Test1 t1 = new Test1();
        Test1 t2 = new Test1();
    }
}

/**
 * 3. variable passing by value (primitive(value itself) object(reference value/address))
 */

class Test3{
    public static void main(String[] args) {
        int i=1;

        char c;
        long l;
        int j=i;
        //integer pool (-128 ~127)
        Integer integer =-128;
        Integer integer1 =-128;
        Integer integer2 =0;
        //OBJ(-128) OBJ(0)
        Long long1 = 1l;
        Long long2 = 1l;
//        System.out.println(integer==integer2);
        System.out.println(long1.equals(long2));
    }

}
class Student {
    int age=0;
}

class Test4{
    static int i=0;
    //T4.i(0) -> i(0)
    static void changeValue(int i) {
//        Test4.i=20;
        //i(0)
        i=10;
        //i(10)
    }
    // student(@11)  -> s(@11)
    static void changeAge(Student s) {
        //s(@11)
        s=new Student();
        //s(@12)
        s.age=10;
    }

    public static void main(String[] args) {
        //list(@1)
        final List<Integer> list = new LinkedList<>();
        //list(@2)
        list.add(1);
        list.add(2);
        System.out.println(list);
        //
//        Student student = new Student();
//        System.out.println(student.age);
//        changeAge(student);
//        System.out.println(student.age);
        System.out.println(Test4.i);
        changeValue(i);
        System.out.println(i);
    }
}
/**
 * 4. final
 *      class cannot inherit
 *      method cannot override
 *      variable change value
 */