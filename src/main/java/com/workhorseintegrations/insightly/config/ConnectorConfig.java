package com.workhorseintegrations.insightly.config;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.components.HttpBasicAuth;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.param.Optional;
import org.mule.api.annotations.rest.BasicAuthPassword;
import org.mule.api.annotations.rest.BasicAuthUsername;

@SuppressWarnings("deprecation")
@HttpBasicAuth( headerName="Authorization", friendlyName = "Config")
public class ConnectorConfig
{
	/**
     * Username to authenticate in insightly. This will be the API key that is associated with your Insightly account.
     */
	@Configurable
    @Placement(group="Security Configuration", order=1)
	@BasicAuthUsername
	private String username;

	/**
     * Password to authenticate in insightly. This is to be left as a BLANK string.
     */
	@BasicAuthPassword
    @Placement(group="Security Configuration", order=2)
	@Optional
	@Password
	private String password;

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword()
	{
		if( password == null )
			password = "";
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}	
}