/**
 * 
 */
package com.workhorseintegrations.insightly.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

/**
 * @author mbrigilin
 *
 */
public class RetrieveByQueryTestCases extends FunctionalTestCase
{
	@Rule
	public Timeout globalTimeout = new Timeout(100000);
	
	@Override
	protected String getConfigResources()
	{
		return "insightly-test-flows.xml";
	}
	
	@Test
	public void testProjectsRetrieveByQueryNoData() throws Exception
	{
		String resultMsg = "[]";
		MuleClient client = muleContext.getClient();
		Map<String, String> inputMap = new HashMap<String, String>();
		inputMap.put("resourceKey", "tag");
		inputMap.put("resourceValue", "workhorse");
		MuleMessage _msg = new DefaultMuleMessage(inputMap, muleContext);
		MuleMessage result = client.send("vm://test.retrievebyquery.in", _msg);
		
		assertNotNull( result );
		assertEquals("Expected insighlty response :: ", resultMsg, result.getPayloadAsString() );
	}
	
	@Test
	public void testProjectsRetrieveByQueryData() throws Exception
	{
		MuleClient client = muleContext.getClient();
		Map<String, String> inputMap = new HashMap<String, String>();
		inputMap.put("resourceKey", "tag");
		inputMap.put("resourceValue", "Project");
		MuleMessage _msg = new DefaultMuleMessage(inputMap, muleContext);
		MuleMessage result = client.send("vm://test.retrievebyquery.in", _msg);
		
		assertNotNull( result );
		assertNotNull("Valid insighlty response :: ", result.getPayload() );
	}	
}
