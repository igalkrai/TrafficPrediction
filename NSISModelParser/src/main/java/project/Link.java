package project;

/**
 * Created by Igal Kraisler on 13/12/2016.
 */
public class Link {

    private Long id;
    private String roadName;
    private String description;
    private Long fromNodeId;
    private Long toNodeId;

    public Long getFromNodeId() {
        return fromNodeId;
    }

    public void setFromNodeId(Long fromNodeId) {
        this.fromNodeId = fromNodeId;
    }

    public Long getToNodeId() {
        return toNodeId;
    }

    public void setToNodeId(Long toNodeId) {
        this.toNodeId = toNodeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", roadName='" + roadName + '\'' +
                ", description='" + description + '\'' +
                ", fromNodeId=" + fromNodeId +
                ", toNodeId=" + toNodeId +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        return id != null ? id.equals(link.id) : link.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
