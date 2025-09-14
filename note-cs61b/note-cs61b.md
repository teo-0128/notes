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
double x = Math.round(5,6);
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
Dog maya = new Dog(15);
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

![](media/QQ20250708-194424.png)

#### array

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

#### Singly Linked List

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
↓↓↓can also be nested in SLList(`private static class IntNode{...}`，IntNode then is only used for the internal implementation of SLList)
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
3. but still nothing to do with protection against hackers,spies and other evil entities

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
    return 1 + size(p.next);//recursion
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
        return size(first);
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

- benefits of SLList vs. IntList so far:
  1. faster size() method than would have been convenient for IntList
  2. User of an SLList never sees the IntList class
- also it's easy to represent the empty list by setting first to null
- in the above code there's a subtle bug,it crashes when calling addLast on the empty list:
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
        first = new IntNode(x,null);
        return;
    }
        IntNode p = first;
        while(p.next != null){
            p = p.next;
        }
        p.next = new IntNode(x,null);
        
    }
    ```

how to make all SLLists(even empty) consistant of consistency:
![](media/QQ20250707-174138.png)
```java
public class SLList{
    //the first item,if it exists,is at sentinel.next
    private IntNode sentinel;
    private int size;

    public SLList(int x){
        sentinel = new IntNode(0,null);
        sentinel.next = new IntNode(x, null);
        size = 1;
    }

    //create an empty SLList
    public SLList(){
        sentinel = new IntNode(0,null);
        size = 0;
    }

    public void addFirst(int x){
        sentinel.next = new IntNode(x, sentinel.next);
        size += 1;
    }

