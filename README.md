# egova_invoker

服务器端调用：
	HttpInvoker.invoker 方法，设置InvokerProcess处理
	
客户端调用：
	ProxyFactory.proxy 方法，生成接口的代理对象，直接调用方法
	
客户端和服务器端的接口和实体类要一致，实体类必须是可以序列化的