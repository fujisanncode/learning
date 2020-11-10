package ink.fujisann.learning.code.service.impl;

import ink.fujisann.learning.code.pojo.MongoBlog;
import ink.fujisann.learning.code.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hulei
 * @date 2020-11-10 23:26:23:26
 */
@Service
public class BlogServiceImpl implements BlogService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void save(MongoBlog mongoBlog) {
        mongoTemplate.save(mongoBlog);
    }

    @Override
    public List<MongoBlog> findAll() {
        return mongoTemplate.findAll(MongoBlog.class);
    }
}
