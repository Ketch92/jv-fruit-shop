package core.basesyntax.model.shopstrategy;

import core.basesyntax.model.abstractstorage.AbstractItem;
import core.basesyntax.model.abstractstorage.AbstractStorage;
import core.basesyntax.model.shopdao.ShopDao;
import core.basesyntax.shopimpl.entity.DataRecord;
import java.util.Map;

public abstract class AbstractTransaction<R extends DataRecord,
        I extends AbstractItem> implements ShopTransaction {
    private AbstractStorage<R, I> storage;
    private ShopDao<R> shopDao;
    
    public AbstractTransaction(AbstractStorage<R, I> storage, ShopDao<R> shopDao) {
        if (storage == null || shopDao == null) {
            throw new RuntimeException("NonNull arguments expected");
        }
        this.storage = storage;
        this.shopDao = shopDao;
    }
    
    @Override
    public abstract void apply(AbstractItem item, int amount);
    
    public Map<I, Integer> getStorage() {
        return storage.getStorage();
    }
    
    public ShopDao<R> getShopDao() {
        return shopDao;
    }
}
