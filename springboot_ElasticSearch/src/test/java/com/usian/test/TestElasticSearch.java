package com.usian.test;

import com.usian.App;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class TestElasticSearch {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //创建索引库
    @Test
    public void testcreateIndex() throws IOException {
        //创建  添加  索引对象  设置索引库名称
        CreateIndexRequest indexRequest = new CreateIndexRequest("java1906");
        //设置索引参数，主分片
        indexRequest.settings(Settings.builder()
                             .put("number_of_shards",2)
                             .put("number_of_replicas",0)
                             );
        //设置索引里面的表和字段参数1表，参数二字段，参数三参数二的添加格式
        indexRequest.mapping("course","{\n" +
                "  \"_source\": {\n" +
                "    \"excludes\":[\"description\"]\n" +
                "  }, \n" +
                " \t\"properties\": {\n" +
                "      \"name\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"analyzer\":\"ik_max_word\",\n" +
                "          \"search_analyzer\":\"ik_smart\"\n" +
                "      },\n" +
                "      \"description\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"analyzer\":\"ik_max_word\",\n" +
                "          \"search_analyzer\":\"ik_smart\"\n" +
                "       },\n" +
                "       \"studymodel\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "       },\n" +
                "       \"price\": {\n" +
                "          \"type\": \"float\"\n" +
                "       },\n" +
                "       \"pic\":{\n" +
                "\t\t   \"type\":\"text\",\n" +
                "\t\t   \"index\":false\n" +
                "\t    },\n" +
                "       \"timestamp\": {\n" +
                "      \t\t\"type\":   \"date\",\n" +
                "      \t\t\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd\"\n" +
                "    \t }\n" +
                "  }\n" +
                "}", XContentType.JSON);
        //获取索引操作的客户端，调用创建命令将创建对象放到创建方法中作为参数，调用是否创建成功的方发，boolena类型
        boolean respone = restHighLevelClient.indices().create(indexRequest).isAcknowledged();
        System.out.println(respone);
    }

    //删除索引库的测试
    @Test
    public void testDeleteIndex() throws IOException {
        //创建删除索引的请求对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("java1906");
        //获取索引操作的客户端，调用删除命令将索引库删除，调用是否删除成功的方发，boolena类型,参数一删除指令对象，参数二删除
        boolean respone = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT).isAcknowledged();
        System.out.println(respone);
    }
}
