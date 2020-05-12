package edu.albany.icsi418.fa19.teamy.backend.models.portfolio;

import edu.albany.icsi418.fa19.teamy.backend.models.user.User;
import edu.albany.icsi418.fa19.teamy.backend.respositories.PortfolioRepository;
import edu.albany.icsi418.fa19.teamy.backend.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class PortfolioShareApiModel {

    private long id;
    private long portfolioId;
    private long sharedWithUserId;
    private PortfolioRights portfolioRights;

    public PortfolioShareApiModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public long getSharedWithUserId() {
        return sharedWithUserId;
    }

    public void setSharedWithUserId(long sharedWithUserId) {
        this.sharedWithUserId = sharedWithUserId;
    }

    public PortfolioRights getPortfolioRights() {
        return portfolioRights;
    }

    public void setPortfolioRights(PortfolioRights portfolioRights) {
        this.portfolioRights = portfolioRights;
    }

    public PortfolioShare toBaseModel(PortfolioRepository portfolioRepository, UserRepository userRepository) {
        PortfolioShare result = new PortfolioShare();
        result.setId(this.getId());
        Optional<Portfolio> portfolioOptional = portfolioRepository.findById(this.getPortfolioId());
        if (portfolioOptional.isPresent()) {
            result.setPortfolio(portfolioOptional.get());
        }
        Optional<User> userOptional = userRepository.findById(this.getSharedWithUserId());
        if (userOptional.isPresent()) {
            result.setSharedWith(userOptional.get());
        }
        result.setPortfolioRights(this.getPortfolioRights());
        return result;
    }
}
