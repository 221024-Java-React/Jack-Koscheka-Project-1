package general;

import org.junit.*;

public class PriceTest
{	
	static Price price;
	
	@BeforeClass
	public static void classSetUp()
	{
		price = new Price("0");
	}
	
	@AfterClass
	public static void classCleanUp()
	{
		
	}
	
	@Before
	public void testSetUp()
	{
		
	}
	
	@After
	public void testCleanUp()
	{
		
	}
	
	@Test
	public void test1()
	{
		Assert.assertEquals(price.getDollars(), 0);
		Assert.assertEquals(price.getCents(), 0);
	}
	
	@Test
	public void test2()
	{
		price.set("$10.52");
		
		Assert.assertEquals(price.getDollars(), 10);
		Assert.assertEquals(price.getCents(), 52);
	}
	
	@Test
	public void test3()
	{
		price.set("$10.5207");
		
		Assert.assertEquals(price.getDollars(), 10);
		Assert.assertEquals(price.getCents(), 52);
	}
	
	@Test
	public void test4()
	{
		price.set("$10.52");
		
		Assert.assertEquals(price.getAmount(), 10.52, Math.ulp(10.52));
	}
}
