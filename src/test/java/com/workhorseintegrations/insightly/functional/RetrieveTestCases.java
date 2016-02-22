/**
 * 
 */
package com.workhorseintegrations.insightly.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class RetrieveTestCases extends FunctionalTestCase 
{
	@Rule
	public Timeout globalTimeout = new Timeout(100000);
	
	@Override
	protected String getConfigResources()
	{
		return "insightly-test-flows.xml";
	}
	
	@Test
	public void testContactsRetrieveReturnNoData() throws Exception
	{
		String resultMsg = "[{\"Name\":\"id\",\"Message\":\"The value 'workhorse' is not valid for Int64.\"}]";
		MuleClient client = muleContext.getClient();
		MuleMessage _msg = new DefaultMuleMessage("workhorse", muleContext);
		MuleMessage result = client.send("vm://test.retrieve.in", _msg);
		
		assertNotNull( result );
		assertEquals("Expected insighlty response :: ", resultMsg, result.getPayloadAsString() );
	}
	
	@Test
	public void testContactsRetrieveReturnData() throws Exception
	{
		MuleClient client = muleContext.getClient();
		MuleMessage _msg = new DefaultMuleMessage("96311625", muleContext);
		JSONParser parser = new JSONParser(); 
		MuleMessage result = client.send("vm://test.retrieve.in", _msg);
		
		assertNotNull( result );
		JSONObject obj = (JSONObject) parser.parse(result.getPayloadAsString());
		assertEquals("Valid insighlty response :: ", new Long(96311625), obj.get("CONTACT_ID") );
	}	
}
