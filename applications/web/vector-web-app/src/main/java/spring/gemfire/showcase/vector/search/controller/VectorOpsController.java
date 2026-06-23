package spring.gemfire.showcase.vector.search.controller;

import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vector/ops")
public class VectorOpsController {

    private final GemfireTemplate gemfireTemplate;

    public VectorOpsController(GemfireTemplate gemfireTemplate) {
        this.gemfireTemplate = gemfireTemplate;
    }

    @DeleteMapping
    public void deleteEmbeddings() {

        var keys = gemfireTemplate.getRegion().keySetOnServer();

        gemfireTemplate.removeAll(keys);
    }
}
