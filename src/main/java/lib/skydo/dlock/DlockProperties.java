package lib.skydo.dlock;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dlock")
public class DlockProperties {
    private String host;
    private String port;
    private String lockRegistry;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLockRegistry() {
        return lockRegistry;
    }

    public void setLockRegistry(String lockRegistry) {
        this.lockRegistry = lockRegistry;
    }
}
