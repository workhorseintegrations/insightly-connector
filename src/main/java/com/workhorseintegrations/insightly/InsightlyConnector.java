package com.workhorseintegrations.insightly;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;

import java.io.IOException;

import org.mule.api.annotations.ReconnectOn;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.annotations.param.Payload;
import org.mule.api.annotations.rest.HttpMethod;
import org.mule.api.annotations.rest.RestCall;
import org.mule.api.annotations.rest.RestUriParam;

import com.workhorseintegrations.insightly.config.ConnectorConfig;
import com.workhorseintegrations.insightly.util.InsightlyResourceName;

@SuppressWarnings("deprecation")
@Connector(name="insightly", friendlyName="Insightly")
public abstract class InsightlyConnector
{
	/**
	 * config ConnectorConfig instance
	 */
	@Config
    ConnectorConfig config;

	/**
	 * generatedUrl Contructed insightly Url
	 */
	@RestUriParam("generatedUrl")
    private String generatedUrl;

	/**
     * Insightly host to connect to.
     *
     */
    @Configurable
    @Placement(group="URL Configuration", order=3)
    @FriendlyName("Host")
    @Optional
    private String host;

	/**
     * Port number to use when a connection is made.
     */
    @Configurable
    @Placement(group="URL Configuration", order=4)
    @FriendlyName("Port")
    @Default(value="443")
    private String port;

	/**
     * Base path for the url. It must not start with a slash.
     */
    @Configurable
    @Placement(group="URL Configuration", order=5)
    @FriendlyName("Base Path")
    @Optional
    private String basePath;

	/**
     * Complete url to connect to. This need not be set if host, port and basePath is specified.
     */
    @Configurable
    @Placement(tab="Advanced Settings", group="Address Information", order=6)
    @FriendlyName("Address")
    @Optional
    private String address;


    /**
     * Retrieve message processor to retrieve Project/Contact/Organization information from Insightly for a resource id.
     * 
     * @param resourceName	InsightlyResourceName object containing the name of the resource.
     * @param resourceId	String value of the id.
     * @return A String value(JSON format) with the retrieved information from Insightly.
     * @throws IOException
     */
	@Processor
    @ReconnectOn(exceptions = { Exception.class })
    @RestCall(uri="https://{generatedUrl}/{resourceName}/{resourceId}", method=HttpMethod.GET, contentType="application/json")
    public abstract String retrieve(@RestUriParam("resourceName") InsightlyResourceName resourceName, @RestUriParam("resourceId") String resourceId) throws IOException;

    /**
     * Update message processor to update Project/Contact/Organization information in Insightly.
     *
     * @param resourceName	InsightlyResourceName object containing the name of the resource to be updated.
     * @param payload	String value(JSON Format) to be updated.
     * @return A String value(JSON Format) with the status of the update.
     * @throws IOException
     */
    @Processor
    @RestCall(uri="https://{generatedUrl}/{resourceName}", method=HttpMethod.PUT)
    public abstract String update(@RestUriParam("resourceName")InsightlyResourceName resourceName, @Payload String payload) throws IOException;

    /**
     * Create message processor to create Project/Contact/Organization information in Insightly.
     * 
     * @param resourceName	InsightlyResourceName object containing the name of the resource to be created.
     * @param payload	String value(JSON Format) to be created.
     * @return A String value(JSON Format) with the status of the create.
     * @throws IOException
     */
    @Processor
    @RestCall(uri="https://{generatedUrl}/{resourceName}", method=HttpMethod.POST)
    public abstract String create(@RestUriParam("resourceName") InsightlyResourceName resourceName, @Payload String payload) throws IOException;

    /**
     * RetrieveContacts message processor to retrieve Contact information from Insightly for an id, email and tag.
     *
     * @param resourceName	InsightlyResourceName object containing the name of the resource.
     * @param ids	String value of comma-separated list of Contact Ids, maximum 200, 1st order of precedence.
     * @param email	String value of email address, 2nd order of precedence.
     * @param tag	String value of a single tag, 3rd order of precedence.
     * @return A list of Contacts(JSON format).
     * @throws IOException
     */
    @Processor
    @RestCall(uri="https://{generatedUrl}/Contacts?ids={ids}&email={email}&tag={tag}", method=HttpMethod.GET)
    public abstract String retrieveContacts(@RestUriParam("ids") @Optional String ids, @RestUriParam("email") @Optional String email, @RestUriParam("tag") @Optional String tag) throws IOException;

    /**
     * Delete message processor to delete Project/Contact/Organization information from Insightly for a resourceId.
     * 
     * @param resourceName InsightlyResourceName object containing the name of the resource to be deleted.
     * @param resourceId	String value of the resourceId.
     * @return A String value(JSON format) with the status of delete.
     * @throws IOException
     */
    @Processor
    @RestCall(uri="https://{generatedUrl}/{resourceName}/{resourceId}", method=HttpMethod.DELETE)
    public abstract String delete(@RestUriParam("resourceName") InsightlyResourceName resourceName, @RestUriParam("resourceId") String resourceId) throws IOException;
    
