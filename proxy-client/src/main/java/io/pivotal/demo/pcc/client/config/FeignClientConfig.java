package io.pivotal.demo.pcc.client.config;

import io.pivotal.demo.pcc.client.client.CustomerOrderClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@EnableFeignClients
public class FeignClientConfig {
    @Autowired
    ApplicationContext applicationContext;

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Arrays.stream(registry.getBeanDefinitionNames())
                .filter(beanName -> beanName.matches("customer-order-service-*"))
                .forEach(beanName -> registry.removeBeanDefinition(beanName));
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(applicationContext);

        int currentYear = LocalDate.now().getYear();
        for (int year = currentYear; year > currentYear - 25; year--) {
            String beanName = "customer-order-service-" + year;
            CustomerOrderClient customerOrderClient = feignClientBuilder
                    .forType(CustomerOrderClient.class, beanName)
                    .build();
            beanFactory.registerSingleton(beanName, customerOrderClient);
        }
    }

    @PostConstruct
    public void init() {
        postProcessBeanDefinitionRegistry((BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory());
        postProcessBeanFactory((ConfigurableListableBeanFactory) applicationContext.getAutowireCapableBeanFactory());
    }
}