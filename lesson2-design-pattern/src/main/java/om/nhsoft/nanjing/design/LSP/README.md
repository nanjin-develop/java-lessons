## 什么是里氏代换原则

里氏代换原则是由麻省理工学院（MIT）计算机科学实验室的Liskov女士，在1987年的OOPSLA大会上发表的一篇文章《Data Abstraction and Hierarchy》里面提出来的，主要阐述了有关继承的一些原则，也就是什么时候应该使用继承，什么时候不应该使用继承，以及其中的蕴涵的原理。2002年，我们前面单一职责原则中提到的软件工程大师Robert C. Martin，出版了一本《Agile Software Development Principles Patterns and Practices》，在文中他把里氏代换原则最终简化为一句话：“Subtypes must be substitutable for their base types”。也就是，子类必须能够替换成它们的基类。
    我们把里氏代换原则解释得更完整一些：在一个软件系统中，子类应该可以替换任何基类能够出现的地方，并且经过替换以后，代码还能正常工作。

#### 第一个例子：正方形不是长方形
“正方形不是长方形”是一个理解里氏代换原则的最经典的例子。在数学领域里，正方形毫无疑问是长方形，它是一个长宽相等的长方形。所以，我们开发的一个与几何图形相关的软件系统中，让正方形继承自长方形是顺利成章的事情。现在，我们截取该系统的一个代码片段进行分析：
长方形类Rectangle:

```
class Rectangle {
  double length;
  double width;
  public double getLength() { return length; } 
  public void setLength(double height) { this.length = length; }   
  public double getWidth() { return width; }
  public void setWidth(double width) { this.width = width; } 
}
正方形类Square:
class Square extends Rectangle {
  public void setWidth(double width) {
    super.setLength(width);
    super.setWidth(width);   
 }
  public void setLength(double length) { 
    super.setLength(length);
    super.setWidth(length);   
  } 
}
```

由于正方形的度和宽度必须相等，所以在方法setLength和setWidth中，对长度和宽度赋值相同。类TestRectangle是我们的软件系统中的一个组件，它有一个resize方法要用到基类Rectangle，resize方法的功能是模拟长方形宽度逐步增长的效果:

```
  测试类TestRectangle：
class TestRectangle {
  public void resize(Rectangle objRect) {
    while(objRect.getWidth() <= objRect.getLength()  ) {
        objRect.setWidth(  objRect.getWidth () + 1 );
    }
  }
}
```
我们运行一下这段代码就会发现，假如我们把一个普通长方形作为参数传入resize方法，就会看到长方形宽度逐渐增长的效果，当宽度大于长度,代码就会停止，这种行为的结果符合我们的预期；假如我们再把一个正方形作为参数传入resize方法后，就会看到正方形的宽度和长度都在不断增长，代码会一直运行下去，直至系统产生溢出错误。所以，普通的长方形是适合这段代码的，正方形不适合。
    我们得出结论：在resize方法中，Rectangle类型的参数是不能被Square类型的参数所代替，如果进行了替换就得不到预期结果。因此，Square类和Rectangle类之间的继承关系违反了里氏代换原则，它们之间的继承关系不成立，正方形不是长方形。

### 3 第二个例子：鸵鸟不是鸟
“鸵鸟非鸟”也是一个理解里氏代换原则的经典的例子。“鸵鸟非鸟”的另一个版本是“企鹅非鸟”，这两种说法本质上没有区别，前提条件都是这种鸟不会飞。生物学中对于鸟类的定义：“恒温动物，卵生，全身披有羽毛，身体呈流线形，有角质的喙，眼在头的两侧。前肢退化成翼，后肢有鳞状外皮，有四趾”。所以，从生物学角度来看，鸵鸟肯定是一种鸟。
我们设计一个与鸟有关的系统，鸵鸟类顺理成章地由鸟类派生，鸟类所有的特性和行为都被鸵鸟类继承。大多数的鸟类在人们的印象中都是会飞的，所以，我们给鸟类设计了一个名字为fly的方法，还给出了与飞行相关的一些属性,比如飞行速度（velocity）。

```
  鸟类Bird:
class Bird {
   double velocity;
   public fly() { //I am flying; };

   public setVelocity(double velocity) { this.velocity = velocity; };
   public getVelocity() { return this.velocity; };
}
    鸵鸟不会飞怎么办？我们就让它扇扇翅膀表示一下吧，在fly方法里什么都不做。至于它的飞行速度，不会飞就只能设定为0了，于是我们就有了鸵鸟类的设计。
   鸵鸟类Ostrich:
class Ostrich extends Bird {
    public fly() { //I do nothing; };
    public setVelocity(double velocity) { this.velocity = 0; };
   public getVelocity() { return 0; };
}
    好了，所有的类都设计完成，我们把类Bird提供给了其它的代码（消费者）使用。现在，消费者使用Bird类完成这样一个需求：计算鸟飞越黄河所需的时间。
    对于Bird类的消费者而言，它只看到了Bird类中有fly和getVelocity两个方法，至于里面的实现细节，它不关心，而且也无需关心，于是给出了实现代码：
   测试类TestBird:
class TestBird {
   public calcFlyTime(Bird bird) {
   try{
     double riverWidth = 3000;
     System.out.println(riverWidth / bird.getVelocity());
   }catch(Exception err){
     System.out.println("An error occured!");
   }
   };
}
```

