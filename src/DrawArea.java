import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class DrawArea extends JComponent
{
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private Image image;
	private static Graphics2D g2;
	private Graphics2D g3;
	private int currentX, currentY, oldX, oldY;
	private static int brushThickness;
	private boolean firstMousePress;
	private String buttonPressed;
	ArrayList<Point> points;
	StringBuilder dataExport;
	StringBuilder sb;
	static Color canvasColor;
	private static int canvasWidth;
	private static int canvasHeight;
	
	public DrawArea()
	{
		setDoubleBuffered(false);
		firstMousePress = false;
		brushThickness = 10;
		buttonPressed = "pointButton";
		points = new ArrayList<Point>();
		dataExport = new StringBuilder();
		sb = new StringBuilder();
		canvasWidth = 1580;
		canvasHeight = 920;
		canvasColor = Color.BLACK;

		addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseReleased(MouseEvent e)
			{
				// System.out.println("Mouse pressed & Released!");
				// System.out.println(e.getSource());
				// System.out.println(buttonPressed);
				// System.out.println("Current point : "+e.getX()+" , "+e.getY());

				if (buttonPressed == "pointButton")
				{
					drawPoint(e.getX(), e.getY());
				}
				else if (buttonPressed == "lineButton")
				{

					if (firstMousePress == false)
					{
						oldX = e.getX();
						oldY = e.getY();
						firstMousePress = true;
					}
					else
					{
						currentX = e.getX();
						currentY = e.getY();

						drawLine(oldX, oldY, currentX, currentY);
						firstMousePress = false;
					}
				}
				else if (buttonPressed == "circleButton")
				{

					if (firstMousePress == false)
					{
						oldX = e.getX();
						oldY = e.getY();
						drawPoint(oldX, oldY);
						firstMousePress = true;
					}
					else
					{
						currentX = e.getX();
						currentY = e.getY();
						int r = getDistance(oldX, oldY, currentX, currentY);
						drawCircle(oldX, oldY, r);

						firstMousePress = false;
					}
				}
				else if (buttonPressed == "textButton")
				{
					String input = JOptionPane.showInputDialog("Please enter the text");
					drawText(input, e.getX(), e.getY());
				}
				else if (buttonPressed == "hullButton")
				{
					//
				}
				else if (buttonPressed == "importButton")
				{

				}
				else if (buttonPressed == "exportButton")
				{

				}
			}

		});

		addMouseMotionListener(new MouseMotionAdapter()
		{
			// public void mouseMoved(MouseEvent e)
			// {
			// clear();
			// drawLine2(e.getX(), e.getY(), e.getX(), 0);
			// drawLine2(e.getX(), e.getY(), e.getX(), getSize().height);
			// drawLine2(e.getX(), e.getY(), 0, e.getY());
			// drawLine2(e.getX(), e.getY(),getSize().width, e.getY());
			// repaint();
			// }

			// @Override
			// public void mouseDragged(MouseEvent e)
			// {
			// currentX=e.getX();
			// currentY=e.getY();
			//
			// if(g2!=null)
			// {
			// g2.drawLine(oldX, oldY, currentX, currentY);
			// repaint();
			// oldX=currentX;
			// oldY=currentY;
			//
			// }
			// }

		});
	}

	protected void drawPoint(int x, int y)
	{
		if (g2 != null)
		{
			g2.setStroke(new BasicStroke(brushThickness / 5));
			g2.drawOval(x - (brushThickness / 2), y - (brushThickness / 2), brushThickness, brushThickness);
			Point p = new Point(x, y);
			points.add(p);

			float red = canvasColor.getRed();
			float green = canvasColor.getGreen();
			float blue = canvasColor.getBlue();
			dataExport.append(x + " " + (canvasHeight - y) + " " + (brushThickness / 5) + " "
					+ (brushThickness / 5) + " " + red + " " + green + " " + blue + " circle \n");

			repaint();
		}
	}

	protected void drawLine(int oldX, int oldY, int currentX, int currentY)
	{
		if (g2 != null)
		{
			g2.setStroke(new BasicStroke(brushThickness / 5));
			g2.drawOval(oldX - (brushThickness / 2), oldY - (brushThickness / 2), brushThickness,
					brushThickness);
			g2.drawOval(currentX - (brushThickness / 2), currentY - (brushThickness / 2), brushThickness,
					brushThickness);
			g2.drawLine(oldX, oldY, currentX, currentY);
			Point p = new Point(oldX, oldY);
			points.add(p);
			Point q = new Point(currentX, currentY);
			points.add(q);

			float red = canvasColor.getRed();
			float green = canvasColor.getGreen();
			float blue = canvasColor.getBlue();
			dataExport.append(oldX + " " + (canvasHeight - oldY) + " " + (brushThickness / 5) + " "
					+ (brushThickness / 5) + " " + red + " " + green + " " + blue + " circle \n");
			dataExport.append(currentX + " " + (canvasHeight - currentY) + " " + (brushThickness / 5) + " "
					+ (brushThickness / 5) + " " + red + " " + green + " " + blue + " circle \n");
			dataExport.append(
					oldX + " " + (canvasHeight - oldY) + " " + currentX + " " + (canvasHeight - currentY)
							+ " " + red + " " + green + " " + blue + " " + (brushThickness / 5) + " edge \n");

			repaint();
			oldX = currentX;
			oldY = currentY;
		}
	}

	private void drawLine1(int oldX, int oldY, int currentX, int currentY)
	{
		if (g2 != null)
		{
			g2.setStroke(new BasicStroke(brushThickness / 10));
			// g2.drawOval(oldX-(brushThickness/2), oldY-(brushThickness/2), brushThickness, brushThickness);
			// g2.drawOval(currentX-(brushThickness/2), currentY-(brushThickness/2), brushThickness, brushThickness);
			g2.drawLine(oldX, oldY, currentX, currentY);

			repaint();
			oldX = currentX;
			oldY = currentY;
		}

	}

	protected void drawCircle(int oldX, int oldY, int r)
	{
		if (g2 != null)
		{
			g2.setStroke(new BasicStroke(brushThickness / 5));
			g2.drawOval(oldX - r, oldY - r, 2 * r, 2 * r);

			float red = canvasColor.getRed();
			float green = canvasColor.getGreen();
			float blue = canvasColor.getBlue();
			dataExport.append(oldX + " " + (canvasHeight - oldY) + " " + r + " " + (brushThickness / 5) + " "
					+ red + " " + green + " " + blue + " circle \n");

			repaint();
			oldX = currentX;
			oldY = currentY;
		}

	}

	protected void drawText(String input, int X, int Y)
	{
		if (g2 != null)
		{
			g2.setStroke(new BasicStroke(brushThickness / 5));
			g2.drawString(input, X, Y);
			repaint();
		}
	}
	

	private void fillOval(int x, int y, int i, int j)
	{
		if (g2 != null)
		{
			g2.setStroke(new BasicStroke(brushThickness / 5));
			g2.fillOval(x, y, i, j);
			repaint();
		}
		
	}


	protected void paintComponent(Graphics g)
	{
		if (image == null)
		{
			image = createImage(getSize().width, getSize().height);
			g2 = (Graphics2D) image.getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			clear();

			g3 = (Graphics2D) image.getGraphics();
			g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			clear();

		}
		g.drawImage(image, 0, 0, null);
	}

	public void clear()
	{
		oldX = 0;
		currentX = 0;
		oldY = 0;
		currentY = 0;

		if (points.isEmpty() == false)
			points.clear();

		dataExport.delete(0, dataExport.length());
		dataExport.setLength(0);
		sb.delete(0, sb.length());
		sb.setLength(0);

		g2.setPaint(Color.WHITE);
		g2.fillRect(0, 0, getSize().width, getSize().height);
		g2.setPaint(Color.BLACK);

		for (int i = 0; i < canvasHeight; i = i + 10)
		{
			if (i % 100 == 0)
			{
//				System.out.println(i);
				drawLine1(0, i, 20, i);
			}
			else if (i % 50 == 0)
			{
				drawLine1(0, i, 10, i);
			}
			else
			{
				drawLine1(0, i, 5, i);
			}
		}

		for (int i = 0; i < canvasHeight; i = i + 10)
		{
			if (i % 100 == 0)
			{
//				System.out.println(i);
				drawLine1(canvasWidth, i, canvasWidth - 20, i);
			}
			else if (i % 50 == 0)
			{
				drawLine1(canvasWidth, i, canvasWidth - 10, i);
			}
			else
			{
				drawLine1(canvasWidth, i, canvasWidth - 5, i);
			}
		}

		for (int i = 0; i < canvasWidth; i = i + 10)
		{
			if (i % 100 == 0)
			{
//				System.out.println(i);
				drawLine1(i, canvasHeight, i, canvasHeight - 20);
			}
			else if (i % 50 == 0)
			{
				drawLine1(i, canvasHeight, i, canvasHeight - 10);
			}
			else
			{
				drawLine1(i, canvasHeight, i, canvasHeight - 5);
			}
		}

		for (int i = 0; i < canvasWidth; i = i + 10)
		{
			if (i % 100 == 0)
			{
//				System.out.println(i);
				drawLine1(i, 0, i, 20);
			}
			else if (i % 50 == 0)
			{
				drawLine1(i, 0, i, 10);
			}
			else
			{
				drawLine1(i, 0, i, 5);
			}
		}

		repaint();
	}

	public void setButtonPressed(String stringButton)
	{
		buttonPressed = stringButton;
	}

	public static void setColor(Color c)
	{
		canvasColor = c;
		g2.setColor(c);
	}

	public Color getColor()
	{
		return canvasColor;
		// return g2.getColor();
	}

	public void drawConvexHull()
	{
		// System.out.println("in convex hull");
		// System.out.println(points);

		ArrayList<Point> hull = quickHull(points);

		for (int i = 0; i < hull.size() - 1; i++)
		{
			drawLine((int) hull.get(i).getX(), (int) hull.get(i).getY(), (int) hull.get(i + 1).getX(),
					(int) hull.get(i + 1).getY());
		}

		drawLine((int) hull.get(0).getX(), (int) hull.get(0).getY(), (int) hull.get(hull.size() - 1).getX(),
				(int) hull.get(hull.size() - 1).getY());

		// if(points.isEmpty()==false)
		// points.clear();

	}

	@SuppressWarnings("unchecked")
	private ArrayList<Point> quickHull(ArrayList<Point> points)
	{
		ArrayList<Point> convexHull = new ArrayList<Point>();
		if (points.size() < 3)
			return (ArrayList<Point>) points.clone();

		int minPoint = -1, maxPoint = -1;
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		for (int i = 0; i < points.size(); i++)
		{
			if (points.get(i).x < minX)
			{
				minX = points.get(i).x;
				minPoint = i;
			}
			if (points.get(i).x > maxX)
			{
				maxX = points.get(i).x;
				maxPoint = i;
			}
		}
		Point A = points.get(minPoint);
		Point B = points.get(maxPoint);
		convexHull.add(A);
		convexHull.add(B);
		points.remove(A);
		points.remove(B);

		ArrayList<Point> leftSet = new ArrayList<Point>();
		ArrayList<Point> rightSet = new ArrayList<Point>();

		for (int i = 0; i < points.size(); i++)
		{
			Point p = points.get(i);
			if (pointLocation(A, B, p) == -1)
				leftSet.add(p);
			else if (pointLocation(A, B, p) == 1)
				rightSet.add(p);
		}
		hullSet(A, B, rightSet, convexHull);
		hullSet(B, A, leftSet, convexHull);

		return convexHull;
	}

	public int distance(Point A, Point B, Point C)
	{
		int ABx = B.x - A.x;
		int ABy = B.y - A.y;
		int num = ABx * (A.y - C.y) - ABy * (A.x - C.x);
		if (num < 0)
			num = -num;
		return num;
	}

	public void hullSet(Point A, Point B, ArrayList<Point> set, ArrayList<Point> hull)
	{
		int insertPosition = hull.indexOf(B);
		if (set.size() == 0)
			return;
		if (set.size() == 1)
		{
			Point p = set.get(0);
			set.remove(p);
			hull.add(insertPosition, p);
			return;
		}
		int dist = Integer.MIN_VALUE;
		int furthestPoint = -1;
		for (int i = 0; i < set.size(); i++)
		{
			Point p = set.get(i);
			int distance = distance(A, B, p);
			if (distance > dist)
			{
				dist = distance;
				furthestPoint = i;
			}
		}
		Point P = set.get(furthestPoint);
		set.remove(furthestPoint);
		hull.add(insertPosition, P);

		// Determine who's to the left of AP
		ArrayList<Point> leftSetAP = new ArrayList<Point>();
		for (int i = 0; i < set.size(); i++)
		{
			Point M = set.get(i);
			if (pointLocation(A, P, M) == 1)
			{
				leftSetAP.add(M);
			}
		}

		// Determine who's to the left of PB
		ArrayList<Point> leftSetPB = new ArrayList<Point>();
		for (int i = 0; i < set.size(); i++)
		{
			Point M = set.get(i);
			if (pointLocation(P, B, M) == 1)
			{
				leftSetPB.add(M);
			}
		}
		hullSet(A, P, leftSetAP, hull);
		hullSet(P, B, leftSetPB, hull);

	}

	public int pointLocation(Point A, Point B, Point P)
	{
		int cp1 = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
		if (cp1 > 0)
			return 1;
		else if (cp1 == 0)
			return 0;
		else
			return -1;
	}

	private int getDistance(int x1, int y1, int x2, int y2)
	{
		int distance = (int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return distance;
	}

	public void drawVoronoiDiagram()
	{
		
		createVoronoiDiagram(points);
		repaint();
		setColor(Color.BLACK);
	}

	@SuppressWarnings("unchecked")
	public  void createVoronoiDiagram(ArrayList<Point> nodes)
	{
		// p = v, q = u
		// ArrayList<Vertex> vertices = new ArrayList<>();
		// for (Node n : nodes)
		// vertices.add(new Vertex(n.x, n.y));
		///DrawArea da = new DrawArea();
		Point nearest[][] = new Point[canvasWidth][canvasHeight];

		Random rand = new Random();
		for (Point v : nodes)
		{
			Color col = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
			System.out.println(col);
			setColor(col);
			g2.setColor(col);
			
			for (int i = 0; i < canvasWidth; i++)
			{
				for (int j = 0; j < canvasHeight; j++)
				{
					Point u = new Point(i, j);
//					System.out.println(u);
					if ((nearest[i][j] == null) || distance(u.x, u.y, v.x, v.y) < distance(u.x, u.y,
							nearest[i][j].x, nearest[i][j].y))
					{			
						nearest[i][j] = v;
						g2.fillOval(i, j, 1, 1);
					}
				}
				
			}
			setColor(Color.BLACK);
			g2.setColor(Color.BLACK);
			g2.fillOval(v.x, v.y, 10, 10);
		}
		
	}

	// public void drawVoronoiDiagram()
	// {
	// System.out.println("voronoi diagram");
	//
	// int cells=points.size();
	// int px[] = new int[cells], py[]= new int[cells], color[]= new int[cells], size = 1000;
	// BufferedImage I = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
	// Random rand = new Random();
	//
	// for (int i = 0; i < cells; i++)
	// {
	// px[i] = (int) points.get(i).getX();
	// py[i] = (int) points.get(i).getY();
	// color[i] = rand.nextInt(16777215);
	//
	// }
	//
	// for (int x = 0; x < size; x++)
	// {
	// for (int y = 0; y < size; y++)
	// {
	// int n = 0;
	// for (byte i = 0; i < cells; i++)
	// {
	// if (distance(px[i], x, py[i], y) < distance(px[n], x, py[n], y))
	// {
	// n = i;
	// }
	// }
	// I.setRGB(x, y, color[n]);
	// }
	// }
	//
	// g2 = I.createGraphics();
	// g2.setColor(Color.BLACK);
	// for (int i = 0; i < cells; i++)
	// {
	// g2.fill(new Ellipse2D .Double(px[i] - 2, py[i] - 2, 4, 4));
	// }
	//
	//// Graphics2D g = I.createGraphics();
	//// g.setColor(Color.BLACK);
	//// for (int i = 0; i < cells; i++)
	//// {
	//// g.fill(new Ellipse2D .Double(px[i] - 2, py[i] - 2, 4, 4));
	//// }
	// }

	static double distance(int x1, int x2, int y1, int y2)
	{
		double d;
		d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)); // Euclidian
		// d = Math.abs(x1 - x2) + Math.abs(y1 - y2); // Manhattan
		// d = Math.pow(Math.pow(Math.abs(x1 - x2), p) + Math.pow(Math.abs(y1 - y2), p), (1 / p)); // Minkovski
		return d;
	}

	public void importFile(File file)
	{
		if (file.toString() == "")
		{
			return;
		}

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(new File(file.toString())));

			String myString = null;
			reader.readLine();

			// for(int i = 0; i<4;i++)
			// reader.readLine();

			while ((myString = reader.readLine()) != null)
			{
				// System.out.println("read : "+myString);

				StringTokenizer st = new StringTokenizer(myString);

				if (myString.endsWith("oval "))
				{
					int x1 = Integer.parseInt(st.nextToken());
					int y1 = Integer.parseInt(st.nextToken());

					y1 = canvasHeight - y1;

					// float red = Float.parseFloat(st.nextToken());
					// float green = Float.parseFloat(st.nextToken());
					// float blue = Float.parseFloat(st.nextToken());
					// Color c = new Color(red,green,blue);

					drawCircle(x1, y1, brushThickness / 5);
				}

				else if (myString.endsWith("edge "))
				{
					int x1 = Integer.parseInt(st.nextToken());
					int y1 = Integer.parseInt(st.nextToken());
					int x2 = Integer.parseInt(st.nextToken());
					int y2 = Integer.parseInt(st.nextToken());

					y1 = canvasHeight - y1;
					y2 = canvasHeight - y2;

					// System.out.println("edge : "+x1+" "+y1+" "+x2+" "+y2);

					// float red = Float.parseFloat(st.nextToken());
					// float green = Float.parseFloat(st.nextToken());
					// float blue = Float.parseFloat(st.nextToken());
					// Color c = new Color(red,green,blue);
					// int lineSize = Integer.parseInt(st.nextToken());

					drawPoint(x1, y1);
					drawPoint(x2, y2);
					drawLine(x1, y1, x2, y2);

				}

				else if (myString.endsWith("circle "))
				{
					int x1 = Integer.parseInt(st.nextToken());
					int y1 = Integer.parseInt(st.nextToken());

					y1 = canvasHeight - y1;

					int r = Integer.parseInt(st.nextToken());

					// System.out.println("circle : "+x1+" "+y1+" "+r);

					// int lineSize = Integer.parseInt(st.nextToken());
					// float red = Float.parseFloat(st.nextToken());
					// float green = Float.parseFloat(st.nextToken());
					// float blue = Float.parseFloat(st.nextToken());
					// Color c = new Color(red,green,blue);

					drawPoint(x1, y1);
					drawCircle(x1, y1, r);
				}

			}
			reader.close();
		}

		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public void exportFile(File file)
	{
		// int xmin=Integer.MAX_VALUE;
		// int xmax=Integer.MIN_VALUE;
		// int ymin=Integer.MAX_VALUE;
		// int ymax=Integer.MIN_VALUE;
		//
		// for(int i=0;i<points.size();i++)
		// {
		// if(points.get(i).getX()<xmin)
		// {
		// xmin=(int) points.get(i).getX();
		// }
		// if(points.get(i).getX()>xmax)
		// {
		// xmax=(int) points.get(i).getX();
		// }
		// if(points.get(i).getY()<ymin)
		// {
		// ymin=canvasHeight-(int) points.get(i).getY();
		// }
		// if(points.get(i).getY()>ymax)
		// {
		// ymax=canvasHeight-(int) points.get(i).getY();
		// }
		// }

		// ymin=canvasHeight-ymin;
		// ymax=canvasHeight-ymax;
		// int offset=100;
		// xmin=xmin-100;
		// ymin=ymin-100;
		// xmax=xmax+100;
		// ymax=ymax+100;

		int xmin = 0;
		int ymin = 0;
		int xmax = canvasWidth;
		int ymax = canvasHeight;

		// System.out.println("my boundaries are : "+xmin + " " + ymin + " " + xmax + " " + ymax);

		float redBrush = canvasColor.getRed();
		float greenBrush = canvasColor.getGreen();
		float blueBrush = canvasColor.getBlue();
		float redFill = canvasColor.getRed();
		float greenFill = canvasColor.getGreen();
		float blueFill = canvasColor.getBlue();
		int lineSize = brushThickness / 5;

		dataExport.append("closepath\n");
		dataExport.append("gsave" + " " + redFill + " " + greenFill + " " + blueFill + " " + "setrgbcolor"
				+ " " + "fill" + " " + "grestore\n");
		dataExport.append(redBrush + " " + greenBrush + " " + blueBrush + " " + "setrgbcolor" + " " + lineSize
				+ " " + "setlinewidth" + " " + "stroke\n");

		sb.append("%!PS-Adobe-3.0 EPSF-3.0\n");
		sb.append("%%BoundingBox: " + (xmin) + " " + (ymin) + " " + (xmax) + " " + (ymax) + "\n");
		sb.append("/circle { setrgbcolor setlinewidth newpath 0 360 arc stroke } def\n");
		sb.append("/edge   { setlinewidth setrgbcolor newpath moveto lineto stroke } def\n");
		sb.append("/oval { setrgbcolor newpath 10 0 360 arc fill } def\n");
		sb.append(dataExport.toString());

		try
		{
			FileWriter writer = new FileWriter(new File(file.toString()));
			writer.write(sb.toString());
			writer.close();
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void findEnclosingCircle()
	{
		// System.out.println("in enclosing circles");
		smallestenclosingcircle c = new smallestenclosingcircle();
		java.util.List<Integer> list = new ArrayList<Integer>();

		ArrayList<Point> pointsToBePassed = new ArrayList<Point>();
		pointsToBePassed.add(new Point(1, 2));
		list = c.makeCircle1(points);
		// System.out.println(list.get(0)+" : "+list.get(1)+" : "+list.get(2));
		drawCircle(list.get(0), list.get(1), list.get(2));
	}

	public void findEnclosingRectangle()
	{
		int xmin = Integer.MAX_VALUE;
		int xmax = Integer.MIN_VALUE;
		int ymin = Integer.MAX_VALUE;
		int ymax = Integer.MIN_VALUE;

		for (int i = 0; i < points.size(); i++)
		{
			if (points.get(i).getX() < xmin)
			{
				xmin = (int) points.get(i).getX();
			}
			if (points.get(i).getX() > xmax)
			{
				xmax = (int) points.get(i).getX();
			}
			if (points.get(i).getY() < ymin)
			{
				ymin = (int) points.get(i).getY();
			}
			if (points.get(i).getY() > ymax)
			{
				ymax = (int) points.get(i).getY();
			}
		}

		drawLine(xmin, ymin, xmax, ymin);
		drawLine(xmax, ymin, xmax, ymax);
		drawLine(xmax, ymax, xmin, ymax);
		drawLine(xmin, ymax, xmin, ymin);
	}

}