PartitionStatus
===============

Java编写的用于多线程获取腾讯云Linux系统磁盘分区状态的小工具，可以生成Excel或者HTML文件


运行环境
===============

* Windows,Linux,Unix 皆可运行


依赖
===============

* JDK1.6+


适用环境
===============

* Linux系统


目录说明
===============

> getPartitionStatus.jar  ---- 主程序

> start.bat   ---- windows系统上运行主程序的脚本

> start.sh   ---- Linux系统上运行主程序的脚本

> conf      ---- 为配置文件目录

> lib       ---- 依赖库文件目录

> result    ---- 生成的结果存放目录


注意事项
===============

* 注意使用之前，先把token关闭

* 所有主机的用户名和密码需相同

* 把所有的IP写入conf/servers_ip.txt,也可以在conf/configure.properties中指定

* 用户名和密码在conf/configure.properties中指定，密码请用base64对密码进行加密

* HTTP代理服务器在conf/configure.properties中指定

* 在conf/configure.properties中指定获取分区状态的命令,命令的类似下面(目前每一台服务器仅能获取一个分区的状态):

        df -hP | grep data | awk '$1 ~ /^\//{print $NF,$2,$3,$4,$5}' 
        
        结果如下依次是: 分区 总大小 已经使用的大小 剩余大小 已经使用的百分比
        
        /data 197G 82G 106G 44%


最终效果
===============

* Excel文件效果

![Excel](/demo/excel_demo.png)


* html文件效果

![html](/demo/html_demo.jpg)