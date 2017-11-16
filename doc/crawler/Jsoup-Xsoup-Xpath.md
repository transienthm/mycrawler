# 从Xpath说起
##  什么是Xpath
XPath是W3C的一个标准。它最主要的目的是为了在XML1.0或XML1.1文档节点树中定位节点所设计。
XPath是一种表达式语言，它的返回值可能是节点，节点集合，原子值，以及节点和原子值的混合等。

## 语法
### 选取结点
| 表达式 | 描述 |
| -- | --|
| / | 从根节点选取 |
| // | 从匹配选择的当前节点选择文档中的节点，而不考虑它们的位置。 |
| . | 选取当前结点 |
| .. | 选取当前节点的父结点 |
| @ | 选取属性 |

#### 实例

| 路径表达式	| 结果 |
| -- | -- |
| bookstore |	选取 bookstore 元素的所有子节点。 |
| /bookstore | 选取根元素 bookstore。注释：假如路径起始于正斜杠( / )，则此路径始终代表到某元素的绝对路径！|
| bookstore/book	| 选取属于 bookstore 的子元素的所有 book 元素。 |
| //book	| 选取所有 book 子元素，而不管它们在文档中的位置。 |
| bookstore//book	| 选择属于 bookstore 元素的后代的所有 book 元素，而不管它们位于 bookstore 之下的什么位置。 |
| //@lang	| 选取名为 lang 的所有属性。|

### 谓语
| 表达式 | 描述 |
| -- | --|
| /bookstore/book[1]	| 选取属于 bookstore 子元素的第一个 book 元素。
| /bookstore/book[last()]	| 选取属于 bookstore 子元素的最后一个 book 元素。|
|/bookstore/book[last()-1]	| 选取属于 bookstore 子元素的倒数第二个 book 元素。|
| /bookstore/book[position()<3]	| 选取最前面的两个属于 bookstore 元素的子元素的 book 元素。|
| //title[@lang]	| 选取所有拥有名为 lang 的属性的 title 元素。
| //title[@lang='eng']	| 选取所有 title 元素，且这些元素拥有值为 eng 的 lang 属性。
| /bookstore/book[price>35.00]	| 选取 bookstore 元素的所有 book 元素，且其中的 price 元素的值须大于 35.00。|
| /bookstore/book[price>35.00]/title	| 选取 bookstore 元素中的 book 元素的所有 title 元素，且其中的 price 元素的值须大于 35.00。 |

### 选取未知节点

| 通配符 |	描述 |
| -- | --|
|*	| 匹配任何元素节点。|
|@* |	匹配任何属性节点。 |
| node()	| 匹配任何类型的节点。 |

#### 实例

