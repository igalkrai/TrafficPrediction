package project;

/**
 * Created by Igal Kraisler on 13/12/2016.
 */
public class Node {

    private Long id;
    private Double lat;
    private Double lon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (id != null ? !id.equals(node.id) : node.id != null) return false;
        if (lat != null ? !lat.equals(node.lat) : node.lat != null) return false;
        return lon != null ? lon.equals(node.lon) : node.lon == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (lon != null ? lon.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
