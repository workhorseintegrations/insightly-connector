/**
 * 
 */
package com.workhorseintegrations.insightly.util;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * InsightlyResourceName will have enum values of Projects/Organizations/Contacts to use in the message processors
 * 
 * @author mbrigilin
 *
 */
@XmlType
@XmlEnum(String.class)
public enum InsightlyResourceName
{
	/**
	 * Set to Projects when the request is to retrieve/update project information from insightly
	 */
    @XmlEnumValue("Projects") Projects,
	/**
	 * Set to Organizations when the request is to retrieve/update organization information from insightly
	 */
    @XmlEnumValue("Organisations") Organisations,
	/**
	 * Set to Contacts when the request is to retrieve/update contact information from insightly
	 */
    @XmlEnumValue("Contacts") Contacts;

    public String value()
    {
        return name();
    }

    public static InsightlyResourceName fromValue(String v)
    {
        return valueOf(v);
    }
}