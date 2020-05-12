package edu.albany.icsi418.fa19.teamy.backend.respositories;

import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findPortfoliosByOwner_Id(long ownerId);
    List<Portfolio> findPortfoliosByName(String name);
    List<Portfolio> findPortfoliosByOwner_IdAndName(long ownerId, String name);

}

