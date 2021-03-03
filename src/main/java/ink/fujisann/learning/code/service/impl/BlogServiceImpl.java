package ink.fujisann.learning.code.service.impl;

import ink.fujisann.learning.code.mongo.MongoBlog;
import ink.fujisann.learning.code.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hulei
 * @date 2020-11-10 23:26:23:26
 */
@Service
@Slf4j
public class BlogServiceImpl implements BlogService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String save(MongoBlog mongoBlog) {
        mongoBlog.setCreateTime(new Date());
        mongoBlog.setUpdateTime(new Date());
        MongoBlog saved = mongoTemplate.save(mongoBlog);
        return saved.getId();
    }

    @Override
    public List<MongoBlog> findAll() {
        return mongoTemplate.findAll(MongoBlog.class);
    }

    @Override
    public MongoBlog findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, MongoBlog.class);
    }

    @Override
    public void update(MongoBlog mongoBlog) {
        // 更新约束条件
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(mongoBlog.getId()));
        // 更新数据体
        Update update = new Update();
        update.set("title", mongoBlog.getTitle());
        update.set("content", mongoBlog.getContent());
        update.set("author", mongoBlog.getAuthor());
        update.set("updateTime", new Date());
        mongoTemplate.upsert(query, update, MongoBlog.class);
    }
}
