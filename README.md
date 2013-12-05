Effective JAVA 笔记
--

豆瓣阅读的批注功能很好用，读中文版觉得不顺的自己做上批注同时设置公开，这样别人看到这里的时候，可以点开批注看看，同时 有好资源也可以顺手链上。
but，中文版翻得太烂了，有必要建立个垃圾译者黑名单了。。。还是自己看英文版吧

#Creating and Destroying Objects

##item1 Consider static factory methods instead of constructors

静态工厂方法的好处：

	 1.有名称，易读易猜
	 2.不可变类：不必要每次都创建新对象：
	*3.隐藏实现。返回工厂方法指定的返回类型的的任何子类对象。隐藏具体对象的构造、组装。
	 4.参数化调用时，代码简洁

比如：

		Map<String, List<String>> m = new HashMap<String,List<String>>();

可以简化为

		public static <K,V>HashMap<K,V> newInstance(){
			return new HashMap<K,V>();
		}

然后调用

	Map<String, List<String>> m = HashMap.newInstance()


坏处：

	1.如果不含public、protect constructor，不能被子类化
	2.现有的javadoc 不能很好的把他们区分起来，找起来麻烦

实例：

非public类+static 方法

	java.util.Collections:32个非public类+static 方法。

Service provider Framework

	Three essential components: 

	-Service Interface:对外提供服务的接口
	-Service Provider Registration API:注册Provider：我provider，可以提供服务，我叫provider-xx。Service manager根据名字查找client指定的服务类
	-Services access API:用户直接接触的调用入口
	-opt:Service provider interface:


如果没有service provider interface,则通过类名进行注册，通过反射进行实例化操作


源码阅读

	h2-1.3.174-sources中jdbc的实现
	JMS

延伸阅读:

Guava https://code.google.com/p/guava-libraries/wiki/NewCollectionTypesExplained

	BiMap
	Map<String, Integer> nameToId = Maps.newHashMap();
	Map<Integer, String> idToName = Maps.newHashMap();

使用复合（composition），而不是继承
类型推导

##Item 2: Consider a builder when faced with many constructor parameters

http://javapapers.com/design-patterns/builder-pattern/

和GOF的build pattern有点关系又不太相同
>In builder pattern emphasis is on ‘step by step’.
常见的场景：可选参数不固定，且所需的全部参数需要一步步的交互来搞定，协作开发，别人调用你的constructor，发现方法签名不能变，不然一不小心对方的代码就会编译不过.

和staticFactory区别：
>...But in abstract factory how complex the built object might be, it will not have step by step object construction.

>Builder pattern is used when there are many steps to create an object. Factory pattern is used when the factory can easily create the entire object within one method call.

为什么一定要用builder？可读性！

http://dreamhead.blogbus.com/logs/17667876.html

>Kent Beck在他的《Implementation Patterns》的第三章《A Theory Of Programming》中，谈到了编程的价值观（Value）：Communication（沟通）、Simplicity（简单）和Flexibility（灵活）。如果说简单和灵活很容易理解的话，那么把沟通放在价值观中，尤其排在所有价值观的第一位，则显现出Kent Beck对于编程的深刻。在这个软件开发越来越需要协作的年代，写代码的时候，多站在让别人理解的角度考虑一下，会极大提升代码的可读性。在ThoughtWorks的招聘流程中，有一个Code Review的环节，拜这个环节所赐，我看过很多人的代码，不在少数的应聘者其代码唯一的优点就是完成了需求。以沟通为标准进行衡量，这显然是不够的。

