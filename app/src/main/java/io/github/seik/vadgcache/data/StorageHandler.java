package io.github.seik.vadgcache.data;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Iván
 */

public class StorageHandler {

    public static <Item extends RealmObject> Item saveObject(Item item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm.copyToRealmOrUpdate(item));
        realm.close();
        return item;
    }

    public static <Item extends RealmObject> List<Item> saveObjects(List<Item> items) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm.copyToRealmOrUpdate(items));
        realm.close();
        return items;
    }
}
