package edu.albany.icsi418.fa19.teamy.backend.asset.queues;

import edu.albany.icsi418.fa19.teamy.backend.models.asset.Asset;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetPriceData;
import edu.albany.icsi418.fa19.teamy.backend.respositories.AssetPriceDataRepository;
import edu.albany.icsi418.fa19.teamy.backend.respositories.AssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;

@Component
@Scope("prototype")
public class AgentStartup extends Thread {
    private static final Logger log = LoggerFactory.getLogger(AgentStartup.class);

    private AssetRepository assetRepository;
    private AssetPriceDataRepository assetPriceDataRepository;

    @Override
    public void run() {
        log.info("AgentStartup Thread Started");
        enqueueData();
    }

    private void enqueueData() {
        int total = 0;
        int queued = 0;
        for (Asset asset : assetRepository.findAll()) {
            total++;
            if (enqueueMissingData(asset) || enqueueStaleData(asset)) {
                queued++;
            }
        }

        log.info("Enqueued {} of {} Assets for asset data gathering", queued, total);
    }

    /**
     * Method to queue up missing data in the APIQueue on startup.
     */
    private boolean enqueueMissingData(Asset asset) {
        long count = assetPriceDataRepository.countAllByAsset(asset);
        if (count == 0) {
            APIQueueItem apiQueueItem = new APIQueueItem(asset.getId(), APIQueueItem.Request.NEW_ASSET);
            AgentManager.assetQueue.add(apiQueueItem);
            return true;
        }
        return false;
    }

    private boolean enqueueStaleData(Asset asset) {
        AssetPriceData assetPriceData = assetPriceDataRepository.findFirstByAssetOrderByDateTimeDesc(asset).orElse(null);

        if (assetPriceData == null) {
            return false;
        }

        // Set Target Date. last theoretical Trading day... holidays excl
        OffsetDateTime target = OffsetDateTime.now();
        if (target.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            target = target.minusDays(2);
        } else if (target.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            target = target.minusDays(1);
        } else if (target.getHour() < 17) {
            target = target.minusDays(1);
        }

        if (assetPriceData.getDateTime().compareTo(target) < 0) {
            APIQueueItem apiQueueItem = new APIQueueItem(asset.getId(), APIQueueItem.Request.UPDATE);
            AgentManager.assetQueue.add(apiQueueItem);
            return true;
        }

        return false;
    }

    @Autowired
    public void setAssetRepository(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Autowired
    public void setAssetPriceDataRepository(AssetPriceDataRepository assetPriceDataRepository) {
        this.assetPriceDataRepository = assetPriceDataRepository;
    }
}
