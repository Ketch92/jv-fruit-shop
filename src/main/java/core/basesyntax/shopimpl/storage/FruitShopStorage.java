package core.basesyntax.shopimpl.storage;

import core.basesyntax.model.abstractstorage.AbstractItem;
import core.basesyntax.model.abstractstorage.AbstractStorage;
import core.basesyntax.model.shopdao.ShopDao;
import core.basesyntax.model.shopstrategy.ShopActions;
import core.basesyntax.shopimpl.entity.DataRecord;
import core.basesyntax.shopimpl.entity.Fruit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FruitShopStorage extends AbstractStorage<DataRecord, Fruit> {
    public FruitShopStorage(ShopDao<DataRecord> shopDao) {
        super(shopDao);
    }
    
    @Override
    protected Map<Fruit, Integer> initStorage(ShopDao<DataRecord> shopDao) {
        Map<Fruit, Integer> storage = new HashMap<>();
        
        List<AbstractItem> fruits = shopDao.getTransactionHistory().stream()
                .map(DataRecord::getItem)
                .distinct()
                .collect(Collectors.toList());
        
        for (AbstractItem fruit : fruits) {
            storage.put((Fruit) fruit, 0);
        }
        
        for (DataRecord record : shopDao.getTransactionHistory()) {
            int actualAmount = record.getAction() == ShopActions.PURCHASE
                    ? storage.get(record.getItem()) - record.getAmount()
                    : storage.get(record.getItem()) - record.getAmount();
            storage.put((Fruit) record.getItem(), actualAmount);
        }
        
        return storage;
    }
}