    public int getFirst(){
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
(an "invariant" is a condition that is guaranteed to be true during code execution(assuming there are no bugs in the code))
- an SLList with a sentinel node has at least the following invariants:
  1. the sentinel reference always points to the sentinel node
  2. the first node(if exists) is always at sentinel.next
  3. the size variable is always the total number of items that have been added
- invariants make it easier to reason about code:
  1. can assume they are true in order to simplify code(e.g. addLast doesn't need to worry about nulls)
  2. must ensure that methods preserve invariants

### 5. Lists III

#### Doubly Linked List and Circular Linked List

inserting at the back of an SLList is much slower than the front,how to make them almost fast?
1. 
- add backwards links from every node
- this yields a "doubly linked list" or DLList,as opposed to our earlier "singly linked list" or SLList

![](media/QQ20250708-151934.png)
2. 
- arrows point at entire node,not fields
e.g. last holds the address of the last node,not the item field of the sentinel node
- last sometimes points at the sentinel,and sometimes points at a "real" node,one solution is having two sentinels,another solution is let the  last sentinel point to sentinel 
  
![](media/QQ20250708-154440.png)

#### generics 

generics(泛型) allow developers to define type-safe classes, interfaces, and methods with type parameters
the core idea of generics is to parameterize data types, enabling the same code logic to work with different data types without duplication

```java
public class SLList<BleepBlorp>{
    private IntNode sentinel;
    private int size;
    public class IntNode{
        public BleepBlorp item;
        public IntNode next;
        ...
    }
    ...
}
```
- in java file implementing your data structure,specify your "generic type" only once at the very top of the file
- in java files that use your data structure,
  1. write out desired type during declaration
  2. use the empty `<>` during instantiation
```java
SLList<Intger> s1 = new SLList<>(5);
s1.addFirst(10);
SLList<String> s2 = new SLList<>("hi");
s2.addFirst("apple");
//int:Integer double:Double char:Character boolean:Boolean long:Long
```

#### array again

- arrays are a special kind of object which consists of a numbered sequence of
memory boxes
- to get i-th item of array A, use A[i]
- unlike class instances which have named memory boxes
- arrays consist of:
  1. a fixed integer length(can't change)
  2. a sequence of N memory boxes where N=length,such that:
    - all of the boxes hold the same type of value(and bits)
    - the boxes are numbered 0 through length-1
- like instances of classes:
  - you get one reference when it's created
  - if you reassign all variables containing that reference,you can never get the array back
- unlike classes,arrays don't have methods 
```java
x = new int[3];
y = new int[]{1,2,3,4,5};
int[] z = {6,7,8,9,10};
```

![](media/QQ20250708-202904.png)

unlike C standard,the second position of 2d array in java is not necessary,a 2d array in java is not a contiguous memory space,it is an irregular (jagged) structure,meaning each row can have a different length:
```java
int[][] pascalsTriangle;//array of int array references
pascalsTriangle = new int[4][];//create four boxes,each can store an int array reference
int[] rowZero = pascalsTriangle[0];

pascalsTriangle[0] = new int[]{1};
pascalsTriangle[1] = new int[]{1,1};
//↓create a new array with 3 boxes,storing 1,2,1 respectively
//↓store a reference to this array in pascalsTriangle box #2
pascalsTriangle[2] = new int[]{1,2,1};
pascalsTriangle[3] = new int[]{1,3,3,1};
/*
pascalsTriangle → [
    [1],
    [1, 1],
    [1, 2, 1],
    [1, 3, 3, 1]
]
*/
int[] rowTwo = pascalsTriangle[2];
rowTwo[1] = -5;
/*
pascalsTriangle → [
    [1],
    [1, 1],
    [1, -5, 1],
    [1, 3, 3, 1]
]
*/

int[][] matrix;
matrix = new int[4][];//create 1 total array
matrix = new int[4][4];//create 5 total arrays

int[][] pascalAgain = new int[][]{{1},{1,1},{1,2,1},{1,3,3,1}}
```

![](media/QQ20250708-213233.png)

arrays indices can be computed at runtime,class member variable names can't be computed and used at runtime

### 6. Test

#### Junit

the most natural approach to testing would be to start with an input and expected result
```java
public class TestSort{
    public static void testSort(){
        String[] input = {"AA", "BB", "CC", "DD"};
        String[] expected = {"AA","BB","CC","DD"};
        ...
    }
    public static void main(String[] args){
        testSort(); 
    }
}
```
this is the most primitive method,which is annoying and thankless
`org.junit` library provides many useful methods for simplifying coding
```xml
<dependencies>
<!--JUnit5 unit testing framework-->
    <dependency>
    <!--allow IDEs or build tools (such as Maven or Gradle) to discover and execute tests programmatically-->
        <groupId>org.junit.platform</groupId>
        <artifactId>junit-platform-launcher</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
    <!--responsible for executing tests written using the JUnit Jupiter API-->
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
    <!--test cases for compatibility and operation with both JUnit 3 and JUnit 4-->
        <groupId>org.junit.vintage</groupId>
        <artifactId>junit-vintage-engine</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```
```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Test
void testAddition() {
    Calculator calculator = new Calculator();
    int result = calculator.add(2, 3);
    assertEquals(5, result);
}
```

just look at
[Junit5 架构、新特性及基本使用（常用注解与套件执行） - 知乎](https://zhuanlan.zhihu.com/p/161623597)

#### Truth

In CS61B,we use Google's Truth assertions library. We use this library over JUnit assertions for the following reasons:
- Better failure messages for lists.
- Easier to read and write tests.
- Larger assertions library out of the box.

We often write tests using the Arrange-Act-Assert pattern:
1. Arrange(准备) the test case, such as instantiating the data structure or filling it with elements.
2. Act(执行) by performing the behavior you want to test.
3. Assert(断言) the result of the action in (2).

A Truth assertion takes the following format:
```java
assertThat(actual).isEqualTo(expected);
```

To add a message to the assertion, we can instead use:
```java
assertWithMessage("actual is not expected")
    .that(actual)
    .isEqualTo(expected);
```

We can use things other than `isEqualTo`, depending on the type of `actual`. For example, if `actual` is a `List`, we could do the following to check its contents without constructing a new `List`:
```java
assertThat(actualList)
    .containsExactly(0, 1, 2, 3)
    .inOrder();
```

If we had a `List` or other reference object, we could use:
```java
assertThat(actualList)
    .containsExactlyElementsIn(expected)  // `expected` is a List
    .inOrder();
```

Truth has many assertions, including isNull and isNotNull; and isTrue and isFalse for booleans. IntelliJ's autocomplete will often give you suggestions for which assertion you can use.

### 7. Lists IV

#### AList

when there are too many elements in a linked list, will the retrieval slow down no matter what,so we should use arrays instead，retrieval from any position of an array is very fast

```java
public class AList {
    //basic functions
    private int[] items;
    private int size;
    public AList() {
        items = new int[100];
        size = 0;
    }
    public void addLast(int x) {
        items[size] = x;
        size += 1;
    }
    public int getLast() {
        return items[size - 1];
    }
    public int get(int i) {
        if(i >= items.length) {
            throw new IllegalArgumentException();
        }
        return items[i];
    }
    public int size() {
        return size;
    }
}
```

#### removeLast() and addLast()

1. the position of the next item to be inserted is always size
2. size is always the number of items in the AList
3. the last item in the list is always in position size-1
therefore,when removing the last element in the array,
```java
public int removeLast() {
    int x = item[size - 1];
    //item[size - 1] = 0;  <-  isn't necessary
    size -= 1;
    return x;
}
```

if the array is full and can't give more space,we can make a new array
```java
//modified addLast
public void addLast(int x) {
    if (size == items.length) {
        int[] a = new int[size + 1];
        System.arraycopy(items, 0, a, 0, size);
        items = a;//points to a new array
    }
    items[size] = x;
    size += 1;
}
```
↓↓↓ easier to read,understand,and test
```java
//resize the underlying array to the target capacity
private void resize(int capacity) {
    int[] a = new int[capacity];
    System.arraycopy(items, 0, a, 0, size);
    items = a;
}
//insett into the back of the list
public void addLast(int x) {
    if (size == items.length) {
        resize(size + 1);
    }
    items[size] = x;
    size += 1;
}
```
- invoking this method twice requires creating and filling 101+102=203 total memory boxes,which is too slow
- if calling addLast until size = 1000,roughly 500000 total array memory boxes work(101+102+...+1000)
- obviously size+1 or size+10 won't change the nature of the curve(O(n^2)):
  ![](media/QQ20250714-095253.png)

faster addLast():
```java
public void addLast(int x) {
    if (size == items.length) {
        resize(size * 2);//every time a new array is needed,create double the size(O(n))
    }
    items[size] = x;
    size += 1;
}
```

an AList shouldn't only be efficient in time,but also in space
- can define the "usage ratio" `R = size / items.length;`
- typical solution:half array when R < 0.25

space efficiency and time efficiency will be studied in the future

#### generic Alists

```java
public class Alist<Glorp> {
    private Glorp[] items;
    private int size;
    public Alist() {
      //items = new Glorp[100];  <-  syntax error in java!
        items = (Glorp[]) new Object[8];//  <-  okay
        size = 0;
    }
    private void resize(int cap) {
        Glorp[] a = (Glorp[]) new Object[cap];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }
    public Glorp get(int i) {
        return items[i];
    }
    ...
} 
```

unlike integer based ALists,we actually want to null out deleted items
- java only destroy unwanted objects when the last reference has been lost
- keeping references to unneeded objects is sometimes called "loitering"
- save memory,don't loiter
```java
public Glorp removeLast() {
    Glorp returnItem = getLast();

    //no more references,garbage collector works
    items[size - 1] = null;
    
    size -= 1;
    return returnItem;
}
```

### 8. Inheritance I

#### interface and implementation

java allows multiple methods with same name,but different parameters,this is called method "overloading(重载)"
```java
public class OneClass {
    public static String longest(AList<String> list) {
    ...
    }
    public static String longest(SLList<String> list) {
    ...
    }
}
```

but it looks bad and hard to maintian, won't work for future lists,if creating a QList class,have to make a third method

List is a hypernym of SLList and AList，express this in java is a two-step process:
1. define a reference type for the hypernym
2. specify that SLLists and ALists are hyponyms of that type

- use the new keyword `interface` instead of `class` to define a List61B
- interface is a specification of what a List is able to do,not how to do it:
```java
public interface List61B<Blorp> {
    public void addFirst(Blorp x);
    public void addLast(Blorp x);
    public void insert(Blorp x, int position);
    public Blorp getFirst();
    public Blorp getLast();
    public Blorp removeLast();
    public Blorp get(int i);
    public int size();
}
```
use the new keyword `implements` to tell java compiler SLList and AList are hyponyms of List61B
```java
public interface List61B<Item> {
    public void addLast(Item y);//no actual code here
    ...
}
```
if a implementing class has a method with the exact same signature as in the interface,the implementing class overrides the method
```java

public class AList<Item> implements List61B<Item> {
    ...
    public void addLast(Item x) {//ite overrides the List61B's addLast()
        ...
    }
}
```

we can now adjust this method to work on either kind of list:
```java
public static String longest(List61B<String>list) {
    int maxDex = 0;
    for(int i = 0; i < list.size(); i +=1) {
        String longestString = list.get(maxDex);
        String thisString = list.get(i);
        if (thisString.length() > longestString.length()) {
            maxDex = i;
        }
    }
    return list.get(maxDex);
}
```
```java
public static void main(String[] args) {
    AList<String> a = new AList<>();
    a.addLast("egg");
    a.addLast("cat");
    longest(a);
}
```

difference between override and overload:
![](media/QQ20250715-031202.png)

- adding `@Override` before a function makes it easier to read,declares it was overwritten from a interface
- the `implements List61B<Item>` is essentially a commitment,AList states it will own and define all the elements and behaviors specified in the List61B interface
- if List61B defines a method and AList/SLList doesn't realize it,the compiler will report the error 

use the keyword `default` in an interface to provide default implementations:
```java
public interface MyList {
    ...
    default public void print() {
        for (int i = 0; i < size(); i += 1) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }
}
```
can be overridden by implementing classes:
```java
public class MyLinkedList implements MyList {
    ...
    @Override
    public void print() {
        for (Node p = sentinel.next; p != null; p = p. next) {
            System.out.print(p.item + " ");
        }
    }
}
```

#### static and dynamic type

```java
public static void main(String[] args) {
    List61B<String> someList = new SLList<String>();//is dogA a Dog?yes!
    someList.addLast("1");
    someList.addLast("2");
    someList.addLast("3");
    someList.print();//using SLList.print(),not List61B.print(),reason↓↓↓
}
```
- 1. every variable in java has a "compile-time type", a.k.a. "static type"
  2. this is the type specified at declaration,it never changes
  3. the compoler determines which methods can be called based on static type
- 1. variables also have a "run-time type" a.k.a. "dynamic type"
  1. this is the type specified at instantiation(e.g. when using `new`)
  2. equal to the type of the object being pointed at
   
![](media/QQ20250722-095145.png)

### 9. Inheritance II

build a RotatingSLList that can perform any SLList operation as well as,and include a rotateRight() method to move back item the front
example: [1, 2, 3, 4, 5] -> [5, 1, 2, 3, 4]

if you want one class to be a hyponym of another class(instead of an interface),use keyword `extends`:
```java
public class RotatingSLList<Item> extends SLList<Item> {
    public void rotateRight() {
        Item x = removeLast();
        addFirst(x);
    }

    public static void main(String[] args) {
        RotatingSLList<Integer> rsl = new RotatingSLList<>();//"Integer" in <> can be omitted since java7
        //creates S-list:[10, 11, 12, 13]
        rsl.addLast(10);
        rsl.addLast(11);
        rsl.addLast(12);
        rsl.addLast(13);

        //should be:[13, 10, 11, 12]
        rsl.rotateRight();
        rsl.print();
    }
}
```
by `extends` RotatingSLList inherits all members of SLList(instance and static variables,methods,nested classes)except constructors,but members may be private and thus inaccessible

VengefulSLList has an additional method printLostItems(),which prints all deleted items
```java
public class VengefulSLList<Item> extends SLList<Item> {
    SLList<Item> lostItems;

    public VengefulSLList() {
        //super();  <-  must come first,can be omitted 
        lostItems = new SLList<>();
    }

    public VengefulSLList(Item x) {
        super(x);// <-  must give parameters like x to super(),otherwise default super()
        lostItems = new SLList<>();
    }

    public void printLostItems() {
        lostItems.print();
    }

    @Override
    public Item removeLast(Item x) {
        item x = super.removeLast();
        lostItems.addLast(x);
        return x;
    }

    public static void main(String args) {
        VengefulSLList<Integer> vsl = new VengefulSLList<>();
        vsl.addLast(1);
        vsl.addLast(5);
        vsl.addLast(10);
        vsl.addLast(13);//vsl is now:[1, 5, 10, 13]
        vsl.removeLast();
        vsl.removeLast();//vsl is now:[1, 5]
        System.out.print("the fallen are: ");
        vsl.printLostItems();//the fallen are: 13 10
    }
}
```

VengefulSLList extends SLList,SLList extends Object implicitly

![](media/QQ20250725-073154.png)

java7 and earlier versions have a fundamental issue:memory boxes(variables) can't contain pointers to functions
can use an interface instead:
```java
public interface IntUnaryFunction {
    public int apply(int x);
}
```
```java
public class TenX implements IntUnaryFunction {
    @Override
    public int apply(int x) {
        return 10 * x;
    }
}
```
```java
public class HoFDemo {
    public static int doTwice(IntUnaryFunction f, int x) {
        return f.apply(f.apply(x));
    }//it's a higher-order function
    public static void main(String[] args) {
        int result = doTwice(new TenX(), 2);
        /*
        IntUnaryFunction func = new TenX();
        //in python:
        def tenX(x):
            return 10 * x
        func = tenX
        */
        System.out.println(result);//200,/TexX(Tenn(2)) -> 10*(10*2)=200
    }
}
```

in java8,new types were introduced,now can hold references to methods,without `new` 
```java
public class Java8HofDemo {
    public static int tenX(int x) {
        return 10*x;
    }
    public static int doTwice(Functio<Integer, Integer> f, int x) {
        return f.apply(f.apply(x));
    }
    public static void main(String[] args) {
        int result = doTwice(Java8HofDemo::tenX, 2);
        System.out.println(result);
    }
}
```

### 10. Inheritance III

subtype polymorphism:子类型多态性
polymorphism:provides a single interface to entities of different types
consider a variable deque of static type Deque:
- when calling deque.addFirst(),the actual behavior is based on the dynamic type,a.k.a. run-time type
- java automatically selects the right behaviod using what's sometimes called "dynamic method selection"

write a function max() that returns the max of any array,regardless of type
```java
public interface OurComparable {
    /**
     * return -1 if less than o
     * return 0 if equal to o
     * return 1 if more than o
     */
    public int compareTo(Object o);
}
```
```java
public class Dog implements OurComparable{
    public String name;
    private int size;
    public Dog(String n,int s) {...}
    public void bark(){System.out.println(name + "says: bark!");}

    @Override
    public int compareTo(Object o) {
        Dog otherDog = (Dog) o;
        if (this.size < otherDog.size) {
            return -1;
        } else if (this.size == otherDog.size) {
            return 0;
        } else {
            return 1;
        }
        /**
         * or:
         * return this.size - otherDog.size;
         */
    }
} 
```
```java
public class Maximizer {
    public static OurComparable max(OurComparable[] items) {
        int maxDex = 0;
        for (int i = 0; i < items.length; i +=1) {
            int cmp = items[i].compareTo(items[maxDex]);
            if (cmp > 0) {
                maxDex = i;
            }
        }
        return items[maxDex];
    }
}
```
```java
public static void main(String[] args) {
    Dog[] dogs = {
        new Dog("Elyse", 3),
        new Dog("Sture", 9),
        new Dog("Benjamin", 15)
    };
    Dog maxDog = (Dog) Maximizer.max(dogs);
    maxDog.bark();//Benjamin says: bark!
}
```

better version:
![](media/QQ20250730-212908.png)

implement a new comparator that compares things by size:
```java
import java.util.Comparator;//java standard library

//can be nested in Dog,with `static`
public class NameComparator implements Comparator<Dog> {
    @Override
    public int compare(Dog o1, Dog o2) {
        return o1.name.compareTo(o2.name);
    }
}
```
```java
public static void main(String[] args) {
    ...
    Dog d4 = new Dog("Osiki", 200);
    Dog d5 = new Dog("Cerebus", 99999);
    NameComparator nc = new NameComparator();
    int cmp = nc.compare(d4, d5);
    if (cmp > 0) {
        d4.bark();
    } else {
        d5.bark();
    }
    //Oski comes later in the alphabet
    //Oski says: bark!
}
```

![](media/QQ20250730-230931.png)

### 11. Inheritance IV

#### ArraySet

build an implementation of a Set called ArraySet
1. add(value): add the value to the ArraySet if it is not already present
2. contains(value): checks to see if ArraySet contains the key
3. size(): returns number of values

basic part:
```java
public class ArraySet<T> {
    private T[] items;
    private int size;

    public int size() {
        return size;
    }

    public ArraySet() {
        items = (T[]) new Object[100];
        size = 0;
    }

    public void add(T x) {
        if (!contains(x)) {
            items[size] = x;
            size += 1;
        }
    }

    public boolean contains(T x) {
        for (int i = 0; i < size; i += 1) {
            if (items[i].equals(x)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        lec11_inheritance4.ArraySet<Integer> S = new lec11_inheritance5.ArraySet<>();
        S.add(5);
        S.add(23);
        S.add(42);
        System.out.print(S.contains(42));
        System.out.print(S.contains(50));
    }
}
```

the enhanced for loop:
```java
Set<Integer> aset = new HashSet<>();
javaset.add(5);
javaset.add(23);
javaset.add(42);
for (int i : aset) {
    System.out.println(i);
}//can't be compiled yet
//foreach can only called by like `int[]arr `and object that implements `java.lang.Iterable<T>`
//so it requires interface:Iterable<T>,interator<T> :
/**
    public interface Iterable<T> {
        Iterator<T> iterator();//
    }
 **
    public interface Iterator<T> {
        boolean hasNext();
        T next();
    }
 */
```
original code:
```java
Set<Integer> aset = new HashSet<>();
javaset.add(5);
javaset.add(23);
javaset.add(42);
Iterator<Integer> seer = javaset.iterator();
while (seer.hasNext()) {
    int x = seer.next();
    System.out.println(x);
    /*
    5
    23
    42 
    */
}
```
how does the code above it make the compiler work in this way?:
```java
import java.util.Iterator;

public class ArraySet<T> implements Iterable<T> {
    private T[] items;
    private int size;

    public ArraySet() {
        items = (T[]) new Object[100];
        size = 0;
    }

    public void add(T x) {
        if (!contains(x)) {
            items[size] = x;
            size += 1;
        }
    }

    public boolean contains(T x) {
        for (int i = 0; i < size; i += 1) {
            if (items[i].equals(x)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    private class ArraySetIterator implements Iterator<T> {
        private int wizPos = 0;

        @Override
        public boolean hasNext() {
            return wizPos < size;
        }

        @Override
        public T next() {
            return items[wizPos++];
        }
    }
}

```

#### toString() and equals(Object obj)

`String toString()` and `boolean equals(Object obj)`are hyponyms of Object

1. the `toString()` method provides a string representation of an object
2. e.g. `println` calls `String.valueOf` which calls `toString`
3. the default `toString()` just prints out the name of the object ,then @ the memory location of where it stores
   
```java
public class ArraySet<T> implements Iterable<T> {
    ...
    @Override
    public String toString() {
        String x = "(";
        for (T i : this) {
            x += i.toString() + " ";//it actually builds a new String,so it's slow
        }
        x += ")";
        return x;
    }
}//(5 23 42 )
```
↓↓↓quicker way:
```java
public class ArraySet<T> implements Iterable<T> {
    ...
    @Override
    public String toString() {
        StringBuilder x =new StringBuilder();
        x.append("(");
        for (T i : this) {
            x.append(i.toString());
            x.append(" ");
        }
        x.append(")");
        return x.toString();
    }
}//(5 23 42 )
```

`==` compares the bits,for references,it means referencing the same object
`equals()` compares the content that reference type points to 
```java
public class Object {
    ...
    public boolean equals(Object obj) {
        return (this == obj);
    }
}
```
↓↓↓rewrite:
```java
public class ArraySet<T> implements Iterable {
    ...
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }//can save time

        if (o instanceof ArraySet otherArraySet) {//check if o is an arrayset
        //↑↑↑ errors will be reported below java16
            if (this.size != otherArraySet.size) {
                return false;
            }
            for (T i : this) {//check if all my items in the other array set
                if (!otherArraySet.contains(i)) {
                    return false;
                }
            }
            return true;
        }
        //a compatible and reliable way to write(below java16)↓↓↓
        /*
            if (o instanceof ArraySet<?>) {
                ArraySet<?> otherArraySet = (ArraySet<?>) o;
                if (this.size != otherArraySet.size()) {
                    return false;
                }
                for (T item : this) {
                    if (!otherArraySet.contains(item)) {
                        return false;
                    }
                }
                return true;
            }
        */
        return false;
    }
}
```

### 12. Asymptotics I

objective:determine if a sorted array contains any duplicates
given sorted array A,are there indices i!=j where A[i]=A[j] ?
(sorting feature: duplicate elements must be adjacent)

```java
public static boolean dup1(int[] A) {
    for (int i = 0; i < A.length; i += 1) {
        for (int j = i + 1; j < A.length; j += 1) {
            if (A[i] == A[j]) {
                return true;
            }
        }
    }
    return false;
}
public static boolean dup2(int[] A) {
    for (int i = 0; i < A.length - 1; i += 1) {
        if (A[i] == A[i + 1]) {
            return true;
        }
    }
    return false;
}
```

![](media/QQ20250817-153455.png)

our goal is to somehow characterize the runtimes of the 2 functions

dup1:
![](media/QQ20250817-154222.png)
dup2:
![](media/QQ20250817-154447.png)

1. fewer operations to do the same work(50015001 and 10000 operations)
2. algorithm scales better in the worst case(N^2 + 3N +2 and N)
3. parabolas(N^2) grow faster than lines(N)

given a function like Q(N)=3N^3 + N^2,ignore low orders terms and mulriplicative constants,yield the order of growth of Q(N) is N^3

we used Big Theta Θ to describe the order of growth of a function
|function R(N)|order of growth|
|---|---|
|N^3 + 3N^4|Θ(N^4)|
|1/N + N^3|Θ(N^3)|
|1/N + 5|Θ(1)|
|N·e^N + N|Θ(N·e^N)|
|40sin(N) + 4N^2|Θ(N^2)|
|(4N^2 + 3N·ln(N))/2|Θ(N^2)|

![](media/QQ20250817-161956.png)
![](media/QQ20250817-162055.png)

### 13. Midterm 1 Review

[Lecture 13 - Midterm Review_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1hJ4m1M7ZA?spm_id_from=333.788.player.switch&vd_source=33952b30ae6467330b98b47ef5fea60f&p=13)

## Data Structures

### 14. Disjoint Sets

#### note

The Disjoint Sets(并查集/不相交的集) data structure has two operations:
connect(x, y): Connects x and y.
isConnected(x, y): Returns true if x and y are connected. Connections can be
transitive, i.e. they don't need to be direct.

example:
connect(Russia, China)
connect(Russia, Mongolia)
isConnected(China, Mongolia)? true
connect(USA, Canada)
isConnected(USA, Mongolia)? false

```java
public interface DisjointSets {
    void connect(int p, int q);
    boolean isConnected(int p, int q);
}
```

idea1:
- List of sets of intergers,e.g.[{0,1,2,4},{3,5},{6}]
- in java:`List<Set<Integer>>`
- intuitive and terrible idea,overall runtime of Θ(N)

idea2: 
- list of integers where i-th entry gives set number 
- ![](media/QQ20250818-024758.png)
- connect(p, q):change entries that equal id[p] to id[q]

```java
public class QuickFindDS implements DisjointSets {
    private int[] id;
    
    public QuickFindDS(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = -1;
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return id[p] == id[q];
    } //Θ(1),very fast

    @Override
    public void connect(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if(id[i] == pid) {
                id[i] = qid;
            }
        }
    }//Θ(N),relatively slow still   
} 
```

idea3:
- assign each item a parent (instead of an id),results in a tree-like shape
- ![](media/QQ20250818-030641.png)
- example:connect(5,2)
  1. find root(5) // returns 3
  2. find root(2) //return 0
  3. set root(5)'s value to root(2)
- ![](media/QQ20250818-154457.png)

```java
public class QuickUnionDS implements DisjointSets {
    private int[] parent;

    public QuickUnionDS(int N) {
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1;
        }
    }

    private int find(int p) {
        int r = p;
        while (parent[r] >= 0) {
            r = parent[r];
        }
        return r;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public void connect(int p, int q) {
        int i = find(p);
        int j = find(q);
        parent[i] = j;
    }
}
/*
but in the worst case this tree looks like:
0
 \
  1
   \
    2
     \
      3
both connect(p,q) and isConnected(p,q) still Θ(N)
*/
```

one possible approach is to keep track of the height of every tree
- link up shorter tree below the larger tree
- in case of a tie, break tie arbitrarily
- track the tree's height by counting the total number of items in that tree
- to make the tree short,always take the smaller tree and hang it under the larger tree

↓↓↓

```java
/*
conect(int p, int q) needs to keep track of sizes:
1. replace -1 with weight for roots(top approach)
2. create a separate size array(bottom approach)
*/
```

![](media/QQ20250818-161600.png)
![](media/QQ20250818-161649.png)

path compression:
![](media/QQ20250819-163809.png)
↓↓↓calling isConnected(15,10)
![](media/QQ20250819-163839.png)
↓↓↓ calling isConnected(14,13)
![](media/QQ20250819-164013.png)
![](media/QQ20250819-164834.png)

The ideas that made our implementation efficient:Represent sets as connected components (don't ack individual connections).
1. ListofSetsDS: Store connected components as aList of Sets (slow,complicated).
2. QuickFindDS: Store connected components asset ids.
3. QuickUnionDS: Store connected components asparent ids.
   - WeightedQuickUnionDS: Also track the size of each set, and use size to decide on new tree root.
     - WeightedQuickUnionWithPathCompressionDS: On calls to connect and isConnected, set parent id to the root for all items seen.


Runtimes are given assuming:
- We have a DisjointSets object of size N.
- We perform M operations, where an operation is defined as either a call to connected or isConnected.

Implementation|Runtime
---|---
ListOfSetsDS|Ο(NM)
QuickFindDS|Θ(NM)
QuickUnionDS|Ο(NM)
WeightedQuickUnionDS|Ο(M·logN)
WeightedQuickUnionDSWithPathCompression|Ο(M·α(N))

#### UnionFind code

[Lab 05 Disjoint Sets | CS自学社区](https://www.learncs.site/docs/curriculum-resource/cs61b/cs61b_ch/labs/lab05#%E7%BB%83%E4%B9%A0unionfind)

![](media/QQ20250819-163809.png)
↓↓↓calling isConnected(15,10)
![](media/QQ20250819-163839.png)
↓↓↓ calling isConnected(14,13)
![](media/QQ20250819-164013.png)

```java
public class UnionFind {
    private int[] parent;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        int root = find(v);
        return -parent[root];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if (v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("Invalid vertex: " + v);
        }
        return parent[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("Invalid vertex: " + v);
        }
        int root = v;
        // Find the root
        while (parent[root] >= 0) {
            root = parent[root];
        }
        // Path compression: set the parent of all nodes along the path to the root
        int current = v;
        while (current != root) {
            int next = parent[current];
            parent[current] = root;
            current = next;
        }
        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);
        if (root1 == root2) {
            return;
        }
        int size1 = -parent[root1];
        int size2 = -parent[root2];
        if (size1 <= size2) {
            // Merge root1 into root2
            parent[root2] -= size1;
            parent[root1] = root2;
        } else {
            // Merge root2 into root1
            parent[root1] -= size2;
            parent[root2] = root1;
        }
    }
}
```


### 15. Asymptotics II

If my function f(n) is ...|Doubling N|Adding 1 to N
---|---|---
Θ(1)|Doesn't affect runtime|Doesn't affect runtime
Θ(logn) (base independent)|Adds 1 to runtime|Affects runtime minimally
Θ(n)|Doubles runtime|Adds 1 to runtime
Θ(n^2)|Quadruples runtime|Adds n to runtime
Θ(2^n) (base dependent)|Squares runtime|Doubles runtime

```java
public static int f3(int n) {
    if (n <= 1) {
        return 1;
    }
    return f3(n - 1) + f3(n - 1);
}
/*

          4
       /     \
    3           3
   / \         / \
 2     2     2     2
 /\    /\    /\    /\
1  1  1  1  1  1  1  1 

C(N) = 1 + 2 + 4 + ... + 2^(N - 1)
2C(N) = 2 + 4 + 8 + ... + 2^N
(2 - 1)C(N) = C(N) = 2^N - 1
the order of growth of the runtime of this code is 2^N

*/ 
``` 

```java
public static int fib(int n) {
    if (n <= 1) {
        return 1;
    }
    return fib(n - 1) + fib(n - 2);
}
/*

        4
      /   \
    3       2
   / \     / \
  2   1   1   0
 /\
1  0

(Fibonacci sequence grows at this rate)

still Θ(2^N)
*/
```
![](media/QQ20250819-195944.png)

binary search:
```java
static int binarySearch(String[] sorted, String  x, int lo, int hi) {
    if (lo > hi) return -1;
    int m = (lo + hi) / 2;
    int cmp = x.compareTo(sorted[m]);
    if (cmp < 0) return binarySearch(sorted, x, lo, m - 1);
    else if (cmp > 0) return binarySearch(sorted, x, m + 1, hi);
    else return m;
}
//N -> 1/2N -> 1/4N -> ... -> (1/2)^(n-1)
//log₂N
```

mergesort:
- sorting is one of the problems that pops up most often in asymptotic analysis along with matrix multiplication
- given a list of Comparables,return them in sorted order
- ![](media/QQ20250820-000829.png)
- mergesort is a recursive way to sort a list:
  1. split the list into two parts
  2. sort the two lists individually
  3. merge the two lists together
   
```java
static List<Comparable> sort(List<Comparable> x) {
    List<Comparable> firsthalf = sort(x.sublist(0, x.size() / 2));
    List<Comparable> secondhalf = sort(x.sublist(x.size() / 2, x.size()));
    return merge(firsthalf, secondhalf);
}
```
![](media/QQ20250820-001950.png)
- N² vs. NlogN is an enormous difference.
- Going from NlogN to N is nice, but not a radical change.

### 16. ADTs,Sets,Maps,BSTs

#### note

ADTs,abstruct data types(抽象数据类型):
ADT only cares about what can be done,not how it is done
so you can kind of think of it like a more philosophical version of the interface that we saw in java

for example,the Stack ADT supports the following operations:
- push(int x):push x on top of the stack
- int pop():remove and return the top item from the stack

Maps(映射):
Maps are very handy tools for all sorts of tasks. Example: Counting words.
```java
//Map<[key], [value]>
Map<String, Integer> m = new TreeMap<>();
String[] text = {"sumomo", "mo", "momo", "mo", "momo", "no", "uchi"};
for (String s : text) {
    int currentCount = m.getOrDefault(s, 0);
    m.put(s, currentCount + 1);
}
```

BSTs,binary search trees(二叉搜索树):
we use BST to represent an unordered set of items with no duplicates
- A tree consists of:
  1. A set of nodes.
  2. A set of edges that connect those nodes.
  3. Constraint: There is exactly one path between any two nodes.
- In a rooted tree, we call one node the root.
  1. Every node N except the root has exactly one parent, defined as the first node on the path from N to the root.
  2. Unlike (most) real trees, the root is usually depicted at the top of the tree.
  3. A node with no child is called a leaf.
  4. In a rooted binary tree, every node has either 0, 1, or 2 children (subtrees).
- A binary search tree is a rooted binary tree with the BST property.
  - BST Property. For every node X in the tree:
  1.  Every key in the left subtree is less than X's key.
  2. Every key in the right subtree is greater than X's key.

```java
class BST {
    int key;        
    BST left;       
    BST right;      
    BST(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
    }
}
```

```java
/*
if searchKey equals T.key,return
if searchKey < T.key,search T.left
if searchKey > T.key,search T.right
*/
static BST find(BST T, key sk) {
    if (T == null) {
        return null;
    }
    if (sk.equals(T.key)) {
        return T;
    } else if (sk < T.key) {
        return find(T.left, sk);
    } else {
        return find(T.right, sk);
    }
}
/*
BST result = find(root, 40);
if (result != null) {
    System.out.println("result.key = " + result.key);
} else {
    System.out.println("not exist");
}
*/
//Θ(logN)
```
insert a new key in BST:
```java
static BST insert(BST T, key ik) {
    if (T == null) {
        return new BST(ik);
    }
    if (ik < T.key) {
        T.left = insert(T.left, ik);
    } else if (ik > T.key) {
        T.right = insert(T.right, ik);
    }
    return T;
}
/*
root = insert(root, 123);
*/
```
when deleting from a BST,3 cases:
1. Deletion key has no children.
   - just sever the parent's link
2. Deletion key has one child.
   - move its parent's pointer to its child
   - no need to change its pointer
3. Deletion key has two children.
   - have to promote its right or left to be the new parent
   - stick new copy in the parent position
   - This strategy is sometimes known as "Hibbard deletion".
   - ![](media/QQ20250826-084330.png)
     ↓↓↓
     ![](media/QQ20250826-084358.png)

![](media/QQ20250826-085546.png)
but what if we wanted to represent a mapping of word counts?:
![](media/QQ20250826-085646.png)

#### BSTMap code

[Lab 06 BSTMap | CS自学社区](https://www.learncs.site/docs/curriculum-resource/cs61b/cs61b_ch/labs/lab06#%E6%9B%B4%E5%A4%9A%E6%9C%AA%E8%AF%84%E5%88%86bstmap-%E7%BB%83%E4%B9%A0)

```java
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private int size;
    private BSTNode root;

    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;
        private BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private BSTNode put(BSTNode node, K key, V value) {
        if (node == null) {
            size++;
            return new BSTNode(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;//already exists
        }
        return node;
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(BSTNode node , K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        } else {
            return node.value;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    private boolean containsKey(BSTNode node, K key) {
        if (node == null) {
            return false;
        }
        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            return containsKey(node.right, key);
        } else if (cmp < 0) {
            return containsKey(node.left, key);
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}

```

### 17. B-Trees (and 2-3 and 2-3-4 Trees)

height and average depth are important properties of BSTs
- the depth of a node is how far it is from the root,e.g. depth(g) = 2
- the height of a tree is the depth of its deepest leaf,e.g. height(T) = 4
- the average depth of a tree is the average depth of a tree's nodes

BSTs have:
- worst case Θ(N) height
- best case Θ(log N) height

in this case,when building trees we should consider randomized BSTs
if randomly inserting into a tree,the trees end up bushy

if N distinct keys are inserted into a BST
- the expected average depth is ~ 2lnN,with random inserts the average runtime for contain operation is Θ(log N) 
- the expected tree height is ~ 4.311lnN,with random inserts the worst case runtime for contains operation is Θ(log N) 

BSTs have great performance if we insert items randomly,performance is Θ(log N) per operation,but we can't always insert out items in a random order,because datas comes in over time,don't have all at once("inserting data one by one" does not equal "inserting randomly in sequence")

the key idea is not allowing to make the tree taller,have to overstuff the nodes

![](media/QQ20250911-204428.png)

↓↓↓split the middle-left item (17)

![](media/QQ20250911-210026.png)

this is a logically consistent and not so weird data structure,for example:
- contains(18)
  1. 18 > 13,go right
  2. 18 < 15,compare vs. 17
  3. 18>17,go right

what if a non-leaf node gets too full?we can split that,after adding too much stuff:

![](media/QQ20250911-211102.png)

so does the root:

![](media/QQ20250911-211413.png)

splitting-trees have perfect balance
- if we split the root,every node gets pushed down by exactly one level
- if we split a leaf node or internal node,the height doesn't change
- their real name: B-trees,B-trees of order L = 3 are also called a 2-3-4 tree,2-3-4 refers to the number of children that a node can have
- B-trees of order L = 2 are also called a 2-3 tree

B-trees are most popular in 2 specific contexts:
1. small L(L = 2 or 3)
   - used as a simple balanced search tree
2. L is very large like thousands
   - used in practice for databases and filesystems(very large records)

B-trees' 2 nice invariants:
1. all leaves must be the same distance from the root
2. a non-leaf node with k items must have exactly k+1 children(so  below tree is not B-tree:)

![](media/QQ20250911-213845.png)

![](media/QQ20250911-215329.png)

runtime for contains:
- worst case number of nodes to inspect: H+1 (height+1)
- worst case number of items to inspect per node: L
- overall runtime: Ο(HL)
- since H = Θ(log N),overall runtime is Ο(L log N)
- since L is constant,runtime is therefore Ο(log N)
   
### Red Black Trees

```java
/*
1          1                     3          3
 \          \         2         /          /
  2          3       / \       1          2
   \        /       1   3       \        /
    3      2                     2      1
*/
```

given any BST,it's possible to move to a different configuration using "rotation":
rotateLeft(G):let x be the right child of G,make G the new left child of x
this operation preservessearch tree property,nochange to semaintics of tree

![](media/QQ20250912-151151.png)
↓↓↓
![](media/QQ20250912-151406.png)
↓↓↓
![](media/QQ20250912-151429.png)

can think of as temporarily merging G and P,then sending G down and left:

![](media/QQ20250912-151730.png)

rotateRight(P) turns it to the original shape:

![](media/QQ20250912-152036.png) 

another example:
![](media/QQ20250912-211450.png)

rotating a node right is undefined if that node has no left child,so does rotating a node left if the node has no right child

- now we know:
  - BSTs: can be balanced using rotating,but we have no algorithm for doing so yet
  - 2-3 trees: are balanced by construction,no rotations required
- our goal: 
  - build a BST that is structurally identical to a 2-3 tree
  
create "glue" links with the smaller item off to the left:
![](media/QQ20250912-214829.png)

a BST with left glue links that represents a 2-3 tree is often called a "Left Leaning Red Black Binary Search Tree" a.k.a. LLRB (左倾红黑二叉搜索树)
- LLRBs are normal BSTs
- there is a 1-1 correspondence between an LLRB and an equivalent 2-3 tree
- the red is just a vonvenient fiction,red links don't do anything special ↓↓↓

![](media/QQ20250912-215056.png)
![](media/QQ20250912-231024.png)

practice:
![](media/QQ20250912-231250.png)
![](media/QQ20250912-231840.png)

we implement an LLRB insert as follows:
1. insert as usual into a BST
2. use zero or more rotations to maintain the 1-1 mapping
3. when performing LLRB operation,pretend like it's a 2-3 tree

![](media/QQ20250912-233257.png)
![](media/QQ20250912-234048.png)
![](media/QQ20250912-234121.png)
![](media/QQ20250912-234601.png)
![](media/QQ20250912-235642.png)

summary:
1. when inserting,use a red link
2. if there's a right leaning "3-node",we have a Left Leaning Violation
   - rotate left the appropiate node to fix
3. if there are 2 consecutive left links,we have an Incorrect 4 Node Violation
   - rotate right the appropriate node to fix
4. if there are any nodes with 2 red children,we have a Temporary 4 Node
   - Color flip the node to emulate the split operation
5. Cascading operations: it is possible that a rotation or flip operation will cause an additional violation that needs fixing

![](media/QQ20250912-235851.png)