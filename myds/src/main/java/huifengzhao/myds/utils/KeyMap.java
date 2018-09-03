package huifengzhao.myds.utils;

import java.util.*;

/**
 * @author huifengzhao
 * @ClassName KeyMap
 * @date 2018/8/30
 */
public class KeyMap<K, V> {
    private Map<K, V> map;

    public KeyMap(Map<K, V> map) {
        Objects.requireNonNull(map);
        this.map = map;
        Set<Map.Entry<K, V>> entries = this.map.entrySet();
        HashMap temp = new HashMap<>();
        for (Map.Entry<K, V> entry:entries) {
            String key = entry.getKey().toString();
            temp.put(key,entry.getValue());
        }
        this.map.clear();
        this.map.putAll(temp);
    }

    public V get(K k) {
        return this.map.get(k);
    }
}
