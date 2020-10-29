实验过程：
1.将exercise2放在webapps文件夹下，尝试运行。
2.修改index.html，添加Form及各个表单项，填写请求url‘Servlet1’，请求方法post，及各个表单项名称。
3.配置web.xml
4.运行，提交表单，获得预期结果。
5.用<center>标签实现居中，并修改部分表单项颜色美化界面。
遇到的问题：
1.误将action的值'Servlet1'写为'/Servlet1'，导致找不到页面，通过调试发现post请求的地址为localhost:8080/Servlet1,正确地址应为localhost:8080/exercise2/Servlet1,故发现action出错，改正后运行成功。