package spring.gemfire.showcase.mcp.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class LookupService {

    private final Map<String, String> map;

    /**
     * Finds the configuration value associated with the given key in the CONFIG_MAP.
     * This method is marked with @Tool to be exposed to the LLM.
     *
     * @param key The  key
     * @return The value of the key
     */
    @Tool(name = "lookupFindTool", description = "Find the value for a specific cached key).")
    public String lookupValue(String key) {

        log.info("Lookup key: {}",key);

        var value= map.get(key.toLowerCase()); // Use toLowerCase for case insensitivity

        log.info("Found Value: {} for key: {}",value,key);

        return value;

    }

    @Tool(name = "lookupSaveTool", description = "Save the value for a given key).")
    public void saveValue(String key, String value) {

        log.info("Saved key: {}, value: {}",key,value);

        map.put(key.toLowerCase(),value); // Use toLowerCase for case insensitivity

    }

}
