/*
 * Copyright by The Regents of the University of California
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * you may obtain a copy of the License from
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package entity;

import datatype.Date;
import socialGen.ADMAppendVisitor;
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
        return accept(new ADMAppendVisitor()).toString();
    }

    public IAppendVisitor accept(IAppendVisitor visitor) {
        visitor.append("{\"organization\": ").visit(organization);
        visitor.append(", \"start_date\": ").visit(startDate);
        if (endDate != null) {
            visitor.append(", \"end_date\": ").visit(endDate);
        }
        return visitor.append("}");
    }
}
