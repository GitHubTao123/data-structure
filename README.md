# data-structure

# HashMap 底层实现 #

1.7之前：数组 + 链表（头插法）
1.8 ： 数组 + 红黑树（尾插法）

当新建一个HashMap的时候，就会初始化一个数组。Entry就是数组中的元素，每个 Map.Entry 其实就是一个key-value对，它持有一个指向下一个元素的引用，这就构成了链表。

![](https://img-blog.csdn.net/20131105152201453?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdmtpbmdfd2FuZw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

（插入的流程）：
	* new hashmap时不会进行初始化，而是在插入数据时进行初始化
	
	1. 判断当前“链表散列”的容量，是否大于初始容量（initial_capacity） * 扩容因子（load_factor）的乘积，若大于，则执行resize()，进行扩容操作
		1.1 在扩容操作中，多线程情况下线程不安全。可能同时有多个线程执行扩容操作，比如两个线程插入数据计算后hash值相同，而且同时需要扩容操作，那么势必会有一个数据产生丢失或异常。

		1.2 若需要进行扩容操作，将初始容量进行*2操作，因此hashmap的容量总是2的次幂（initical_capacity 默认为16），创建一个新数组（newTable）存放数据，之后进行rehash操作，遍历table数组中的每个元素，重新计算hash值，放入newTable中，最终覆盖table

	2. 计算hash值，得到该entry在table数组中的位置，进行头插法，entry.next = table[hash];table[hash] = entry; 进行覆盖

	3. count++

（查询的流程）：
	1. 进行hash计算得到在table数组中的位置
	
	2. 得到table[hash]的链表头，进行遍历查找，当 key.hashCode == entry.key.hashCode && key.key.equals(entry.key) 时返回，否则entry = entry.next查找下一个节点 

# ArrayList 底层实现 #

底层： 数组

new ArrayList时初始化，会调用无参构造器： 
ArrayList (){
	this(10); //指定初始化容量，调用有参构造器
}

类中有一个参数size，用于记录当前数组中已插入数据的个数

（插入的流程）： 
	1. 查看array的容量是否够用 ensureCapacity()，大致流程如下
		1.1 若size = array.length 即已插入的数据个数等于数组能存放的最大个数，执行扩容操作
		1.2 扩容操作就是执行 size + (size >> 1)  (1 + 1/2 = 1.5)，扩容之后根据新容量创建新数组，System.arraycopy进行数组拷贝，覆盖原数组完成扩容操作
	
（删除的流程）：
	1. 执行rangeCheck(index) ，确保参数没有越界
	2. 进行数组拷贝操作，从index+1 位开始复制，到最后一位，跳过index所在的值，在进行size--操作完成remove流程

# LinkedList 底层实现 #

数据结构： 双向链表

类中有Node first; Node last;存储头节点和尾节点

Node 属性有 Node pre、Element e、Node next

在查询、更新、删除时，会根据传入的index 找出接下来依据first节点更近，还是last节点更近，提高操作效率 

# HashSet 底层实现 #

数据结构： 封装后的HashMap

底层就是HashMap，只不过value值固定，在类中有一个类对象：

private static final Object PRESENT = new Object();

此后每次插入的值，value都是这个PRESENT

底层新增、删除操作都是直接调用hashMap的方法即可