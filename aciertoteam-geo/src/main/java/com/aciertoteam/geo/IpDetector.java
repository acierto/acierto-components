package com.aciertoteam.geo;

import com.aciertoteam.common.utils.SiteContentReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author Bogdan Nechyporenko
 */
@Component
public class IpDetector {

    private static final String WHAT_IS_MY_IP_URL = "http://checkip.amazonaws.com/";

    @Value("${local.hosts}")
    private String localHosts;

    public String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        for (String localHostIpAddress : getLocalHostIpAddresses()) {
            if (ipAddress.startsWith(localHostIpAddress)) {
                return getExternalIp();
            }
        }
        return ipAddress;
    }

    public String getExternalIp() {
        return SiteContentReader.readContent(WHAT_IS_MY_IP_URL);
    }

    private List<String> getLocalHostIpAddresses() {
        return Arrays.asList(localHosts.split(","));
    }

    public void setLocalHosts(String localHosts) {
        this.localHosts = localHosts;
    }
}
