package edu.albany.icsi418.fa19.teamy.backend.asset.queues;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * API interface to manage (display/add) assets to the API queue.
 */
@RestController
public class APIQueueController {

    /**
     * List the current contents of the API Queue.
     *
     * @return ResponseEntity containing a list of APIQueueItems in the queue
     */
    @GetMapping(value = "/APIQueue/list",
            produces = {"application/json"})
    public ResponseEntity<List<APIQueueItem>> apiQueueList() {
        APIQueueItem[] queued = AgentManager.assetQueue.toArray(new APIQueueItem[0]);
        Arrays.sort(queued);
        return new ResponseEntity<>(List.of(queued), HttpStatus.OK);
    }

    /**
     * Adds an asset to the queue.
     *
     * @param assetId Id of the asset to add
     * @param isNew   boolean true if the asset is a new asset and needs a full pull, otherwise false for an update
     * @return ResponseEntity with a boolean true on success
     */
    @GetMapping(value = "/APIQueue/add",
            produces = {"application/json"})
    public ResponseEntity<Boolean> apiQueueAdd(@RequestParam("assetId") long assetId, @RequestParam("isNew") boolean isNew) {
        APIQueueItem apiQueueItem = new APIQueueItem(assetId, isNew ? APIQueueItem.Request.NEW_ASSET : APIQueueItem.Request.UPDATE);
        AgentManager.assetQueue.add(apiQueueItem);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
