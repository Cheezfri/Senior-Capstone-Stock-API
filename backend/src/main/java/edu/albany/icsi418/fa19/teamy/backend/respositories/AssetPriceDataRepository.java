package edu.albany.icsi418.fa19.teamy.backend.respositories;

import edu.albany.icsi418.fa19.teamy.backend.models.asset.Asset;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetPriceData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface AssetPriceDataRepository extends JpaRepository<AssetPriceData, Long> {

    List<AssetPriceData> findByOrderByDateTimeAsc();

    List<AssetPriceData> findAssetPriceDatasByDateTimeBeforeOrderByDateTimeAsc(OffsetDateTime endDate);

    List<AssetPriceData> findAssetPriceDatasByDateTimeAfterOrderByDateTimeAsc(OffsetDateTime startDate);

    List<AssetPriceData> findAssetPriceDatasByDateTimeAfterAndDateTimeBeforeOrderByDateTimeAsc(OffsetDateTime startDate, OffsetDateTime endDate);

    List<AssetPriceData> findAssetPriceDatasByAsset_IdOrderByDateTimeAsc(Long assetId);

    List<AssetPriceData> findAssetPriceDatasByAsset_IdAndDateTimeEqualsOrderByDateTimeAsc(Long assetId, OffsetDateTime date);

    List<AssetPriceData> findAssetPriceDatasByAsset_IdAndDateTimeBeforeOrderByDateTimeAsc(Long assetId, OffsetDateTime endDate);

    List<AssetPriceData> findAssetPriceDatasByAsset_IdAndDateTimeAfterOrderByDateTimeAsc(Long assetId, OffsetDateTime startDate);

    List<AssetPriceData> findAssetPriceDatasByAsset_IdAndDateTimeAfterAndDateTimeBeforeOrderByDateTimeAsc(Long assetId, OffsetDateTime startDate, OffsetDateTime endDate);

    Optional<AssetPriceData> findFirstByAssetOrderByDateTimeDesc(Asset asset);

    long countAllByAsset(Asset asset);

}
