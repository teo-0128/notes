# CS61B

[Home | CS 61B Spring 2024](https://sp24.datastructur.es/)
[CS 61B Staff](https://github.com/Berkeley-CS61B)
[cs61b | CS自学社区](https://www.learncs.site/docs/curriculum-resource/cs61b/cs61b_ch)

## Java

### 1.Introduction

hello world in java:
```java
public class HelloWorld{
    public static void main(String[] args){
        System.out.println("Hello,world!");
    }
}
```
HelloWorld.java 
↓Compiler`javac`
HelloWorld.class
↓Interpreter`java`
```sh
Hello,world!
```
in terminal:
```sh
[teo@myarch java250702]$ javac HelloWorld.java
[teo@myarch java250702]$ java HelloWorld
Hello,world!
```


### 2. Defining and Using Classes

#### define and instantiate classes

1. every method is associated with some classes
2. to run a class,must define a main method
3. not all classes have a main method


```java
package test;
public class Dog{
    public static void makeNoise(){
        System.out.println("Bark!");
    }//can't run directly because no main method
}
```
```java
package test;
public class DogLauncher{
    public static void main(String[] args){
        Dog.makeNoise();
    }//calls a method from another class
}
```
result:
```sh
Bark!
```

```java
package test;
public class Dog{
    public static void makeNoise(){
        System.out.println("Bark!");
    }//can't run directly because no main method
    public int weightInPounds;
}
```
```java
package test;
public class DogLauncher{
    public static void main(String[] args){
        Dog.makeNoise();
    }//calls a method from another class
}
```

#### static and non-static methods

classes can not only contain functions/methods,but also data:
```java
package dogdog;
public class Dog{
    public int weightInPounds;
    //instance variable(实例变量),a general element to all dogs

    public Dog(int startingWeight){
        weightInPounds = startingWeight;
    }
    //constructor(构造函数),the same name to the class
    //similar to a method but not,determines how to instantiate the class
    
    public void makeNoise(){
        if(weightInPounds < 10){
            System.out.println("yip");
        }else if(weightInPounds < 30){
            System.out.println("bark");
        }else{
            System.out.println("woof");
        }
    }
    //non-static method(非静态方法)/instance method(实例方法)
    //if the method's going to be invoked by an instance(aka object) of the class,it should be non-static
}
```
```java
package dogdog;
public class DogLauncher{
    public static void main(String[] args){
        Dog.makeNoise();//error,makeNoise isn't static
        Dog d = new Dog(15);
        d.makeNoise();//okay,create an object to invoke the non-static method
        //bark

        Dog a;//not initialize yet
        a = new Dog(10);//assign a value to a
        Dog b = new Dog(20);
        a = b;
        a.weightInPounds = 30;
        a.makeNoise();
        //woof

        Dog[] dogs = new Dog[2];//creates an array of dogs of size 2
        dogs[0] = new Dog(8);
        dogs[1] = new Dog(20);
        dogs[0].makeNoise();
        //yip
    }
}
```
```java
//when there are 2 or more constructors:
public class Dog{
    public int weightInPounds;
    public String name;

    //version with dogName
    public Dog(int startingWeight, String dogName) {
        weightInPounds = startingWeight;
        name = dogName;
    }

    //version without dogName
    public Dog(int startingWeight) {
        this(startingWeight, "No Name"); 
        //this(...)
        //pass ... to the other constructor
    }
}
```

1. `static`methods are invoked using the class name,e.g.`Dog.makeNoise()`
2. instance methods are invoked using an instance name,e.g.`maya.makeNoise()`
3. if having a non-static method,the non-static method is specific to one dog

static:
```java
public static void makeNoise(){
    System.out.println("bark");
}
```
```java
Dog.makeNoise();
```
some classes are never instantiated
for example,Math
```java
x = Math.round(5,6);
```

non-static:
```java
public void makeNoise(){
    if(weightInPounds < 10){
        System.out.println("yip");
    }else if(weightInPounds < 30){
        System.out.println("bark");
    }else{
        System.out.println("woof");
    }
}
```
```java
Dog maya = new Dog();
maya.makeNoise();
```

ways to  return the heavier dog:

```java
//with `static`
public static Dog maxDog(Dog d1,Dog d2){
    if(d1.weightInPounds < d2.weightInPounds){
        return d2;
    }else{
        return d1;
    }
}
```
```java
Dog larger = Dog.maxDog(dog1,dog2);//a class invokes the method
```
or
```java
//no `static`
public Dog maxDog(Dog d2){
    if(weightInPounds < d2.weighInPounds){
        return d2;
    }else{
        return this;
    }
}
```
```java
Dog larger = dog1.maxDog(dog2); //an object invokes the method
```

### 3. Lists I

#### reference type

```java
int x = 5;
int y;
y = x;
x = 2;
System.out.println("x = " + x);//x = 2
System.out.println("y = " + y);//y = 5
```
however
```java
Walrus a = new Walrus(1000,8.3);
Walrus b;
b = a;
b.weight = 5;
System.out.println(a);//weight:5,tusk size:8.30
System.out.println(b);//weight:5,tusk size:8.30
```

- computers store information in memory,like integer 72 stored as 01001000,character H also stored as 01001000 according to ASCII
- 8 primitive types in java:`byte` `shore` `int` `long` `float` `double` `boolean` `char`
- everything else,including arrays and `String`,are "reference type"(引用类型)

1. when an Object is instantiated(e.g.Dog,Walrus),java first allocates a box of bits for each instance variable of the class and fills them with a default value(e.g.0,null)
2. the constructor then usually fills every such box with some other value

```java
new Walrus(1000,8.3);
```
3. can think of `new` as returning the address of the newly created object.for example, if the object is created in memory location 1234567890,then `new` returns 1234567890

```java
Walrus someWalrus;
someWalrus = null;
```

4. addresses in java are 64 bits,these bits can be either set to null or the 64 bits "address" of a specific instance of that class(return by `new`)

#### arrays

```java
Planet p = new Planet(0,0,0,0,0,"blah.png");
int[] x = new int[]{0,1,2,95,4};//declaration,instantiation and assignment
```
arrays are also objects,objects are usually instantiated using the `new` keyword

```java
int[] a;//declaration
```
- declaretion creates a 64 bit box intended only for storing a reference to an int array
- no object is instantiateds

```java
new int[]{0,1,2,95,4};
```
- instantiates a new object,in this case an int array
- object is anonymous essentially in java

#### lists

```java
public class IntList {
    public int first;
    public IntList rest;

    public static void main(String[] args) {
        IntList L = new IntList();
        L.first = 5;
        L.rest = new IntList();
        L.rest.first = 10;
        L.rest.rest = new IntList();
        L.rest.rest.first = 15;
    }
}
```
or
```java
public class IntList {
    public int first;
    public IntList rest;
    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    public static void main(String[] args) {
        IntList L = new IntList(15,null);
        L = new IntList(10,L);//recursion
        L = new IntList(5,L);
    }
}
```
↓↓↓
`[5] -> [10] -> [15] -> null`
![](media/QQ20250706-123754.png)

how to count the size of the list:
```java
public int size(){
    if(rest == null){
        return 1;
    }
    return 1 + rest.size();//recursion
}
```
or
```java
public int interativeSize(){
    int totalSize = 0;
    IntList p = this;//this:a reference to the current object
    while(p != null){
        totalSize += 1; 
        p = p.rest;//iteration
    }
    return totalSize;
}
```

write a get method through recursion:
```java
public int get(int i){
    if(i == 0){
        return first;
    }
    return rest.get(i - 1);
}
```

### 4. Lists II

#### SLList

a more proper way:
```java
public class IntNode{
    public int item;
    public IntNode next;
    public IntNode(int i,IntNode n){
        item = i;
        next = n;
    }
}//IntNode is dumb now,has no methods 
```
↓↓↓can also be nested in SLList(`private static class IntNode{...}`)
```java
public class SLList{
    //use `private` keyword to hide implementation details from users of the class
    private IntNode first;
    //creates a new SLList with one item,namely x
    public SLList(int x){
        first = new IntNode(x,null);
    }
    //add item x to the front of the list
    public void addFirst(int x){
        first = new IntNode(x,first);
    }
    //get the first item in the list
    public int getFirst(){
        return first.item;
    }

    public static void main(String[] args){
        SLList L = new SLList(5);
        L.addFirst(10);
        L.addFirst(15);
        System.out.println(L.getFirst());//15
    }
}
```

![](media/QQ20250707-130714.png)

the `private` prevent code like:
```java
SLList L = new SLList(5);
L.first = null;//directly disturb the structure of the linked list
System.out.println(L.getFirst());//NullPointerException!
```
or
```java
L.first.next = new IntNode(999, null);//bypass the addFirst method and directly tamper with the linked list structure
```

hide implementation details from users of the class,
1. less for user of class to understand
2. safe for you to change private implementation
3. still nothing to do with protection against hackers,spies and other evil entities

#### more methods for SLList

create addLast and size method for SLList:
```java
//add x to the end of the list
public void addLast(int x){
    IntNode p = first;
    while(p.next != null){
        p = p.next;//iteration
    }
    p.next = new IntNode(x,null);
}

//return the size of the list
public int size(){
    return size(first);
}

//return the size of the list,starting at IntNode p
private int size(IntNode p){
    if(p.next == null){
        return 1;
    }
    return 1 + size(p.next);
}

/*
//or
public int size() {
    if (first == null) return 0;
    return 1 + size(first.next);
}//poor readability
*/
```

#### faster size()

```java
public class SLList{
    private IntNode first;

    //...

    private int size(IntNode p){
        if(p.next == null){
            return 1;
        }
        return 1 + size(p.next);
    }

    public int size(){
        return siez(first);
    }
}
```
↓↓↓
```java
public class SLList {
    private IntNode first;
    private int size;//all S lists have a size

    public SLList(int x) {
        first = new IntNode(x, null);
        size = 1;//size is 1
    }

    public void addFirst(int x) {
        first = new IntNode(x, first);
        size += 1;//size grow by 1
    }

    public int getFirst() {
        return first.item;
    }

    public void addLast(int x){
        IntNode p = first;
        while(p.next != null){
            p = p.next;
        }
        p = new IntNode(x,null);
        size += 1;//size grow by 1
    }

    public int size(){
        return size;
    }
```

- the solution is maintaining a special size variable that caches the size of the list
- caching:putting aside data to speed up retrieval,use a bit of extra memory in exchange for getting faster size()

#### empty list

- benefits of SLList vs. IniList so far:
  1. faster size() method than would have been convenient for IntList
  2. User of an SLList never sees the IntList class
- also it's easy to represent the empty list by setting first to null
- in the above code there's a subtle bug,it crashes then calling addLast on the empty list:
    ```java
    public void addLast(int x){
        IntNode p = first;
        while(p.next != null){
            p = p.next;
        }
        p = new IntNode(x,null);
        size += 1;
    }
    ```
    can't ask the null-th item for its next element like `null.next`
    so change to:
    ```java
    public void addLast(int x){
        size += 1;
        if(first == null){
        p = new IntNode(x,null);
        return;
    }
        IntNode p = first;
        while(p.next != null){
            p = p.next;
        }
        p = new IntNode(x,null);
        
    }
    ```

how to make all SLLists(even empty) consistant of consistency:
![](media/QQ20250707-174138.png)
```java
public class SLList {
    //the first item,if it exists,is at sentinel.next
    private IntNode sentinel;
    private int size;

    public SLList(int x) {
        sentinel = new IntNode(0,null);
        sentinel.next = new IntNode(x, null);
        size = 1;
    }

    //create an empty SLList
    public SLList(){
        sentinel = new IntNode(0,null);
        size = 0;
    }

    public void addFirst(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
        size += 1;
    }

    public int getFirst() {
        return sentinel.next.item;
    }

    public void addLast(int x){
        size += 1;
        IntNode p = sentinel;
        while(p.next != null){
            p = p.next;
        }
        p.next = new IntNode(x,null);
    }

    public int size(){
        return size;
    }
```
- an invariant is a condition that is guaranteed to be true during code execution(assuming there are no bugs in the code)
- an SLList with a sentinel node has at least the following invariants:
  1. the sentinel reference always points to the sentinel node
  2. the first node(if exists) is always at sentinel.next
  3. the size variable is always the total number of items that have been added
- invariants make it easier to reason about code:
  1. can assume they are true to simplify code(e.g. addLast doesn't need to worry about nulls)
  2. must ensure that methods preserve invariants

### 5. Lists III
