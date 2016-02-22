/**
 * 
 */
package com.workhorseintegrations.insightly.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
public class RetrieveContactsTestCases extends FunctionalTestCase
{
	@Rule
	public Timeout globalTimeout = new Timeout(100000);
	
	@Override
	protected String getConfigResources()
	{
		return "insightly-test-flows.xml";
	}
	
	@Test
	public void testContactsRetrieveContactsNoData() throws Exception
	{
		String resultMsg = "[]";
		MuleClient client = muleContext.getClient();
		Map<String, String> inputMap = new HashMap<String, String>();
		inputMap.put("ids", "1234");
		inputMap.put("email", "test@test.com");
		inputMap.put("tag", "");
		MuleMessage _msg = new DefaultMuleMessage(inputMap, muleContext);
		MuleMessage result = client.send("vm://test.retrieve.contacts.in", _msg);
		
		assertNotNull( result );
		assertEquals("Expected insighlty response :: ", resultMsg, result.getPayloadAsString() );
	}
	
	@Test
	public void testContactsRetrieveContactsData() throws Exception
	{
		MuleClient client = muleContext.getClient();
		Map<String, String> inputMap = new HashMap<String, String>();
		inputMap.put("ids", "96311625,1234");
		inputMap.put("email", "ap@mulesoft.com");
		inputMap.put("tag", "");
		MuleMessage _msg = new DefaultMuleMessage(inputMap, muleContext);
		MuleMessage result = client.send("vm://test.retrieve.contacts.in", _msg);
		
		assertNotNull( result );
		assertNotNull("Valid insighlty response :: ", result.getPayload() );
		JSONParser parser = new JSONParser(); 
		JSONArray arrayObj = (JSONArray) parser.parse(result.getPayloadAsString());
		assertEquals("Valid insighlty response :: ", new Long(96311625), ((JSONObject)arrayObj.get(0)).get("CONTACT_ID") );
	}	
}