解决方法1：重复构造：telescoping constructor：添加一个新的构造，通知对方调用新的,比如

	public class NutritionFacts {
		private final int servingSize;
		private final int servings;
		private final int calories;
		private final int fat;
		private final int sodium;
		privatefinalintcarbohydrate;
		public NutritionFacts(int servingSize, int servings) {
		    this(servingSize, servings, 0);
		}
		public NutritionFacts(int servingSize, int servings,
		        int calories) {
		    this(servingSize, servings, calories, 0);
		}
		public NutritionFacts(int servingSize, int servings,
		        int calories, int fat) {
		    this(servingSize, servings, calories, fat, 0);
		}
		public NutritionFacts(int servingSize, int servings,
		        int calories, int fat, int sodium) {
		    this(servingSize, servings, calories, fat, sodium, 0);
		}
	}


有强迫症的你，有时候选择一个最少包含你所需设置参数的构造，却发现某个值不想设置，却不得不传递，调用哪个都不舒服。而且都是int，如果参数顺序不小心搞错，隐藏的bug很难跟踪

In short, the telescoping constructor pattern works, but it is hard to write client code when there are many parameters, and harder still to read it. 

解决方法2：JavaBeans pattern：cmd+n:new getter/setter，额 好臃肿

缺点：构造过程不连续。也就不能保证一个对象的使用过程中对象状态是连续的：precludes immutable、difficult to ensure thread safety
如果一不小心set错一个值，可能在后续的代码不定什么地方会引起致命错误，却要回溯很远才能定位到，md，原来set错了

This means that some part of the client application might see this object and assume that is already constructed while that’s actually not the case.
The second disadvantage of this approach is that now the User class is mutable. You’re loosing all the benefits of immutable objects.

http://remonstrate.wordpress.com/2013/03/11/java-%E7%9A%84-idiom%EF%BC%887%EF%BC%89/
>如果正在为一个 immutable 类提供构造函数，需要注意其成员是否有 mutable 的，否则就算这个类没提供相应的修改状态的函数，但由于传递引用，caller 仍然有机会在构造了这个对象后，通过原先对这个 mutable 成员的引用进行修改，这种情况下需要进行 defensive copy，即对 mutable 成员进行 copy，保证这部分成员与外界断绝关系。通常这发生在参数检查以前。检查应该针对复制后的进行。同时返回成员的时候，mutable 只能返回 defensive copy 而不能返回直接的引用。


解决方法3:
Builder

	// Builder Pattern - Pages 14-15
	package org.effectivejava.examples.chapter02.item02.builder;
	
	public class NutritionFacts {
		private final int servingSize;
		private final int servings;
		private final int calories;
		private final int fat;
		private final int sodium;
		private final int carbohydrate;
	
		public static class Builder {
			// Required parameters
			private final int servingSize;
			private final int servings;
	
			// Optional parameters - initialized to default values
			private int calories = 0;
			private int fat = 0;
			private int carbohydrate = 0;
			private int sodium = 0;
	
			public Builder(int servingSize, int servings) {
				this.servingSize = servingSize;
				this.servings = servings;
			}
	
			public Builder calories(int val) {
				calories = val;
				return this;
			}
	
			public Builder fat(int val) {
				fat = val;
				return this;
			}
	
			public Builder carbohydrate(int val) {
				carbohydrate = val;
				return this;
			}
	
			public Builder sodium(int val) {
				sodium = val;
				return this;
			}
	
			public NutritionFacts build() {
				return new NutritionFacts(this);
			}
		}
	
		private NutritionFacts(Builder builder) {
			servingSize = builder.servingSize;
			servings = builder.servings;
			calories = builder.calories;
			fat = builder.fat;
			sodium = builder.sodium;
			carbohydrate = builder.carbohydrate;
		}
	
		public static void main(String[] args) {
			NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
					.calories(100).sodium(35).carbohydrate(27).build();
		}
	}

	NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
			.calories(100).sodium(35).carbohydrate(27).build();

使用：Builder(240, 8):构造参数设置必选参数，可选参数链式自选设置，最后调用一下build()，一气把所有改设置的设上。

延伸：Ada and Python：named optional parameters

`This client code is easy to write and, more importantly, to read.`

好处：
1、参数检查：check the invariants:

