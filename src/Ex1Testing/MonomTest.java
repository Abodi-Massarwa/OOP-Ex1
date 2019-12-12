
package Ex1Testing;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import Ex1.Monom;
import Ex1.function;


class MonomTest {
	Monom m;

	@BeforeAll
	public static void welcome() {
		System.out.println("welcome to Junit MonomTest !");
	}


	@BeforeEach
	public void init() {
		m= new Monom("1337x^9");
	}

	@AfterEach
	void clean() {
		System.out.println("cleaning up......");
	}
	@AfterAll
	static void bye() {
		System.out.println("BYE BYE !");
	}

	@Test
	void testMonomMonom() {
		Monom help = new Monom(m);
		assertAll(()->assertEquals(m.get_coefficient(), help.get_coefficient()),
				()->assertEquals(m.get_power(), help.get_power()));
	}

	@Test
	void testGet_coefficient() {
		assertEquals(1337, m.get_coefficient());
	}

	@Test
	void testGet_power() {
		assertEquals(9, m.get_power());
	}

	@Test
	void testDerivative() {

		assertAll(()->assertEquals(12033, m.derivative().get_coefficient()),
				()->assertEquals(8, m.derivative().get_power()));
	}


	@RepeatedTest (50)
	void testF() {
		Random r = new Random();
		double randomValue = -100 + (100 - (-100)) * r.nextDouble();
		assertEquals((Math.pow(randomValue, 9)*1337), m.f(randomValue));

	}

	@Test
	void testIsZero() {
		assertEquals(false, m.isZero());
	}
	@Nested
	class StringConstructorTest{
		@Test
		@DisplayName("ordinary test")
		void testMonomString() {
			Monom help = new Monom("15x^2");
			assertAll(()->assertEquals(15, help.get_coefficient()),
					()->assertEquals(2, help.get_power()));
		}
		@Test
		@DisplayName ("testing the constructor after adding one monom to another")
		void advancedtestMonomString() {
			Monom help = new Monom("15x^9");
			help.add(m);
			assertAll(()->assertEquals(1352, help.get_coefficient()),
					()->assertEquals(9, help.get_power()));
		}
	}

	@RepeatedTest(100)
	void testAdd() {
		Monom help = new Monom(6, m.get_power());
		help.add(m);
		assertEquals((6+m.get_coefficient()), help.get_coefficient(),"the function isn't working properly !");
	}

	@RepeatedTest(100)
	void testMultipy() {

		m= new Monom(7,9);
		Monom help = new Monom(5, m.get_power());

		help.multipy(m);

		assertEquals(35, help.get_coefficient());
		assertEquals(m.get_power()*2, help.get_power());
	}
	@Nested
	class ToStringTest{
		@Test
		@DisplayName("ordinary Test")
		void testToString() {
			assertEquals("1337.0x^9", m.toString());
		}
		@Test
		@DisplayName ("ToStringTest with add method ")
		void advancedtestToString() {
			Monom help= new Monom("-x^9");
			help.add(m);
			assertEquals("1336.0x^9", help.toString());
		}
	}

	@Test
	void testEqualsMonom() {
		Monom help_1= new Monom(m);
		Monom help_2= new Monom("15x^3");
		assertAll(()->assertEquals(m.get_coefficient(), help_1.get_coefficient()),
				()->assertEquals(m.get_power(), help_1.get_power()),
				()->assertNotEquals(m.get_coefficient(), help_2.get_coefficient()),
				()->assertNotEquals(m.get_power(), help_2.get_power()));
	}

		@Test
		void testInitFromString() {
			function help =m.initFromString(m.toString());
			assertEquals(help.toString(), m.toString());		
			
		}
	
		@Test
		void testCopy() {
			function f=m.copy();
			assertEquals(((Monom)f).get_coefficient(), m.get_coefficient());
			assertEquals(((Monom)f).get_power(), m.get_power());
		}

}
