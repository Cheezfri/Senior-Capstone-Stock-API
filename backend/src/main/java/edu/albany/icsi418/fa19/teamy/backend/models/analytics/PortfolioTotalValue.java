package edu.albany.icsi418.fa19.teamy.backend.models.analytics;

import org.threeten.bp.OffsetDateTime;

import java.util.Objects;

/**
 * The total value of the portfolio on a particular date
 */
public class PortfolioTotalValue   {

    private Long portfolioId = null;
    private Double portfolioValue = null;
    private OffsetDateTime date = null;

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Double getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(Double portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PortfolioTotalValue portfolioTotalValue = (PortfolioTotalValue) o;
        return Objects.equals(this.portfolioId, portfolioTotalValue.portfolioId) &&
                Objects.equals(this.portfolioValue, portfolioTotalValue.portfolioValue) &&
                Objects.equals(this.date, portfolioTotalValue.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portfolioId, portfolioValue, date);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PortfolioTotalValue {\n");

        sb.append("    portfolioId: ").append(toIndentedString(portfolioId)).append("\n");
        sb.append("    portfolioValue: ").append(toIndentedString(portfolioValue)).append("\n");
        sb.append("    date: ").append(toIndentedString(date)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