| 路径表达式	| 结果 |
| -- | --|
| /bookstore/*	| 选取 bookstore 元素的所有子元素。 |
| //*	| 选取文档中的所有元素。|
|//title[@*]	| 选取所有带有属性的 title 元素。 |

> 参考：http://www.w3school.com.cn/xpath/

暂时整理这些，如有需要，可去w3school查阅

# Jsoup
Jsoup 是一款 Java 的 HTML 解析器，可直接解析某个 URL 地址、HTML 文本内容。
## 从字符串中解析
```
String html = "<html><head><title>First parse</title></head><body><p>Parsed HTML into a doc.</p></body></html>";
Document doc = Jsoup.parse(html);
```
## 从URL加载一个Document
```
Document doc = Jsoup.connect("https://www.qq.com").get();
String title = doc.title();
```
值得一提的是，connect()方法可以返回一个新的Connection对象，Connection接口还提供一个方法链来解决特殊请求，具体如下：
```
Document doc = Jsoup.connect("http://example.com")
  .data("query", "Java")
  .userAgent("Mozilla")
  .cookie("auth", "token")
  .timeout(3000)
  .post();
```
从URL加载dom还可以
```
Document dom = Jsoup.parse(new URL("https://www.qq.com"), 10000);
```
## 从一个文件中加载一个dom
```
File input = new File("/tmp/input.html");
Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
```
parse(File in, String charsetName, String baseUri) 这个方法用来加载和解析一个HTML文件。如在加载文件的时候发生错误，将抛出IOException，应作适当处理。
baseUri 参数用于解决文件中URLs是相对路径的问题。如果不需要可以传入一个空的字符串。

## 遍历document的api
查找元素
getElementById(String id)
getElementsByTag(String tag)
getElementsByClass(String className)
getElementsByAttribute(String key) (and related methods)
Element siblings: siblingElements(), firstElementSibling(), lastElementSibling(); nextElementSibling(), previousElementSibling()
Graph: parent(), children(), child(int index)
元素数据
attr(String key)获取属性attr(String key, String value)设置属性
attributes()获取所有属性
id(), className() and classNames()
text()获取文本内容text(String value) 设置文本内容
html()获取元素内HTMLhtml(String value)设置元素内的HTML内容
outerHtml()获取元素外HTML内容
data()获取数据内容（例如：script和style标签)
tag() and tagName()
操作HTML和文本
append(String html), prepend(String html)
appendText(String text), prependText(String text)
appendElement(String tagName), prependElement(String tagName)
html(String value)
## **通过选择器解析**
Selector选择器概述
```
tagname: 通过标签查找元素，比如：a
ns|tag: 通过标签在命名空间查找元素，比如：可以用 fb|name 语法来查找 <fb:name> 元素
#id: 通过ID查找元素，比如：#logo
.class: 通过class名称查找元素，比如：.masthead
[attribute]: 利用属性查找元素，比如：[href]
[^attr]: 利用属性名前缀来查找元素，比如：可以用[^data-] 来查找带有HTML5 Dataset属性的元素
[attr=value]: 利用属性值来查找元素，比如：[width=500]
[attr^=value], [attr$=value], [attr*=value]: 利用匹配属性值开头、结尾或包含属性值来查找元素，比如：[href*=/path/]
[attr~=regex]: 利用属性值匹配正则表达式来查找元素，比如： img[src~=(?i)\.(png|jpe?g)]
*: 这个符号将匹配所有元素
Selector选择器组合使用
el#id: 元素+ID，比如： div#logo
el.class: 元素+class，比如： div.masthead
el[attr]: 元素+class，比如： a[href]
任意组合，比如：a[href].highlight
ancestor child: 查找某个元素下子元素，比如：可以用.body p 查找在"body"元素下的所有 p元素
parent > child: 查找某个父元素下的直接子元素，比如：可以用div.content > p 查找 p 元素，也可以用body > * 查找body标签下所有直接子元素
siblingA + siblingB: 查找在A元素之前第一个同级元素B，比如：div.head + div
siblingA ~ siblingX: 查找A元素之前的同级X元素，比如：h1 ~ p
el, el, el:多个选择器组合，查找匹配任一选择器的唯一元素，例如：div.masthead, div.logo
伪选择器selectors
:lt(n): 查找哪些元素的同级索引值（它的位置在DOM树中是相对于它的父节点）小于n，比如：td:lt(3) 表示小于三列的元素
:gt(n):查找哪些元素的同级索引值大于n，比如： div p:gt(2)表示哪些div中有包含2个以上的p元素
:eq(n): 查找哪些元素的同级索引值与n相等，比如：form input:eq(1)表示包含一个input标签的Form元素
:has(seletor): 查找匹配选择器包含元素的元素，比如：div:has(p)表示哪些div包含了p元素
:not(selector): 查找与选择器不匹配的元素，比如： div:not(.logo) 表示不包含 class=logo 元素的所有 div 列表
:contains(text): 查找包含给定文本的元素，搜索不区分大不写，比如： p:contains(jsoup)
:containsOwn(text): 查找直接包含给定文本的元素
:matches(regex): 查找哪些元素的文本匹配指定的正则表达式，比如：div:matches((?i)login)
:matchesOwn(regex): 查找自身包含文本匹配指定正则表达式的元素
注意：上述伪选择器索引是从0开始的，也就是说第一个元素索引值为0，第二个元素index为1等
可以查看Selector API参考来了解更详细的内容
```
## 修改数据 （暂时懒得复制粘贴了）

# Xsoup 即将到来
