package demo.redis;

import java.util.List;

/**
 * Created by johnson on 3/9/17.
 */
public interface LedgerServiceInterface {
    List<String> getEntries(int count);
}