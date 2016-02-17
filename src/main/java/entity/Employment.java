package entity;

import datatype.Date;

public class Employment {

    private String organization;
    private Date startDate;
    private Date endDate;

    public Employment(String organization, Date startDate, Date endDate) {
        this.organization = organization;
        this.startDate = new Date(startDate.getMonth(), startDate.getDay(), startDate.getYear());
        this.endDate = null;
        if (endDate != null) {
            this.endDate = new Date(endDate.getMonth(), endDate.getDay(), endDate.getYear());
        }
    }

    public Employment() {
        this.startDate = new Date();
        this.endDate = new Date();
    }

    public void reset(String organization, Date startDate, Date endDate) {
        this.organization = organization;
        this.startDate.reset(startDate);
        if ((endDate == null) || (this.endDate == null)) {
            this.endDate = endDate;
        } else {
            this.endDate.reset(endDate);
        }
    }

    public String getOrganization() {
        return organization;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("");
        builder.append("{");
        builder.append("\"organization_name\":");
        builder.append("\"" + organization + "\"");
        builder.append(",");
        builder.append("\"start_date\":");
        builder.append(this.startDate);
        if (endDate != null) {
            builder.append(",");
            builder.append("\"end_date\":");
            builder.append(this.endDate);
        }
        builder.append("}");
        return new String(builder);
    }
}
