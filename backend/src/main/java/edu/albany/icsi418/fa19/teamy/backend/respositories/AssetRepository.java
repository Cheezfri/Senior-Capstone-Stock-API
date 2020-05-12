package edu.albany.icsi418.fa19.teamy.backend.respositories;

import edu.albany.icsi418.fa19.teamy.backend.models.asset.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findAssetsByName(String name);
    List<Asset> findAssetsByTicker(String ticker);
    List<Asset> findAssetsByNameAndTicker(String name, String ticker);

}
