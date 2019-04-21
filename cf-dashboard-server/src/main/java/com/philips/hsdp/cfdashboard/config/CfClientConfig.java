package com.philips.hsdp.cfdashboard.config;

import org.cloudfoundry.reactor.*;
import org.cloudfoundry.reactor.client.*;
import org.cloudfoundry.reactor.doppler.*;
import org.cloudfoundry.reactor.tokenprovider.*;
import org.cloudfoundry.reactor.uaa.*;

public class CfClientConfig {
    
    public TokenProvider tokenProvider(String userName, String password) {
        return PasswordGrantTokenProvider.builder().username(userName)
                       .password(password).build();
    }
    
    public DefaultConnectionContext connectionContext(String apiHost, Integer proxyPort, Boolean isProxyEnabled) {
        ProxyConfiguration proxyConfiguration = null;
        if (isProxyEnabled) {
            proxyConfiguration = ProxyConfiguration.builder().host(apiHost)
                                         .port(proxyPort).build();
        }
        if (proxyConfiguration != null) {
            return DefaultConnectionContext.builder().apiHost(apiHost)
                           .proxyConfiguration(proxyConfiguration).build();
        } else {
            return DefaultConnectionContext.builder().apiHost(apiHost).build();
        }
    }
    
    public ReactorCloudFoundryClient reactorCloudFoundryClient(ConnectionContext connectionContext,
                                                               TokenProvider tokenProvider) {
        return ReactorCloudFoundryClient.builder().connectionContext(connectionContext)
                       .tokenProvider(tokenProvider).build();
    }
    
    public ReactorDopplerClient reactorDopplerClient(ConnectionContext connectionContext,
                                                     TokenProvider tokenProvider) {
        return ReactorDopplerClient.builder().connectionContext(connectionContext)
                       .tokenProvider(tokenProvider).build();
        
    }
    
    public ReactorUaaClient reactorUAAClient(ConnectionContext connectionContext,
                                             TokenProvider tokenProvider) {
        return ReactorUaaClient.builder().connectionContext(connectionContext)
                       .tokenProvider(tokenProvider).build();
        
    }
}
