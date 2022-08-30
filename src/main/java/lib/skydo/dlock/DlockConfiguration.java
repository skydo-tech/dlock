package lib.skydo.dlock;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
@ConfigurationPropertiesScan(basePackages = {"lib.skydo.dlock"})
@EnableConfigurationProperties(DlockConfiguration.class)
@ComponentScan(basePackages = {"lib.skydo.dlock"})
public class DlockConfiguration {
}