如果我们拿一种飞鸟来测试这段代码，没有问题，结果正确，符合我们的预期，系统输出了飞鸟飞越黄河的所需要的时间；如果我们再拿鸵鸟来测试这段代码，结果代码发生了系统除零的异常，明显不符合我们的预期。
    对于TestBird类而言，它只是Bird类的一个消费者，它在使用Bird类的时候，只需要根据Bird类提供的方法进行相应的使用，根本不会关心鸵鸟会不会飞这样的问题，而且也无须知道。它就是要按照“所需时间 = 黄河的宽度 / 鸟的飞行速度”的规则来计算鸟飞越黄河所需要的时间。
    我们得出结论：在calcFlyTime方法中，Bird类型的参数是不能被Ostrich类型的参数所代替，如果进行了替换就得不到预期结果。因此，Ostrich类和Bird类之间的继承关系违反了里氏代换原则，它们之间的继承关系不成立，鸵鸟不是鸟。


### 4 鸵鸟到底是不是鸟?
“鸵鸟到底是不是鸟”，鸵鸟是鸟也不是鸟，这个结论似乎就是个悖论。产生这种混乱有两方面的原因：

**原因一**：对类的继承关系的定义没有搞清楚。
    面向对象的设计关注的是对象的行为，它是使用“行为”来对对象进行分类的，只有行为一致的对象才能抽象出一个类来。我经常说类的继承关系就是一种“Is-A”关系，实际上指的是行为上的“Is-A”关系，可以把它描述为“Act-As”。关于类的继承的细节，我们可以单独再讲。
    我们再来看“正方形不是长方形”这个例子，正方形在设置长度和宽度这两个行为上，与长方形显然是不同的。长方形的行为：设置长方形的长度的时候，它的宽度保持不变，设置宽度的时候，长度保持不变。正方形的行为：设置正方形的长度的时候，宽度随之改变；设置宽度的时候，长度随之改变。所以，如果我们把这种行为加到基类长方形的时候，就导致了正方形无法继承这种行为。我们“强行”把正方形从长方形继承过来，就造成无法达到预期的结果。
    “鸵鸟非鸟”基本上也是同样的道理。我们一讲到鸟，就认为它能飞，有的鸟确实能飞，但不是所有的鸟都能飞。问题就是出在这里。如果以“飞”的行为作为衡量“鸟”的标准的话，鸵鸟显然不是鸟；如果按照生物学的划分标准：有翅膀、有羽毛等特性作为衡量“鸟”的标准的话，鸵鸟理所当然就是鸟了。鸵鸟没有“飞”的行为，我们强行给它加上了这个行为，所以在面对“飞越黄河”的需求时，代码就会出现运行期故障。


**原因二** ：设计要依赖于用户要求和具体环境。
    继承关系要求子类要具有基类全部的行为。这里的行为是指落在需求范围内的行为. A需求期望鸟类提供与飞翔有关的行为，即使鸵鸟跟普通的鸟在外观上就是100%的相像，但在A需求范围内，鸵鸟在飞翔这一点上跟其它普通的鸟是不一致的，它没有这个能力，所以，鸵鸟类无法从鸟类派生，鸵鸟不是鸟。
    B需求期望鸟类提供与羽毛有关的行为，那么鸵鸟在这一点上跟其它普通的鸟一致的。虽然它不会飞，但是这一点不在B需求范围内，所以，它具备了鸟类全部的行为特征，鸵鸟类就能够从鸟类派生，鸵鸟就是鸟。

所有派生类的行为功能必须和使用者对其基类的期望保持一致，如果派生类达不到这一点，那么必然违反里氏替换原则。在实际的开发过程中，不正确的派生关系是非常有害的。伴随着软件开发规模的扩大，参与的开发人员也越来越多，每个人都在使用别人提供的组件，也会为别人提供组件。最终，所有人的开发的组件经过层层包装和不断组合，被集成为一个完整的系统。每个开发人员在使用别人的组件时，只需知道组件的对外裸露的接口，那就是它全部行为的集合，至于内部到底是怎么实现的，无法知道，也无须知道。所以，对于使用者而言，它只能通过接口实现自己的预期，如果组件接口提供的行为与使用者的预期不符，错误便产生了。里氏代换原则就是在设计时避免出现派生类与基类不一致的行为。

