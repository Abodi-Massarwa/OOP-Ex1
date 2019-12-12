package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import Ex1.Monom;
import Ex1.Polynom;
import Ex1.Polynom_able;



class PolynomTest {

	Polynom poly;
	@BeforeAll
	static void print_welcome() {
		System.out.println("Welcome beautiful !");
	}
	@BeforeEach
	void init() {
		poly= new Polynom("2x^2+4x-30"); //roots are -5,3
	}

	///////
	@AfterAll
	static void print_bye() {
		System.out.println("BYE BYE bueatiful !");
	}

	@Test
	void testPolynomString() {
		Polynom suspect = new Polynom("5x^2+2x+1");
		assertEquals("5.0x^2+2.0x+1.0", suspect.toString());
	}

	@RepeatedTest(30)
	void testF() {
		Random r = new Random();
		double randomValue = -100 + (100 - (-100)) * r.nextDouble();
		
		assertEquals((float)(2.00*Math.pow(randomValue, 2)+4.00*randomValue-30.00),(float) poly.f(randomValue));

	}

	@Test
	void testAddPolynom_able() {
		Polynom_able p1= new Polynom(poly.toString());
		poly.add(p1);
		assertEquals("4.0x^2+8.0x-60.0",poly.toString() );
	}

	@Test
	void testAddMonom(){
		Monom m = new Monom(2, 0);
		poly.add(m);
		assertEquals("2.0x^2+4.0x-28.0", poly.toString());
	}

	@Test
	void testSubstract() {
		Polynom_able poly_2= new Polynom("2x^2+4x-30");
		System.out.println(poly);
		System.out.println("before "+poly.getPolynom().size());
		poly.substract(poly_2);
		System.out.println(poly.getPolynom().size());

	}

	@Test
	void testMultiplyPolynom_able() {
		Polynom_able p1 = new Polynom("x");
		if(p1 instanceof Polynom) {
	poly.multiply(p1);
	}
	assertEquals("2.0x^3+4.0x^2-30.0x", poly.toString());
	}

	@Test
	void testEqualsObject() {
		Point p = new Point(1, 2); //not instance of Polynomial @return false;
		Polynom poly_2= new Polynom(poly.toString());
		assertAll(()-> assertFalse(poly.equals(p)),
				()->assertTrue(poly.equals(poly_2)));
	}

	@Test
	void testIsZero() {
		Polynom poly_2= new Polynom("0");
		assertAll(()->assertTrue(poly_2.isZero()),
				()->assertFalse(poly.isZero()));
	}

//	@Test
//	void testRoot() {
//	  assertAll(()->assertEquals((-5), poly.root((-7), 2, Monom.EPSILON)),
//	  ()->assertEquals((3), poly.root(0, 4, Monom.EPSILON)));
//	}

	@Test
	void testCopy() {
		Polynom_able ab=poly.copy();
		assertTrue(poly.equals(ab));
	}

	@Test
	void testDerivative() {
		Polynom poly_2= new Polynom("4x+4");
		poly= new Polynom("2x^2+4x-30");
		poly=(Polynom) poly.derivative();
		assertTrue(poly_2.equals(poly));
	}

}
