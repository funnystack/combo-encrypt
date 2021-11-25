package com.funny.combo.encrypt.web.config;

import com.funny.combo.encrypt.web.domain.SecurityKey;
import org.springframework.core.io.Resource;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class KeyConfig {
    private static final String VERSION_PREFIX = "v";
    private final Map<String, SecurityKey> cacheSecurity = new HashMap<>();
    private final Map<String, Integer> consumerKeyVersionStore = new HashMap<>();
    private static final String salt = "salt";

    public void addSecurityKey(SecurityKey security) {
        this.cacheSecurity.put(security.getSecurityKey(), security);
        this.consumerKeyVersionStore.put(security.getConsumerId(), 1);
    }

    public String getSalt() {
        return this.salt;
    }

    public boolean isExistsConsumerId(String consumerId) {
        return this.consumerKeyVersionStore.containsKey(consumerId.toLowerCase());
    }

    public SecurityKey getSecurityKey(String consumerId) {
        int version = this.consumerKeyVersionStore.getOrDefault(consumerId.toLowerCase(), 0);
        if (version == 0)
            return null;

        return getSecurityKey(consumerId, version);
    }

    public SecurityKey getSecurityKey(String consumerId, int version) {
        String key = consumerId + "." + VERSION_PREFIX + version;
        return this.cacheSecurity.getOrDefault(key, null);
    }

}
