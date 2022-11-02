package week1;

import java.util.*;

public class Day2 {
}
/**
 *
 *
 * 1. immutable class
 *      String
 *      wrapper: Integer, Long...
 *      Threadsafe
 *      key for hashmap
 */
class Test5{
    public static void main(String[] args) {
        //Class.forName("week1.Day2");
        Integer i1 = 1;
        //Integer(1)
        //Integer(2)
        i1= 2;
        //"abc"
        String s ="abc";
        //"d"
        //"abcd"
        s= s+"d";
        StringBuilder sb = new StringBuilder("abc");
        sb.append("d");
        sb.toString();
    }
}
//customer immutable
final class MyList{
    final List<Integer> internalList;
    MyList(List<Integer> input){
        //deep copy input
        internalList = new LinkedList<>(input);
    }
    List<Integer> getInternalList() {
        //deep copy
        return new LinkedList<>(internalList);
    }
    public static void main(String[] args) {

        List<Integer> input = new LinkedList<>();
        MyList myList = new MyList(input);

    }
}

/**
 * 2. Exception
 *            Throwable
 *      /                  \
 *     Error               Exception
 *      stackOverFlow       1.checked (compile)
 *                              Class.forName
 *                          2.uncheck (detech runtime)
 *
 *    Exception handling:
 *      1.throws(just for check ex)
 *      2. try/catch/finally
 */

class TestEx{
    public static void main(String[] args) {
//        try{
//            Class.forName("week1.Day3");
//        }catch(ClassNotFoundException ex){
//
//        }catch (Exception ex){
//            System.out.println(ex);
//        }finally {
//            //execute no matter ex happened or not
//        }
        System.out.println(1/0);
        try{
            System.out.println(1/0);
        }catch(Exception ex){
            System.out.println(ex);
        }


    }
}
/**
 * final/finally/finalize
 */
class Test{
    //when the object is removed, this finalize method will be invoked
    @Override
    protected void finalize(){
        //...
        System.out.println("object is removed");
    }

    public static void main(String[] args) {
        new Test();
        //System.gc();
    }
}

/**
 * 3. Array
 *      fixed size
 *      contiguous memory allocation
 *      O(1) access
 */
class Test6{
    public static void main(String[] args) {
        Integer[] arr = new Integer[10];
        System.out.println(Arrays.toString(arr));
    }
}

/**
 * 4. List
 *      ArrayList (array)
 *          contiguous memory allo
 *          get: O(1)
 *          auto resize O(n)
 *      LinkedList (node)
 *          memory allocation is random
 *          node<-> node <->node<->node
 *          get: O(n) for traverse list
 *          addFirst() add(): O(1)
 *          add(index, ele) O(n) traverse to target pos, O(1) modify the pointer
 */
class Test7{
    public static void main(String[] args) {
    }
}
/**
 * 5.HashMap
 *      O(k) n>>>>k= n/size resize node arr
 *      O(1) constant put get
 *      hashcode()
 *      equals()
 *
 *      Node[] 16
 *      Key Value
 *      Key located where
 *      key.hashcode()1 17%16 (0-15)
 *      Node(key, value, next)[1]
 *      Node[1]
 *      Hash Collison
 *      get(k1) k1.equals(key)
 *      O(n)  O(k=n/16)
 *      [Node(key,value,1)][] []
 *        \
 *        [Node(17)]
 *
 *      put: putVal(hash(key), key, value)
 *      use hashcode to find the bucket index
 *      then check whether there is collision
 *      go inside bucket to see if there is pre exist key
 *      equals() to see if the key is exist
 *      if it is a tree, just use tree method
 *      otherwise it is a linkelist, traverse the linkedlist to see whether key is existed
 *      if surpass the threshhold, resize the node array
 *
 *
 *
 *
 *
 *
 */
class Test8{
    public static void main(String[] args) {
        Person p1 = new Person("x",10);
        Person p2 = new Person("x",10);
        System.out.println(p1.equals(p2));
        HashMap<Person, Integer> map = new HashMap<>();
        map.put(p1, 10);
        p1.age=20;
        System.out.println(map.get(p1));
    }
}
class Person{
    int age;
    String name;
    Person(String name, int age){
        this.name = name;
        this.age=age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {


        return Objects.hash(age, name);
    }
}
/**
 * Set
 *      HashSet
 *      HashMap(key, dummy)
 *      don't allow duplicate
 *      set don't keep the insertion order
 *
 */
class TestSet{
    public static void main(String[] args) {
        HashSet<Person> set = new HashSet<>();
        //new Integer(10)
        set.add(new Person("x",1));
    }
}
/**
 * List vs Set
 *  list will keep insertion order, allow duplicate
 */

/**
 * TreeMap
 *  binary search tree
 *      O(logN)
 *
 */
class TestTreeSet{
    public static void main(String[] args) {
        TreeMap<Integer, Integer> tm = new TreeMap<>((a,b)->{
            if(a==null) {
                return -1;
            }else if(b==null) {
                return 1;
            }else{
                return a-b;
            }
        });
        tm.put(10,10);
        tm.put(9,10);
        tm.put(11,10);
        tm.put(null, 10);
        //fail fast
        //iterator: expectModCount it.next()
        //modCount++
        //for each internally using iterator
        for(Integer key: tm.keySet()) {
            tm.remove(key);
        }
        for(int i=0;i>10;i++)
        System.out.println(tm);

    }
}
class TestExp{
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        //list.removeLast();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> it = list.listIterator();
        for(int i=0;i<5;i++) {
            list.removeLast();
        }


    }
}
//local cache concurrentHashmap