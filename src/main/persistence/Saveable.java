package persistence;

import netscape.javascript.JSObject;
import org.json.JSONObject;

/**
 * This object is saveable.
 */
public interface Saveable {
    /**
     * Save to a json object
     */
    public JSONObject saveToJson();
}
