import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class TreeMapTest {

	public static void main(String[] args) {
		Map<String,Object> tMap = new TreeMap<String,Object>();
		
		tMap.put("10", 1400);
		tMap.put("20", 1300);
		tMap.put("30", 1200);
		tMap.put("110", 1100);
		
		Iterator<Map.Entry<String, Object>> it = tMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Object> entry = it.next();
			System.out.println(entry.getKey()+ "-" + entry.getValue());
		}
		
	}

}
