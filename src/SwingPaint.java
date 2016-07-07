import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwingPaint 
{
	JButton clearButton,pointButton,lineButton,circleButton,textButton,colorButton,hullButton,importButton,exportButton,voronoiButton,enclosingCircleButton,enclosingRectanlgeButton;
	private JLabel sampleText;
	DrawArea drawArea;

	ActionListener actionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==pointButton)
			{
				drawArea.setButtonPressed("pointButton");
			}
			else if(e.getSource()==lineButton)
			{
				drawArea.setButtonPressed("lineButton");
			}
			else if(e.getSource()==circleButton)
			{
				drawArea.setButtonPressed("circleButton");
			}
			else if(e.getSource()==textButton)
			{
				drawArea.setButtonPressed("textButton");
			}
			else if(e.getSource()==colorButton)
			{
				drawArea.setButtonPressed("colorButton");
				Color c = JColorChooser.showDialog(null, "Choose a Color", sampleText.getForeground());
				drawArea.setColor(c);
			}
			else if(e.getSource()==hullButton)
			{
				drawArea.setButtonPressed("hullButton");
				drawArea.drawConvexHull();
			}
			else if(e.getSource()==enclosingCircleButton)
			{
				drawArea.setButtonPressed("enclosingCircleButton");
				drawArea.findEnclosingCircle();
			}
			else if(e.getSource()==enclosingRectanlgeButton)
			{
				drawArea.setButtonPressed("enclosingRectanlgeButton");
				drawArea.findEnclosingRectangle();
			}
			else if(e.getSource()==voronoiButton)
			{
				drawArea.setButtonPressed("voronoiButton");
				drawArea.drawVoronoiDiagram();
			}
			else if(e.getSource()==clearButton)
			{
				drawArea.setButtonPressed("clearButton");
				drawArea.clear();
			}
			else if(e.getSource()==importButton)
			{
				drawArea.setButtonPressed("importButton");
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
				{
				  File file = fileChooser.getSelectedFile();
				  //System.out.println(file);
				  drawArea.importFile(file);
				}
			}
			else if(e.getSource()==exportButton)
			{
				drawArea.setButtonPressed("exportButton");
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) 
				{
				  File file = fileChooser.getSelectedFile();
				  drawArea.exportFile(file);
				}
			}	
		}
	};

	public static void main(String[] args) 
	{
		SwingPaint mySwingPaint=new SwingPaint();
		mySwingPaint.show();
	}

	public void show()
	{
		JFrame frame= new JFrame("VGE");
		Container content=frame.getContentPane();
		content.setLayout(new BorderLayout());
		drawArea= new DrawArea();

		content.add(drawArea,BorderLayout.CENTER);

		JPanel controls = new JPanel();

		pointButton = new JButton("Point");
		//pointButton.setIcon(new ImageIcon("C:/Users/labuser/workspace/VGE/point.gif"));
		pointButton.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,12));
		pointButton.setFocusPainted(false);
		pointButton.addActionListener(actionListener);
		
		lineButton = new JButton("Line");
		lineButton.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,12));
		lineButton.setFocusPainted(false);
		lineButton.addActionListener(actionListener);
		
		circleButton = new JButton("Circle");
		circleButton.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,12));
		circleButton.setFocusPainted(false);
		circleButton.addActionListener(actionListener);
		
		textButton = new JButton("Text");
		textButton.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,12));
		textButton.setFocusPainted(false);
		textButton.addActionListener(actionListener);
		
		sampleText = new JLabel("Label");
		colorButton=new JButton("Color");
		colorButton.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,12));
		colorButton.setFocusPainted(false);
		colorButton.addActionListener(actionListener);
		
		hullButton = new JButton("Convex Hull");
		hullButton.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,12));
		hullButton.setFocusPainted(false);
		hullButton.addActionListener(actionListener);
		
		enclosingCircleButton = new JButton("Enclosing Cirlce");
		enclosingCircleButton.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,12));
		enclosingCircleButton.setFocusPainted(false);
		enclosingCircleButton.addActionListener(actionListener);
		
		enclosingRectanlgeButton = new JButton("Enclosing Rectangle");
		enclosingRectanlgeButton.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,12));
		enclosingRectanlgeButton.setFocusPainted(false);
		enclosingRectanlgeButton.addActionListener(actionListener);
		
		voronoiButton = new JButton("Voronoi Diagram");
		voronoiButton.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,12));
		voronoiButton.setFocusPainted(false);
		voronoiButton.addActionListener(actionListener);
		
		clearButton = new JButton("Clear");
		clearButton.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,12));
		clearButton.setFocusPainted(false);
		clearButton.addActionListener(actionListener);
		
		importButton = new JButton("Import");
		importButton.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,12));
		importButton.setFocusPainted(false);
		importButton.addActionListener(actionListener);
		
		exportButton = new JButton("Export");
		exportButton.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,12));
		exportButton.setFocusPainted(false);
		exportButton.addActionListener(actionListener);

		controls.add(pointButton);
		controls.add(lineButton);
		controls.add(circleButton);
		controls.add(textButton);
		controls.add(colorButton);
		controls.add(hullButton);
		controls.add(enclosingCircleButton);
		controls.add(enclosingRectanlgeButton);
		controls.add(voronoiButton);
		controls.add(clearButton);
		controls.add(importButton);
		controls.add(exportButton);
		content.add(controls,BorderLayout.NORTH);
		frame.setSize(1600, 1000);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
