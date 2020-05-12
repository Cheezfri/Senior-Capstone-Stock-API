package edu.albany.icsi418.fa19.teamy.backend.asset.queues;

/**
 * This class is used by the PriorityQueueAssest class to sort the PriorityQueue according to this classes comparator
 * lower numbers have higher Priority
 */

public class APIQueueItem implements Comparable<APIQueueItem> {

    public enum Request {NEW_ASSET, UPDATE}

    private int priorityNumber;
    private long assetId;
    private Request requestType;

    /**
     * Constructor for the item which sets priorityNumber based on requestType
     *
     * @param assetId     = sets assestID for assest
     * @param requestType = used to determine the priorityNumber
     */
    public APIQueueItem(long assetId, Request requestType) {
        if (Request.NEW_ASSET.equals(requestType)) {
            priorityNumber = 10;
        } else { // or == UPDATE
            priorityNumber = 1;
        }
        this.assetId = assetId;
        this.requestType = requestType;
    }

    public Request getRequestType() {
        return requestType;
    }

    public long getAssetId() {
        return assetId;
    }

    public int getPriorityNumber() {
        return priorityNumber;
    }

    public void setPriorityNumber(int priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    /**
     * Used by priority queue to return the APIQueueItem with greater priority which is a lower number
     *
     * @param o = APIQueueItem which is being compared to the current object
     * @return returns 1 if greater than, 0 if equal, -1 if less than
     */
    @Override
    public int compareTo(APIQueueItem o) {
        // or Greater Than
        if (o == null) {
            return 1;
        }

        return Integer.compare(o.getPriorityNumber(), getPriorityNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof APIQueueItem)) return false;

        APIQueueItem that = (APIQueueItem) o;

        if (priorityNumber != that.priorityNumber) return false;
        if (assetId != that.assetId) return false;
        return requestType == that.requestType;
    }

    @Override
    public int hashCode() {
        int result = priorityNumber;
        result = 31 * result + (int) (assetId ^ (assetId >>> 32));
        result = 31 * result + requestType.hashCode();
        return result;
    }

    public String toString() {
        return "Priority #: " + priorityNumber + "    AssetID: " + assetId + "    RequestType: " + requestType;
    }
}
