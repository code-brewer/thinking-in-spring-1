package com.darian.dependency.injection;

import com.darian.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


/***
 * 基于 Annotation 资源的依赖 Method 注入示例
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/3/16  23:20
 */
@Configuration
public class AnnotationlDependencyMethodInjectionDemo {

    private UserHolder userHolder1;

    private UserHolder userHolder2;

    @Autowired
    public void initUserHolder1(UserHolder userHolder1) {
        this.userHolder1 = userHolder1;
    }

    @Resource
    public void initUserHolder2(UserHolder userHolder2) {
        this.userHolder2 = userHolder2;
    }

    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class ( 配置了类 ) -> Spring Bean
        applicationContext.register(AnnotationlDependencyMethodInjectionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";
        // 加载 XMl 资源，解析并且生成 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        applicationContext.refresh();

        AnnotationlDependencyMethodInjectionDemo demoBean =
                applicationContext.getBean(AnnotationlDependencyMethodInjectionDemo.class);
        System.out.println(demoBean.userHolder1);
        System.out.println(demoBean.userHolder2);

        System.out.println(demoBean.userHolder1 == demoBean.userHolder2); // true

        applicationContext.close();
    }


}
