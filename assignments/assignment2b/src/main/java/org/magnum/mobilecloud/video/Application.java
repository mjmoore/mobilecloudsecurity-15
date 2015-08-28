package org.magnum.mobilecloud.video;

import java.io.File;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.magnum.mobilecloud.video.auth.OAuth2SecurityConfiguration;
import org.magnum.mobilecloud.video.json.ResourcesMapper;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

//Inject dependencies for @Autowired
@EnableAutoConfiguration

//Dispatcher to controllers
@EnableWebMvc

@EnableJpaRepositories

//Application configuration
@Configuration

//Auto-connect controllers to dispatcher
@ComponentScan
@Import(OAuth2SecurityConfiguration.class)
public class Application extends RepositoryRestMvcConfiguration {

	//Add flag to vm args before launch
	// -Dkeystore.file=src/main/resources/private/keystore -Dkeystore.pass=changeit

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public ObjectMapper halObjectMapper(){
		return new ResourcesMapper();
	}	
}
