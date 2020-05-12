package edu.albany.icsi418.fa19.teamy.backend.asset.queues;

import edu.albany.icsi418.fa19.teamy.backend.asset.apis.Crypto;
import edu.albany.icsi418.fa19.teamy.backend.asset.apis.CurrencyExchange;
import edu.albany.icsi418.fa19.teamy.backend.asset.apis.StockData;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.Asset;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetCategory;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetPriceData;
import edu.albany.icsi418.fa19.teamy.backend.respositories.AssetPriceDataRepository;
import edu.albany.icsi418.fa19.teamy.backend.respositories.AssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class deals with the priorityQueueAsset which runs while the priorityQueueAsset has items still in it.
 * Handles Stocks, Currency and Crypto by looking at the "Asset" in the database as a queue item
 */
@Component
@Scope("prototype") // Each call to the bean gets a new instance
public class QueueAgent extends Thread {

    private static final Logger log = LoggerFactory.getLogger(QueueAgent.class);

    private AtomicBoolean runable = new AtomicBoolean(true);

    public QueueAgent(@Autowired AssetRepository assetRepository, @Autowired AssetPriceDataRepository assetPriceDataRepository) {
        this.assetRepository = assetRepository;
        this.assetPriceDataRepository = assetPriceDataRepository;
    }

    private AssetRepository assetRepository;
    private AssetPriceDataRepository assetPriceDataRepository;

    /**
     * Runs as long as priorityQueue is active. Saves all priceData when new Asset. Checks for updates through
     * pricesInDatabase.size() for updates.
     */
    @Override
    public void run() {
        log.info("Starting QueueAgent Thread");
        try {
            while (runable.get()) {
                fetchLoop();
            }
        } catch (InterruptedException ex) {
            log.warn("QueueAgent Interrupted", ex);
        }
    }

    /**
     * Main Loop for polling and updating AssetPriceData.
     *
     * @throws InterruptedException if interrupted during a sleep() call
     */
    private void fetchLoop() throws InterruptedException {
        APIQueueItem priorityQueueAsset = AgentManager.assetQueue.poll();
        try {
            if (priorityQueueAsset != null) {
                log.info("Got Queued Asset: {}", priorityQueueAsset);
                Asset asset = assetRepository.findById(priorityQueueAsset.getAssetId()).orElse(null);
                if (asset == null) {
                    log.error("Queued invalid asset: {}", priorityQueueAsset);
                    sleep(10000);
                    return;
                }
                if (!asset.getCategory().equals(AssetCategory.STOCK) &&
                        !asset.getCategory().equals(AssetCategory.CURRENCY) &&
                        !asset.getCategory().equals(AssetCategory.CRYPTO)) {
                    log.warn("Unsupported Queued Item, Category: {} with ID: {}", asset.getCategory(), asset.getId());
                    return;
                }
                if (priorityQueueAsset.getRequestType() == APIQueueItem.Request.NEW_ASSET) {
                    fetchNewAsset(priorityQueueAsset, asset);
                } else { // or == UPDATE
                    fetchUpdatedAsset(priorityQueueAsset, asset);
                }
                sleep(10000); // Sleeps before next request
            } else { // No asset to poll, sleep 15s
                sleep(15000);
            }
        } catch (IOException ex) { //Adds the item back when the queue fails
            log.error("Exception in processing", ex);
            sleep(10000);
        }
    }

    private void fetchNewAsset(APIQueueItem apiQueueItem, Asset asset) throws InterruptedException, IOException {
        List<AssetPriceData> prices = new ArrayList<>();
        if (asset.getCategory().equals(AssetCategory.STOCK)){
            prices = StockData.getStockDataFirstTime(asset);
        }
        if (asset.getCategory().equals(AssetCategory.CURRENCY)){
            prices = CurrencyExchange.getCurrencyFirstTime(asset);
        }
        if (asset.getCategory().equals(AssetCategory.CRYPTO)){
            prices = Crypto.getCryptoData(asset);
        }
        if (prices.isEmpty()) {
            // Something went wrong.
            log.warn("Could not fetch stock data for {}, empty list, deprioritizing and adding back to queue", apiQueueItem);
            if (apiQueueItem.getPriorityNumber() == 10) {
                apiQueueItem.setPriorityNumber(9);
            }
            AgentManager.assetQueue.add(apiQueueItem);
            sleep(10000);
        } else {
            assetPriceDataRepository.saveAll(prices);
        }
    }

    private void fetchUpdatedAsset(APIQueueItem apiQueueItem, Asset asset) throws InterruptedException, IOException {
        List<AssetPriceData> prices = new ArrayList<>();
        if (asset.getCategory().equals(AssetCategory.STOCK)){
            prices = StockData.updateStockData(asset);
        }
        if (asset.getCategory().equals(AssetCategory.CURRENCY)){
            prices = CurrencyExchange.getCurrencyUpdate(asset);
        }
        if (asset.getCategory().equals(AssetCategory.CRYPTO)){
            prices = Crypto.getCryptoData(asset);
        }
        if (prices.isEmpty()) {
            // Something went wrong.
            log.warn("Could not fetch stock data for {}, empty list, deprioritizing and adding back to queue", apiQueueItem);
            if (apiQueueItem.getPriorityNumber() == 1) {
                apiQueueItem.setPriorityNumber(0);
            }
            AgentManager.assetQueue.add(apiQueueItem);
            sleep(10000);
        }
        for (AssetPriceData price : prices) {
            List<AssetPriceData> pricesInDatabase =
                    assetPriceDataRepository.findAssetPriceDatasByAsset_IdAndDateTimeEqualsOrderByDateTimeAsc(asset.getId(),
                            price.getDateTime());
            if (pricesInDatabase.isEmpty()) {
                assetPriceDataRepository.save(price);
            }
        }
    }

    @Override
    public void interrupt() {
        runable.set(false);
        super.interrupt();
    }
}
