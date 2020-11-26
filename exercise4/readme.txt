Catfish:定义鱼的位置、是否存活、能量等属性，返回鱼的属性，实现鱼的随机游动（位置与能量的改变）、年龄增长。
HtmlAnchor:生成一个链接的HTML
HtmlImage：生成图片的HTML
HtmlPage：生成网页的HTML
HtmlTable：生成表格的HTML并配置行列等参数
LivingBeing：定义一个抽象类，规定生物对象需要实现的方法
MySimulation：实现一个servlet，处理post请求，返回一个选择鱼的初始位置、设定时间的表单界面。
Simulation:定义一个鱼群和框的整体，控制框中鱼群的动态行为。
SimulationServlet：实现一个servlet，返回某一时间所有鱼群位置的界面。
SimulationView:生成fishSimulation页面的HTML

两个HTML文件的不同：
请求的接口不同，MySimulation.html请求的 是MySimulation，目的是重新显示表单。initialWorldFish.html请求的是SimulationServlet，目的是开始模拟鱼群运动。



