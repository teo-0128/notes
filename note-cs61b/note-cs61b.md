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
- 8 primitive types in java:`byte``shore``int``long``float``double``boolean``char`
- everything else,including arrays,is a "reference type"(引用类型)

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
a.k.a
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
[5] -> [10] -> [15] -> null
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
        p = p.rest;
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