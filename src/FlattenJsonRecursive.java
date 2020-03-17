import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FlattenJsonRecursive {
    /**
     * This method calls the helper method once its called with a json after doing a non null json check.
     * @param json input json
     * @return String representation of a flattened json.
     */
    public static String flattenJson(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        JSONObject result = new JSONObject();
        helperFlatten(json, new StringBuilder(), result);
        return result.toString();
    }

    /**
     * This method helps to flatten the json object passed to it. It recursively calls when a json object is present
     * within the argument json object.
     * @param json input json.
     * @param prefix prefix to be added to key of json.
     * @param result result is a json object to which we add key value pairs.
     */
    private static void helperFlatten(final String json, final StringBuilder prefix, final JSONObject result) {
        final JSONObject jsonObject = new JSONObject(json);

        final StringBuilder newKey = new StringBuilder(prefix);
        if (newKey.length() != 0) {
            newKey.append(".");
        }
        for (String key : jsonObject.keySet()) {
            newKey.append(key);
            if (jsonObject.get(key) instanceof JSONObject) {
                // This can be converted to an iterative call by using a queue, in case of memory constraints/ large input.
                helperFlatten(jsonObject.get(key).toString(), newKey, result);
            } else {
                result.put(newKey.toString(), jsonObject.get(key));
            }
            newKey.deleteCharAt(newKey.length() - 1);
        }
        if (newKey.length() != 0) {
            newKey.deleteCharAt(newKey.length() - 1);
        }
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
