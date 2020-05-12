package edu.albany.icsi418.fa19.teamy.backend.models.analytics;

import io.swagger.annotations.ApiModel;
import org.threeten.bp.OffsetDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The aggregate value of a portfolio from start to end date
 */
@ApiModel(description = "The aggregate value of a portfolio from start to end date")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-03T03:07:32.449Z[GMT]")
public class PortfolioAggregateData   {

    private Long portfolioId = null;
    private String name = null;
    private OffsetDateTime startDate = null;
    private OffsetDateTime endDate = null;
    private List<PortfolioTotalValue> portfolioValueByDate = null;

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public PortfolioAggregateData addPortfolioValueByDateItem(PortfolioTotalValue portfolioValueByDateItem) {
        if (this.portfolioValueByDate == null) {
            this.portfolioValueByDate = new ArrayList<PortfolioTotalValue>();
        }
        this.portfolioValueByDate.add(portfolioValueByDateItem);
        return this;
    }

    public List<PortfolioTotalValue> getPortfolioValueByDate() {
        return portfolioValueByDate;
    }

    public void setPortfolioValueByDate(List<PortfolioTotalValue> portfolioValueByDate) {
        this.portfolioValueByDate = portfolioValueByDate;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PortfolioAggregateData portfolioAggregateData = (PortfolioAggregateData) o;
        return Objects.equals(this.portfolioId, portfolioAggregateData.portfolioId) &&
                Objects.equals(this.name, portfolioAggregateData.name) &&
                Objects.equals(this.startDate, portfolioAggregateData.startDate) &&
                Objects.equals(this.endDate, portfolioAggregateData.endDate) &&
                Objects.equals(this.portfolioValueByDate, portfolioAggregateData.portfolioValueByDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portfolioId, name, startDate, endDate, portfolioValueByDate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PortfolioAggregateData {\n");

        sb.append("    portfolioId: ").append(toIndentedString(portfolioId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
        sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
        sb.append("    portfolioValueByDate: ").append(toIndentedString(portfolioValueByDate)).append("\n");
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
