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

import java.util.ArrayList;
import java.util.List;

public class Employments {
    List<Employment> employment;

    public Employments(int size) {
        employment = new ArrayList<Employment>(size);
    }

    public int size() {
        return employment.size();
    }

    public Employment get(int i) {
        return employment.get(i);
    }

    public void add(Employment emp) {
        employment.add(emp);
    }

    public void set(Employments employments) {
        this.employment.clear();
        for (Employment e : employments.employment) {
            this.employment.add(e);
        }
    }
}
