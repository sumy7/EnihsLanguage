EnihsLangguage
==================
基于 Java 的~~面向对象~~无类型自带虚拟机解释型脚本语言  

In building

# 开始

目前解释器支持一下程序的执行：  

Example1:  
普通函数（只有`print`）

    print "Hello World"

Example2:  
分支语句（ `if`）

    a = 0
    b = 0
    if a>b {
        print "max num is ", a
    } else {
        print "max num is ", b
    }

Example3:
循环语句（只有`while`）

    sum = 0
    i = 0
    while i<10 {
        i = i + 1
        sum = sum + i
    }
    print "sum from 1 to 10 is", sum

# 感谢
