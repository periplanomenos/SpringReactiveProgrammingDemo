package org.example.srpd.configuration;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.KubernetesConfig;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Class: HazelcastClientConfiguration
 */
@Slf4j
@Configuration
public class HazelcastClientConfiguration {

    @Bean
    public HazelcastInstance hazelcastInstance(ClientConfig clientConfig) {
        return HazelcastClient.newHazelcastClient(clientConfig);
    }

    @Bean
    public ClientConfig myDigitalBankConfig(
            @Value("${hazelcast-kubernetes.my-digital-bank.namespace}") String namespace,
            @Value("${hazelcast-kubernetes.my-digital-bank.service-name}") String serviceName) {
        return createClientConfig(namespace, serviceName);
    }

    private ClientConfig createClientConfig(String namespace, String serviceName) {
        ClientConfig clientConfig = new ClientConfig();

        if (new File("/var/run/secrets/kubernetes.io/").exists()) {
            clientConfig.getNetworkConfig().getKubernetesConfig().setEnabled(false);

            KubernetesConfig kubernetes = clientConfig.getNetworkConfig().getKubernetesConfig();
            kubernetes.setEnabled(true);
            kubernetes.setProperty("namespace", namespace);
            kubernetes.setProperty("service-name", serviceName);
            log.info("Runs @ OpenShift!");
        } else {
            log.info("Runs @ Workstation!");

            clientConfig.getNetworkConfig().addAddress("127.0.0.1");
        }

        return clientConfig;
    }

}
