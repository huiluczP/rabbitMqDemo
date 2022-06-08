# rabbitMqDemo
Verification Code acquisition system achieved by message queue.

基于springboot实现的利用消息队列的验证码获取。为了方便，实现的时候在一个项目下搭建了两个模块，这样能共用一些类和配置。</br>
同时每个模块都有各自的启动类。消息队列用了RabbitMq。</br>

请求模块 rabbitMqServer</br>
实现功能：</br>
* 验证码页面请求
* 即时返回前端请求提示，并将发送验证码的消息传递给消息队列
* 页面接受验证码输入，redis中查询，完成校验

发送模块 rabbitMqClient</br>
实现功能：</br>
* 消息队列监听，获取消息
* 消费消息，redis限时存储验证码，验证码邮件发送

主要就是通过两个模块来模拟两个服务通过消息队列的异步通信。</br>
具体分析：[消息队列实现验证码请求](https://blog.csdn.net/qq_41733192/article/details/125182620)