    /**
     * RetrieveLeads message processor to retrieve Lead information from Insightly for an id, email, tag and if it is converted.
     *
     * @param ids	String value of comma-separated list of Lead Ids, maximum 200, 1st order of precedence.
     * @param email	String value of email address, 2nd order of precedence.
     * @param tag	String value of a single tag, 3rd order of precedence.
     * @param includeConverted By default converted leads are not included, pass in "true" to include them in the response.
     * @return A list of Leads(JSON format).
     * @throws IOException
     */
    @Processor
    @RestCall(uri="https://{generatedUrl}/Leads?ids={ids}&email={email}&tag={tag}&includeConverted={includeConverted}", method=HttpMethod.GET)
    public abstract String retrieveLeads(@RestUriParam("ids") @Optional String ids, @RestUriParam("email") @Optional String email, @RestUriParam("tag") @Optional String tag, @RestUriParam("includeConverted") @Default("false") Boolean includeConverted) throws IOException;
    
    /**
     * RetrieveOpportunities message processor to retrieve Opportunity information from Insightly for an id, email and tag.
     *
     * @param ids	String value of comma-separated list of Opportunity Ids, maximum 200, 1st order of precedence.
     * @param email	String value of email address, 2nd order of precedence.
     * @param tag	String value of a single tag, 3rd order of precedence.
     * @return A list of Opportunities(JSON format).
     * @throws IOException
     */
    @Processor
    @RestCall(uri="https://{generatedUrl}/Opportunities?ids={ids}&email={email}&tag={tag}", method=HttpMethod.GET)
    public abstract String retrieveOpportunities(@RestUriParam("ids") @Optional String ids, @RestUriParam("email") @Optional String email, @RestUriParam("tag") @Optional String tag) throws IOException;
    
    /**
     * RetrieveOrganizations message processor to retrieve Organization information from Insightly for an id, domain and tag.
     *
     * @param ids	String value of comma-separated list of Organization Ids, maximum 200, 1st order of precedence.
     * @param domain	String value of email domain, 2nd order of precedence.
     * @param tag	String value of a single tag, 3rd order of precedence.
     * @return A list of Organizations(JSON format).
     * @throws IOException
     */
    @Processor
    @RestCall(uri="https://{generatedUrl}/Organisations?ids={ids}&domain={domain}&tag={tag}", method=HttpMethod.GET)
    public abstract String retrieveOrganizations(@RestUriParam("ids") @Optional String ids, @RestUriParam("tag") @Optional String tag, @RestUriParam("domain") @Optional String domain) throws IOException;
    
    /**
     * RetrieveProjects message processor to retrieve Project information from Insightly for an id and tag.
     *
     * @param ids	String value of comma-separated list of Organization Ids, maximum 200, 1st order of precedence.
     * @param tag	String value of a single tag, 2nd order of precedence.
     * @return A list of Projects(JSON format).
     * @throws IOException
     */
    @Processor
    @RestCall(uri="https://{generatedUrl}/Projects?ids={ids}&tag={tag}", method=HttpMethod.GET)
    public abstract String retrieveProjects(@RestUriParam("ids") @Optional String ids, @RestUriParam("tag") @Optional String tag) throws IOException;
    
    /**
     * RetrieveAll message processor to retrieve all records from a specific resource from Insightly.
     *
     * @param resourceName InsightlyResourceName object containing the name of the resource to be retrieved.
     * @return A list of Projects(JSON format).
     * @throws IOException
     */
    @Processor
    @RestCall(uri="https://{generatedUrl}/{resourceName}", method=HttpMethod.GET)
    public abstract String retrieveAll(@RestUriParam("resourceName") InsightlyResourceName resourceName) throws IOException;
    
	/**
	 * @return the config
	 */
    public ConnectorConfig getConfig()
    {
        return config;
    }

	/**
	 * @param config the config to set
	 */
    public void setConfig(ConnectorConfig config)
    {
        this.config = config;
    }

	/**
	 * @return the generatedUrl
	 */
	public String getGeneratedUrl()
	{
		if( address == null )
			return host + ":" + port + "/" + basePath;
		else
		{
			if( address.startsWith("https://") )
				return address.substring(8);
			else if ( address.startsWith("http://") )
				return address.substring(7);
			else
				return address;
		}
	}

	/**
	 * @param generatedUrl the generatedUrl to set
	 */
	public void setGeneratedUrl(String generatedUrl)
	{
		this.generatedUrl = generatedUrl;
	}

	/**
	 * @return the host
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host)
	{
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public String getPort()
	{
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port)
	{
		this.port = port;
	}

	/**
	 * @return the basePath
	 */
	public String getBasePath()
	{
		return basePath;
	}

	/**
	 * @param basePath the basePath to set
	 */
	public void setBasePath(String basePath)
	{
		this.basePath = basePath;
	}

	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}
}