检查域：在class filed上面做检查 而不是在builder fields里面做检查
检查时机：因builder本身不是线程安全的，在创建之前的，处于窗口期，很可能被其他线程修改了。
在set完毕后，build()就可以对已生成对象的参数进行检查并今早确定是否符合约束,提前抛出IllegalStateException异常，而不是导致运行时出错。
It is critical that they be checked after copying the parameters from the builder to the object, and that they be checked on the object fields rather than the builder fields (Item 39).
参考：http://swordinhand.iteye.com/blog/1264191

	Thread-safe
	public User build() {
	    User user = new user(this);
	    if (user.getAge() > 120) {
	        throw new IllegalStateException(“Age out of range”); // thread-safe
	    }
	    return user;
	}	

	not thread-safe	
	public User build() {
	    if (age > 120) {
	        throw new IllegalStateException(“Age out of range”); // bad, not thread-safe
	    }
	    // This is the window of opportunity for a second thread to modify the value of age
	    return new User(this);
	}

2、varargs:

构造函数只能包含一个可变参数，build可以有多个

3、builder 可以做为参数传递给其他方法

4、A final advantage of this pattern is that a builder could be passed to a method to enable this method to create one or more objects for the client

缺点：
1、性能While the cost of creating the builder is unlikely to be noticeable in practice, it could be a problem in some performance- critical situations. 
2、冗长
so it should be used only if there are enough parameters, say, four or more. 
同时如果需要用的话也趁早，不然等到参数越来越多时候再用，the obsolete constructors or static factories will stick out like a sore thumb.

场景：
removing the unnecessary complexity that stems from multiple constructors, multiple optional parameters and overuse of setters.

//构建器像构造函数一样，加强了参数的约束条件。像"Builder(240, 8)"就代表servingSize和servings必填。          //又可以保证在多个备选参数间拥有良好的可读性。同时NutritionFacts2中的各指标是final约束的，又可以保证线程安全。         //这便是构建器的典型应用。          //“构建器”模式特别适合应用在创建拥有多个备选参数的对象时使用。          //在日常开发时DAO时，将查询到的单条表记录(ResultSet)转换为(POJO、DTO、ENTITIES)对象的过程就可以考虑使用Builder模式进行。

Tools：
我用intellij：
系统自带refactor：http://www.jetbrains.com/idea/webhelp/replace-constructor-with-builder.html
其他：
http://plugins.jetbrains.com/plugin/6585
http://builderplugin.googlecode.com/

如果你用eclipse 插件

http://code.google.com/p/bpep/

http://code.google.com/a/eclipselabs.org/p/bob-the-builder/ 

自动生成validate方法，但是实在create new 之前校验的。

	
	public FuBaz build() {
      validate();
      return new FuBaz(this);
    }
    private void validate() {
      Validate.notNull(longs, "longs may not be null");
      Validate.isTrue(!longs.isEmpty(), "longs may not be empty");
      Validate.isTrue(id > 0L, "id should be set");
      Validate.isTrue(!StringUtils.isBlank(description), "description may not be blank");
    } 


http://code.google.com/p/fluent-builders-generator-eclipse-plugin/ 有不错的useage

更严格的Builder：
http://java.dzone.com/articles/dive-builder-pattern
1、编译时校验调用是否正确：分两步,一个预校验，只有调用了指定的with方法，才允许使用下一步的builder
2、Builder methods have to be called in a certain order. 
3、Even more complex, some steps are dependent on previous steps.

延伸阅读
DSL
defensive copy
防御式编程 http://en.wikipedia.org/wiki/Defensive_programming 
http://jlordiales.wordpress.com/2012/12/13/the-builder-pattern-in-practice
http://www.programcreek.com/2013/02/java-design-pattern-builder/
GOF builder pattern http://javapapers.com/design-patterns/builder-pattern/
使用Java实现内部领域特定语言 http://dreamhead.blogbus.com/logs/17667876.html


