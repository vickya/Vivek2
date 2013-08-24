

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class Shopstyle1 {

	WebDriver Reddress;//WebDriver Declaration 
	
	
	@Test//No @Before and @After As I have used everything within the Test Annotation
	public void MyTest() throws Exception{
		//Pointing to the ChromeDriverServer as Running test in chrome browser needs chromedriverserver.exe file
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\VIVEK\\workspace\\ShopStyle\\ChromeDriver\\chromedriver.exe");
		Reddress = new ChromeDriver();//Starting Chrome Browser
		Reddress.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);//Giving Timeout as WebPage has to load Ajax Based Rich Elements
		Reddress.manage().window().maximize();//Maximizing the Browser After opening the WebPage
		Reddress.navigate().to("http://www.shopstyle.com");//Giving Url to navigate 
		Thread.sleep(3000);//As the Page takes time to load all the Elements after navigation, Giving 3 Seconds of sleep
		
		//Typing "red dress" in the Box Displayed after navigation
		Reddress.findElement(By.xpath("//*[@id='searchFrmHome']/input")).sendKeys("red dress");
		
		//Again Giving Some sleep to load Red dress Products
		Thread.sleep(3000); 
		
		//Pressing the Keyboard Enter Key Using Keys Method in Webdriver
		Reddress.findElement(By.xpath("//*[@id='searchFrmHome']/input")).sendKeys(Keys.ENTER);
		
		Thread.sleep(3000);
		
		/*As the next Step is to Change the UK Flag to US Flag or Country...We Need to perform Mouse Over Operation as the list of countries 
		displayed after pointing mouse on to the Flag and Clicking on US Flag
		For this We Use Actions Method with Move to Element*/
		
		WebElement MyElement = Reddress.findElement(By.xpath("//*[@id='pageWrapperBody']/div[1]/div[2]/span/div/em/sup"));
		WebElement MySecElement = Reddress.findElement(By.xpath("//*[@id='pageWrapperBody']/div[1]/div[2]/span/div/ul/li[2]"));
		Actions MyAction = new Actions(Reddress);
		MyAction.moveToElement(MyElement).build().perform();//Move mouse on to UK Flag
		Thread.sleep(3000);
		MyAction.moveToElement(MySecElement).build().perform();//Move Mouse to US Flag
		Thread.sleep(3000);
		MyAction.moveToElement(MySecElement).click().build().perform();//Click on US Flag
		Thread.sleep(3000);
		
		
		/*As the Functionality for each and Every Red Dress Product is the same as the others
		I declare the process of verification in First Red dress Displayed*/
		
	//First Red Dress Data Verification
		System.out.println("--------------------------------------------------------");
		System.out.println("******First Dress******");
		
		//Here I am getting the Product name from the Web Page and Verifying the Text whether the Displayed text in Web Page Matches with the given Data
		String FDN = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[3]/div/div/div/div[2]/span[1]/a")).getText();
		System.out.println("Product Name is : " + FDN);
		
		//Here I am getting the Product Price from the Web Page and Verifying the Text whether the Displayed text Displayed on the Web Page Matches with the given Data
		String FDP = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[3]/div/div/div/div[2]/span[2]")).getText();
		System.out.println("Product Price is : " + FDP);
		
		if(FDN.equalsIgnoreCase("L'Wren Scott Sleeveless Red Dress")){
			System.out.println("The Product Name is Matched");//If the WebPage text matches with the Data, Then Give me It is a Pass
		} else {
			System.out.println("The Product Name is Failed to Match");//If the WebPage text matches with the Data, Then Give me It is a Fail
		}
		
		if(FDP.equalsIgnoreCase("$1,390")){
			System.out.println("The Product Price is Matched");//If the WebPage text matches with the Data, Then Give me It is a Pass
		} else {
			System.out.println("The Product Price is Failed to Match");//If the WebPage text matches with the Data, Then Give me It is a Fail
		}
		//In Order to Verify the Retailer We need to Click on The Product Related Image
		Reddress.findElement(By.xpath("//*[@id='productImage']/img")).click();
		
		/*Once the Image got clicked, Then it will open another two additional windows in which one is related to shop alert and 
		the other with product description..So, in this kind of situation We have to go with window's Handling Concept*/
		
	//Window handler for 1st Red Dress
		
		Set<String> windowids = Reddress.getWindowHandles();//Here I am asking the driver to get the WindowHandle
		
		//Now as Program doesnt know how many of them were opened after clicking on the image, We use Iterator method
		Iterator<String> iter= windowids.iterator(); 
		
		//This next method related to the Parent window which has the list of products (Basically our first webpage)
		String firstdressid=iter.next();
		
		//This next method related to the Child window, Which is not useful for getting retailer info
		String firstdressid2=iter.next();
		
		//This next method related to the another child window which contains the info about the retailer
		
		String firstdressid3=iter.next(); 
		
		//So we need to choose the window which contains retailer, and in our case it is the third window
		Reddress.switchTo().window(firstdressid3);
		
		//So we are giving driver a command to switch to that 2nd child window to get the required info for our test
		
		Thread.sleep(2000);//As it takes little bit of time to switch on 2nd child window, we are giving a wait command
		
		//Each and Every Page has a title, and in our case the title of each page contains the reatiler info with product name
		
		System.out.println(Reddress.getTitle());
		
		String Firstd = Reddress.getTitle();//Keeping Title text in String Firstd(First Red Dress)
		
		//As we dont need the Product name which we already got from the First page, we need to split the text
		System.out.println(Firstd.length()); 
		
		//using Split method in Java to capture Retailer Information
		String[] data = Firstd.split("\\ ");//Splitting the page title based on Space between each word 
		String retOne = data[data.length-1];//getting the Last Word which contains retailer info
		
		//Printing the Split Data in the Console to check whether the retailer info is what we are looking in the json code
		System.out.println(retOne);
		
		//As we know from json script, that the first retailer name is SHOPBOP and verifying the Output from the get tile with the know Data in json
		if (retOne.equalsIgnoreCase("SHOPBOP")){ 
			System.out.println("Retailer Name is Matched");//if both data in json and webpage matched, then give me verification as a pass
		} else {
			System.out.println("Retailer Name is not Matched");//if both data in json and webpage matched, then give me verification as a Fail
		}
		
		Thread.sleep(2000);
		
		//Once the Verifications Done Successfully the close the child windows only as it is focused on 2nd child window
		Reddress.close(); 
		
		//Note: Once if we give close command to close the 2nd child window, it will automatically close the 1st child window 
		
		Thread.sleep(2000);
		Reddress.switchTo().window(firstdressid);//Again Back to the Parent window to Click on Another Product
		System.out.println("Window Focus Changed");//Giving an Output that the Focus on Window Changed from Child Window to Parent Window
		Thread.sleep(3000);
		
		
		//Same process is followed to the Other 9 Products as Declared for the First Product
		
		
	//Second Red Dress Data Verification
		System.out.println("--------------------------------------------------------");
		System.out.println("******Second Dress******");
		String SDN = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[4]/div/div/div/div[2]/span[1]/a")).getText();
		System.out.println("Product Name is : " + SDN);
		String SDP = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[4]/div/div/div/div[2]/span[2]")).getText();
		System.out.println("Product Price is : " + SDP);
		if(SDN.equalsIgnoreCase("Carolina Herrera Sleeveless Ruffle-Front Dress, Mercury Red")){
			System.out.println("The Product Name is Matched");
		} else {
			System.out.println("The Product Name is Failed to Match");
		}
		
		if(SDP.equalsIgnoreCase("$1,890")){
			System.out.println("The Product Price is Matched");
		} else {
			System.out.println("The Product Price is Failed to Match");
		}
				Reddress.findElement(By.xpath("//*[@alt='red dress-carolina herrera sleeveless rufflefront dress mercury red']")).click();
		
		//Window handler for 2nd Red Dress
				windowids = Reddress.getWindowHandles();	
				iter= windowids.iterator();
				String seconddressid=iter.next();
				String seconddressid2=iter.next();
				String seconddressid3=iter.next();
				Reddress.switchTo().window(seconddressid3);
				Thread.sleep(2000);
				System.out.println(Reddress.getTitle());
				String Secondd = Reddress.getTitle();
				System.out.println(Secondd.length());
				String[] data2 = Secondd.split("\\ ");
				String retTwo = data2[data2.length-1];
				System.out.println(retTwo);
				if (retTwo.equalsIgnoreCase("Marcus")){
					System.out.println("Retailer Name is Matched");
				} else {
					System.out.println("Retailer Name is not Matched");
				}
				Thread.sleep(2000);
				Reddress.close();
				Thread.sleep(2000);
				Reddress.switchTo().window(seconddressid);
				System.out.println("Window Focus Changed");
				Thread.sleep(3000);
				
		//Third Red Dress Data Verification
				System.out.println("--------------------------------------------------------");
				System.out.println("******Third Dress******");
				String TDN = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[5]/div/div/div/div[2]/span[1]/a")).getText();
				System.out.println("Product Name is : " + TDN);
				String TDP = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[5]/div/div/div/div[2]/span[3]")).getText();
				System.out.println("Product Price is : " + TDP);
				if(TDN.equalsIgnoreCase("Rachel Roy Sexy Red Dress (True Red) - Apparel")){
					System.out.println("The Product Name is Matched");
				} else {
					System.out.println("The Product Name is Failed to Match");
				}
				
				if(TDP.equalsIgnoreCase("$184.99")){
					System.out.println("The Product Price is Matched");
				} else {
					System.out.println("The Product Price is Failed to Match");
				}
						Reddress.findElement(By.xpath("//*[@alt='red dress-rachel roy sexy red dress true red apparel']")).click();
				
			//Window handler for 3rd Red Dress
				windowids = Reddress.getWindowHandles();
				iter= windowids.iterator();
				String thirdreddress=iter.next();
				String thirdreddress2=iter.next();
				Reddress.switchTo().window(thirdreddress2);
				Thread.sleep(2000);
				System.out.println(Reddress.getTitle());
				
				String Thirdd = Reddress.getTitle();
				System.out.println(Thirdd.length());
				String[] data3 = Thirdd.split("\\ ");
				String retThree = data3[data3.length-2];
				System.out.println(retThree);
				if (retThree.equalsIgnoreCase("Zappos")){
					System.out.println("Retailer Name is Matched");
				} else {
					System.out.println("Retailer Name is not Matched");
				}
				Thread.sleep(2000);
				Reddress.close();
				Thread.sleep(2000);
				Reddress.switchTo().window(thirdreddress);
				System.out.println("Window Focus Changed");		
				Thread.sleep(3000);
				
		//Fourth Red Dress Data Verification
				System.out.println("--------------------------------------------------------");
				System.out.println("******Fourth Dress******");
				String FTDN = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[6]/div/div/div/div[2]/span[1]/a")).getText();
				System.out.println("Product Name is : " + FTDN);
				String FTDP = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[6]/div/div/div/div[2]/span[2]")).getText();
				System.out.println("Product Price is : " + FTDP);
				if(FTDN.equalsIgnoreCase("Alexander McQueen Quilted Jacquard Knit Dress")){
					System.out.println("The Product Name is Matched");
				} else {
					System.out.println("The Product Name is Failed to Match");
				}
				
				if(FTDP.equalsIgnoreCase("$2,385")){
					System.out.println("The Product Price is Matched");
				} else {
					System.out.println("The Product Price is Failed to Match");
				}
				Reddress.findElement(By.xpath("//*[@alt='red dress-luck be a lady dress in red']")).click();
				
			//Window handler for 4th Red Dress
				windowids = Reddress.getWindowHandles();
				iter= windowids.iterator();
				String fourthreddress=iter.next();
				String fourthreddress2=iter.next();
				Reddress.switchTo().window(fourthreddress2);
				Thread.sleep(2000);
				System.out.println(Reddress.getTitle());
				String Fourthd = Reddress.getTitle();
				System.out.println(Fourthd.length());
				String[] data4 = Fourthd.split("\\ ");
				String retFour = data4[data4.length-1];
				System.out.println(retFour);
				if (retFour.equalsIgnoreCase("ModCloth.com")){
					System.out.println("Retailer Name is Matched");
				} else {
					System.out.println("Retailer Name is not Matched");
				}
				Thread.sleep(2000);
				Reddress.close();
				Thread.sleep(2000);
				Reddress.switchTo().window(fourthreddress);
				System.out.println("Window Focus Changed");
				Thread.sleep(3000);
		
		//Fifth Red Dress Data Verification
				System.out.println("--------------------------------------------------------");
				System.out.println("******Fifth Dress******");
				String FFDN = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[7]/div/div/div/div[2]/span[1]/a")).getText();
				System.out.println("Product Name is : " + FFDN);
				String FFDP = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[7]/div/div/div/div[2]/span[2]")).getText();
				System.out.println("Product Price is : " + FTDP);
				if(FFDN.equalsIgnoreCase("ModCloth Luck Be A Lady Dress in Red")){
					System.out.println("The Product Name is Matched");
				} else {
					System.out.println("The Product Name is Failed to Match");
				}
				
				if(FFDP.equalsIgnoreCase("$74.99")){
					System.out.println("The Product Price is Matched");
				} else {
					System.out.println("The Product Price is Failed to Match");
				}
				Reddress.findElement(By.xpath("//*[@alt='red dress-alexander mcqueen quilted jacquard knit dress']")).click();
				
			//Window handler for 5th Red Dress
				windowids = Reddress.getWindowHandles();
				iter= windowids.iterator();
				String fifthreddress=iter.next();
				String fifthreddress2=iter.next();
				Reddress.switchTo().window(fifthreddress2);
				Thread.sleep(5000);
				System.out.println(Reddress.getTitle());
				
				String Fifthd = Reddress.getTitle();
				System.out.println(Fifthd.length());
				String[] data5 = Fifthd.split("\\ ");
				String retFive = data5[data5.length-1];
				System.out.println(retFive);
				if (retFive.equalsIgnoreCase("Nordstrom")){
					System.out.println("Retailer Name is Matched");
				} else {
					System.out.println("Retailer Name is not Matched");
				}
				
				Thread.sleep(2000);
				
				//Thread.sleep(2000);
				Reddress.close();
				Thread.sleep(2000);
				Reddress.switchTo().window(fifthreddress);
				System.out.println("Window Focus Changed");	
				Thread.sleep(3000);
				
		//Sixth Red Dress Data Verification
				System.out.println("--------------------------------------------------------");
				System.out.println("******Sixth Dress******");
				String SXDN = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[8]/div/div/div/div[2]/span[1]/a")).getText();
				System.out.println("Product Name is : " + SXDN);
				String SXDP = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[8]/div/div/div/div[2]/span[2]")).getText();
				System.out.println("Product Price is : " + SXDP);
				if(SXDN.equalsIgnoreCase("ModCloth Right Said Red Dress")){
					System.out.println("The Product Name is Matched");
				} else {
					System.out.println("The Product Name is Failed to Match");
				}
				
				if(SXDP.equalsIgnoreCase("$52.99")){
					System.out.println("The Product Price is Matched");
				} else {
					System.out.println("The Product Price is Failed to Match");
				}
				Reddress.findElement(By.xpath("//*[@alt='red dress-right said red dress']")).click();
				
			//Window handler for 6th Red Dress
				windowids = Reddress.getWindowHandles();
				iter= windowids.iterator();
				String sixthreddress=iter.next();
				String sixthreddress2=iter.next();
				Reddress.switchTo().window(sixthreddress2);
				Thread.sleep(2000);
				Reddress.findElement(By.xpath("//*[@id='join-tab']/div[1]/a")).click();
				System.out.println(Reddress.getTitle());
				
				String Sixthd = Reddress.getTitle();
				System.out.println(Sixthd.length());
				String[] data6 = Sixthd.split("\\ ");
				String retSix = data6[data6.length-1];
				System.out.println(retSix);
				if (retSix.equalsIgnoreCase("ModCloth.com")){
					System.out.println("Retailer Name is Matched");
				} else {
					System.out.println("Retailer Name is not Matched");
				}
				
				Thread.sleep(2000);
				Reddress.close();
				Thread.sleep(2000);
				Reddress.switchTo().window(sixthreddress);
				System.out.println("Window Focus Changed");	
				Thread.sleep(3000);
				
		//Seventh Red Dress Data Verification
				System.out.println("--------------------------------------------------------");
				System.out.println("******Seventh Dress******");
				String SEDN = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[9]/div/div/div/div[2]/span[1]/a")).getText();
				System.out.println("Product Name is : " + SEDN);
				String SEDP = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[9]/div/div/div/div[2]/span[3]")).getText();
				System.out.println("Product Price is : " + SEDP);
				if(SEDN.equalsIgnoreCase("Jones New York Women's Boat Neck Dress With Embellishment")){
					System.out.println("The Product Name is Matched");
				} else {
					System.out.println("The Product Name is Failed to Match");
				}
				
				if(SEDP.equalsIgnoreCase("$134.25")){
					System.out.println("The Product Price is Matched");
				} else {
					System.out.println("The Product Price is Failed to Match");
				}
				Reddress.findElement(By.xpath("//*[@alt='red dress-jones new york womens boat neck dress with embellishment']")).click();
				
			//Window handler for 7th Red Dress
				windowids = Reddress.getWindowHandles();
				iter= windowids.iterator();
				String seventhreddress=iter.next();
				String seventhreddress2=iter.next();
				Reddress.switchTo().window(seventhreddress2);
				System.out.println(Reddress.getTitle());
				
				String Seventhd = Reddress.getTitle();
				System.out.println(Seventhd.length());
				String[] data7 = Seventhd.split("\\ ");
				String retSeven = data7[data7.length-11];
				System.out.println(retSeven);
				if (retSeven.equalsIgnoreCase("Amazon.com:")){
					System.out.println("Retailer Name is Matched");
				} else {
					System.out.println("Retailer Name is not Matched");
				}
				
				Thread.sleep(2000);
				Reddress.close();
				Thread.sleep(2000);
				Reddress.switchTo().window(seventhreddress);
				System.out.println("Window Focus Changed");	
				Thread.sleep(3000);
				
		//Eighth Red Dress Data Verification
				System.out.println("--------------------------------------------------------");
				System.out.println("******Eighth Dress******");
				String EDN = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[10]/div/div/div/div[2]/span[1]/a")).getText();
				System.out.println("Product Name is : " + EDN);
				String EDP = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[10]/div/div/div/div[2]/span[3]")).getText();
				System.out.println("Product Price is : " + EDP);
				if(EDN.equalsIgnoreCase("Issa Silk-jersey dress")){
					System.out.println("The Product Name is Matched");
				} else {
					System.out.println("The Product Name is Failed to Match");
				}
				
				if(EDP.equalsIgnoreCase("$294.97")){
					System.out.println("The Product Price is Matched");
				} else {
					System.out.println("The Product Price is Failed to Match");
				}
				Reddress.findElement(By.xpath("//*[@alt='red dress-herve leger bandage dress']")).click();
				
			//Window handler for 8th Red Dress
				windowids = Reddress.getWindowHandles();
				iter= windowids.iterator();
				String eighthreddress=iter.next();
				String eighthreddress2=iter.next();
				Reddress.switchTo().window(eighthreddress2);
				System.out.println(Reddress.getTitle());
				
				String Eighthd = Reddress.getTitle();
				System.out.println(Eighthd.length());
				String[] data8 = Eighthd.split("\\ ");
				String retEight = data8[data8.length-1];
				System.out.println(retEight);
				if (retEight.equalsIgnoreCase("OUTNET")){
					System.out.println("Retailer Name is Matched");
				} else {
					System.out.println("Retailer Name is not Matched");
				}
				
				Thread.sleep(2000);
				Reddress.close();
				Thread.sleep(2000);
				Reddress.switchTo().window(eighthreddress);
				System.out.println("Window Focus Changed");	
				Thread.sleep(3000);
				
		//Ninth Red Dress Data Verification
				System.out.println("--------------------------------------------------------");
				System.out.println("******Ninth Dress******");
				String NDN = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[11]/div/div/div/div[2]/span[1]/a")).getText();
				System.out.println("Product Name is : " + NDN);
				String NDP = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[11]/div/div/div/div[2]/span[3]")).getText();
				System.out.println("Product Price is : " + NDP);
				if(NDN.equalsIgnoreCase("Herve Leger Bandage dress")){
					System.out.println("The Product Name is Matched");
				} else {
					System.out.println("The Product Name is Failed to Match");
				}
				
				if(NDP.equalsIgnoreCase("$694.98")){
					System.out.println("The Product Price is Matched");
				} else {
					System.out.println("The Product Price is Failed to Match");
				}
				Reddress.findElement(By.xpath("//*[@alt='red dress-vera wang asymmetric bubblehem dress']")).click();
				
			//Window handler for 9th Red Dress
				windowids = Reddress.getWindowHandles();
				iter= windowids.iterator();
				String ninthreddress=iter.next();
				String ninthreddress2=iter.next();
				Reddress.switchTo().window(ninthreddress2);
				System.out.println(Reddress.getTitle());
				
				String Ninthd = Reddress.getTitle();
				System.out.println(Ninthd.length());
				String[] data9 = Ninthd.split("\\ ");
				String retNine = data9[data9.length-1];
				System.out.println(retNine);
				if (retNine.equalsIgnoreCase("Marcus")){
					System.out.println("Retailer Name is Matched");
				} else {
					System.out.println("Retailer Name is not Matched");
				}
				
				Thread.sleep(2000);
				Reddress.close();
				Thread.sleep(2000);
				Reddress.switchTo().window(ninthreddress);
				System.out.println("Window Focus Changed");	
				Thread.sleep(3000);
				
		//Tenth Red Dress Data Verification
				System.out.println("--------------------------------------------------------");
				System.out.println("******Tenth Dress******");
				String TEDN = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[12]/div/div/div/div[2]/span[1]/a")).getText();
				System.out.println("Product Name is : " + TEDN);
				String TEDP = Reddress.findElement(By.xpath("//*[@id='browsePage1ContentDiv']/div[12]/div/div/div/div[2]/span[3]")).getText();
				System.out.println("Product Price is : " + TEDP);
				if(TEDN.equalsIgnoreCase("Vera Wang Asymmetric Bubble-Hem Dress")){
					System.out.println("The Product Name is Matched");
				} else {
					System.out.println("The Product Name is Failed to Match");
				}
				
				if(TEDP.equalsIgnoreCase("$103.95")){
					System.out.println("The Product Price is Matched");
				} else {
					System.out.println("The Product Price is Failed to Match");
				}
				Reddress.findElement(By.xpath("//*[@alt='red dress-issa silkjersey dress']")).click();
				
			//Window handler for 10th Red Dress
				windowids = Reddress.getWindowHandles();
				iter= windowids.iterator();
				String tenthreddress=iter.next();
				String tenthreddress2=iter.next();
				Reddress.switchTo().window(tenthreddress2);
				System.out.println(Reddress.getTitle());
				
				String Tenthd = Reddress.getTitle();
				System.out.println(Tenthd.length());
				String[] data10 = Tenthd.split("\\ ");
				String retTen = data10[data10.length-1];
				System.out.println(retTen);
				if (retTen.equalsIgnoreCase("OUTNET")){
					System.out.println("Retailer Name is Matched");
				} else {
					System.out.println("Retailer Name is not Matched");
				}
				
				Thread.sleep(2000);
				Reddress.close();
				Thread.sleep(2000);
				Reddress.switchTo().window(tenthreddress);
				System.out.println("Window Focus Changed");
				Thread.sleep(3000);
	}
	
}