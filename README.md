# egova_invoker

基于Spring的HttpInvoker实现改写

服务器端调用：
	HttpInvoker.invoker 方法，设置InvokerProcess处理
	
客户端调用：
	ProxyFactory.proxy 方法，生成接口的代理对象，直接调用方法
	
客户端和服务器端的接口和实体类要一致，实体类必须是可以序列化的

比如：
	定义Bean：
	

		public class DemoBean implements java.io.Serializable{
		
			/**
			 * 
			 */
			private static final long serialVersionUID = -5401380996187961689L;
		
			private String name;
			
			private String passWork;
			
			//省略get,set
		}
	定义接口:
		
		public interface DemoService {
		
			public Object doTest(DemoBean bean);
			
		}
		
	客户端调用:
		import com.egova.rpc.proxy.ProxyFactory;

		public class ClientDemo {
			public static void main(String[] args) {
				String bindUrl = "服务器端请求地址/egova_invoker/invoker";
				DemoService demoService = ProxyFactory.proxy(DemoService.class, bindUrl);
				DemoBean  demo = new DemoBean();
				DemoBean  value = demoService.doTest(demo);
				System.out.println(value);
			}
		}
		
	服务端处理:
		import javax.servlet.http.HttpServletRequest;
		import javax.servlet.http.HttpServletResponse;
		
		import org.springframework.stereotype.Controller;
		import org.springframework.web.bind.annotation.RequestMapping;
		
		import com.egova.rpc.remote.HttpInvoker;
		import com.egova.rpc.remote.InvokerProcess;
		import com.egova.rpc.support.RemoteInvocation;
		
		@Controller
		@RequestMapping(value="/egova_invoker")
		public class ServerDemo{
			
			@RequestMapping(value="invoker")
			public void invoker(HttpServletRequest request, HttpServletResponse  response) {
				HttpInvoker.invoker(request, response, new InvokerProcess(){
					public Object process(RemoteInvocation invocation){
						//获取targetObject,即接口invocation.getInterfaceClazz()实现类
						Object targetObject = new Object();
						invocation.invoke(targetObject);
					}
				});
			}
		}

