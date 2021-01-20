package nk.gk.wyl.semanticsearch.entity.search;

import java.util.List;
import java.util.Map;

/**
 * 复合检索实体类
 */
public class FullSearchEntity extends SearchEntity{

    private Map<String, Integer> order;

    public Map<String, Integer> getOrder() {
        return order;
    }

    public void setOrder(Map<String, Integer> order) {
        this.order = order;
    }
}
