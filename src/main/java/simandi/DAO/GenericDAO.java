package simandi.DAO;

import simandi.util.MongoManager;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;

public class GenericDAO<T> implements BaseDAO<T> {

    private final MongoCollection<T> collection;
    private final Class<T> clazz;

    public GenericDAO(String collectionName, Class<T> clazz) {

        this.clazz = clazz;

        this.collection =
                MongoManager
                        .getDatabase()
                        .getCollection(collectionName, clazz);
    }

    @Override
    public void save(T entity) {
        collection.insertOne(entity);
    }

    @Override
    public void update(Bson filter, T entity) {
        collection.replaceOne(filter, entity);
    }

    @Override
    public void delete(Bson filter) {
        collection.deleteOne(filter);
    }

    @Override
    public List<T> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public T findOne(Bson filter) {
        return collection.find(filter).first();
    }

    @Override
    public List<T> findMany(Bson filter) {
        return collection.find(filter).into(new ArrayList<>());
    }

    // TAMBAHAN
    public MongoCollection<T> getCollection() {
        return collection;
    }
}