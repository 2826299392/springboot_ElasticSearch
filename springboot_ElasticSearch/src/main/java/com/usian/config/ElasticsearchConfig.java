package com.usian.config;


import org.apache.http.HttpHost;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig extends ElasticsearchProperties {

    @Bean
    public RestHighLevelClient getRestHighLevelClient(){
        //获取配置文件里面的：cluster-nodes: 192.168.159.134:9200， 当有多个id和端口的集群的时候，进行切割
        String[] hosts=getClusterNodes().split(",");
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int i = 0; i < httpHosts.length; i++) {
            String h=hosts[i];   //获取配置文件中的第一个
            //将获取到的id和端口进行分割存储
            httpHosts[i] = new HttpHost(h.split(":")[0],Integer.parseInt(h.split(":")[1]));
        }
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }
}
