package entity;

import datatype.Date;
import socialGen.AdmAppendVisitor;
import socialGen.IAppendVisitor;

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
        return accept(new AdmAppendVisitor()).toString();
    }

    public IAppendVisitor accept(IAppendVisitor visitor) {
        visitor.append("{\"organization_name\": ").visit(organization);
        visitor.append(", \"start_date\": ").visit(startDate);
        if (endDate != null) {
            visitor.append(", \"end_date\": ").visit(endDate);
        }
        return visitor.append("}");
    }
}
