/**
 * 
 */
package com.workhorseintegrations.insightly.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.io.IOUtils;
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
public class UpdateTestCases extends FunctionalTestCase
{
	@Rule
	public Timeout globalTimeout = new Timeout(100000);
	
	@Override
	protected String getConfigResources()
	{
		return "insightly-test-flows.xml";
	}
	
	@Test
	public void testContactsUpdateInvalidData() throws Exception
	{
		String resultMsg = "[{\"Name\":\"apiContact\",\"Message\":\"\"}]";
		MuleClient client = muleContext.getClient();
		MuleMessage _msg = new DefaultMuleMessage("Testing update with invalid data.", muleContext);
		MuleMessage result = client.send("vm://test.update.in", _msg);
		
		assertNotNull( result );
		assertEquals("Expected insighlty response :: ", resultMsg, result.getPayloadAsString() );
	}
	
	@Test
	public void testContactsUpdateData() throws Exception
	{
		MuleClient client = muleContext.getClient();
		JSONParser parser = new JSONParser(); 
		JSONObject obj = (JSONObject) parser.parse( IOUtils.toString( loadResource("valid.json") ));
		MuleMessage _msg = new DefaultMuleMessage(obj, muleContext);
		MuleMessage result = client.send("vm://test.update.in", _msg);
		
		assertNotNull( result );
		assertNotNull("Valid insighlty response :: ", result.getPayload() );
	}	
}