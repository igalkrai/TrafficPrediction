package project;

import java.util.Date;

/**
 * Created by Igal Kraisler on 18/12/2016.
 */
public class Data {

    private Long id;
    private Long time;
    private Date date;
    private Float speed;
    private Float travelTime;
    private Float normallyExpectedTravelTime;

    public Data() {
        super();
    }

    public Data(Long id, Long time, Date date, Float speed, Float travelTime, Float normallyExpectedTravelTime) {
        super();
        this.id = id;
        this.time = time;
        this.date = date;
        this.speed = speed;
        this.travelTime = travelTime;
        this.normallyExpectedTravelTime = normallyExpectedTravelTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Float getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Float travelTime) {
        this.travelTime = travelTime;
    }

    public Float getNormallyExpectedTravelTime() {
        return normallyExpectedTravelTime;
    }

    public void setNormallyExpectedTravelTime(Float normallyExpectedTravelTime) {
        this.normallyExpectedTravelTime = normallyExpectedTravelTime;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Data [id=" + id + ", time=" + time + ", date=" + date + ", speed=" + speed + ", travelTime="
                + travelTime + ", normallyExpectedTravelTime=" + normallyExpectedTravelTime + "]";
    }

    public String[] alFields() {
        return new String[]{id.toString(), time.toString(), date.toString(), speed.toString(), travelTime.toString(),
                normallyExpectedTravelTime.toString()};
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Data other = (Data) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        return true;
    }

    /**
     * @return iff all mandatory fields are not null
     */
    public boolean isFull() {
        return (this.id != null) && (this.time != null) && (this.travelTime != null);
    }
}
