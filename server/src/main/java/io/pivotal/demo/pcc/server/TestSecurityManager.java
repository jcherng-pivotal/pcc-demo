package io.pivotal.demo.pcc.server;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.geode.security.AuthenticationFailedException;
import org.apache.geode.security.ResourcePermission;
import org.springframework.core.env.Environment;
import org.springframework.geode.security.support.SecurityManagerSupport;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

public class TestSecurityManager extends SecurityManagerSupport {

    private static final String GEMFIRE_SECURITY_USERNAME_PROPERTY = "security-username";
    private static final String GEMFIRE_SECURITY_PASSWORD_PROPERTY = "security-password";
    private static final String SPRING_SECURITY_USERNAME_PROPERTY = "spring.data.gemfire.security.username";
    private static final String SPRING_SECURITY_PASSWORD_PROPERTY = "spring.data.gemfire.security.password";

    private final Environment environment;

    public TestSecurityManager(Environment environment) {

        Assert.notNull(environment, "Environment must not be null");

        this.environment = environment;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public Object authenticate(Properties credentials) throws AuthenticationFailedException {

        Environment environment = getEnvironment();

        String expectedUsername = Optional.of(environment)
                .filter(env -> env.containsProperty(SPRING_SECURITY_USERNAME_PROPERTY))
                .map(env -> env.getProperty(SPRING_SECURITY_USERNAME_PROPERTY))
                .orElseGet(() -> UUID.randomUUID().toString());

        String expectedPassword = Optional.of(environment)
                .filter(env -> env.containsProperty(SPRING_SECURITY_PASSWORD_PROPERTY))
                .map(env -> env.getProperty(SPRING_SECURITY_PASSWORD_PROPERTY))
                .orElse(expectedUsername);

        String actualUsername = credentials.getProperty(GEMFIRE_SECURITY_USERNAME_PROPERTY);
        String actualPassword = credentials.getProperty(GEMFIRE_SECURITY_PASSWORD_PROPERTY);

        if (!(expectedUsername.equals(actualUsername) && expectedPassword.equals(actualPassword))) {
            throw new AuthenticationFailedException(String.format("User [%s] is not authorized", actualUsername));
        }

        return User.of(actualUsername);
    }

    @Override
    public boolean authorize(Object principal, ResourcePermission permission) {
        return principal != null;
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")
    static class User implements Serializable {
        @NonNull
        private final String name;
    }
}