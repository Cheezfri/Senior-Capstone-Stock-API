package edu.albany.icsi418.fa19.teamy.backend.models.portfolio;

import edu.albany.icsi418.fa19.teamy.backend.models.DBObject;
import edu.albany.icsi418.fa19.teamy.backend.models.user.User;

import javax.persistence.*;

/**
 * Used to show that a given portfolio is shared with another user.
 */
@Entity
public class PortfolioShare implements DBObject {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(optional = false)
    private Portfolio portfolio;
    @ManyToOne(optional = false)
    private User sharedWith;
    @Enumerated(EnumType.STRING)
    private PortfolioRights portfolioRights;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public User getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(User sharedWith) {
        this.sharedWith = sharedWith;
    }

    public PortfolioRights getPortfolioRights() {
        return portfolioRights;
    }

    public void setPortfolioRights(PortfolioRights portfolioRights) {
        this.portfolioRights = portfolioRights;
    }

    public PortfolioShareApiModel toApiModel() {
        PortfolioShareApiModel result = new PortfolioShareApiModel();
        result.setId(this.getId());
        result.setPortfolioId(this.getPortfolio().getId());
        result.setSharedWithUserId(this.getSharedWith().getId());
        result.setPortfolioRights(this.getPortfolioRights());
        return result;
    }
}
