package com.springimplant.complaintmanager;

import com.springimplant.complaintmanager.pojo.Alien;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import io.micrometer.context.ContextRegistry;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import reactor.core.publisher.Hooks;

@SpringBootApplication(scanBasePackages = "com.springimplant.complaintmanager")
@EnableEncryptableProperties
@EnableJdbcHttpSession
public class ComplaintManagerApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringApplicationBuilder.class);
    }

	public static void main(String[] args) {
		Hooks.enableAutomaticContextPropagation();
		ContextRegistry.getInstance().registerThreadLocalAccessor("cid",
				() -> MDC.get("cid"),
				cid->MDC.put("cid",cid),
				()->MDC.remove("cid"));
		ConfigurableApplicationContext context = SpringApplication.run(ComplaintManagerApplication.class, args);
		Alien a = context.getBean(Alien.class);
		System.out.println(a.toString());
		a.show();
	}

}
