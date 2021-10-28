/*
 * Copyright (C) 2011 John Currier
 * Copyright (C) 2017 Mårten Bohlin
 * Copyright (C) 2021 Yoichiro Takeda
 *
 * This file is a part of the SchemaSpy project (http://schemaspy.org).
 *
 * SchemaSpy is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * SchemaSpy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.schemaspy.model;

import java.util.*;

/**
 * Metadata about a stored procedure or function
 *
 * @author John Currier
 * @author Mårten Bohlin
 */
public class Enum implements Comparable<Enum> {
    private final String name;
    private final String label;
    private final String description;
    private final int enumsortorder;

    /**
     * @param name
     * @param label
     * @param description
     */
    public Enum(String name,
                    String label,
                    String description,
                    int enumsortorder) {
        this.name = name;
        this.label = label;
        this.description = description;
        this.enumsortorder = enumsortorder;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return
     */
    public int getEnumSortOrder() {
        return enumsortorder;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Enum other) {
        int rc = getName().compareTo(other.getName());
        if (rc == 0)
            rc = getLabel().compareTo(other.getLabel());
        if (rc == 0)
            rc = String.valueOf(getDescription()).compareTo(String.valueOf(other.getDescription()));
        return rc;
    }

    /**
     * Implementation of {@link Comparator} that sorts {@link Enum}s
     */
    public static class ByEnumIdComparator implements Comparator<Enum> {
        public int compare(Enum enum1, Enum enum2) {
            String name1 = enum1.getName();
            String name2 = enum2.getName();

            int rc = name1.compareToIgnoreCase(name2) ;
            if (rc == 0) {
                rc = enum1.getEnumSortOrder() - enum2.getEnumSortOrder();
            }
            return rc;
        }
    }
}

