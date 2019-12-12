package Ex1;


import java.awt.Color;
import java.awt.Font;
import java.io.EOFException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;



import org.json.simple.JSONArray;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import junit.framework.Test;



public class Functions_GUI implements functions,Serializable  {
	public static Color[] Colors = {Color.blue, Color.cyan,
			Color.MAGENTA, Color.ORANGE, Color.red, Color.GREEN, Color.PINK};
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<function> list;
	public Functions_GUI() {
		this.list= new ArrayList<function>();
	}
	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public boolean isEmpty() {

		return this.list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return this.list.contains(o);
	}

	@Override
	public Iterator<function> iterator() {
		return this.list.iterator();

	}

	@Override
	public Object[] toArray() {
		return this.list.toArray();

	}

	@Override
	public <T> T[] toArray(T[] a) {
		return (T[]) this.list.toArray(a);

	}

	@Override
	public boolean add(function e) {

		return this.list.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return this.list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends function> c) {
		return this.list.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.list.retainAll(c);
	}

	@Override
	public void clear() {
		this.list.clear();

	}
	public String toString() {
		return this.list.toString();
	}

	@Override
	public void initFromFile(String file) throws IOException {
		FileInputStream fs = new FileInputStream(file);
		ObjectInputStream os= new ObjectInputStream(fs);
		boolean flag=true;
		function func =null;//temporary
		while(flag) {
			try {
				func= (function) os.readObject();
			} catch (ClassNotFoundException e) {e.printStackTrace();}
			catch (EOFException e) {
				flag=false; break;
			}
			if(func!=null) {
				this.add(func);		
			}
			else {
				flag=false;
			}
		}
		os.close();
		fs.close();
	}

	@Override
	public void saveToFile(String file) throws IOException {
		Iterator<function> it= this.iterator();
		ObjectOutputStream om= new ObjectOutputStream(new FileOutputStream(file));
		while(it.hasNext()) {
			om.writeObject(it.next());
		}
		om.close();
	}

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());
		double max_x_axis=rx.get_max() , min_x_axis=rx.get_min();
		double max_y_axis=ry.get_max() , min_y_axis=ry.get_min();
		boolean flag=true;
		int counter=0;
		int k =0;
		Color color = null;
		for(function test:this.list) {
			counter++;
			int n = resolution;
			int j=-n/2;
			double[] x = new double[n+1];
			double[] y = new double[n+1];
			for (int i = 0; i <= n; i++) {
				x[i] = 100* j / n;
				y[i] = test.f(x[i]);
				j++;
			}		

			/**
			 * vertical and horizontal lines most be drown once 
			 */
			if(flag) {
				flag=false;
				// rescale the coordinate system
				StdDraw.setXscale(min_x_axis, max_x_axis);
				StdDraw.setYscale(min_y_axis,max_y_axis);

				//////// vertical lines
				StdDraw.setPenColor(Color.LIGHT_GRAY);
				for ( int i = (int)min_x_axis; i <= 2*max_x_axis; i=i+25) {
					StdDraw.line(min_x_axis+i, min_y_axis, min_x_axis+i, max_y_axis);
				}
				//////// horizontal  lines
				for (double i = min_y_axis; i <= 2*max_y_axis; i=i+25) {
					StdDraw.line(min_x_axis, i, max_x_axis, i);
				}



				//////// x axis		
				StdDraw.setPenColor(Color.black);
				StdDraw.setPenRadius(0.005);
				StdDraw.line(min_x_axis, 0, max_x_axis, 0);
				StdDraw.setPenColor(Color.RED);
				StdDraw.setFont(new Font("TimesRoman", Font.ROMAN_BASELINE, 10));
				for (int i = 0; i <= 2*max_x_axis; i=i+(int)max_x_axis/20) {
					StdDraw.text(min_x_axis+i,(-2.3*(max_x_axis/100.0)), Integer.toString((int)(min_x_axis+i)));


				}
				//				//////// y axis	
				StdDraw.setPenColor(Color.black);
				StdDraw.line(0, min_y_axis, 0, max_y_axis);
				StdDraw.setPenColor(Color.RED);
				double mid = (min_x_axis+max_x_axis)/2;
				for (int i = 0; i < 2*max_y_axis; i+=max_y_axis/20) {
					StdDraw.text(mid-(2.6*(max_y_axis/100.0)), min_y_axis+i, Integer.toString((int)min_y_axis+i));
				}
			}
			StdDraw.setPenColor(Colors[k]);
			for (int i = 0; i < n; i++) {

				StdDraw.line(x[i], y[i], x[i+1], y[i+1]);


			}
			System.out.println(counter+") "+Colors[k]+", "+"f(x)= "+test+" .");
			k=(k+1)%Colors.length;
		}


	}

	@Override
	public void drawFunctions(String json_file) {
		Object obj = null;
		try {
			obj = new JSONParser().parse(new FileReader("GUI_params.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("unchecked")
		HashMap<String , Object> jo= (HashMap<String, Object>) obj;
		int height= Integer.parseInt(jo.get("Height").toString());
		int width= Integer.parseInt(jo.get("Width").toString());
		int res= Integer.parseInt(jo.get("Resolution").toString());
		JSONArray a= (JSONArray) jo.get("Range_X");
		JSONArray b= (JSONArray) jo.get("Range_Y");
		Range x = new Range(Double.parseDouble(a.get(0).toString()),Double.parseDouble(a.get(1).toString()));
		Range y= new Range(Double.parseDouble(b.get(0).toString()),Double.parseDouble(b.get(1).toString()));
		this.drawFunctions(Math.toIntExact(width), Math.toIntExact(height), x, y,Math.toIntExact(res));


	}
	public static void main(String[] args) throws Exception {
		//		Functions_GUI test = new Functions_GUI();
		//		test.add(new Polynom("x^2"));
		//		test.add(new Polynom("x^3"));
		//		test.add(new Polynom("x+5"));
		//		Range x_y= new Range(-100, 100);
		//		Range x = new Range(-20, 20);
		//		test.drawFunctions(1280, 720, x, x_y, 100);

		functions ans = new Functions_GUI();
		ans.add(new Polynom("x^2"));
		//ans.add(new Polynom("x^2-50"));
		ans.add(new Polynom("x^2+50"));
		ans.add(new Polynom("x^3"));
		ans.add(new Polynom("x^3-50"));
		//ans.add(new Polynom("x^3+50"));
		ans.drawFunctions("GUI_params.txt");

	}

}