### 5 如何正确地运用里氏代换原则
   里氏代换原则目的就是要保证继承关系的正确性。我们在实际的项目中，是不是对于每一个继承关系都得费这么大劲去斟酌？不需要，大多数情况下按照“Is-A”去设计继承关系是没有问题的，只有极少的情况下，需要你仔细处理一下，这类情况对于有点开发经验的人，一般都会觉察到，是有规律可循的。最典型的就是使用者的代码中必须包含依据子类类型执行相应的动作的代码：
动物类Animal：

```
public class Animal{
  String name;
  public Animal(String name) {
    this.name = name;
 }
  public void printName(){
  try{
     System.out.println("I am a " + name + "!");
  }catch(Exception err){
     System.out.println("An error occured!");
  }
}
}
猫类Cat：
public class Cat extends Animal{
  public Cat(String name){
    super(name);
  }
  public void Mew(){
  try{
       System.out.println("Mew~~~ ");
  }catch(Exception err){
       System.out.println("An error occured!");
  }
 }
}
狗类Dog：
public class Dog extends Animal {
  public Dog(String name) {
    super(name);
 }
 public void Bark(){
 try{
     System.out.println("Bark~~~ ");
 }catch(Exception err){
     System.out.println("An error occured!");
 }
}
}
测试类：TestAnimal
public class TestAnimal {
   public void TestLSP(Animal animal){
     if (animal instanceof Cat ){
         Cat cat = (Cat)animal;
         cat.printName();
         cat.Mew();
     }
     if (animal instanceof Dog ){

       Dog dog = (Dog)animal;
       dog.printName();
       dog.Bark();
     }
 }
}
```

象这种代码是明显不符合里氏代换原则的，它给使用者使用造成很大的麻烦，甚至无法使用，对于以后的维护和扩展带来巨大的隐患。实现开闭原则的关键步骤是抽象化，基类与子类之间的继承关系就是一种抽象化的体现。因此，里氏代换原则是实现抽象化的一种规范。违反里氏代换原则意味着违反了开闭原则，反之未必。里氏代换原则是使代码符合开闭原则的一个重要保证。

我们常见这样的代码，至少我以前的Java和php项目中就出现过。比如有一个网页，要实现对于客户资料的查看、增加、修改、删除功能，一般Server端对应的处理类中都有这么一段：

```
if(action.Equals(“add”)){
  //do add action
}
else if(action.Equals(“view”)){
  //do view action
}
else if(action.Equals(“delete”)){
  //do delete action
}
else if(action.Equals(“modify”)){
  //do modify action
}
```

大家都很熟悉吧，其实这是违背里氏代换原则的，结果就是可维护性和可扩展性会变差。有人说：我这么用，效果好像不错，干嘛讲究那么多呢，实现需求是第一位的。另外，这种写法看起来很很直观的，有利于维护。其实，每个人所处的环境不同，对具体问题的理解不同，难免局限在自己的领域内思考问题。对于这个说法，我觉得应该这么解释：作为一个设计原则，是人们经过很多的项目实践，最终提炼出来的指导性的内容。如果对于你的项目来讲，显著增加了工作量和复杂度，那我觉得适度的违反并不为过。做任何事情都是个度的问题，过犹不及都不好。在大中型的项目中，是一定要讲究软件工程的思想，讲究规范和流程的，否则人员协作和后期维护将会是非常困难的。对于小型的项目可能相应的要简化很多，可能取决于时间、资源、商业等各种因素，但是多从软件工程的角度去思考问题，对于系统的健壮性、可维护性等性能指标的提高是非常有益的。像生命周期只有一个月的系统，你还去考虑一大堆原则，除非脑袋被驴踢了。
    实现开闭原则的关键步骤是抽象化，基类与子类之间的继承关系就是一种抽象化的体现。因此，里氏代换原则是实现抽象化的一种规范。违反里氏代换原则意味着违反了开闭原则，反之未必。里氏代换原则是使代码符合开闭原则的一个重要保证。

通过里氏代换原则给我们带来了什么样的启示？
    类的继承原则：如果一个继承类的对象可能会在基类出现的地方出现运行错误，则该子类不应该从该基类继承，或者说，应该重新设计它们之间的关系。
    动作正确性保证：符合里氏代换原则的类扩展不会给已有的系统引入新的错误。

### 里氏替换原则为良好的继承定义了一个规范，它包含了4层定义：
* 子类必须完全实现父类的方法。
* 子类可以有自己的个性。
* 覆盖或实现父类的方法时输入参数可以被放大。
* 覆写或实现父类的方法时输出结果可以被缩小。
* 采用里氏替换原则的目的就是增强程序的健壮性，版本升级时也可以保持非常好的兼容性。即使增加子类，原有的子类还可以继续运行。在实际项目中，每个子类对应不同的业务含义，使用父类作为参数，传递不同的子类完成不同的业务逻辑，非常完美。

    
### 问题：JAVA中，多态是不是违背了里氏替换原则？？