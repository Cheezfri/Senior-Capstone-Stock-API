package edu.albany.icsi418.fa19.teamy.backend.respositories;

import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioShare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioShareRepository extends JpaRepository<PortfolioShare, Long> {

    List<PortfolioShare> findPortfolioSharesByPortfolio_Id(Long portfolioId);
    List<PortfolioShare> findPortfolioSharesBySharedWith_Id(Long sharedWithId);
    List<PortfolioShare> findPortfolioSharesByPortfolio_IdAndSharedWith_Id(Long portfolioId, Long sharedWithId);

}
