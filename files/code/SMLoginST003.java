
import resources.SM_Login_ST_003Helper;
import com.rational.test.ft.*;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.WPF.*;
import com.rational.test.ft.object.interfaces.dojo.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.object.interfaces.generichtmlsubdomain.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.value.*;
import com.rational.test.ft.vp.*;
import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;
/**
 * Description   : Functional Test Script
 * @author Carlos Mestre and Stephanie Lunn
 */
public class SM_Login_ST_003 extends SM_Login_ST_003Helper
{
	/**
	 * Script Name   : <b>SM_Login_ST_003</b>
	 * Generated     : <b>Feb 29, 2016 11:22:19 PM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 6.1  Build 7601 (S)
	 * 
	 * @since  2016/02/29
	 * @author Carlos Mestre and Stephanie Lunn
	 */
	public void testMain(Object[] args) 
	{	
		// HTML Browser
		// Document: Database Project: http://localhost:8080/SM/login.jsp
		// User selects login text field
		text_pantherID().click(atPoint(70,9));
		
		// User inputs data from keyboard
		browser_htmlBrowser(document_databaseProject(),DEFAULT_FLAGS).inputKeys(dpString("username"));
		
		// User selects password field
		text_password().click(atPoint(51,1));
		
		// User inputs data from keyboard
		browser_htmlBrowser(document_databaseProject(),DEFAULT_FLAGS).inputKeys(dpString("password"));
		
		// User clicks submit button
		button_loginsubmit().click();
		
		// Document: Database Project: http://localhost:8080/SM/manage.jsp
		// Perform Verification Point Test
		html_center_box().performTest(center_box_textVP());
		
		// User clicks logout button
		link_logOut().click();
	}
}

