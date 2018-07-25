package grap3Month;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;

public class DozerUtil {

    private static DozerBeanMapper dozerBeanMapper =   new DozerBeanMapper();

    public static <T> List<T> mapList(Collection collection, Class<T> destinationClass){
        List<T> destinationList = Lists.newArrayList();
        for (Object sourceObject : collection) {
            T destinationObject = dozerBeanMapper.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }
}
