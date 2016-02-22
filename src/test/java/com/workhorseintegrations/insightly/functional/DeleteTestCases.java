/**
 * 
 */
package com.workhorseintegrations.insightly.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class DeleteTestCases extends FunctionalTestCase 
{
	@Rule
	public Timeout globalTimeout = new Timeout(100000);
	
	@Override
	protected String getConfigResources()
	{
		return "insightly-test-flows.xml";
	}
	
	@Test
	public void testContactsDeleteInvalidInput() throws Exception
	{
		String resultMsg = "[{\"Name\":\"id\",\"Message\":\"The value 'workhorse' is not valid for Int64.\"}]";
		MuleClient client = muleContext.getClient();
		MuleMessage _msg = new DefaultMuleMessage("workhorse", muleContext);
		MuleMessage result = client.send("vm://test.delete.in", _msg);
		
		assertNotNull( result );
		assertEquals("Expected insighlty response :: ", resultMsg, result.getPayloadAsString() );
	}
	
	@Test
	public void testContactsDeleteIValidInput() throws Exception
	{
		String resultMsg = "No Contact with ID = 1234";
		MuleClient client = muleContext.getClient();
		MuleMessage _msg = new DefaultMuleMessage("1234", muleContext);
		MuleMessage result = client.send("vm://test.delete.in", _msg);
		
		assertNotNull( result );
		assertEquals("Expected insighlty response :: ", resultMsg, result.getPayloadAsString() );
	}
	
}
