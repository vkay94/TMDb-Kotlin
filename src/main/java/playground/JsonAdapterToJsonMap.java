package playground;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonAdapterToJsonMap {

    public static void main(String... args) throws IOException {
        Map<String, Test> resultsObject = new HashMap<>();
        Test item1 = new Test("Dog");
        Test item2 = new Test("Cat");
        resultsObject.put("AR", item1);
        resultsObject.put("DE", item2);

        Moshi moshi = new Moshi.Builder().build();

        JsonAdapter<Map<String, Test>> jsonAdapter = moshi.adapter(Types.newParameterizedType(Map.class, String.class, Test.class));
        String jsonStringFromObject = jsonAdapter.toJson(resultsObject);
        System.out.println("JSON String from Object: " + jsonStringFromObject);

        Map<String, Test> re = jsonAdapter.fromJson(jsonStringFromObject);
        assert re != null;
        for (Map.Entry<String, Test> entry : re.entrySet()) {
            System.out.println(entry.getKey() + " / name = " + entry.getValue().name);
        }
    }

    public static class Test {
        public String name;

        public Test(String name) {
            this.name = name;
        }
    }
}