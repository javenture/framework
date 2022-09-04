package net.javenture.framework.test;


import com.codeborne.selenide.*;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.UtcTimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class SelenideTest
{

	@BeforeEach
	public void setup()
	{
		Configuration.baseUrl = "http://neolink.local";

		System.out.println("setup");
	}


	@Test
	public void test1()
	{
		open("/");
		$("h1").shouldHave(text("Неолинк — интернет-магазин в Полтаве"));

		//$(byAttribute("placeholder", "Поиск")).setValue("core i5").pressEnter();
		$("input[placeholder=Поиск]").setValue("core i5").pressEnter();


		$("h1").shouldHave(text("Поиск"));


		try
		{
			Thread.sleep(3000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}


	@Test
	public void test2()
	{
		//Configuration.browser = "firefox";
		//Configuration.headless = true;

		open("/");
		$("h1").shouldHave(text("Неолинк — интернет-магазин в Полтаве"));

		ElementsCollection.SelenideElementIterable elements = $$("p[class=javenture-title] a").asDynamicIterable();

		for (SelenideElement element : elements)
		{
			if (element.text().equals("Ноутбуки"))
			{
				element.should(visible);
				element.click();

				break;
			}
		}


		$("h1").should(visible, text("Ноутбуки"));


		try
		{
			Thread.sleep(3000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}


	@Test
	public void test3()
	{
		//Configuration.browserSize = "1280x960";
		open("/catalog/notebook/index.html");
		$("h1").shouldHave(text("Ноутбуки"));

		$(".catalog_filter").$(withText("Производитель")).click();
		$(".catalog_filter").$(withText("Lenovo")).should(visible).click();

		//$(".catalog_filter").$(withText("Операционная система")).click();
		//$(".catalog_filter").$(withText("Windows 10 Pro")).should(visible).click();

		String s = $(withText("Товаров найдено")).parent().$("strong").text();
		int count = IntUtil.parse(s, 0);

		System.out.println(count);

		Assertions.assertEquals(count, 398);

		ElementsCollection elements = $$(".catalog-card");

		long begin = UtcTimeUtil.ms();


		while (UtcTimeUtil.ms() - begin < 60000)
		{
			elements.last().scrollTo();

			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			elements = $$(".catalog-card");

			if (elements.size() == count) break;
		}

		elements.should(CollectionCondition.size(count));


/*
		SelenideElement item = $(withText("81YH00PCRA"));
		item.scrollTo();
		item.should(exist);
		item.closest(".catalog-card").$("div.q-chip span").should(text("23999"));
*/


		try
		{
			Thread.sleep(3000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}


	@Test
	public void test4()
	{
		Configuration.browserSize = "1280x960";
		open("/catalog/notebook/index.html");
		$("h1").shouldHave(text("Ноутбуки"));

		SelenideElement filter = $(".catalog_filter");

		if (!filter.isDisplayed())
		{
			$("div.q-page-sticky").click();

			filter.$(withText("Производитель")).click();
			filter.$(withText("Lenovo")).should(visible).click();

			//$(".catalog_filter").$(withText("Операционная система")).click();
			//$(".catalog_filter").$(withText("Windows 10 Pro")).should(visible).click();

			$("div.q-drawer__backdrop").click(ClickOptions.usingDefaultMethod().offset(1, 1));
		}
		else
		{
			filter.$(withText("Производитель")).click();
			filter.$(withText("Lenovo")).should(visible).click();
		}

		String s = $(withText("Товаров найдено")).parent().$("strong").text();
		int count = IntUtil.parse(s, 0);

		System.out.println(count);

		Assertions.assertEquals(count, 398);

		ElementsCollection elements = $$(".catalog-card");

		long begin = UtcTimeUtil.ms();


		while (UtcTimeUtil.ms() - begin < 60000)
		{
			elements.last().scrollTo();

			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			elements = $$(".catalog-card");

			if (elements.size() == count) break;
		}

		elements.should(CollectionCondition.size(count));




		try
		{
			Thread.sleep(3000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}

}
