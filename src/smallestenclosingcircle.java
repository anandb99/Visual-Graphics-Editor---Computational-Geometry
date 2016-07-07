
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class smallestenclosingcircle
{
	public static Circle makeCircle(ArrayList<java.awt.Point> points) 
	{
		List<Point> shuffled = new ArrayList<Point>();

		for(int i=0;i<points.size();i++)
		{
			int x=(int) points.get(i).getX();
			int y=(int) points.get(i).getY();
			Point temp = new Point(x,y);
			shuffled.add(temp);
		}

		Collections.shuffle(shuffled, new Random());

		Circle c = null;
		for (int i = 0; i < shuffled.size(); i++) 
		{
			Point p = shuffled.get(i);
			if (c == null || !c.contains(p))
				c = makeCircleOnePoint(shuffled.subList(0, i + 1), p);
		}
		return c;
	}

	public List<Integer> makeCircle1(ArrayList<java.awt.Point> points)
	{
		List<Integer> answer= new ArrayList<Integer>();
		Circle ans=makeCircle(points);
		answer.add((int) ans.c.x);
		answer.add((int) ans.c.y);
		answer.add((int) ans.r);
		return answer;
	}

	private static Circle makeCircleOnePoint(List<Point> points, Point p) 
	{
		Circle c = new Circle(p, 0);
		for (int i = 0; i < points.size(); i++) 
		{
			Point q = points.get(i);
			if (!c.contains(q)) 
			{
				if (c.r == 0)
					c = makeDiameter(p, q);
				else
					c = makeCircleTwoPoints(points.subList(0, i + 1), p, q);
			}
		}
		return c;
	}


	private static Circle makeCircleTwoPoints(List<Point> points, Point p, Point q) 
	{
		Circle temp = makeDiameter(p, q);
		if (temp.contains(points))
			return temp;

		Circle left = null;
		Circle right = null;
		for (Point r : points)
		{  
			Point pq = q.subtract(p);
			double cross = pq.cross(r.subtract(p));
			Circle c = makeCircumcircle(p, q, r);
			if (c == null)
				continue;
			else if (cross > 0 && (left == null || pq.cross(c.c.subtract(p)) > pq.cross(left.c.subtract(p))))
				left = c;
			else if (cross < 0 && (right == null || pq.cross(c.c.subtract(p)) < pq.cross(right.c.subtract(p))))
				right = c;
		}
		return right == null || left != null && left.r <= right.r ? left : right;
	}


	static Circle makeDiameter(Point a, Point b) 
	{
		return new Circle(new Point((a.x + b.x)/ 2, (a.y + b.y) / 2), a.distance(b) / 2);
	}


	static Circle makeCircumcircle(Point a, Point b, Point c)
	{

		double d = (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y)) * 2;
		if (d == 0)
			return null;
		double x = (a.norm() * (b.y - c.y) + b.norm() * (c.y - a.y) + c.norm() * (a.y - b.y)) / d;
		double y = (a.norm() * (c.x - b.x) + b.norm() * (a.x - c.x) + c.norm() * (b.x - a.x)) / d;
		Point p = new Point(x, y);
		return new Circle(p, p.distance(a));
	}

}



class Circle {

	private static double EPSILON = 1e-12;

	public final Point c;   
	public final double r; 

	public Circle(Point c, double r) 
	{
		this.c = c;
		this.r = r;
	}

	public boolean contains(Point p) 
	{
		return c.distance(p) <= r + EPSILON;
	}

	public boolean contains(Collection<Point> ps) 
	{
		for (Point p : ps)
		{
			if (!contains(p))
				return false;
		}
		return true;
	}

	public String toString()
	{
		return String.format("Circle(x=%g, y=%g, r=%g)", c.x, c.y, r);
	}

}



class Point
{
	public final double x;
	public final double y;

	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public Point subtract(Point p) 
	{
		return new Point(x - p.x, y - p.y);
	}

	public double distance(Point p)
	{
		return Math.hypot(x - p.x, y - p.y);
	}

	public double cross(Point p)
	{
		return x * p.y - y * p.x;
	}

	public double norm()
	{
		return x * x + y * y;
	}

	public String toString()
	{
		return String.format("Point(%g, %g)", x, y);
	}
}