import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class FlattenJsonBFS {

    /**
     * This class stores the jsonObject and the prefix associated to it. This class objects will be stored in queue,
     * when traversing through all jsonObjects within a jsonObject.
     */
    public static class JsonTuple {
        String prefix;
        JSONObject jsonObject;
        public JsonTuple(String prefix, JSONObject jsonObject) {
            this.prefix = prefix;
            this.jsonObject = jsonObject;
        }
    }
    /**
     * This method calls the helper method once its called with a json after doing a non null json check.
     * @param json input json
     * @return String representation of a flattened json.
     */
    private static String flattenJson(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return helperFlatten(json);
    }

    /**
     * This method helps to flatten the json object passed to it. It adds jso object to queue when a json object is
     * present within a json object.
     * @param json string representation of json.
     * @return string representation of resultant json object.
     */
    private static String helperFlatten(final String json) {
        JSONObject result = new JSONObject();
        final JSONObject jsonObject = new JSONObject(json);
        Deque<JsonTuple> q = new ArrayDeque<>();

        JsonTuple jsonTuple = new JsonTuple("", jsonObject);
        q.addLast(jsonTuple);

        while(!q.isEmpty()) {
            JsonTuple jsonTupleFromQueue = q.removeFirst();
            String prefix = jsonTupleFromQueue.prefix;
            if (!prefix.isEmpty()) {
                prefix += ".";
            }
            JSONObject jsonObject1 = jsonTupleFromQueue.jsonObject;
            for (String key : jsonObject1.keySet()) {
                if (jsonObject1.get(key) instanceof JSONObject) {
                    q.addLast(new JsonTuple(prefix + key, (JSONObject) jsonObject1.get(key)));
                } else {
                    result.put(prefix + key, jsonObject1.get(key));
                }
            }
        }
        return result.toString();
    }

    /**
     * Main method for execution.
     * @param args args.
     * @throws Exception in case of issues.
     */
    public static void main(String[] args) throws Exception {
        final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        final StringBuilder inputSB = new StringBuilder();
        String line;
        while((line = input.readLine()) != null) {
            inputSB.append(line);
        }

        if (inputSB.length() == 0) {
            System.out.println("Input is invalid, please provide a JSON input.");
            return;
        }
        // Stated in assumptions: Input received as a JSON object.
        System.out.println("Flattened json output is:\n" + flattenJson(inputSB.toString()));
    }
}
