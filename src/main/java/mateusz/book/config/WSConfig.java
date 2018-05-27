package mateusz.book.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WSConfig extends WsConfigurerAdapter {
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ksiegarnia/*");
	}
	@Bean(name = "ksiazki")   //  this will be wsdl file name
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema booksSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("PortBook");
		wsdl11Definition.setLocationUri("/ksiegarnia");
		wsdl11Definition.setTargetNamespace("http://mateusz.wadas/ksiegarnia");
		wsdl11Definition.setSchema(booksSchema);
		return wsdl11Definition;
	}
	@Bean
	public XsdSchema booksSchema() {
		return new SimpleXsdSchema(new ClassPathResource("xsds/ksiazki.xsd"));
	}
}
