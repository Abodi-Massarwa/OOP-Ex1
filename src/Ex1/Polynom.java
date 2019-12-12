package Ex1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values.
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private LinkedList<Monom>  Polynom;


	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		this.setPolynom(new LinkedList<Monom>());
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		this.setPolynom(new LinkedList<Monom>());
		int index=0;
		if (s.equals(new String("0"))) {
			add(new Monom("0"));
			return;
		}
		for (int i = 0; i < s.length(); i++) {
			if (((s.charAt(i) == '+' || s.charAt(i) == '-') && i!=0)) {
				Monom m = new Monom(s.substring(index, i));
				if (m.isZero()) {
					setPolynom(null);
					return;
				}
				add(m);
				index=i;
			}
		}
		Monom m = new Monom(s.substring(index, s.length()));
		if (m.isZero()) {
			setPolynom(null);
			return;
		}
		add(m);
	}
	public boolean equals(Object ob) {
	
		if(!(ob instanceof Polynom)) return false;
		if(ob instanceof Polynom) {
			for (int i = 0; i < 1; i++) {
				System.out.println(((Polynom) ob).f(i));
				if(((Polynom) ob).f(i)!=f(i)) 	return false;								
			}
		}
		return true;
	}	


	@Override
	public double f(double x) {
		double ans = 0;
		StringTokenizer p1 = new StringTokenizer(getPolynom().toString(),"[],");
		while (p1.hasMoreTokens()) {
			Monom m = new Monom(p1.nextToken());
			ans+=m.f(x);
		}	
		return ans;	
	}

	@Override
	public void add(Polynom_able p1) {		
		if (p1 instanceof Polynom) {
		for ( Monom i : ((Polynom) p1).getPolynom()) {
					add(i);							
			}
		}
		else {
			System.out.println("------------------------------------");
			System.out.println("ERROR: "+p1+" is not instant of Polynom!");
			System.out.println("------------------------------------");
			}
	}

	@Override
	public void add(Monom m1) {
										 
			for(Monom i : getPolynom()) {			
		if (i.get_power()==m1.get_power()) { 
			i.add(m1);
			return;
				}
			}	
		
		getPolynom().add(m1);
	}

	@Override
	public void substract(Polynom_able p1) {

		if (p1 instanceof Polynom) {	
			StringTokenizer split = new StringTokenizer(((Polynom) p1).getPolynom().toString(),"[],");
			Monom m = new Monom(-1,0);
		while ( split.hasMoreTokens()) {
			Monom m1 = new Monom(split.nextToken());
			m1.multipy(m);		
			add(m1);	
		}
				
		}
		else {
			System.out.println("------------------------------------");
			System.out.println("ERROR: "+p1+" is not instant of Polynom!");
			System.out.println("------------------------------------");
			}
	}

	@Override
	public void multiply(Polynom_able p1) {
		if (p1 instanceof Polynom) {	
			String[] s =new String[((Polynom) p1).getPolynom().size()*getPolynom().size()];
			int k=0;
		for ( Monom i : ((Polynom) p1).getPolynom()) {
			for	(	Monom j : getPolynom()) {
				Monom m = new Monom(i.toString());						
				m.multipy(j);		
				s[k]=m.toString();
				k++;
			}	
	}
		while (!getPolynom().isEmpty())	getPolynom().remove();	
		for (int j = 0; j < s.length; j++) {		
			add(new Monom(s[j]));
		}
		}
		else {
		System.out.println("------------------------------------");
		System.out.println("ERROR: "+p1+" is not instant of Polynom!");
		System.out.println("------------------------------------");
		}
	}

	@Override
	public Polynom_able derivative() {
		StringTokenizer split = new StringTokenizer(getPolynom().toString(),"[],");		
		Polynom_able p1 = new Polynom(); 		
		while(split.hasMoreTokens()) {
			Monom m = new Monom(split.nextToken());
			p1.add(m.derivative());						
		}					
		return p1;
	}


	@Override
	public void multiply(Monom m1) {	
		for (Monom m : getPolynom()) {
			m.multipy(m1);
		}
	}
	

	@Override
	public double root(double x0, double x1, double eps) {
		double check = x0*x1;
		if (check>0) {
			System.out.println("Error: both of x0,x1 are positive or  negative");
			return -1;
		}					
		double ans=0;
		while (Math.abs(x1-x0)>eps) {
			ans=(x0+x1)/2;
			if(f(ans)>0) x1=ans;
			else if (f(ans)<0) x0=ans;
		}
		String s = ""+String.format("%.2f", ans);
		ans=Double.parseDouble(s);
		return ans;
	}
	
	@Override
	public double area(double x0, double x1, double eps) {
		if (x0>x1) return 0;					
		double v= 0;
		double k =0;
		int counter=0;
		boolean first = true;
		while (k+eps<x1) {
			k=k+eps;
			if (first) {
				v= (x0-eps)*f(x0);
				first = false;				
			}
			else {
				counter++;
				v=v+eps*f(x0+eps);			
			}						
		}
		v=v+x1-(counter*eps)*f(counter*eps);
		String s = ""+String.format("%.2f", v);
		v=Double.parseDouble(s);
		return v;
	}


	/// toString function
	@Override
	public String toString() {		
		StringTokenizer p1 = new StringTokenizer(getPolynom().toString(),"[],");
		String ans = "";
		boolean stopper = true;
		while (p1.hasMoreTokens()) {
			Monom m = new Monom(p1.nextToken());
			if (stopper ==true) {
				ans+=m.toString();
				stopper=false;
			}
			else if (m.get_coefficient()<0) ans+=m.toString();		
			else if (m.get_coefficient()>0) ans+="+"+m.toString();	
		}
	return ans;
	}
	
	@Override
	public Polynom_able copy() {		
		Polynom_able copy = new Polynom();		
		for (Monom i : getPolynom()) copy.add(i);				
		return copy;
	}
	
	@Override
	public boolean isZero() {		
		if (getPolynom() == null) return true;
		for (Monom i : getPolynom()) if(!i.isZero()) return false;
		return true;
	}
	
	@Override
	public function initFromString(String s) {
		return new Polynom(s);
	}
	@Override
	public Iterator<Monom> iteretor() {
		return getPolynom().iterator();
	}
	public LinkedList<Monom> getPolynom() {
		return Polynom;
	}
	public void setPolynom(LinkedList<Monom> polynom) {
		Polynom = polynom;
	}
	

}
